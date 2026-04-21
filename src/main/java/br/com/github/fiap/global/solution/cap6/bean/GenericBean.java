package br.com.github.fiap.global.solution.cap6.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericBean<Dto> implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Dto dto;
	private String dataCriacao;
	private String dataAtualizacao;
	
	public GenericBean(Dto dto) {
		this.dataCriacao = getDate();
		this.dataAtualizacao = getDate();
		this.dto = dto;
	}
	
	private static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
		return sdf.format(new Timestamp(System.currentTimeMillis()));
	}
}