package com.firma.psp.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupportedMethodResponseDTO {

	private Long id;
	private String name;
	private boolean checked;
	private List<AttributeValueResponseDTO> values;
}
