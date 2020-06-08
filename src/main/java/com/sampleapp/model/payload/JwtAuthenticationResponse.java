package com.sampleapp.model.payload;

import com.sampleapp.model.User;

public class JwtAuthenticationResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiryDuration;
    
    private User user;

    public JwtAuthenticationResponse(String accessToken, String refreshToken,Long expiryDuration, User user) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiryDuration = expiryDuration;
		this.user = user;
		tokenType = "Bearer ";
	}

	public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiryDuration = expiryDuration;
        tokenType = "Bearer ";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiryDuration() {
        return expiryDuration;
    }

    public void setExpiryDuration(Long expiryDuration) {
        this.expiryDuration = expiryDuration;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}
