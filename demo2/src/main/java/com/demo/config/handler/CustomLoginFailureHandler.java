/*// 로그인 정비중
 * package com.demo.config.handler;
 * 
 * import java.io.IOException;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.authentication.BadCredentialsException; import
 * org.springframework.security.core.AuthenticationException; import
 * org.springframework.security.web.authentication.AuthenticationFailureHandler;
 * 
 * import com.demo.domain.user.User; import com.demo.domain.user.UserRepo;
 * 
 * import jakarta.servlet.ServletException; import
 * jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse; import
 * jakarta.transaction.Transactional; import lombok.extern.slf4j.Slf4j;
 * 
 * @Slf4j public class CustomLoginFailureHandler implements
 * AuthenticationFailureHandler {
 * 
 * private static final int MAX_ATTEMPT = 5; private static final int LOCK_TIME
 * = (1000 * 60) * 180; // 3h
 * 
 * @Autowired private UserRepo userRepo;
 * 
 * @Transactional
 * 
 * @Override public void onAuthenticationFailure(HttpServletRequest req,
 * HttpServletResponse res, AuthenticationException exception) throws
 * IOException, ServletException {
 * 
 * String username = req.getParameter("username"); String password =
 * req.getParameter("password"); String error = exception.getMessage();
 * log.info("values {}, {}, {}, {}", username, password, exception,
 * exception.getClass());
 * 
 * if (exception.getClass() == BadCredentialsException.class) { int errorCode =
 * badCredential(username); } }
 * 
 * private int badCredential(String username) throws IOException { User user =
 * userRepo.findById(username); }
 * 
 * }
 */