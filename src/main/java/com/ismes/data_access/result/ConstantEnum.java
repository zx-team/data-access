package com.ismes.data_access.result;

public enum ConstantEnum {
	
	SUCCESS("100", "成功"), 
	BACK_101("101", "接收数据没有对应的数据库存储表"),
	BACK_102("102", "解析xml异常"),
	BACK_103("103", "存储数据异常"),
	BACK_104("103", "存储数据返回异常"),
	BACK_105("103", "校验数据异常"),
	BACK_999("999", "系统异常");
	
	private String code;
	private String msg;
	
	private ConstantEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
