package com.redt.ddd.shop.app.services;

import com.redt.ddd.shop.app.http.models.Response;
import com.redt.ddd.shop.domain.authentication.dto.CredentialDTO;
import com.redt.ddd.shop.domain.authentication.dto.FormSignupDTO;
import io.smallrye.mutiny.Uni;

public interface AppUserService {
	Uni<Response> signin(CredentialDTO credential);
	Uni<String> signup(FormSignupDTO formSignupDTO);
}
