package hello.login.web.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *  직접 세션 만들기
 *
 *  1. 세션 관리
 */
@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new HashMap<>();

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response) {
        //세션 id를 생성하고, 값을 세션에 저장
        String sessionid = UUID.randomUUID().toString();
        sessionStore.put(sessionid, value);

        //쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionid);
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie==null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }


    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie!=null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }


}
