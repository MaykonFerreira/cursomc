package com.maykon.cursomc.resources.exceptions;
 
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();




	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
}
