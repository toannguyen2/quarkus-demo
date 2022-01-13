package com.redt.ddd.shop.app.http.models;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
public class Response {
	private Integer code;
	private Integer status;
	private String message;
	private Object data;
	private Timestamp timestamp;

	public Response() {
		this.timestamp = Timestamp.from(Instant.now());
	}

	public static final class CODE {
		public static Integer OK = 0;
		public static Integer ERROR = 99;

	}

	public static final class STATUS {
		public static Integer SUCCESS = 0;
		public static Integer FAILURE = 1;
	}
}
