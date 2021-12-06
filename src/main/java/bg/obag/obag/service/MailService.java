package bg.obag.obag.service;

import bg.obag.obag.model.service.OrderServiceModel;
import bg.obag.obag.model.service.UserServiceModel;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.UnsupportedEncodingException;

public interface MailService {

    void newOrderMail(OrderServiceModel orderServiceModel) throws MessagingException, UnsupportedEncodingException;

    void newUserRegistrationMail(UserServiceModel userServiceModel) throws UnsupportedEncodingException, MessagingException;
}
