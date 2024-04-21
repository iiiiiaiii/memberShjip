package project.newmembership.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import project.newmembership.session.SessionConst;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String RequestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행{}", RequestURI);
        HttpSession session = request.getSession();
        String sessionUsername = (String) session.getAttribute("username");
        String pathUsername = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/api/user/login?redirectURL" + RequestURI);
            return false;
        } else if (sessionUsername != null && !sessionUsername.equals(pathUsername)) {
            response.sendRedirect("/"+sessionUsername);
            return false;
        }
        return true;
    }
}
