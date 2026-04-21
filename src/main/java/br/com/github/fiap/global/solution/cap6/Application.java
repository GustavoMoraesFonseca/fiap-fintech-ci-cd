package br.com.github.fiap.global.solution.cap6;

import java.math.BigDecimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.github.fiap.global.solution.cap6.bean.GenericBean;
import br.com.github.fiap.global.solution.cap6.command.CrudCommand;
import br.com.github.fiap.global.solution.cap6.dao.CrudDAOJdbc;
import br.com.github.fiap.global.solution.cap6.dto.BankUserDTO;
import br.com.github.fiap.global.solution.cap6.dto.InvestmentDTO;
import br.com.github.fiap.global.solution.cap6.dto.PersonalUserDTO;
import br.com.github.fiap.global.solution.cap6.dto.UserDTO;
import br.com.github.fiap.global.solution.cap6.dto.UserInvestmentDTO;
	
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public static void maintTest(String[] args) {
		testInvestment();
		testBank();
		testPersonalUser();
		testUser();
		testUserInvestment();
	}
	
	private static void testUserInvestment() {
		var investment = new UserInvestmentDTO();
		investment.setInvestmentId(new BigDecimal(1));
		investment.setUserId(new BigDecimal(1));
		try {
			var command = new CrudCommand<UserInvestmentDTO>(new CrudDAOJdbc<UserInvestmentDTO>());
			command.create(new GenericBean<UserInvestmentDTO>(investment));
			
			var dtos = command.findAll(investment.getClass().getSimpleName());
			System.out.println(dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testUser() {
		var user = new UserDTO();
		user.setBalance(new BigDecimal(1));
		user.setUserBankId(new BigDecimal(1));
		user.setUserPersonalId(new BigDecimal(1));
		try {
			var command = new CrudCommand<UserDTO>(new CrudDAOJdbc<UserDTO>());
			command.create(new GenericBean<UserDTO>(user));
			
			var dtos = command.findAll(user.getClass().getSimpleName());
			System.out.println(dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testPersonalUser() {
		var personal = new PersonalUserDTO();
		personal.setPersonalUserCpf("000.000.000-12");
		personal.setPersonalUserEmail("asdas@asd.com");
		personal.setPersonalUserName("Gustavo");
		personal.setPersonalUserTelefone("999999999");
		try {
			var command = new CrudCommand<PersonalUserDTO>(new CrudDAOJdbc<PersonalUserDTO>());
			command.create(new GenericBean<PersonalUserDTO>(personal));
			
			var dtos = command.findAll(personal.getClass().getSimpleName());
			System.out.println(dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testBank() {
		var bank = new BankUserDTO();
		bank.setBankUserAccount("0001");
		bank.setBankUserAccountType("Corrente");
		bank.setBankUserAgencyCd("1");
		bank.setBankUserBankCd(new BigDecimal(1));
		try {
			var command = new CrudCommand<BankUserDTO>(new CrudDAOJdbc<BankUserDTO>());
			command.create(new GenericBean<BankUserDTO>(bank));
			
			var dtos = command.findAll(bank.getClass().getSimpleName());
			System.out.println(dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testInvestment() {
		var investment = new InvestmentDTO();
		investment.setInvestmentIsFgcGuarantee(new BigDecimal(1));
		investment.setInvestmentLiquidity("No mesmo dia");
		investment.setInvestmentMinValue(new BigDecimal(1.0));
		investment.setInvestmentName("CDB");
		investment.setInvestmentProfitability(new BigDecimal(15.0));
		investment.setInvestmentType("Renda Fixa");		
		try {
			var command = new CrudCommand<InvestmentDTO>(new CrudDAOJdbc<InvestmentDTO>());
			command.create(new GenericBean<InvestmentDTO>(investment));
			
			var dtos = command.findAll(investment.getClass().getSimpleName());
			System.out.println(dtos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}