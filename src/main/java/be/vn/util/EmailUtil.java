package be.vn.util;

import org.springframework.http.MediaType;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

/**
 * EmailUtil class
 *
 * @author TuanDV <tuandv@agrimedia.vn>
 */
public class EmailUtil {
    public static void sendEmail(String subject, String emailTo, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication("tmd.suport@gmail.com", "adarsjckmdiklqrc");
//                            return new PasswordAuthentication("tmd.suport@gmail.com", "qdhtdpvkajogrvni");
                            return new PasswordAuthentication("LupeSamiafax67@gmail.com", "koktlrmwzaczxptk");
                        }
                    });
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setHeader("Content-Type", MediaType.TEXT_HTML.toString());
            msg.setFrom(new InternetAddress("tmd.suport@gmail.com"));
            msg.setSubject(subject, StandardCharsets.UTF_8.name());
            msg.setText(body, StandardCharsets.UTF_8.name(), "html");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            Transport.send(msg);
//            final String fromEmail = "tmd.suport@gmail.com";
//            // Mat khai email cua ban
////            final String password = "Qqwer@1234";
//            final String password = "adarsjckmdiklqrc";
////            final String fromEmail = "tuandv1994@gmail.com";
////            // Mat khai email cua ban
////            final String password = "xwypbkchjufkujwf";
//            // dia chi email nguoi nhan
//            final String toEmail = "tuandv1994@gmail.com";
//
//            subject = "Buồn ơi là buồn";
//            body = "Mã xác thực của bạn là: 8865";
//
//            Properties props = new Properties();
//            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
//            props.put("mail.smtp.port", "587"); //TLS Port
//            props.put("mail.smtp.auth", "true"); //enable authentication
//            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//
//            Authenticator auth = new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(fromEmail, password);
//                }
//            };
//            Session session = Session.getInstance(props, auth);
//
//
//            MimeMessage msg = new MimeMessage(session);
//            //set message headers
//            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
//            msg.addHeader("format", "flowed");
//            msg.addHeader("Content-Transfer-Encoding", "8bit");
//
//            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
//
//            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
//
//            msg.setSubject(subject, "UTF-8");
//
//            msg.setText(body, "UTF-8");
//
//            msg.setSentDate(new Date());
//
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
//            Transport.send(msg);
//            System.out.println("Gui mail thanh cong");
        } catch (Exception ex) {
            LogUtil.error(ex);
        }
    }
}
