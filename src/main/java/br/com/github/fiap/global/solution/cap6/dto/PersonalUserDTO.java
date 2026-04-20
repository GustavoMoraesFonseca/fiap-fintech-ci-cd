package br.com.github.fiap.global.solution.cap6.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class PersonalUserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private BigDecimal id;
    private String personalUserName;
    private String personalUserEmail;
    private String personalUserTelefone;
    private String personalUserCpf;
}