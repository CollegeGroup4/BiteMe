package common;

import guiNew.IResponse;

public class Response implements IResponse{
	private Integer code;
	private String description;
	private Object body;
	
	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
