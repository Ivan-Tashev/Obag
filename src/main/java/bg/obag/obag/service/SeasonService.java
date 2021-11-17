package bg.obag.obag.service;

import bg.obag.obag.exception.SeasonNotFoundException;
import bg.obag.obag.model.binding.SeasonBindModel;
import bg.obag.obag.model.service.SeasonServiceModel;

import java.security.Principal;
import java.util.List;

public interface SeasonService {
    void initializeSeasons();

    SeasonServiceModel findBySeason(String season) throws SeasonNotFoundException;

    List<SeasonServiceModel> findAllOrderByPriorityAsc();

    SeasonServiceModel addEditSeason(SeasonServiceModel seasonServiceModel, Principal principal) throws SeasonNotFoundException;

    SeasonServiceModel findById(Long id) throws SeasonNotFoundException;

    boolean existSeasonExceptId(String season, Long id);
}
