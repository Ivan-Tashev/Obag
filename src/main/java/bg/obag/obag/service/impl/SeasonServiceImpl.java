package bg.obag.obag.service.impl;

import bg.obag.obag.exception.SeasonNotFoundException;
import bg.obag.obag.model.entity.SeasonEntity;
import bg.obag.obag.model.service.SeasonServiceModel;
import bg.obag.obag.repo.SeasonRepo;
import bg.obag.obag.service.SeasonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeasonServiceImpl implements SeasonService {
    private final SeasonRepo seasonRepo;
    private final ModelMapper modelMapper;

    public SeasonServiceImpl(SeasonRepo seasonRepo, ModelMapper modelMapper) {
        this.seasonRepo = seasonRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeSeasons() {
        if (seasonRepo.count() == 0) {
            seasonRepo.saveAll(List.of(
                    new SeasonEntity().setSeason("CARRY OVER"),
                    new SeasonEntity().setSeason("SPRING'21"),
                    new SeasonEntity().setSeason("SUMMER'21"),
                    new SeasonEntity().setSeason("FALL'21"),
                    new SeasonEntity().setSeason("WINTER'21")
            ));
        }
    }

    @Override
    public SeasonServiceModel findBySeason(String season) throws SeasonNotFoundException {

        SeasonEntity seasonEntity = seasonRepo.findBySeason(season)
                .orElseThrow(() -> new SeasonNotFoundException("Season name " + season + " not found in database."));

        return modelMapper.map(seasonEntity, SeasonServiceModel.class);
    }

    @Override
    public List<SeasonServiceModel> findAll() {
        return seasonRepo.findAll().stream()
                .map(seasonEntity -> modelMapper.map(seasonEntity, SeasonServiceModel.class))
                .collect(Collectors.toList());
    }
}
