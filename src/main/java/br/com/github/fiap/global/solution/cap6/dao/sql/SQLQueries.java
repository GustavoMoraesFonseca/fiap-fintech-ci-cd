package br.com.github.fiap.global.solution.cap6.dao.sql;

public interface SQLQueries {

	String DELETE
	="DELETE FROM t_%s "
	+"WHERE t_%s.id = ?";
	
	String INSERT
	= "INSERT INTO t_%s"
	+ "	(%s, dth_create, dth_update) "
	+ "VALUES "
	+ "	(%s, ?, ?)";
	
	String SELECT_ALL
	= "SELECT %s "
	+ "FROM t_%s ";
	
	String SELECT_BY_ID
	= SELECT_ALL
	+ "WHERE id = ?";
	
	String UPDATE
	="UPDATE t_%s SET "
	+ 	"%s = ?, "
	+	"dth_update = ? "
	+"WHERE id = ?";
}