package br.com.github.fiap.global.solution.cap6.business;

import static br.com.github.fiap.global.solution.cap6.commons.CommonsUtils.notFoundChecker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.github.fiap.global.solution.cap6.bean.GenericBean;
import br.com.github.fiap.global.solution.cap6.command.CrudCommand;
import br.com.github.fiap.global.solution.cap6.exception.ConflictException;
import br.com.github.fiap.global.solution.cap6.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrudBusinessImpls<Dto> implements ICrudBusiness<Dto> {
	
	@Autowired
	CrudCommand<Dto> command;
	
	@Override
	public Dto findById(String dtoName, int id) throws Exception {
		Dto dto = null;
		try {
			dto = command.findById(dtoName, id);
			notFoundChecker((int) dto.getClass().getMethod("getId", null).invoke(dto));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return dto;
	}

	@Override
	public List<Dto> findAll(String dtoName) throws Exception {
		List<Dto> lst = null;
		try {
			lst = command.findAll(dtoName);
			notFoundChecker(lst.size());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return lst;
	}

	@Override
	public int create(String dtoName, Dto dto) throws ConflictException, Exception {
		GenericBean<Dto> bean = new GenericBean<>();
		String date = getDate();
		int id = 0;
		try {
			bean.setDto(dto);
			bean.setDataCriacao(date);
			bean.setDataAtualizacao(date);
			id = command.create(bean);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return id;
	}

	@Override
	public void update(String dtoName, Dto dto) throws ConflictException, NotFoundException, Exception {
		GenericBean<Dto> bean = new GenericBean<>();
		try {
			bean.setDto(dto);
			bean.setDataAtualizacao(getDate());
			int affectedRows = command.update(bean);
			notFoundChecker(affectedRows);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public void delete(String dtoName, int id) throws NotFoundException, Exception {
		try {
			int affectedRows = command.delete(dtoName, id);
			notFoundChecker(affectedRows);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	private static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Timestamp(System.currentTimeMillis()));
	}
}