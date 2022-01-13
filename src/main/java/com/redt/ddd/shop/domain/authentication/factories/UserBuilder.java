package com.redt.ddd.shop.domain.authentication.factories;

import com.redt.ddd.shop.domain.authentication.entities.User;
import com.redt.ddd.shop.domain.authentication.entities.UserEmail;
import com.redt.ddd.shop.domain.authentication.entities.UserPhone;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.redt.ddd.shop.domain.authentication.entities.UserPhone.patternPhone;

@Data
public class UserBuilder {
	private String id;
	private String name;
	private String password;
	private List<UserEmail> emails;
	private List<UserPhone> phones;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	private UserBuilder() {
	}

	public static UserBuilder bulder() {
		return new UserBuilder();
	}

	public UserBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public UserBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public UserBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public UserBuilder setEmails(List<UserEmail> emails) {
		this.emails = emails;
		return this;
	}

	public UserBuilder setPhones(List<UserPhone> phones) {
		this.phones = phones;
		return this;
	}

	public UserBuilder setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public UserBuilder setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	public UserBuilder addEmail(String email) {
		if (Objects.isNull(emails))
			this.emails = new ArrayList<>();

		UserEmail userEmail = new UserEmail();
		userEmail.setEmail(email);
		this.emails.add(userEmail);
		return this;
	}

	public UserBuilder addPhoneNumber(String phoneNumber) {
		if (Objects.isNull(phones))
			this.phones = new ArrayList<>();

		UserPhone userPhone = new UserPhone();
		Pattern pattern = Pattern.compile(patternPhone);
		Matcher matcher = pattern.matcher(phoneNumber);

		if (matcher.find()) {
			userPhone.setPrefix(matcher.group(1));
			userPhone.setPhone(matcher.group(2));
			this.phones.add(userPhone);
		}
		return this;
	}

	public User build() {
		User user = new User();
		if (Objects.isNull(this.id))
			this.id = User.generateID();

		user.setId(this.id);
		user.setName(this.name);
		user.setPassword(this.password);
		user.setEmails(this.emails);
		user.setPhones(this.phones);

		Timestamp now = Timestamp.from(Instant.now());
		user.setCreatedAt(now);
		user.setUpdatedAt(now);

		if (!Objects.isNull(this.emails)) this.emails.forEach(email -> email.setUser(user));
		if (!Objects.isNull(this.phones)) this.phones.forEach(phone -> phone.setUser(user));
		return user;
	}
}
