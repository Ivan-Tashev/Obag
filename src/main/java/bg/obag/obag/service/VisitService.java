package bg.obag.obag.service;

import bg.obag.obag.model.view.VisitViewModel;

import java.util.List;

public interface VisitService {

    void interceptRequest(String sessionId, String user, String resource);

    void saveDailyVisits();

    List<VisitViewModel> findAll();
}
