package hello.login.web.filter;

import hello.login.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/","/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        try{
            log.info("인증 체크 필터 시작{}",requestURI);

            if(isLoginCheckPath(requestURI)){
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = request.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
                    response.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            filterChain.doFilter(request, servletResponse);
        }catch (Exception e){
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }


    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
