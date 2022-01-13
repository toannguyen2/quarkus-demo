package com.redt.ddd.shop.domain.authentication.services.impl;

import com.redt.ddd.shop.domain.authentication.cache.SessionCache;
import com.redt.ddd.shop.domain.authentication.entities.UserSession;
import com.redt.ddd.shop.domain.authentication.event.UserSessionDomainEvent;
import com.redt.ddd.shop.domain.authentication.services.SessionService;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SessionServiceImpl implements SessionService {
	public static final String TOPIC_SESSION = "session";

	@Inject
	SessionCache sessionCache;

	@Inject
	EventBus bus;

	@ConsumeEvent(value = TOPIC_SESSION)
	public void consumeCacheSession(Message msg) {
		List<UserSessionDomainEvent.UserSessionEvent> events = (List<UserSessionDomainEvent.UserSessionEvent>) msg.body();

		List<UserSession> userSessionsNeedSave = new ArrayList<>();
		List<UserSession> userSessionsNeedDel = new ArrayList<>();

		for (UserSessionDomainEvent.UserSessionEvent event : events) {
			if (UserSessionDomainEvent.UserSessionEvent.TYPE.CREATE.equals(event.getType()))
				userSessionsNeedSave.add(event.getUserSession());
			else if (UserSessionDomainEvent.UserSessionEvent.TYPE.REMOVE.equals(event.getType()))
				userSessionsNeedDel.add(event.getUserSession());
		}

		UserSession.persist(userSessionsNeedSave).subscribeAsCompletionStage();
		UserSession.persist(userSessionsNeedDel).subscribeAsCompletionStage();
	}

}
