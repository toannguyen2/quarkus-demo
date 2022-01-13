package com.redt.ddd.shop.domain.authentication.dto;

import lombok.Data;

@Data
public class FormSignupDTO extends CredentialDTO {
	private String name;
	private String email;
	private String phone;
}
