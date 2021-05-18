package app.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}") private String jwtSecret;

	public String generateJwtToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("login", user.getLogin());
		// claims.put("phone", user.getPhone()); // need ???
		return Jwts
				   .builder()
				   .setClaims(claims)
				   .signWith(SignatureAlgorithm.HS512, jwtSecret)
				   .compact();
	}

	public Boolean validateJwtTokenByLogin(String jwtToken, User user) {
		Claims claims = null;
		claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
		if (claims != null) {
			String login = (String) claims.get("login");
			if (login != null && login.equals(user.getLogin())) return true;
			else return false;
		} else {
			return false;
		}
	}

	public String getLoginUserFromJwtToken(String jwtToken) {
		return (String) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody().get("login");
	}
}
