package bg.obag.obag.service;

import bg.obag.obag.exception.ProductNotFoundException;
import org.aspectj.lang.Signature;

public interface LogService {
    void addLog(Long productId, String action) throws ProductNotFoundException;
}
