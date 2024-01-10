package olymp.mental_arithmetic.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String redirectPage;
        exception.printStackTrace();
        if(exception instanceof BadCredentialsException){
            redirectPage = "/error/auth-error?error=0";
        } else if (exception instanceof DisabledException) {
            redirectPage = "/error/auth-error?error=1";
        } else if (exception instanceof UsernameNotFoundException){
            redirectPage = "/error/auth-error?error=2";
        }
        else {
            redirectPage = "/error";
        }
        response.sendRedirect(redirectPage);
    }
}
