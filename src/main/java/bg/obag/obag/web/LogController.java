package bg.obag.obag.web;

import bg.obag.obag.model.custom.ProductsLogCount;
import bg.obag.obag.model.view.LogViewModel;
import bg.obag.obag.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
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


}
