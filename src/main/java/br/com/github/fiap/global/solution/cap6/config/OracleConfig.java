package br.com.github.fiap.global.solution.cap6.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConfig {

	public static Connection getConnection() throws Exception {
		Properties props = new Properties();
		Connection conn = null;
		try {
			props.put("user", "SYS as SYSDBA");
			props.put("password", "admin123");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", props);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw e;
		}
		return conn;
	}
}