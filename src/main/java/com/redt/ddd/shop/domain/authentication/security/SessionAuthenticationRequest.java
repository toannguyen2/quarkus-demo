package com.redt.ddd.shop.domain.authentication.security;

import com.redt.ddd.shop.domain.authentication.security.credential.SessionCredential;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.identity.request.BaseAuthenticationRequest;
import lombok.Getter;

@Getter
public class SessionAuthenticationRequest extends BaseAuthenticationRequest implements AuthenticationRequest {
	private final SessionCredential session;

	public SessionAuthenticationRequest(SessionCredential session) {
		this.session = session;
	}
}
