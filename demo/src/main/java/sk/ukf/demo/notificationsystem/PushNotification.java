package sk.ukf.demo.notificationsystem;

import org.springframework.stereotype.Component;


@Component("pushNotification")
public class PushNotification implements NotificationService {

    @Override
    public String send(String message) {
        return "Posielam notifikáciu Push: " + message;
    }
}
