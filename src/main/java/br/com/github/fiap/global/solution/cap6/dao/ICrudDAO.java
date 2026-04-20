package br.com.github.fiap.global.solution.cap6.dao;

import java.sql.Connection;
import java.util.List;

import br.com.github.fiap.global.solution.cap6.bean.GenericBean;

public interface ICrudDAO<Dto> {

	int insert(String dtoName, Connection conn, GenericBean<Dto> bean) throws Exception;
	Dto findById(String dtoName, Connection conn,int id) throws Exception;
	List<Dto> findAll(String dtoName, Connection conn) throws Exception;
	int update(String dtoName, Connection conn, GenericBean<Dto> bean) throws Exception;
	int delete(String dtoName, Connection conn, int id) throws Exception;
}