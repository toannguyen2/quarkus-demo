package com.redt.ddd.shop.domain.authentication.entities;

import com.redt.ddd.shop.infrastructure.support.Str;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_sessions")
@EqualsAndHashCode(callSuper = true)
public class UserSession extends PanacheEntityBase {
	@Id
	public String id;

	private String userId;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;

	// Behavior
	public void generateSession() {
		this.id = Str.randomLower(60);
	}
}
