package com.redt.ddd.shop.domain.authentication.dto;

import lombok.Data;

@Data
public class CredentialDTO {
	private String username;
	private String password;
	private Boolean remember_me = false;
}
