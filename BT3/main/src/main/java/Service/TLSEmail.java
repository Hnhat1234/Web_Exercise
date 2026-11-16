package Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TLSEmail {

    /**
       Outgoing Mail (SMTP) Server
       requires TLS or SSL: smtp.gmail.com (use authentication)
       Use Authentication: Yes
       Port for TLS/STARTTLS: 587
     */
    private static final Properties ENV = loadEnv();

    final String email = ENV.getProperty("MAIL_EMAIL", "");
    final String password = ENV.getProperty("MAIL_PASSWORD", "");

    final String host = "smtp.gmail.com";
    final int port = 587;

    private static Properties loadEnv() {
        Properties env = new Properties();
        try (InputStream in = TLSEmail.class.getClassLoader().getResourceAsStream(".env")) {
            if (in == null) {
                System.err.println("TLSEmail: .env not found on the classpath.");
            } else {
                env.load(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return env;
    }

    public boolean sendOTP(String to, String otp) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", String.valueOf(port));

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Account activation OTP");
            message.setText("Your OTP is: " + otp + "\n\nEnter it on the verification page to activate your account.");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
