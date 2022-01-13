package com.redt.ddd.shop.domain.authentication.cache;

import com.redt.ddd.shop.domain.authentication.entities.UserSession;
import io.quarkus.cache.*;
import io.smallrye.mutiny.Uni;
import lombok.Getter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Objects;

@Getter
@Dependent
public class SessionCache {
	@Inject
	@CacheName("sessions")
	Cache cache;

	@CacheResult(cacheName = "sessions")
	public Uni<UserSession> findById(@CacheKey String id) {
		return UserSession.findById(id);
	}

	@CacheResult(cacheName = "sessions")
	public Uni<UserSession> findById(@CacheKey String id, UserSession def) {
		return UserSession.findById(id).onItem().transform(userSession -> {
			if (Objects.isNull(userSession)) return def;
			return (UserSession) userSession;
		});
	}

	@CacheResult(cacheName = "sessions")
	public Uni<UserSession> insert(@CacheKey String id, UserSession def) {
		return Uni.createFrom().item(def);
	}

	@CacheInvalidate(cacheName = "sessions")
	public void invalidate(@CacheKey String id) {
	}

	@CacheInvalidateAll(cacheName = "sessions")
	public void invalidateAll() {
	}
}
