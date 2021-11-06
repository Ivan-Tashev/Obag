package bg.obag.obag.service;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.custom.ProductsLogCount;
import bg.obag.obag.model.view.LogViewModel;

import java.util.List;

public interface LogService {
    void addLog(Long productId, String action) throws ProductNotFoundException;

    List<LogViewModel> findAllLogsByUser();

    List<ProductsLogCount> findAllLogsByProduct();
}
