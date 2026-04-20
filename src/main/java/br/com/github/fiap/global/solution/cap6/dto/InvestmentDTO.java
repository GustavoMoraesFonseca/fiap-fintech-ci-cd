package br.com.github.fiap.global.solution.cap6.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class InvestmentDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private BigDecimal id;
    private String investmentName;
    private String investmentType;
    private BigDecimal investmentMinValue;
    private BigDecimal investmentProfitability;
    private BigDecimal investmentIsFgcGuarantee;
    private String investmentLiquidity;
}