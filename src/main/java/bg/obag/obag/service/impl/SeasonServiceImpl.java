package bg.obag.obag.service.impl;

import bg.obag.obag.exception.SeasonNotFoundException;
import bg.obag.obag.model.entity.SeasonEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.service.SeasonServiceModel;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.repo.SeasonRepo;
import bg.obag.obag.service.SeasonService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeasonServiceImpl implements SeasonService {
    private final SeasonRepo seasonRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public SeasonServiceImpl(SeasonRepo seasonRepo, UserService userService, ModelMapper modelMapper) {
        this.seasonRepo = seasonRepo;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeSeasons() {
        if (seasonRepo.count() == 0) {
            UserServiceModel userServiceModel = userService.findById(1L);
            UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);
            seasonRepo.saveAll(List.of(
                    new SeasonEntity().setSeason("CARRY OVER").setPriority(1).setDeleted(false).setCreatedBy(userEntity),
                    new SeasonEntity().setSeason("SPRING'21").setPriority(2).setDeleted(false).setCreatedBy(userEntity),
                    new SeasonEntity().setSeason("SUMMER'21").setPriority(3).setDeleted(false).setCreatedBy(userEntity),
                    new SeasonEntity().setSeason("FALL'21").setPriority(4).setDeleted(false).setCreatedBy(userEntity),
                    new SeasonEntity().setSeason("WINTER'21").setPriority(5).setDeleted(false).setCreatedBy(userEntity)
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
    public List<SeasonServiceModel> findAllOrderByPriorityAsc() {
        return seasonRepo.findAllByOrderByPriority().stream()
                .map(seasonEntity -> modelMapper.map(seasonEntity, SeasonServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public SeasonServiceModel addEditSeason(SeasonServiceModel seasonServiceModel, Principal principal) throws SeasonNotFoundException {
        SeasonEntity seasonEntity;
        if (seasonServiceModel.getId() == null) {
            seasonEntity = modelMapper.map(seasonServiceModel, SeasonEntity.class);
        } else {
            seasonEntity = seasonRepo.findById(seasonServiceModel.getId())
                    .orElseThrow(() -> new SeasonNotFoundException("Season with id " + seasonServiceModel.getId() + " not found."));
            seasonEntity.setSeason(seasonServiceModel.getSeason())
                    .setPriority(seasonServiceModel.getPriority())
                    .setImage(seasonServiceModel.getImage())
                    .setDeleted(seasonServiceModel.isDeleted());
        }
        seasonEntity.setCreatedBy(userService.findByEmail(principal.getName()).get());

        SeasonEntity savedSeasonEntity = seasonRepo.save(seasonEntity);

        return modelMapper.map(savedSeasonEntity, SeasonServiceModel.class);
    }

    @Override
    public SeasonServiceModel findById(Long id) throws SeasonNotFoundException {
        SeasonEntity seasonEntity = seasonRepo.findById(id)
                .orElseThrow(() -> new SeasonNotFoundException("Season with id " + id + " not found."));
        return modelMapper.map(seasonEntity, SeasonServiceModel.class)
                .setCreatedBy(seasonEntity.getCreatedBy().getEmail());
    }

    @Override
    public boolean existSeasonExceptId(String season, Long id) {
        return seasonRepo.existsBySeasonExceptId(season ,id).isPresent();
    }
}
