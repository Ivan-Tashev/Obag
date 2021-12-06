package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.service.OrderServiceModel;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class MailServiceImpl implements MailService {
    private final Properties properties;
    private static final String EMAIL = "info@obag.bg";
    @Value("${mail.password}")
    private String MAIL_PASSWORD;
    private static final String PHONE = "0889.776694";


    public MailServiceImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void newOrderMail(OrderServiceModel orderServiceModel) throws MessagingException, UnsupportedEncodingException {
        Session session = getSession();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL, "O BAG"));

        final UserEntity user = orderServiceModel.getUser();

        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
        message.setSubject("поръчка #" + Long.toHexString(orderServiceModel.getId()).toUpperCase());

        String html = "<p>Здравейте " + user.getFirstName() + ",</p>" +
                "<p>Благодаря ви, че поръчахте от O bag.</p>" +

                "<h4>ПОРЪЧКА</h4>" +
                "<span>#" + Long.toHexString(orderServiceModel.getId()).toUpperCase() +
                " от дата " + orderServiceModel.getCreatedOn().format(DateTimeFormatter.ofPattern("dd.MMM.yy, HH:mm")) + "ч.</span>" +

                "<h4>ПЛАТЕЦ</h4>" +
                "<span>" + user.getFirstName() + " " + user.getLastName() + ", " +
                user.getAddress() + ", " + user.getCity() + ", " + user.getPostCode() + ", " + user.getCountry() + ", " +
                user.getPhone() + ", " + user.getEmail() + "</span>" +

                "<h4>ПРОДУКТИ</h4>" +
                "<hr>" +
                "<table><tbody>";

        html += orderServiceModel.getProducts().stream().map(product ->
                        "<tr>" +
                                "<td><img src=" + product.getImage() + " alt='image' width='80' height='80'></td>" +
                                "<td>" + product.getName() + "</td>" +
                                "<td>" + product.getQty() + "x</td>" +
                                "<td>" + product.getPrice() + "лв.</td>" +
                                "</tr>")
                .collect(Collectors.joining(""));


        html += "</tbody>" +
                "<tfoot><tr>" +
                "<td></td>" +
                "<td></td>" +
                "<td><h4>Общо:</h4></td>" +
                "<td><h4>" + orderServiceModel.getTotalValue() + "лв.</h4></td>" +
                "</tr></tfoot></table>" +
                "<hr>" +

                "<h4>ПЛАЩАНЕ</h4>" +
                "<span>" + "Наложен платеж" + "</span>" +

                "<h4>ДОСТАВКА</h4>" +
                "<span>" + "Speedy: СТАНДАРТ 24 ЧАСА" + "</span><br>" +
                "<p><small>Времето за доставка е средно от 1 или 2 работни дни от направата на поръчката, без да надвишава " +
                "3 работни дни. София и всички останали населени места - 1 или 2 работни дни (вкл. Събота).</small></p>" +

                "<p>За да разгледате тази поръчка във Вашия профил натиснете <a href='http://localhost:8080/orders'>тук</a>.</p>" +

                "<p>Желаем Ви приятен и усмихнат ден!</p>" +
                "<p>Екипът на O bag</p>" +
                "<p><small>Работно време, всеки ден 10:00-20:00</small><br>" +
                "<small>тел.: " + PHONE + ", e-mail: " + EMAIL + "</small></p>" +
                "<h1>O bag</h1>";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(html, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    @Override
    public void newUserRegistrationMail(UserServiceModel userServiceModel) throws UnsupportedEncodingException, MessagingException {
        Session session = getSession();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL, "O BAG"));

        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(userServiceModel.getEmail()));
        message.setSubject("Успешна регистрация");

        String html = "<p>Здравейте " + userServiceModel.getFirstName() + ",</p>" +
                "<h3>Благодарим Ви, че се регистрирахте!</h3>" +
                "<p>Детайли на вашият профил:</p>" +
                "<p>име: " + userServiceModel.getFirstName() + " " + userServiceModel.getLastName() + "</p>" +
                "<p>адрес: " + userServiceModel.getAddress() + ", " + userServiceModel.getCity() + ", "
                + userServiceModel.getPostCode() + ", " + userServiceModel.getCountry() + "</p>" +
                "<p>тел.: " + userServiceModel.getPhone() + "</p>" +
                "<p>e-mail: " + userServiceModel.getEmail() + "</p>" +

                "<p>Вашите лични данни (име, адрес, тел и e-mail) ще бъдат съхранявани съгласно действащата Политика " +
                "за Лични данни - Регламент (ЕС) 2016/679 („GDPR”)." +

                "<p>За да разгледате или редактирате Вашия профил натиснете <a href='http://localhost:8080/profile'>тук</a>.</p>" +

                "<p><small>За да премахнете Вашите лични данни, влезте във Вашия профил, и отмаркирайте " +
                "\"Съгласен съм с Политиката за лични данни\", това ще премахне Вашето име, адрес, тел и e-mail, като " +
                "ще запазим информация само за осъществените поръчки и продукти, НЕ представляващи лични данни. " +
                "Внимание! Операцията е необратима, и ще е необходима нова регистрация/профил.</small></p>" +

                "<p>Ако не сте Вие, или погрешно е подаден Вашият емейл адрес, моля свържете се с нас.</p>" +

                "<p>Желаем Ви приятен и усмихнат ден!</p>" +
                "<p>Екипът на O bag</p>" +
                "<p><small>Работно време, всеки ден 10:00-20:00</small><br>" +
                "<small>тел.: " + PHONE + ", e-mail: " + EMAIL + "</small></p>" +
                "<h1>O bag</h1>";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(html, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    private Session getSession() {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, MAIL_PASSWORD);
            }
        });
    }
}
