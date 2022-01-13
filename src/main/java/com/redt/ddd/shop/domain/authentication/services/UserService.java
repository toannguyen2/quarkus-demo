package com.redt.ddd.shop.domain.authentication.services;

import com.redt.ddd.shop.domain.authentication.dto.CredentialDTO;
import com.redt.ddd.shop.domain.authentication.dto.FormSignupDTO;
import com.redt.ddd.shop.domain.authentication.entities.User;

public interface UserService {
	User signin(CredentialDTO credentialDTO);

	User signup(FormSignupDTO formSignupDTO);

	User save(User user);

	User findById(String id);
}
