package br.com.github.fiap.global.solution.cap6.command;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.github.fiap.global.solution.cap6.bean.GenericBean;
import br.com.github.fiap.global.solution.cap6.config.OracleConfig;
import br.com.github.fiap.global.solution.cap6.dao.ICrudDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CrudCommand<Dto> {
	
	private ICrudDAO<Dto> crudDAOImpls; 
	
	public int create(GenericBean<Dto> bean) throws Exception {
		Connection conn = OracleConfig.getConnection();
		int id = 0;
		try {
			id = crudDAOImpls.insert(bean.getDto().getClass().getSimpleName(), conn, bean);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return id;
	}

	public Dto findById(String dtoName, int id) throws Exception {
		Connection conn = OracleConfig.getConnection();
		Dto dto;
		try {
			dto = crudDAOImpls.findById(dtoName, conn, id);
		} finally {
			conn.close();
		}
		return dto;
	}	
	
	public List<Dto> findAll(String dtoName) throws Exception {
		Connection conn = OracleConfig.getConnection();
		List<Dto> lst = new ArrayList<Dto>();
		try {
			lst = crudDAOImpls.findAll(dtoName, conn);
		} finally {
			conn.close();
		}
		return lst;
	}
	
	public int update(GenericBean<Dto> bean) throws Exception {
		Connection conn = OracleConfig.getConnection();
		int retorno = 0;
		try {
			retorno = crudDAOImpls.update(bean.getDto().getClass().getSimpleName(), conn, bean);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return retorno;
	}
	
	public int delete(String dtoName, int id) throws Exception {
		Connection conn = OracleConfig.getConnection();
		int retorno = 0;
		try {
			retorno = crudDAOImpls.delete(dtoName, conn, id);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return retorno;
	}
}
