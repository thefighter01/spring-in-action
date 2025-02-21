package tacos;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TacoOrder {
	
	@NotBlank(message = "Delivery name is required")
	private String deliveryName;
	@NotBlank(message = "Delivery street is required")
	private String deliveryStreet;
	@NotBlank(message = "Delivery city is required")
	private String deliveryCity;
	@NotBlank(message = "Delivery state is required")
	private String deliveryState;
	@NotBlank(message = "Delivery zip is required")
	private String deliveryZip;
	@CreditCardNumber(message="Not a valid credit card number")
	private String ccNumber;
	@NotBlank(message = "ccVV name is required")
	private String ccCVV;
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
			message="Must be formatted MM/YY")
	private String ccExpiration;
	
	private List<Taco> tacos = new ArrayList<>();
	
	public void addTaco(Taco taco) {
		this.tacos.add(taco);
	}
	

}
