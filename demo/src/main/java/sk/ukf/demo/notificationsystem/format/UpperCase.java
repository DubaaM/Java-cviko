package sk.ukf.demo.notificationsystem.format;

import org.springframework.stereotype.Component;

public class UpperCase {

    @Component("uppercase")
    public class upperCase implements MessageFormatter{
        @Override
        public String format(String message) {
            return message.toUpperCase();
        }
    }
}