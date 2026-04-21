package br.com.github.fiap.global.solution.cap6.controller;

import static br.com.github.fiap.global.solution.cap6.commons.CommonsUtils.conflict;
import static br.com.github.fiap.global.solution.cap6.commons.CommonsUtils.created;
import static br.com.github.fiap.global.solution.cap6.commons.CommonsUtils.notFound;
import static br.com.github.fiap.global.solution.cap6.commons.CommonsUtils.ok;
import static br.com.github.fiap.global.solution.cap6.commons.CommonsUtils.registerTrace;
import static br.com.github.fiap.global.solution.cap6.commons.CommonsUtils.serverError;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.github.fiap.global.solution.cap6.business.CrudBusinessImpls;
import br.com.github.fiap.global.solution.cap6.dto.ResponseDto;
import br.com.github.fiap.global.solution.cap6.exception.ConflictException;
import br.com.github.fiap.global.solution.cap6.exception.NotFoundException;


public abstract class AbstractCommonController<Dto> {

	@Autowired
	CrudBusinessImpls<Dto> business;
	
	protected abstract String getDtoName();
	
	@GetMapping("/health")
	public ResponseEntity<ResponseDto> healthCheck() {
		return ok("Running");
	}

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") int id) {
    	LocalDateTime dtHrStart = LocalDateTime.now();
    	ResponseEntity response = null;
    	try {
			var obj = business.findById(getDtoName(), id);
			response = ok(obj);
    	} catch (NotFoundException e) {
    		response = notFound();
    	} catch (Exception e) {
			response = serverError(e.getMessage());
		} finally {
			registerTrace(dtHrStart, response.getStatusCode().value(), "Find By Id "+getDtoName());
		}
        return response;
    }
    
    @GetMapping
    public ResponseEntity findAll() {
    	LocalDateTime dtHrStart = LocalDateTime.now();
    	ResponseEntity response = null;
    	try {
			var lst = business.findAll(getDtoName());
			response = ok(lst);
    	} catch (NotFoundException e) {
    		response = notFound();
		} catch (Exception e) {
			response = serverError(e.getMessage());
		} finally {
			registerTrace(dtHrStart, response.getStatusCode().value(), "Find All "+getDtoName());
		}
        return response;
    }
    
    @PostMapping
    public ResponseEntity create(@RequestBody Dto dto) {
    	LocalDateTime dtHrStart = LocalDateTime.now();
    	ResponseEntity response = null;
    	try {
    		int id = business.create(getDtoName(), dto);
			response = created(id);
    	} catch (ConflictException e) {
    		response = conflict(e.getMessage());
		} catch (Exception e) {
			response = serverError(e.getMessage());
		} finally {
			registerTrace(dtHrStart, response.getStatusCode().value(), "Create "+getDtoName());
		}
    	return response;
    }
    
    @PutMapping
    public ResponseEntity update(@RequestBody Dto dto) {
    	LocalDateTime dtHrStart = LocalDateTime.now();
    	ResponseEntity response = null;
    	try {
    		business.update(getDtoName(), dto);
			response = ok("Alteração feita com Sucesso");
    	} catch (ConflictException e) {
    		response = conflict(e.getMessage());
    	} catch (NotFoundException e) {
    		response = notFound();
    	} catch (Exception e) {
			response = serverError(e.getMessage());
		} finally {
			registerTrace(dtHrStart, response.getStatusCode().value(), "Update "+getDtoName());
		}
    	return response;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
    	LocalDateTime dtHrStart = LocalDateTime.now();
    	ResponseEntity response = null;
    	try {
    		business.delete(getDtoName(), id);
    		response = ok("Exclusão feita com Sucesso");
    	} catch (NotFoundException e) {
    		response = notFound();
		} catch (Exception e) {
			response = serverError(e.getMessage());
		} finally {
			registerTrace(dtHrStart, response.getStatusCode().value(), "Delete "+getDtoName());
		}
    	return response;
    }
}