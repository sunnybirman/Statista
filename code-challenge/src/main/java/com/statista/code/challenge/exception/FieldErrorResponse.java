package com.statista.code.challenge.exception;

import java.util.Map;

public class FieldErrorResponse extends ErrorResponse{

	  private Map<String, String> field_errors;
	  
	  public Map<String, String> getField_errors() {
			return field_errors;
		}

		public void setField_errors(Map<String, String> field_errors) {
			this.field_errors = field_errors;
		}

}
