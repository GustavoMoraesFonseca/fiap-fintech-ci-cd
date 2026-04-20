package br.com.github.fiap.global.solution.cap6.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.github.fiap.global.solution.cap6.bean.GenericBean;
import br.com.github.fiap.global.solution.cap6.config.OracleConfig;
import br.com.github.fiap.global.solution.cap6.dao.ICrudDAO;

@ExtendWith(MockitoExtension.class)
public class CrudCommandTest {

    static class DummyDto {
    }

    @Mock
    ICrudDAO<DummyDto> crudDao;

    @Mock
    Connection conn;

    @Test
    public void testCreateSuccess() throws Exception {
        GenericBean<DummyDto> bean = mock(GenericBean.class);
        DummyDto dto = new DummyDto();
        when(bean.getDto()).thenReturn(dto);

        when(crudDao.insert(eq(dto.getClass().getSimpleName()), any(Connection.class), eq(bean))).thenReturn(42);

        try (MockedStatic<OracleConfig> oracle = mockStatic(OracleConfig.class)) {
            oracle.when(OracleConfig::getConnection).thenReturn(conn);

            CrudCommand<DummyDto> command = new CrudCommand<>(crudDao);
            int id = command.create(bean);

            assertEquals(42, id);
            verify(crudDao).insert(eq("DummyDto"), eq(conn), eq(bean));
            verify(conn).commit();
            verify(conn).close();
            verify(conn, never()).rollback();
        }
    }

    @Test
    public void testCreateExceptionRollsBackAndCloses() throws Exception {
        GenericBean<DummyDto> bean = mock(GenericBean.class);
        DummyDto dto = new DummyDto();
        when(bean.getDto()).thenReturn(dto);

        when(crudDao.insert(anyString(), any(Connection.class), any())).thenThrow(new RuntimeException("fail"));

        try (MockedStatic<OracleConfig> oracle = mockStatic(OracleConfig.class)) {
            oracle.when(OracleConfig::getConnection).thenReturn(conn);

            CrudCommand<DummyDto> command = new CrudCommand<>(crudDao);
            Exception ex = assertThrows(RuntimeException.class, () -> command.create(bean));
            assertEquals("fail", ex.getMessage());

            verify(conn).rollback();
            verify(conn).close();
            verify(conn, never()).commit();
        }
    }

    @Test
    public void testFindByIdReturnsDtoAndCloses() throws Exception {
        DummyDto dto = new DummyDto();
        when(crudDao.findById(eq("DummyDto"), any(Connection.class), eq(1))).thenReturn(dto);

        try (MockedStatic<OracleConfig> oracle = mockStatic(OracleConfig.class)) {
            oracle.when(OracleConfig::getConnection).thenReturn(conn);

            CrudCommand<DummyDto> command = new CrudCommand<>(crudDao);
            DummyDto result = command.findById("DummyDto", 1);

            assertSame(dto, result);
            verify(crudDao).findById("DummyDto", conn, 1);
            verify(conn).close();
        }
    }

    @Test
    public void testFindAllReturnsListAndCloses() throws Exception {
        DummyDto d1 = new DummyDto();
        DummyDto d2 = new DummyDto();
        List<DummyDto> list = Arrays.asList(d1, d2);
        when(crudDao.findAll(eq("DummyDto"), any(Connection.class))).thenReturn(list);

        try (MockedStatic<OracleConfig> oracle = mockStatic(OracleConfig.class)) {
            oracle.when(OracleConfig::getConnection).thenReturn(conn);

            CrudCommand<DummyDto> command = new CrudCommand<>(crudDao);
            List<DummyDto> result = command.findAll("DummyDto");

            assertEquals(2, result.size());
            assertSame(list, result);
            verify(crudDao).findAll("DummyDto", conn);
            verify(conn).close();
        }
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        GenericBean<DummyDto> bean = mock(GenericBean.class);
        DummyDto dto = new DummyDto();
        when(bean.getDto()).thenReturn(dto);

        when(crudDao.update(eq("DummyDto"), any(Connection.class), eq(bean))).thenReturn(7);

        try (MockedStatic<OracleConfig> oracle = mockStatic(OracleConfig.class)) {
            oracle.when(OracleConfig::getConnection).thenReturn(conn);

            CrudCommand<DummyDto> command = new CrudCommand<>(crudDao);
            int ret = command.update(bean);

            assertEquals(7, ret);
            verify(conn).commit();
            verify(conn).close();
            verify(conn, never()).rollback();
        }
    }

    @Test
    public void testUpdateExceptionRollsBack() throws Exception {
        GenericBean<DummyDto> bean = mock(GenericBean.class);
        DummyDto dto = new DummyDto();
        when(bean.getDto()).thenReturn(dto);

        when(crudDao.update(anyString(), any(Connection.class), any())).thenThrow(new IllegalStateException("upd fail"));

        try (MockedStatic<OracleConfig> oracle = mockStatic(OracleConfig.class)) {
            oracle.when(OracleConfig::getConnection).thenReturn(conn);

            CrudCommand<DummyDto> command = new CrudCommand<>(crudDao);
            Exception ex = assertThrows(IllegalStateException.class, () -> command.update(bean));
            assertEquals("upd fail", ex.getMessage());

            verify(conn).rollback();
            verify(conn).close();
            verify(conn, never()).commit();
        }
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        when(crudDao.delete(eq("DummyDto"), any(Connection.class), eq(5))).thenReturn(3);

        try (MockedStatic<OracleConfig> oracle = mockStatic(OracleConfig.class)) {
            oracle.when(OracleConfig::getConnection).thenReturn(conn);

            CrudCommand<DummyDto> command = new CrudCommand<>(crudDao);
            int ret = command.delete("DummyDto", 5);

            assertEquals(3, ret);
            verify(conn).commit();
            verify(conn).close();
            verify(conn, never()).rollback();
        }
    }

    @Test
    public void testDeleteExceptionRollsBack() throws Exception {
        when(crudDao.delete(anyString(), any(Connection.class), anyInt())).thenThrow(new RuntimeException("del fail"));

        try (MockedStatic<OracleConfig> oracle = mockStatic(OracleConfig.class)) {
            oracle.when(OracleConfig::getConnection).thenReturn(conn);

            CrudCommand<DummyDto> command = new CrudCommand<>(crudDao);
            Exception ex = assertThrows(RuntimeException.class, () -> command.delete("DummyDto", 5));
            assertEquals("del fail", ex.getMessage());

            verify(conn).rollback();
            verify(conn).close();
            verify(conn, never()).commit();
        }
    }
}