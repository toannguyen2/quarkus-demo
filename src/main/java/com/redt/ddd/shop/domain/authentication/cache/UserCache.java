package com.redt.ddd.shop.domain.authentication.cache;

import com.redt.ddd.shop.domain.authentication.entities.User;
import io.quarkus.cache.*;
import io.smallrye.mutiny.Uni;
import lombok.Getter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Objects;

@Getter
@Dependent
public class UserCache {
	@Inject
	@CacheName("users")
	Cache cache;

	@CacheResult(cacheName = "users")
	public Uni<User> findById(@CacheKey String id) {
		return User.findById(id);
	}

	@CacheResult(cacheName = "users")
	public Uni<User> findById(@CacheKey String id, User def) {
		return User.findById(id).onItem().transform(user -> {
			if (Objects.isNull(user))
				return def;
			return (User) user;
		});
	}

	@CacheResult(cacheName = "users")
	public Uni<User> insert(@CacheKey String id, User def) {
		return Uni.createFrom().item(def);
	}

	@CacheInvalidate(cacheName = "users")
	public void invalidate(@CacheKey String id) {
	}

	@CacheInvalidateAll(cacheName = "users")
	public void invalidateAll() {
	}
}
