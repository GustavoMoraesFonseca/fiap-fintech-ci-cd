package br.com.github.fiap.global.solution.cap6.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.github.fiap.global.solution.cap6.bean.GenericBean;
import br.com.github.fiap.global.solution.cap6.dto.IntIdDTO;

@ExtendWith(MockitoExtension.class)
public class CrudDAOJdbcTest {

	@Mock
	Connection conn;

	@Mock
	PreparedStatement ps;

	@Mock
	ResultSet rs;

	@Test
	public void testInsertExecutesAndCloses() throws Exception {
		// Arrange
		CrudDAOJdbc<IntIdDTO> dao = new CrudDAOJdbc<>();
		IntIdDTO dto = new IntIdDTO();
		dto.setId(5);
		dto.setName("abc");
		GenericBean<IntIdDTO> bean = new GenericBean<>(dto);

		when(conn.prepareStatement(anyString())).thenReturn(ps);

		// Act
		int ret = dao.insert("IntIdDTO", conn, bean);

		// Assert
		assertEquals(0, ret, "insert currently returns 0 by implementation");
		verify(conn).prepareStatement(anyString());
		verify(ps).execute();
		verify(ps).close();
	}

	@Test
	public void testFindByIdSetsPropertiesAndCloses() throws Exception {
		// Arrange
		CrudDAOJdbc<Object> dao = new CrudDAOJdbc<>();

		when(conn.prepareStatement(anyString())).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true);

		when(rs.getObject("balance")).thenReturn(new BigDecimal("100.00"));
		when(rs.getObject("user_id")).thenReturn(new BigDecimal("7"));
		when(rs.getObject("user_bank_id")).thenReturn(new BigDecimal("11"));
		when(rs.getObject("user_personal_id")).thenReturn(new BigDecimal("22"));

		// Act
		Object result = dao.findById("UserDTO", conn, 1);

		// Assert
		assertNotNull(result);
		// result is a UserDTO instance; reflectively verify id and balance via toString of fields
		// cast to the known type
		br.com.github.fiap.global.solution.cap6.dto.UserDTO user = (br.com.github.fiap.global.solution.cap6.dto.UserDTO) result;
		assertEquals(new BigDecimal("100.00"), user.getBalance());
		assertEquals(new BigDecimal("7"), user.getId());

		verify(ps).setInt(1, 1);
		verify(rs).close();
		verify(ps).close();
	}

	@Test
	public void testFindAllReturnsListAndCloses() throws Exception {
		// Arrange
		CrudDAOJdbc<Object> dao = new CrudDAOJdbc<>();

		when(conn.prepareStatement(anyString())).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true, true, false);

		// first row values
		when(rs.getObject("balance")).thenReturn(new BigDecimal("10.00"), new BigDecimal("20.00"));
		when(rs.getObject("user_id")).thenReturn(new BigDecimal("1"), new BigDecimal("2"));
		when(rs.getObject("user_bank_id")).thenReturn(new BigDecimal("3"), new BigDecimal("4"));
		when(rs.getObject("user_personal_id")).thenReturn(new BigDecimal("5"), new BigDecimal("6"));

		// Act
		List<?> list = dao.findAll("UserDTO", conn);

		// Assert
		assertEquals(2, list.size());
		br.com.github.fiap.global.solution.cap6.dto.UserDTO u1 = (br.com.github.fiap.global.solution.cap6.dto.UserDTO) list.get(0);
		br.com.github.fiap.global.solution.cap6.dto.UserDTO u2 = (br.com.github.fiap.global.solution.cap6.dto.UserDTO) list.get(1);

		assertEquals(new BigDecimal("10.00"), u1.getBalance());
		assertEquals(new BigDecimal("20.00"), u2.getBalance());

		verify(rs, atLeastOnce()).next();
		verify(rs).close();
		verify(ps).close();
	}

	@Test
	public void testDeleteExecutesAndReturnsCount() throws Exception {
		// Arrange
		CrudDAOJdbc<Object> dao = new CrudDAOJdbc<>();
		when(conn.prepareStatement(anyString())).thenReturn(ps);
		when(ps.executeUpdate()).thenReturn(3);

		// Act
		int ret = dao.delete("UserDTO", conn, 5);

		// Assert
		assertEquals(3, ret);
		verify(ps).setInt(1, 5);
		verify(ps).close();
	}

}
