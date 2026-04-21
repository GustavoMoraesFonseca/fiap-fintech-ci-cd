package br.com.github.fiap.global.solution.cap6.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConfig {

	static String urlDb = System.getenv("ORACLE_URL");
	static String userDb = System.getenv("ORACLE_USER");
	static String passwordDb = System.getenv("ORACLE_PASSWORD");

	public static Connection getConnection() throws Exception {
		Properties props = new Properties();
		Connection conn = null;
		try {
			// validate environment configuration to avoid NullPointerException from Properties/DriverManager
			if (urlDb == null || urlDb.isBlank()) {
				throw new IllegalStateException("Environment variable ORACLE_URL is not set or empty");
			}
			if (userDb == null || userDb.isBlank()) {
				throw new IllegalStateException("Environment variable ORACLE_USER is not set or empty");
			}
			if (passwordDb == null) {
				// allow empty password but not null reference
				throw new IllegalStateException("Environment variable ORACLE_PASSWORD is not set (null)");
			}

			props.put("user", userDb);
			props.put("password", passwordDb);
			conn = DriverManager.getConnection(urlDb, props);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw e;
		}
		return conn;
	}
}