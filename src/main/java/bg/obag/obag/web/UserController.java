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
import org.springframework.validation.ObjectError;
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
    public String login(@Valid UserLoginBindingModel userLoginBindingModel,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:login";
        }
        // TODO: check for existing email and display error massage
        if (userService.existingEmail(userLoginBindingModel.getEmail())) {
            bindingResult.addError(new ObjectError("email", "Този емейл не съществува в базата."));
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:login";
        }
        // TODO: display message email and password don't match.
        UserServiceModel loggedInUser = userService.findByEmailAndPassword(
                userLoginBindingModel.getEmail(), userLoginBindingModel.getPassword());
        currentUser.setId(loggedInUser.getId())
                .setFirstName(loggedInUser.getFirstName())
                .setEmail(loggedInUser.getEmail())
                .setRole(loggedInUser.getRole());
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
        // check for entry/input requirements and errors
        if (bindingResult.hasErrors() ||
                !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getRePassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }
        // TODO; check for existing Email(user)
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
        return "redirect:/";
    }

}
