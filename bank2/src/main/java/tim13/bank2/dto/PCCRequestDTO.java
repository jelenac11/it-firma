package tim13.bank2.dto;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PCCRequestDTO {

	@NotNull(message = "Acquirer order id is not provided.")
	private Long acquirerOrderId;

	@NotNull(message = "Acquirer timestamp is not provided.")
	private Timestamp acquirerTimestamp;

	@NotNull(message = "Amount is not provided.")
	@Min(value = 1,message = "Amount must be greater than 0")
	private Double amount;

	@NotBlank(message = "PAN number is empty.")
    @Pattern(regexp = "^[0-9]{16}$")
	private String PAN;

    @NotBlank(message = "Card Holder Name is empty.")
    @Pattern(regexp = "^(([A-Za-zÀ-ƒ]+[.]?[ ]?|[a-zÀ-ƒ]+['-]?){0,4})$")
	private String cardHolderName;

    @NotBlank(message = "Expiration date is empty")
    @Pattern(regexp = "^([01]?[0-9]?(\\/)[0-9]{2,4})$")
	private String expirationDate;

    @NotBlank(message = "Security Code is empty.")
    @Pattern(regexp = "^[0-9]{3,4}$")
	private String securityCode;

}
