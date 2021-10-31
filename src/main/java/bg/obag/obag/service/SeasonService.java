package bg.obag.obag.service;

import bg.obag.obag.exception.SeasonNotFoundException;
import bg.obag.obag.model.service.SeasonServiceModel;

import java.util.List;

public interface SeasonService {
    void initializeSeasons();

    SeasonServiceModel findBySeason(String season) throws SeasonNotFoundException;

    List<SeasonServiceModel> findAll();
}
