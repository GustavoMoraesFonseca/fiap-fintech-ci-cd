package br.com.github.fiap.global.solution.cap6.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.github.fiap.global.solution.cap6.dto.BankUserDTO;


@RestController
@RequestMapping("/fiap")
public class BankUserController extends AbstractCommonController<BankUserDTO> {
    
	@Override
	protected String getDtoName() {
		return "BankUserDTO";
	}
}