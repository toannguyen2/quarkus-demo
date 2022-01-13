package com.redt.ddd.shop.app.http.controllers.api.user;

import com.redt.ddd.shop.app.http.models.Response;
import com.redt.ddd.shop.app.services.AppUserService;
import com.redt.ddd.shop.domain.authentication.dto.CredentialDTO;
import com.redt.ddd.shop.domain.authentication.dto.FormSignupDTO;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/user")
public class UserController {
	@Inject
	AppUserService appUserService;

	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<Response> signin(CredentialDTO credential) {
		return appUserService.signin(credential);
	}

	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<String> signup(FormSignupDTO formSignupDTO) {
		return appUserService.signup(formSignupDTO);
	}
}
