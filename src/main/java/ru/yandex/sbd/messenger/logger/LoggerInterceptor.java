package ru.yandex.sbd.messenger.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        log.info("[preHandle][" + request.getRequestURI() + "]" + "[Method: " + request.getMethod() + "]");
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {
        log.info("[postHandle][" + request.getRequestURI() + "]" + "[Method: " + request.getMethod() + "]" +
                "[Status: " + response.getStatus() + "][Executing time: " + getExecutionTime(request) + " ms]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("[afterCompletion][" + request.getRequestURI() + "]" + "[Method: " + request.getMethod() + "]" +
                "[Status: " + response.getStatus() + "][Executing time: " + getExecutionTime(request) + " ms]");
    }

    private long getExecutionTime(HttpServletRequest request) {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
