package app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.model.User;
import app.security.JwtTokenUtil;
import app.service.UserService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired private UserService userService;
    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Override
	protected void doFilterInternal(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                            FilterChain chain) throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String login = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null) {
			try {
				jwtToken = requestTokenHeader.substring(0);
				login = jwtTokenUtil.getLoginUserFromJwtToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("not token");
		}
		// Once we get the token validate it. // когда клиент шлет нам хуевый токен
		if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			User user = this.userService.findByLogin(login);

			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateJwtTokenByLogin(jwtToken, user)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}
