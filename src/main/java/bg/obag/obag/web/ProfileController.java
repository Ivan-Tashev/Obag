package bg.obag.obag.web;

import bg.obag.obag.model.binding.UserUpdateBindModel;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController{
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    /* ------------------------------------ PROFILE ------------------------------------------------- */

    @GetMapping
    public String getUserProfilePage(Principal principal, Model model) {
        Long id = userService.findCurrentUserByEmail(principal.getName()).getId();
        UserServiceModel userServiceModel = userService.findById(id);
        model.addAttribute("userUpdateBindModel", modelMapper.map(userServiceModel, UserUpdateBindModel.class));
        return "profile";
    }

    @PostMapping()
    public String updateUserProfile(@Valid UserUpdateBindModel userUpdateBindModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                    Principal principal) {
        Long id = userService.findCurrentUserByEmail(principal.getName()).getId();
        // 1. CHECK FOR ENTRY/INPUT REQUIREMENTS AND @VALIDATIONS
        if (bindingResult.hasErrors() ||
                userService.existingEmailExceptId(userUpdateBindModel.getEmail(), id) != null) {
            redirectAttributes.addFlashAttribute("userUpdateBindModel", userUpdateBindModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userUpdateBindModel", bindingResult);
            // 2. CHECK FOR EXISTING EMAIl IN DB -> "Not existing email in DB."
            if (userService.existingEmailExceptId(userUpdateBindModel.getEmail(), id) != null) {
                redirectAttributes.addFlashAttribute("foundEmail", true);
            }
            return "redirect:/profile";
        }

        UserServiceModel userServiceModel = userService.updateUser(
                modelMapper.map(userUpdateBindModel, UserServiceModel.class), id);
        redirectAttributes.addFlashAttribute("successfullyUpdateProfile", true);
        return "redirect:/profile";
    }
}
