import java.time.Clock;
import java.time.LocalDateTime;

public class SubscriptionService {
    private final Clock clock;

    // Constructor para inyectar el reloj (usado en tests y DI)
    public SubscriptionService(Clock clock) {
        this.clock = clock;
    }

    // Constructor por defecto para producción (usa el reloj real del sistema)
    public SubscriptionService() {
        this(Clock.systemDefaultZone());
    }

    public boolean isExpired(LocalDateTime expirationDate) {
        // En lugar de LocalDateTime.now(), usamos LocalDateTime.now(clock)
        LocalDateTime now = LocalDateTime.now(clock);
        return expirationDate.isBefore(now);
    }
}
