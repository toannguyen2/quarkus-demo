package com.redt.ddd.shop.domain.authentication.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_phones")
@EqualsAndHashCode(callSuper = true)
public class UserPhone extends PanacheEntityBase {
	public static final String patternPhone = "^(\\+?84|0)([3|5|7|8|9][0-9]{8})$";

	@Id
	private String phone;
	private String iso;
	private String prefix;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
}
