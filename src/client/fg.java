package client;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class fg {

    public static void main(String[] args) throws Exception {
    	sendMail("talyeho@gmail.com");
    }

    public static void sendMail(String recepient) throws Exception{
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "fghghf98@gmail.com";
        String password = "1234PERETZ";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message msg = prepareMessage(session, myAccountEmail, recepient);
        javax.mail.Transport.send(msg);
        System.out.println("Message Sent successfully");
    }

    private static Message prepareMessage(Session session, String myEmail, String recepient) {
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(myEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            msg.setSubject("Testing test.email");
            msg.setText("hmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm, I guess it worked");
            return msg;
        } catch (Exception e) {
            Logger.getLogger(fg.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}

