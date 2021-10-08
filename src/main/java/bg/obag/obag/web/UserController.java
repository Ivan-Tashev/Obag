package bg.obag.obag.web;

import bg.obag.obag.model.binding.UserLoginBindingModel;
import bg.obag.obag.model.binding.UserRegisterBindingModel;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.security.CurrentUser;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, CurrentUser currentUser, ModelMapper modelMapper) {
        this.userService = userService;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid
                        @ModelAttribute UserLoginBindingModel userLoginBindingModel,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        // 1. CHECK FOR ENTRY/INPUT REQUIREMENTS AND @VALIDATIONS
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:login";
        }
        // 2. CHECK FOR EXISTING EMAIl IN DB -> "Not existing email in DB."
        if (userService.existingEmail(userLoginBindingModel.getEmail()) == null) {
            redirectAttributes.addFlashAttribute("notFound", true);
            return "redirect:login";
        }
        // save User in HttpSession or @Bean CurrentUser
        UserServiceModel loggedInUser = userService.findByEmailAndPassword(
                userLoginBindingModel.getEmail(), userLoginBindingModel.getPassword());
        // 3. CHECK FOR CORRECT LOGIN (username=pass) FROM DB -> "Not matching email and password." (from DB)
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("notMatch", true);
            return "redirect:login";
        }

        userService.loginUser(loggedInUser);

        return "redirect:/";
    }

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
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }
        // 2. CHECK FOR EXISTING EMAIl IN DB -> "Email already in use from a user."
        if (userService.existingEmail(userRegisterBindingModel.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("found", true);
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }
        // TODO: check for pass and rePass match
        // 3. CHECK FOR PASSWORDS MATCHING (pass==rePass) "Passwords don't match."
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getRePassword())) {
            redirectAttributes.addFlashAttribute("notMatch", true);
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }

        // map the Banding Model to Service Model and save into DB
        UserServiceModel userServiceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        userService.registerUser(userServiceModel);

        UserServiceModel registeredUserServiceModel = userService.findByEmailAndPassword(
                userServiceModel.getEmail(), userServiceModel.getPassword());
        // set the CurrentUser(for session) with just registered User.
        currentUser.setId(registeredUserServiceModel.getId())
                .setFirstName(registeredUserServiceModel.getFirstName())
                .setEmail(registeredUserServiceModel.getEmail())
                .setRole(registeredUserServiceModel.getRole());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logoutUser();
        return "redirect:login";
    }

}
