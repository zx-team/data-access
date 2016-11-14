package com.ismes.data_access.result;


public class Result {
	
	public Result(ConstantEnum e, String machineNo,String type) {
		this.code = e.getCode();
		this.msg = e.getMsg();
		this.type=type;
		this.machineNo=machineNo;
	}
	
	public Result(ConstantEnum e, String machineNo,String type,String uuid) {
		this.code = e.getCode();
		this.msg = e.getMsg();
		this.type=type;
		this.machineNo=machineNo;
		this.uuid = uuid;
	}

	// ignore setter and getter
	private String code;
	private String msg;
	private String type;
	private String machineNo;
	//一次请求的唯一标识
	private String uuid;
	

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
	

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}