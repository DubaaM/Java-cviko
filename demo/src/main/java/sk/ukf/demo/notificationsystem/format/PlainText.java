package sk.ukf.demo.notificationsystem.format;

import org.springframework.stereotype.Component;

public class PlainText {

    @Component("plaintext")
    public class plainText implements MessageFormatter{
        @Override
        public String format(String message) {
            return message;
        }
    }
}
