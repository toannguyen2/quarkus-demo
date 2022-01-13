package com.redt.ddd.shop.domain.authentication.security;

import com.redt.ddd.shop.domain.authentication.security.credential.SessionCredential;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.arc.Priority;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Alternative
@Priority(1)
@ApplicationScoped
public class SessionAuthenticationMechanism implements HttpAuthenticationMechanism {
	private final String SESSION = "Session";
	private final String UID = "Uid";

	/** {@inheritDoc} */
	@Override
	public Uni<SecurityIdentity> authenticate(RoutingContext ctx, IdentityProviderManager identityProviderMgr) {
		String ses = ctx.request().headers().get(SESSION);
		String userId = ctx.request().headers().get(UID);

		if (!Objects.isNull(ses) && !Objects.isNull(userId)) {
			return identityProviderMgr
				.authenticate(new SessionAuthenticationRequest(new SessionCredential(ses, userId)));
		}
		return Uni.createFrom().optional(Optional.empty());
	}

	@Override
	public Uni<ChallengeData> getChallenge(RoutingContext context) {
		ChallengeData result = new ChallengeData(
			HttpResponseStatus.UNAUTHORIZED.code(),
			HttpHeaderNames.WWW_AUTHENTICATE,
			SESSION);
		return Uni.createFrom().item(result);
	}

	@Override
	public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
		return Collections.singleton(SessionAuthenticationRequest.class);
	}

	@Override
	public HttpCredentialTransport getCredentialTransport() {
		return new HttpCredentialTransport(HttpCredentialTransport.Type.OTHER_HEADER, SESSION);
	}
}
