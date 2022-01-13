package com.redt.ddd.shop.domain.authentication.event;

import com.redt.ddd.shop.domain.authentication.entities.UserSession;
import com.redt.ddd.shop.domain.event.DomainEvent;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.vertx.core.eventbus.EventBus;
import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import static com.redt.ddd.shop.domain.authentication.services.impl.SessionServiceImpl.TOPIC_SESSION;

@ApplicationScoped
public class UserSessionDomainEvent implements DomainEvent<UserSessionDomainEvent.UserSessionEvent> {
	@Inject
	EventBus eventBus;

	private final BehaviorSubject<UserSessionEvent> subj;

	public UserSessionDomainEvent() {
		subj = BehaviorSubject.create();
		subj.buffer(1000, TimeUnit.MILLISECONDS, 500).subscribe(events -> {
			if (!events.isEmpty()) eventBus.publish(TOPIC_SESSION, events);
		});
	}

	@Override
	public void raise(UserSessionEvent evt) {
		subj.onNext(evt);
	}

	@Data
	public static class UserSessionEvent {
		public enum TYPE {
			CREATE,
			REMOVE,
			UPDATE
		}

		private TYPE type;
		private String userId;
		private UserSession userSession;
		private Object change;
	}
}
