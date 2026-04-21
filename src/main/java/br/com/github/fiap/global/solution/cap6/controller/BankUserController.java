package br.com.github.fiap.global.solution.cap6.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import br.com.github.fiap.global.solution.cap6.dto.BankUserDTO;


@RequestMapping("/fiap/bank-user")
public class BankUserController extends AbstractCommonController<BankUserDTO> {
    
	@Override
	protected String getDtoName() {
		return "BankUserDTO";
	}
}