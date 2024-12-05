package com.alumniportal.unmsm.util;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyCustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {

        boolean isAppHealthy = checkSomeCondition();

        if (isAppHealthy) {
            return Health.up().withDetail("customStatus", "All systems go").build();
        } else {
            return Health.down().withDetail("customStatus", "Some problem").build();
        }
    }

    private boolean checkSomeCondition() {
        // Lógica personalizada de salud (por ejemplo, verificar base de datos, servicios externos, etc.)
        return true; // Cambiar según la lógica
    }
}
