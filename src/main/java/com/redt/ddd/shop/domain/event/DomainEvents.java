package com.redt.ddd.shop.domain.event;

import com.redt.ddd.shop.domain.authentication.event.UserDomainEvent;
import com.redt.ddd.shop.domain.authentication.event.UserSessionDomainEvent;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public final class DomainEvents {
	public static DomainEvent<UserDomainEvent.UserEvent> User;
	public static DomainEvent<UserSessionDomainEvent.UserSessionEvent> UserSession;

	@Inject
	public DomainEvents(
		DomainEvent<UserDomainEvent.UserEvent> userE,
		DomainEvent<UserSessionDomainEvent.UserSessionEvent> userSessionE
	) {
		DomainEvents.User = userE;
		DomainEvents.UserSession = userSessionE;
	}

	void start(@Observes StartupEvent evt) {
	}
}
