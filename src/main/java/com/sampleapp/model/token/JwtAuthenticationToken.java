package com.sampleapp.model.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;
    private String username, password;

    public JwtAuthenticationToken(Object principal, Object credentials, String token) {
        super(null, null);
        this.token = token;
		this.username = String.valueOf(principal);
		this.password = String.valueOf(credentials);

    }
    
    

    public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
