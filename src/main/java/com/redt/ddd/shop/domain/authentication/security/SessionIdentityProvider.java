package com.redt.ddd.shop.domain.authentication.security;

import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class SessionIdentityProvider implements IdentityProvider<SessionAuthenticationRequest> {
	private final boolean blockingAuthentication;

	public SessionIdentityProvider() {
		this.blockingAuthentication = false;
	}

	@Override
	public Class<SessionAuthenticationRequest> getRequestType() {
		return SessionAuthenticationRequest.class;
	}

	@Override
	public Uni<SecurityIdentity> authenticate(SessionAuthenticationRequest req, AuthenticationRequestContext ctx) {
		if (!blockingAuthentication) {
			return Uni.createFrom().emitter(uniEmitter -> {
				try {
					uniEmitter.complete(createSecurityIdentity(req));
				} catch (AuthenticationFailedException e) {
					uniEmitter.fail(e);
				}
			});
		} else
			return ctx.runBlocking(() -> createSecurityIdentity(req));
	}

	private SecurityIdentity createSecurityIdentity(SessionAuthenticationRequest req) {
		String ses = req.getSession().getSession();
		String uid = req.getSession().getUserId();

//			JsonWebToken jwtPrincipal = parser.parse(req.getToken().getToken());
		return QuarkusSecurityIdentity.builder().setPrincipal(null)
			.addCredential(req.getSession())
			.build();
	}
}
