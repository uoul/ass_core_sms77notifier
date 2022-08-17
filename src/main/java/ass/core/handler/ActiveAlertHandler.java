package ass.core.handler;

import ass.core.Sms77Client;
import ass.core.businessobject.AlertMessages;
import io.quarkus.logging.Log;
import io.quarkus.vertx.ConsumeEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class ActiveAlertHandler {

    @Inject
    @RestClient
    Sms77Client sms77Client;

    @ConfigProperty(name = "sms77.apikey")
    String apiKey;

    @ConfigProperty(name = "sms77.group.full")
    String smsGroupFull;

    @ConfigProperty(name = "sms77.group.min")
    String smsGroupMin;

    @ConfigProperty(name = "sms77.sender")
    String smsSender;

    @ConsumeEvent("activeAlerts")
    public void handle(String msg) {
        AlertMessages alertMessages = AlertMessages.fromJson(msg);
        sendSmsMinInformation(alertMessages);
        sendSmsFullInformation(alertMessages);
    }

    void sendSmsMinInformation(AlertMessages alertMessages) {
        Response response = sms77Client.sendSms(apiKey, smsSender, smsGroupMin, "Alarm von LFK!");
        if (response.getStatus() == 200) {
            Log.info("Successfully sent sms request to " + smsGroupMin + "!");
        } else {
            Log.warn("Error sending sms request to " + smsGroupMin + " - HTTP Code: " + response.getStatus());
        }
    }

    void sendSmsFullInformation(AlertMessages alertMessages) {
        AtomicReference<String> smsText = new AtomicReference<>("Alarm VON LFK!\n");
        alertMessages.getAlertMessages().forEach(msg -> {
            smsText.set(smsText.get() + "\n");
            smsText.set(smsText.get() + "Nr.: " + msg.getOperationId() + "\n");
            smsText.set(smsText.get() + "Art: " + msg.getOperationName() + "\n");
            smsText.set(smsText.get() + "Anrufer: " + msg.getName() + " / " + msg.getCaller() + "\n");
            smsText.set(smsText.get() + "Ort: " + msg.getLocation() + "\n");
            smsText.set(smsText.get() + "Info: " + msg.getInfo() + "\n");
        });
        Response response = sms77Client.sendSms(apiKey, smsSender, smsGroupFull, smsText.get());
        if (response.getStatus() == 200) {
            Log.info("Successfully sent sms request to " + smsGroupFull + "!");
        } else {
            Log.warn("Error sending sms request to " + smsGroupFull + " - HTTP Code: " + response.getStatus());
        }
    }
}
