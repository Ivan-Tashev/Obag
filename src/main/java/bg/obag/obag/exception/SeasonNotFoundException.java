package bg.obag.obag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Client request error, no such season.")
public class SeasonNotFoundException extends Exception  {
    public SeasonNotFoundException(String message) {
        super(message);
    }
}
