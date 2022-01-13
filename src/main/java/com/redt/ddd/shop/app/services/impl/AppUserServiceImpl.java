package com.redt.ddd.shop.app.services.impl;

import com.redt.ddd.shop.app.http.models.Response;
import com.redt.ddd.shop.app.services.AppUserService;
import com.redt.ddd.shop.domain.authentication.dto.CredentialDTO;
import com.redt.ddd.shop.domain.authentication.dto.FormSignupDTO;
import com.redt.ddd.shop.domain.authentication.entities.UserSession;
import com.redt.ddd.shop.domain.authentication.services.UserService;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static com.redt.ddd.shop.domain.authentication.services.impl.UserServiceImpl.TOPIC_SIGNUP;

@ApplicationScoped
public class AppUserServiceImpl implements AppUserService {

	@Inject
	EventBus bus;

	@Inject
	UserService userService;

	@Override
	public Uni<Response> signin(CredentialDTO credential) {
		return null;
	}

	public Uni<String> signup(FormSignupDTO formSignupDTO) {
		Response response = new Response();
//		bus.<FormSignupDTO>request(TOPIC_SIGNUP, "formSignupDTO").onItem().transform(session -> {
//			System.out.println(session);
////			session
////			Message::body
//			return null;
//		});
//		bus.<String>request(TOPIC_SIGNUP, null).onItem().transform(Message::body)();
//		bus.<String>request(TOPIC_SIGNUP, null).onItem().transform(Message::body).await();
		// item -> anotherAsynchronousAction(item)
//		return bus.<String>request(TOPIC_SIGNUP, null).onItem().transformToUni(Message::body);
		return bus.<UserSession>request(TOPIC_SIGNUP, formSignupDTO)
			.onItem()
			.transformToUni(item -> {
				System.out.println("-------");

				Object o = item.body();
				System.out.println(item.body());
//				anotherAsynchronousAction(item)
				return Uni.createFrom().item("ssdsdsdsd");
			});

//		return ;
	}
}
