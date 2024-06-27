package hello.login.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.LogRecord;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try{
            log.info("REQUEST [{}] [{}]", uuid, requestURI);
            filterChain.doFilter(request, servletResponse);
        }catch (Exception e){
            throw e;
        } finally {
            log.info("RESPONSE [{}] [{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}