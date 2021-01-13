package com.piggymetrics.content.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class AccountDto {

	@NotBlank
	private String name;

	private String note;

	public AccountDto(@NotBlank String name) {
		this.name = name;
	}
}
