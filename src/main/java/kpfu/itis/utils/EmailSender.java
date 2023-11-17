package kpfu.itis.utils;

import lombok.SneakyThrows;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender implements Runnable {

    private String to;
    private String subject;
    private String text;

    public EmailSender(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    @Override
    @SneakyThrows
    public void run() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("iluzagalieva0604@gmail.com", "lptr xfux jmjx jqmu");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("iluzagalieva0604@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);

        System.out.println("Email sent successfully.");
    }
}
