package com.redt.ddd.shop.domain.authentication.services.impl;

import com.redt.ddd.shop.domain.authentication.cache.SessionCache;
import com.redt.ddd.shop.domain.authentication.cache.UserCache;
import com.redt.ddd.shop.domain.authentication.dto.CredentialDTO;
import com.redt.ddd.shop.domain.authentication.dto.FormSignupDTO;
import com.redt.ddd.shop.domain.authentication.entities.UserSession;
import com.redt.ddd.shop.domain.authentication.entities.User;
import com.redt.ddd.shop.domain.authentication.event.UserDomainEvent;
import com.redt.ddd.shop.domain.authentication.event.UserSessionDomainEvent;
import com.redt.ddd.shop.domain.authentication.factories.SessionBuilder;
import com.redt.ddd.shop.domain.authentication.factories.UserBuilder;
import com.redt.ddd.shop.domain.authentication.services.UserService;
import com.redt.ddd.shop.domain.event.DomainEvents;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
public class UserServiceImpl implements UserService {
	public static final String TOPIC_SIGNIN = "signin";
	public static final String TOPIC_SIGNUP = "signup";

	public static final String TOPIC_USER = "user";

	@Inject
	UserCache userCache;

	@Inject
	SessionCache sessionCache;

	@Inject
	EventBus bus;

//	@Inject
//	EntityManager entityManager;
//	@Inject
//	CriteriaBuilderFactory criteriaBuilderFactory;
//	@Inject
//	EntityViewManager entityViewManager;

	public UserServiceImpl() {
//		CriteriaBuilder<User> criteriaBuilder = cbf.create(em, User.class);
//		criteriaBuilder.whereOr()
//		criteriaBuilder.select("*")
//			.from(User.class);
//
//		entityViewManager.find()
//
////		return evm.applySetting(EntityViewSetting.create(GiftView.class), cb).getResultList();
//
//		User.find("", "");
//		Uni<User> user = User.findById("aa");
//		user.call(user1 -> {
//			return null;
//		});
	}

//	@ReactiveTransactional
	@ConsumeEvent(value = TOPIC_USER)
	public void consumeUserEvent(Message msg) {
		List<UserDomainEvent.UserEvent> events = (List<UserDomainEvent.UserEvent>) msg.body();
		UserDomainEvent.UserEvent userEvent = events.get(0);
		User user = userEvent.getUser();
		create(user).subscribe().with((e)->{
			log.info(e.toString());
		});
//		Uni<PanacheEntityBase> persistOperation = user.persistAndFlush().onFailure();

//		if(user.isPersistent()){
//			 delete it
//			System.out.println("persist");
//			Uni<Void> deleteOperation = user.delete();
//		}


//		List<User> userSessionsNeedSave = new ArrayList<>();
//		List<User> userSessionsNeedDel = new ArrayList<>();
//
//		for (UserDomainEvent.UserEvent event : events) {
//			if (UserDomainEvent.UserEvent.TYPE.CREATE.equals(event.getType()))
//				userSessionsNeedSave.add(event.getUserSession());
//			else if (UserDomainEvent.UserEvent.TYPE.REMOVE.equals(event.getType()))
//				userSessionsNeedDel.add(event.getUserSession());
//		}
//
//		System.out.println("aa");
	}

	@ReactiveTransactional
	public Uni<?> create(User user){
		//Here I use the persistAndFlush() shorthand method on a Panache repository to persist to database then flush the changes.
		return user.persistAndFlush()
			.onFailure(PersistenceException.class)
			.recoverWithItem(() -> {
				log.error("Unable to create the parameter");
				//in case of error, I save it to disk
//				diskPersister.save(user);
				return null;
			});
	}

	@ConsumeEvent(value = TOPIC_SIGNUP)
	public UserSession consumeForSignupEvent(FormSignupDTO formSignupDTO) {
		var userBuilder = UserBuilder.bulder().setName(formSignupDTO.getName()).setPassword(formSignupDTO.getPassword()).addEmail(formSignupDTO.getEmail()).addPhoneNumber(formSignupDTO.getPhone());
		var user = userBuilder.build();

		// Session
		var sesBuilder = SessionBuilder.bulder();
		sesBuilder.setUserId(user.getId());
		var ses = sesBuilder.build();

		// Cache
		userCache.insert(user.getId(), user);
		sessionCache.insert(ses.getId(), ses);

		// User event
		UserDomainEvent.UserEvent userEvt = new UserDomainEvent.UserEvent();
		userEvt.setUserId(user.getId());
		userEvt.setUser(user);
		userEvt.setType(UserDomainEvent.UserEvent.TYPE.CREATE);
		DomainEvents.User.raise(userEvt);

		UserSessionDomainEvent.UserSessionEvent userSessionEvent = new UserSessionDomainEvent.UserSessionEvent();
		userSessionEvent.setUserId(user.getId());
		userSessionEvent.setUserSession(ses);
		userSessionEvent.setType(UserSessionDomainEvent.UserSessionEvent.TYPE.CREATE);
		DomainEvents.UserSession.raise(userSessionEvent);

//		Mutiny.Session.withTransaction
//		Panache.withTransaction(() -> sessionCache.findById(ses.getId(), ses)).subscribeAsCompletionStage();

		return ses;
	}

	@Override
	public User signin(CredentialDTO credentialDTO) {
//		User. ()
		return null;
	}

	@Override
	public User signup(FormSignupDTO formSignupDTO) {
		return null;
	}

	@Override
	public User save(User user) {
		return null;
	}

	@Override
	public User findById(String id) {
//		cache.as(User.class).get();
//		cache.
		return null;
	}
}
