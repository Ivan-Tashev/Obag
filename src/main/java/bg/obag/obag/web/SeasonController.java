package bg.obag.obag.web;

import bg.obag.obag.exception.SeasonNotFoundException;
import bg.obag.obag.model.binding.SeasonBindModel;
import bg.obag.obag.model.service.SeasonServiceModel;
import bg.obag.obag.service.SeasonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/season")
public class SeasonController {
    private final SeasonService seasonService;
    private final ModelMapper modelMapper;

    public SeasonController(SeasonService seasonService, ModelMapper modelMapper) {
        this.seasonService = seasonService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("seasonBindModel")
    SeasonBindModel seasonBindModel() {
        return new SeasonBindModel();
    }

    @GetMapping("/add")
    public String getAddSeasonPage(Model model) {
        model.addAttribute("allSeasons", seasonService.findAllOrderByPriorityAsc());
        return "addSeason";
    }

    @PostMapping("/add")
    public String addSeason(@Valid SeasonBindModel seasonBindModel,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes,
                            Principal principal) throws SeasonNotFoundException {
        if (bindingResult.hasErrors()
                || seasonService.existSeasonExceptId(seasonBindModel.getSeason(), seasonBindModel.getId())
                || (seasonService.existBySeason(seasonBindModel.getSeason())) && seasonBindModel.getId() == null) {
            redirectAttributes.addFlashAttribute("seasonBindModel", seasonBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.seasonBindModel", bindingResult);
            if (seasonService.existSeasonExceptId(seasonBindModel.getSeason(), seasonBindModel.getId())
                    || (seasonService.existBySeason(seasonBindModel.getSeason()) && seasonBindModel.getId() == null)) {
                redirectAttributes.addFlashAttribute("seasonExist", true);
            }
            return "redirect:/season/add";
        }

        seasonService.addEditSeason(modelMapper.map(seasonBindModel, SeasonServiceModel.class), principal);
        redirectAttributes.addFlashAttribute("successfullyAddedSeason", true);
        return "redirect:/season/add";
    }

    @GetMapping("/edit/{id}")
    public String getEditSeasonPage(@PathVariable Long id, Model model) throws SeasonNotFoundException {
        model.addAttribute("seasonBindModel", seasonService.findById(id))
                .addAttribute("allSeasons", seasonService.findAllOrderByPriorityAsc());
        return "addSeason";
    }
}
