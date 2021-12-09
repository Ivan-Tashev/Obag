package bg.obag.obag.web;

import bg.obag.obag.model.binding.UserLoginBindingModel;
import bg.obag.obag.model.binding.UserRegisterBindingModel;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.service.MailService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final MailService mailService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, MailService mailService, ModelMapper modelMapper) {
        this.userService = userService;
        this.mailService = mailService;
        this.modelMapper = modelMapper;
    }

    /* ------------------------------------- LOG-IN ------------------------------------------------- */

    @ModelAttribute("userLoginBindingModel")
    UserLoginBindingModel userLoginBindingModel() {
        return new UserLoginBindingModel();
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login-error")
    public String loginError(UserLoginBindingModel userLoginBindingModel,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("bad_credentials", true)
                .addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
        return "redirect:/users/login";
    }

    /* ------------------------------------ REGISTER ------------------------------------------------- */

    @GetMapping("/register")
    public String getRegister(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid
                           @ModelAttribute UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws MessagingException, UnsupportedEncodingException {
        // 1. CHECK FOR ENTRY/INPUT REQUIREMENTS AND @VALIDATIONS
        if (bindingResult.hasErrors()
                || userService.existingEmail(userRegisterBindingModel.getEmail()) != null
                || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getRePassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            // 2. CHECK FOR EXISTING EMAIl IN DB -> "Email already in use from a user."
            if (userService.existingEmail(userRegisterBindingModel.getEmail()) != null) {
                redirectAttributes.addFlashAttribute("found", true);
            }
            // 3. CHECK FOR PASSWORDS MATCHING (pass==rePass) "Passwords don't match."
            if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getRePassword())) {
                redirectAttributes.addFlashAttribute("notMatch", true);
            }
            return "redirect:/users/register";
        }

        // map the Banding Model to Service Model and save into DB
        UserServiceModel userServiceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);

        userService.registerUser(userServiceModel);

        mailService.newUserRegistrationMail(userServiceModel); // send registration email to customer

        return "redirect:/cart";
    }
}
