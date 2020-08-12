package com.Food.FoodOrder.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerResponse {

	
	private boolean success;
	
	private String message;
	
	private Object data;
	
	@JsonIgnore
	private int status;
	 
	 public Object getData() {
	        return data;
	    }

	    public void setData(Object data) {
	        this.data = data;
	    }

	 

	    public void setResponse(boolean success, String message, Object data) {
			this.success = success;
			this.message = message;
			this.data = data;
		}

		@Override
	    public String toString() {
	        return getData().toString();
	    }

	    public static HttpStatus getStatus(int status) {
	        return HttpStatus.valueOf(status);
	    }

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	    
	
}
