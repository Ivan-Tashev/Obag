package bg.obag.obag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Client request error, no such product.")
public class ProductNotFoundException extends Exception  {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
