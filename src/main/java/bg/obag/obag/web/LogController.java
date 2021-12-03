package bg.obag.obag.web;

import bg.obag.obag.model.custom.ProductsLogCount;
import bg.obag.obag.model.view.LogViewModel;
import bg.obag.obag.model.view.VisitViewModel;
import bg.obag.obag.service.LogService;
import bg.obag.obag.service.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller // used like REST Controller
@RequestMapping("/logs")
public class LogController {
    private final LogService logService;
    private final VisitService visitService;

    public LogController(LogService logService, VisitService visitService) {
        this.logService = logService;
        this.visitService = visitService;
    }

    @GetMapping
    public String getLogsPage() {
        return "logs";
    }

    @GetMapping("/products")
    @ResponseBody
    public ResponseEntity<List<ProductsLogCount>> getAllLogs() {
        List<ProductsLogCount> productsLogCounts = logService.findAllLogsByProduct();
        return ResponseEntity.ok().body(productsLogCounts);
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<List<LogViewModel>> getAllLogsByUser() {
        List<LogViewModel> logViewModels = logService.findAllLogsByUser();
        return ResponseEntity.ok().body(logViewModels);
    }

    @GetMapping("/views")
    @ResponseBody
    public ResponseEntity<List<VisitViewModel>> getVisits() {
        List<VisitViewModel> visitViewModels = visitService.findAll();
        return ResponseEntity.ok().body(visitViewModels);
    }
}
