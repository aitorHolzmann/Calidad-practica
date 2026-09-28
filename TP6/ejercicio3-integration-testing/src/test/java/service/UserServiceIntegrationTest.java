package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.JpaUserRepository;
import repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test (Khorikov): PostgreSQL es una "managed dependency" ->
 * se usa una instancia REAL (Testcontainers), nunca se mockea, y se
 * verifica el ESTADO final persistido, no interacciones.
 *
 * No hay dependencias "unmanaged" en este escenario (no hay bus de
 * mensajes, SMTP, ni APIs de terceros), así que no hace falta ningún mock.
 */
@Testcontainers
class UserServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("users_test")
                    .withUsername("test")
                    .withPassword("test");

    static EntityManagerFactory emf;

    EntityManager em;
    UserRepository repository;
    UserService service;

    @BeforeAll
    static void setUpFactory() {
        // Acá se inyectan al persistence.xml los datos de conexión reales
        // del contenedor, que Testcontainers resuelve en runtime.
        Map<String, String> overrides = new HashMap<>();
        overrides.put("jakarta.persistence.jdbc.url", postgres.getJdbcUrl());
        overrides.put("jakarta.persistence.jdbc.user", postgres.getUsername());
        overrides.put("jakarta.persistence.jdbc.password", postgres.getPassword());
        overrides.put("jakarta.persistence.jdbc.driver", postgres.getDriverClassName());

        emf = Persistence.createEntityManagerFactory("testPU", overrides);
    }

    @AfterAll
    static void closeFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        repository = new JpaUserRepository(em);
        service = new UserService(repository);

        // Limpiar entre tests: truncar/borrar filas, NO recrear el schema
        // (ver slide "Testing de Base de Datos — Guía Práctica").
        em.getTransaction().begin();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        if (em.isOpen()) {
            em.close();
        }
    }

    // ---------------------------------------------------------------
    // HAPPY PATH
    // ---------------------------------------------------------------

    @Test
    void register_emailValido_persisteUsuarioActivoEnLaDbReal() {
        // Act
        service.register("ana@test.com");

        // Assert — estado final en PostgreSQL real
        User saved = repository.findByEmail("ana@test.com");

        assertNotNull(saved, "El usuario debe existir realmente en la base");
        assertNotNull(saved.getId(), "El ID lo tiene que generar la DB (IDENTITY)");
        assertEquals("ana@test.com", saved.getEmail());
        assertTrue(saved.isActive(), "register() debe dejar el usuario activo");
    }

    // ---------------------------------------------------------------
    // EDGE CASE 1: constraint de unicidad — esto NO se puede probar
    // con un unit test puro, porque depende de que la DB real rechace
    // el insert duplicado.
    // ---------------------------------------------------------------

    @Test
    void register_emailDuplicado_laDbRechazaElSegundoInsertYNoQuedaDuplicado() {
        // Arrange
        service.register("dup@test.com");

        // Act + Assert — la UNIQUE constraint de PostgreSQL debe frenarlo
        assertThrows(PersistenceException.class,
                () -> service.register("dup@test.com"),
                "La constraint UNIQUE sobre email debe rechazar el segundo insert");

        // Assert — estado final: sigue habiendo una sola fila, no dos
        em.clear();
        Long cantidad = em.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", "dup@test.com")
                .getSingleResult();

        assertEquals(1L, cantidad, "El intento fallido no debe dejar una segunda fila");
    }

    // ---------------------------------------------------------------
    // EDGE CASE 2: búsqueda de un email que no existe.
    // Importante para integration testing: confirmar que la query real
    // (JPQL -> SQL) no lanza excepción con 0 resultados, sino que la
    // implementación la traduce correctamente a null.
    // ---------------------------------------------------------------

    @Test
    void findByEmail_emailInexistente_retornaNullSinLanzarExcepcion() {
        User result = repository.findByEmail("no-existe@test.com");

        assertNull(result, "Debe devolver null, no lanzar NoResultException hacia afuera");
    }
}
