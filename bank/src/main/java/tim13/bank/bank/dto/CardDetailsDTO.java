package tim13.bank.bank.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDetailsDTO {

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
