package com.example.demo;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {

    private static final String PARAM_TOKEN = "token";

    @Around(value = "@annotation(com.example.demo.Token)")
    public Object executeBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Token annotation = ((MethodSignature) joinPoint.getSignature()).getMethod()
                .getAnnotation(Token.class);
        String tokenName = annotation.value();
        Object result = null;
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && args[i] instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) args[i];
                result = generateToken(joinPoint, request, tokenName);
            }
        }
        return result;
    }

    @Around(value = "@annotation(com.example.demo.RemoveToken)")
    public Object executeAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        RemoveToken annotation = ((MethodSignature) joinPoint.getSignature()).getMethod()
                .getAnnotation(RemoveToken.class);
        String tokenName = annotation.value();
        Object result = null;
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && args[i] instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) args[i];
                result = validateToken(joinPoint, request, tokenName);
            }
        }
        return result;
    }


    public Object generateToken(ProceedingJoinPoint joinPoint, HttpServletRequest request,
            String tokenName) throws Throwable {
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute(tokenName, token);
        return joinPoint.proceed();
    }

    public Object validateToken(ProceedingJoinPoint joinPoint, HttpServletRequest request,
            String tokenName){
        String token = request.getParameter(PARAM_TOKEN);
        String attribute = (String) request.getSession().getAttribute(tokenName);
        if (token.equals(attribute)) {
            request.getSession().removeAttribute(tokenName);
            Object object = null;
            try {
                object = joinPoint.proceed();
            } catch (Throwable throwable) {
                request.getSession().setAttribute(tokenName,token);
            }

            return object;
        }
        return "请不要重复提交表单";
    }

}
