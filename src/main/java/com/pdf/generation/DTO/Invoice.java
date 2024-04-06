package com.pdf.generation.DTO;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Invoice {
	@NotNull(message="{seller.not.null}")
	private String seller;
	@NotNull(message="{sellerGstin.not.null}")
    private String sellerGstin;
	@NotNull(message="{sellerAddress.not.null}")
    private String sellerAddress;
	@NotNull(message="{buyer.not.null}")
    private String buyer;
	@NotNull(message="{buyerGstin.not.null}")
    private String buyerGstin;
	@NotNull(message="{buyerAddress.not.null}")
    private String buyerAddress;
	@NotEmpty(message="{items.not.empty}")
    private List<Item> items;
}
