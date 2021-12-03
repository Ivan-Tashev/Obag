package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.VisitEntity;
import bg.obag.obag.model.view.VisitViewModel;
import bg.obag.obag.repo.VisitRepo;
import bg.obag.obag.service.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {
    private final VisitRepo visitRepo;
    private final HashMap<String, Integer> sessionIds = new HashMap<>();
    private final ModelMapper modelMapper;

    public VisitServiceImpl(VisitRepo visitRepo, ModelMapper modelMapper) {
        this.visitRepo = visitRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void interceptRequest(String sessionId, String user, String resource) {
        sessionIds.putIfAbsent(sessionId, 0);
        sessionIds.put(sessionId, sessionIds.get(sessionId) + 1);
    }

    @Scheduled(cron = "59 59 23 * * ?") // every day, at 23:59:59
    @Override
    public void saveDailyVisits() {
        VisitEntity dailyVisitEntity = new VisitEntity()
                .setDate(LocalDate.now())
                .setUniqueVisits(sessionIds.size())
                .setTotalVisits(sessionIds.values().stream().mapToInt(e -> e).sum());

        visitRepo.save(dailyVisitEntity);

        this.sessionIds.clear();
    }

    @Override
    public List<VisitViewModel> findAll() {
        return visitRepo.findAll().stream()
                .map(visitEntity -> modelMapper.map(visitEntity, VisitViewModel.class))
                .collect(Collectors.toList());
    }
}
