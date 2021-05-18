package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.User;
import app.security.JwtRequest;
import app.security.JwtResponse;
import app.security.JwtTokenUtil;
import app.service.UserService;

@RestController
public class JwtAuthenticationController {
	@Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private UserService userService;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> signin(@RequestBody(required = true) JwtRequest authenticationRequest) throws Exception {
		String login = authenticationRequest.getLogin();
		String password = authenticationRequest.getPassword();

		Authentication authentication = null;
		try {
			authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
		final User user = userService.findByLogin(authenticationRequest.getLogin());
		String token = this.jwtTokenUtil.generateJwtToken(user);
		return ResponseEntity.ok().body(new JwtResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody(required = true) User user) {
		String token = this.jwtTokenUtil.generateJwtToken(userService.saveOrUpdate(user));
		return ResponseEntity.ok().body(new JwtResponse(token));
	}
}
