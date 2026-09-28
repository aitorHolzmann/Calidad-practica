# Ejercicio 3 — Integration Testing con DB real (Testcontainers)

## Requisitos para correrlo
- JDK 21 (el devcontainer usa el mismo tag que `junit5-practico`, así evitás
  bajar una imagen nueva si ya la tenés)
- Maven
- **Docker corriendo** en la máquina (Testcontainers lo necesita para levantar el
  contenedor de PostgreSQL). Sin Docker activo, el test no puede arrancar.

## Con Dev Containers (recomendado)
El repo trae `.devcontainer/devcontainer.json` (Java 17 + Maven ya instalados).
Abrir la carpeta en VS Code → "Reopen in Container".

Importante: usa la feature `docker-outside-of-docker`, que monta el socket
del Docker del **host** dentro del devcontainer. Esto significa:
- Necesitás Docker corriendo en el host (CachyOS) antes de abrir el devcontainer,
  igual que para cualquier Dev Container.
- Los contenedores que arranca Testcontainers (el Postgres) quedan como
  contenedores "hermanos" del devcontainer, no anidados — no hace falta
  Docker-in-Docker ni nada más pesado.

## Cómo correr los tests
```bash
mvn test
```

La primera corrida descarga la imagen `postgres:15-alpine`, así que puede tardar
un poco más.

## Estructura
```
src/main/java/model/User.java              -> entidad JPA
src/main/java/repository/UserRepository.java-> interfaz (dada por el enunciado)
src/main/java/repository/JpaUserRepository.java -> implementación JPA/Hibernate
src/main/java/service/UserService.java      -> lógica de negocio (dada por el enunciado)
src/main/resources/META-INF/persistence.xml -> unidad de persistencia JPA
src/test/java/service/UserServiceIntegrationTest.java -> integration test
```

## Decisiones de diseño (para la entrega)
- **PostgreSQL es la única dependencia externa** del escenario y es *managed*
  (la aplicación tiene control total sobre su propia base) → se usa una
  instancia real vía Testcontainers, nunca un mock. No hay dependencias
  *unmanaged* (bus de mensajes, SMTP, APIs de terceros) en este ejercicio,
  por eso no aparece ningún mock en el test.
- Las aserciones son **state-based**: se relee el estado desde la base real
  (`findByEmail`, `COUNT(*)`) en lugar de verificar llamadas a métodos.
- `hibernate.hbm2ddl.auto=create-drop` se usa solo para que el ejercicio sea
  autocontenido. En un proyecto real el schema se versiona con
  Flyway/Liquibase (ver slide "Testing de Base de Datos — Guía Práctica").
- Limpieza entre tests: se borran filas (`DELETE`), no se recrea el schema
  completo, tal como recomienda la clase.
