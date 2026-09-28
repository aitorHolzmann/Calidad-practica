import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionServiceTest {

    private SubscriptionService subscriptionService;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        // Fijamos el tiempo en una fecha/hora fija: 2026-09-12 T 12:00:00
        Instant fixedInstant = Instant.parse("2026-09-12T12:00:00Z");
        ZoneId zoneId = ZoneId.of("UTC");
        Clock fixedClock = Clock.fixed(fixedInstant, zoneId);

        // Instanciamos el SUT inyectando el reloj congelado
        subscriptionService = new SubscriptionService(fixedClock);
        now = LocalDateTime.now(fixedClock);
    }

    @Test
    void isExpired_FechaPasada_DevuelveTrue() {
        // Arrange
        LocalDateTime pastDate = now.minusDays(1); // 1 día antes

        // Act
        boolean result = subscriptionService.isExpired(pastDate);

        // Assert
        assertTrue(result);
    }

    @Test
    void isExpired_FechaFutura_DevuelveFalse() {
        // Arrange
        LocalDateTime futureDate = now.plusDays(1); // 1 día después

        // Act
        boolean result = subscriptionService.isExpired(futureDate);

        // Assert
        assertFalse(result);
    }

    @Test
    void isExpired_FechaExactaIgualAhora_DevuelveFalse() {
        // Arrange
        LocalDateTime exactSameDate = now; // Mismo instante exacto

        // Act
        boolean result = subscriptionService.isExpired(exactSameDate);

        // Assert
        // Retorna false porque expirationDate.isBefore(now) requiere que sea estrictamente menor
        assertFalse(result);
    }
}