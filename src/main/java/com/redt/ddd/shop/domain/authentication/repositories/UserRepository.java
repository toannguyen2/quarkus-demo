package com.redt.ddd.shop.domain.authentication.repositories;

import com.redt.ddd.shop.domain.authentication.entities.User;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, String> {
}
