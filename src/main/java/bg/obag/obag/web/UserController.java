package bg.obag.obag.web;

import bg.obag.obag.model.binding.UserLoginBindingModel;
import bg.obag.obag.model.binding.UserRegisterBindingModel;
import bg.obag.obag.model.service.UserServiceModel;
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

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
                           RedirectAttributes redirectAttributes) {
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

        return "redirect:/cart";
    }
}
