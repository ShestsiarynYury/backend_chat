package app.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtRequest {
	@JsonProperty(value = "login")
	private String login;
	@JsonProperty(value = "password")
    private String password;
    
    // need default constructor for JSON Parsing
	public JwtRequest() {

	}

	public JwtRequest(String login, String password) {
		this.setLogin(login);
		this.setPassword(password);
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
    
    public String getLogin() {
		return this.login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	@Override
	public String toString() {
		return "{" +
			" login='" + this.login + "'" +
			", password='" + this.password + "'" +
			"}";
	}
}
