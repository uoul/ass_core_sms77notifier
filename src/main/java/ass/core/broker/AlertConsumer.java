package ass.core.broker;

import io.vertx.core.Vertx;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AlertConsumer {

    @Inject
    Vertx vertx;

    @Incoming("alerts_in")
    public void consume(String msg) {
        vertx.eventBus().publish("activeAlerts", msg);
    }
}
