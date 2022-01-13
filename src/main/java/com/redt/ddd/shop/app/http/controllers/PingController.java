package com.redt.ddd.shop.app.http.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ping")
public class PingController {
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String doGetPing() {
		return "ping";
	}
}
