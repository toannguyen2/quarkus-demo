package com.redt.ddd.shop.domain.event;

public interface DomainEvent<T> {
	void raise(T evt);
}
