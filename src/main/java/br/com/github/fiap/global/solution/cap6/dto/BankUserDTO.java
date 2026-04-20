package br.com.github.fiap.global.solution.cap6.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class BankUserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal id;
    private BigDecimal bankUserBankCd;
    private String bankUserAgencyCd;
    private String bankUserAccount;
    private String bankUserAccountType;
}