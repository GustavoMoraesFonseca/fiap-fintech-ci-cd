package br.com.github.fiap.global.solution.cap6.dao.helper;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;

import br.com.github.fiap.global.solution.cap6.contants.Constants;
import br.com.github.fiap.global.solution.cap6.dao.sql.SQLQueries;

public abstract class CrudDAOHelper<Dto> {

	private String getPatternName(Dto dto) {
		return dto.getClass().getName().substring(Constants.DTOS_PACKAGE.length()).replaceAll("DTO", "");
	}
	
	protected String getTableName(Dto dto) throws Exception {
		return formatterField(getPatternName(dto));
	}
	
	protected List<String> getDtoFields(Dto dto) {
		List<String> dtoFields = new ArrayList<>();
		getGetters(dto).forEach(
			m -> {
				char c[] = getIndexStartPropertyName(m.getName()).toCharArray();
				c[0] = Character.toLowerCase(c[0]);
				dtoFields.add(new String(c));
			}
		);
		dtoFields.sort(Comparator.naturalOrder());
		return dtoFields;
	}
	
	protected List<String> getDtoFieldsFormatter(Dto dto) throws Exception {
		List<String> dtoFields = new ArrayList<>();	
		getDtoFields(dto).forEach(
			f -> dtoFields.add(formatterField(f))
		);
		dtoFields.sort(Comparator.naturalOrder());
		return dtoFields;
	}
	
	protected String getInsertSQL(Dto dto) throws Exception {		
		List<String> interrogations = new ArrayList<>();
		List<String> formattedFields = getDtoFieldsFormatter(dto);
		formattedFields.remove("id");
		formattedFields.forEach(
			(f) -> interrogations.add("?")
		);
		String strInterrogations = String.join(", ", interrogations);
		String fields = String.join(", ", formattedFields);
		return String.format(SQLQueries.INSERT, getTableName(dto), fields, strInterrogations);
	}
	
	protected String getSelectSQL(Dto dto, boolean hasId) throws Exception {
		String fields = String.join(", ", getDtoFieldsFormatter(dto));
		fields = fields.replaceFirst("id", getTableName(dto)+"_id");
		return String.format(hasId? SQLQueries.SELECT_BY_ID : SQLQueries.SELECT_ALL, fields, getTableName(dto));
	}
	
	protected String getUpdateSQL(Dto dto) throws Exception {
		var formattedFields = getDtoFieldsFormatter(dto);
		formattedFields.remove("id");
		String fields = String.join(" = ?, ", formattedFields);
		return String.format(SQLQueries.UPDATE, getTableName(dto), fields);
	}
	
	protected String getDeleteSQL(Dto dto) throws Exception {
		String tabName = getTableName(dto);
		return String.format(SQLQueries.DELETE, tabName, tabName);
	}

	protected List<Method> getGetters(Dto dto) {
		Comparator<Method> comparator = Comparator.comparing(
			Method::getName,
			(name1, name2) -> getIndexStartPropertyName(name1).compareTo(getIndexStartPropertyName(name2))
        );
		List<Method> getters;		
		getters = Arrays.stream(dto.getClass().getMethods()).filter(
			m -> !m.getName().equals("getClass") && m.getName().substring(0,3).equals("get") || m.getName().substring(0,2).equals("is")
		).collect(Collectors.toList());
		getters.sort(comparator);
		return getters;
	}
	
	protected void resultSetterInObj(Dto obj, List<String> fields, final ResultSet rs) throws Exception {
		for (int i = 0; i < fields.size(); i++) {
			String column = formatterField(fields.get(i));
			if (column.equals("id")) {
				column = getTableName(obj)+"_id";
			}
			PropertyUtils.setProperty(obj, fields.get(i), rs.getObject(column));
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Dto getDtoInstance(String dtoName) throws Exception {
		Class<?> dto = Class.forName(Constants.DTOS_PACKAGE+dtoName);
		return (Dto) dto.getConstructor().newInstance();
	}
	
	private String formatterField(String field) {
		String[] fields = field.split("(?=\\p{Lu})");
		StringBuffer sb = new StringBuffer();
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			sb.append("_"+fields[i]);
		}
		return sb.toString().toLowerCase();
	}
	
	private String getIndexStartPropertyName(String str) {
		return str.substring(str.startsWith("is")? 2 : 3);
	}
}