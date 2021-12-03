package bg.obag.obag.web.intercept;

import bg.obag.obag.service.VisitService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class VisitsInterceptor implements HandlerInterceptor {
    private final VisitService visitService;

    public VisitsInterceptor(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        visitService.interceptRequest(request.getRequestedSessionId(), request.getRemoteUser(), request.getRequestURI());

        return true;
    }
}
