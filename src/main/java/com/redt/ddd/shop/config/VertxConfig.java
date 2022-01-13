package com.redt.ddd.shop.config;

import com.redt.ddd.shop.infrastructure.eventbus.enc.GenericCodec;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.eventbus.EventBus;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class VertxConfig {
	@Inject
	EventBus evtBus;

	void config(@Observes StartupEvent evt) {
//		evtBus.registerDefaultCodec(ArrayList.class, new GenericCodec<>(ArrayList.class));
	}
}
