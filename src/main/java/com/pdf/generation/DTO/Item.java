package com.pdf.generation.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
	@NotNull
	private String name;
	@NotNull
    private String quantity;
	@NotNull
    private double rate;
	@NotNull
    private double amount;
}
