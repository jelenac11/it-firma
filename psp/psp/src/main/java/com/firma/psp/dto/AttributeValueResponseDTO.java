package com.firma.psp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueResponseDTO {

	private Long id;
	private String name;
	private String value;
	private String type;
}
