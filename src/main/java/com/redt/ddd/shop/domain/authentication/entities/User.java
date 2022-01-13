package com.redt.ddd.shop.domain.authentication.entities;

import io.micrometer.core.instrument.util.StringUtils;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.vertx.ext.auth.HashingStrategy;
import io.vertx.ext.auth.VertxContextPRNG;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends PanacheEntityBase implements Principal {
	private static final HashingStrategy strategy = HashingStrategy.load();
	private static final Long EPOCH = 641300000000L;
	private static final Long RAND = 10000000L;

	public static final String FIELD_ID = "id";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_EMAILS = "emails";
	public static final String FIELD_PHONES = "phones";
	public static final String FIELD_CREATED_AT = "createdAt";
	public static final String FIELD_UPDATED_AT = "updatedAt";

	@Id
	public String id;

	public String name;

	public String password;

	@OneToMany(cascade = ALL, mappedBy = "user")
	public List<UserEmail> emails;

	@OneToMany(cascade = ALL, mappedBy = "user")
	public List<UserPhone> phones;

	@Column(nullable = false)
	@CreationTimestamp
	public Timestamp createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	public Timestamp updatedAt;

	// Behavior
	public static String generateID() {
		return (Instant.now().toEpochMilli() - EPOCH) +
			String.valueOf((long) (Math.random() * RAND));
	}

	public void changePassword(String newPassword) {
		assert StringUtils.isBlank(newPassword) : "Password is required.";
		this.password = hashPassword(newPassword);
	}

//	public Future<User> signin() {
//		return userService.signin(this);
//	}
//
//	public Future<User> signup() {
//		this.password = hashPassword(this.password);
//
//		return Future.future(handle -> {
//			userService.signup(this);
//			handle.complete(this);
//		});
//	}

	public void setPassword(String password) {
		this.password = hashPassword(password);
	}

	public static String hashPassword(String password) {
		return strategy.hash(
			"pbkdf2",
			null,
			VertxContextPRNG.current().nextString(32),
			password
		);
	}

	public boolean verifyPassword(String passwordNeedVerify) {
		return strategy.verify(this.password, passwordNeedVerify);
	}

}
