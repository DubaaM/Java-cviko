package sk.ukf.demo.notificationsystem;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sk.ukf.demo.notificationsystem.format.MessageFormatter;


@Component("smsNotification")
public class SmsNotification implements NotificationService {

    private final MessageFormatter messageFormatter;
    public SmsNotification(@Qualifier("html") MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }
    @Override
    public String send(String message){
        String formattedMessage = messageFormatter.format(message);
        return "Posielam notifikáciu SMS: " + message;
    }
}

