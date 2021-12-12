package com.firma.psp.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPaymentMethodDTO {

	@NotEmpty
	private String name;
	@NotEmpty
	private String uri;
	@NotEmpty
	private List<NewAttributeDTO> attributes;
}
