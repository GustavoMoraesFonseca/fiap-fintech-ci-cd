package br.com.github.fiap.global.solution.cap6.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserInvestmentDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal id;
    private BigDecimal investmentId;
    private BigDecimal userId;
}