package app.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired UserRepository repository;
	@Autowired PasswordEncoder passwordEncoder;

	@Autowired
    public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public app.model.User findByLogin(String login) {
		return this.repository.findByLogin(login);
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		app.model.User user = this.repository.findByLogin(username);
		if (user != null) {
			return User
						.withUsername(user.getLogin())
						.password(this.passwordEncoder.encode(user.getPassword()))
						.accountExpired(false)
						.accountLocked(false)
						.disabled(false)
						.credentialsExpired(false)
						.roles("USER")
						.build();
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public app.model.User saveOrUpdate(app.model.User user) {
		return this.repository.save(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	//@Secured("ROLE_USER")
	public String getFotoByLogin(String login) {
		return this.repository.findFotoByLogin(login);
	}

	//@Secured("ROLE_USER")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<app.model.User> findAllUsers() {
		return this.repository.findAll();
	}

	// @Override
	// @Secured("ROLE_ADMIN")
	// public void deleteById(Long id) {
	// 	this.repository.deleteById(id);
	// }
}
