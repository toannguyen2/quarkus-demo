package com.redt.ddd.shop.domain.authentication.factories;

import com.redt.ddd.shop.domain.authentication.entities.UserSession;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Data
public class SessionBuilder {
	private String id;
	private String userId;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	private SessionBuilder() {
	}

	public static SessionBuilder bulder() {
		return new SessionBuilder();
	}

	public SessionBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public SessionBuilder setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public SessionBuilder setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public SessionBuilder setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	public UserSession build() {
		UserSession ses = new UserSession();
		if (Objects.isNull(this.id))
			ses.generateSession();
		else
			ses.setId(this.id);

		ses.setUserId(this.userId);

		Timestamp now = Timestamp.from(Instant.now());
		ses.setCreatedAt(now);
		ses.setUpdatedAt(now);
		return ses;
	}
}
