/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.util;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import javax.swing.JFileChooser;

/**
 *
 * @author truong
 */
public class Email {

    public static void sendEmail(String content, String text, String title) {
        try {
            String user = "truongnvps24083@fpt.edu.vn";
            String pass = "dkfkilftpeszjupq";
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });
            // ------------
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(text.trim()));
            message.setSubject(title);
            message.setText(content);
            Transport.send(message);
        } catch (Exception e) {

        }
    }

    public static void sendEmailStore(String content, List<Object[]> email, String title, String files) {
        try {
            String user = "truongnvps24083@fpt.edu.vn";
            String pass = "dkfkilftpeszjupq";
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });
            // ------------
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setSubject(title);
            message.setText(content);

            Valid vl = new Valid();
            for (Object[] obj : email) {
                String x = (String)obj[0];
                if(x.matches(vl.reEmail())){
                message.addRecipients(Message.RecipientType.CC,
                        InternetAddress.parse((String)obj[0]));
                }
            }

            MimeMultipart muti = new MimeMultipart();

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html; Charset=utf-8");
            muti.addBodyPart(bodyPart);

            if (!files.equals("")) {
                MimeBodyPart filepath = new MimeBodyPart();
                File file = new File(files);
                FileDataSource fileData = new FileDataSource(file);
                filepath.setDataHandler(new DataHandler(fileData));
                muti.addBodyPart(filepath);
            }
            message.setContent(muti);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//

    public static void main(String[] args) {
        sendEmail("Hello", "doanhuynhduycuong1601gasdvsaas@gmail.com", "dsds");
    }
}
