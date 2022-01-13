package com.redt.ddd.shop.domain.authentication.security.credential;

import io.quarkus.security.credential.Credential;
import lombok.Getter;

@Getter
public class SessionCredential implements Credential {
	private final String session;
	private final String userId;

	public SessionCredential(String ses, String userId) {
		this.session = ses;
		this.userId = userId;
	}
}
