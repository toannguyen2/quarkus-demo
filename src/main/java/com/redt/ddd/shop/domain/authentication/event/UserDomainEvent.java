package com.redt.ddd.shop.domain.authentication.event;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.redt.ddd.shop.domain.authentication.entities.User;
import com.redt.ddd.shop.domain.event.DomainEvent;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.vertx.core.eventbus.EventBus;
import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.redt.ddd.shop.domain.authentication.services.impl.UserServiceImpl.TOPIC_USER;

@ApplicationScoped
public class UserDomainEvent implements DomainEvent<UserDomainEvent.UserEvent> {
	@Inject
	EventBus eventBus;

	private final BehaviorSubject<UserEvent> subj;

	private LoadingCache<String, BehaviorSubject<UserEvent>> cache = CacheBuilder.newBuilder()
		.maximumSize(1000)
		.expireAfterWrite(10, TimeUnit.MINUTES)
		.build(new CacheLoader<>() {
			@Override
			public BehaviorSubject<UserEvent> load(String userId) {
				BehaviorSubject<UserEvent> behaviorSubj = BehaviorSubject.create();
				behaviorSubj.buffer(500, TimeUnit.MILLISECONDS, 500).subscribe(events -> {
					if (!events.isEmpty())
						eventBus.publish(TOPIC_USER, events);
				});
				return behaviorSubj;
			}
		});

	public UserDomainEvent() {
		subj = BehaviorSubject.create();
		subj.groupBy(UserEvent::getUserId).subscribe(groupedObservable -> {
			BehaviorSubject<UserEvent> behaviorSubj = cache.get(Objects.requireNonNull(groupedObservable.getKey()));
			groupedObservable.subscribe(behaviorSubj::onNext);
		});
	}

	@Override
	public void raise(UserEvent evt) {
		subj.onNext(evt);
	}


	@Data
	public static class UserEvent {
		public enum TYPE {
			CREATE,
			REMOVE,
			UPDATE
		}

		private TYPE type;
		private String userId;
		private User user;
		private Object change;
	}
}
