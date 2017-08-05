package com.mezhou887.train.entity;

public class StationEntity {
	
	private String shortname1; //��վ���1
	private String name;       //��վ����
	private String code_id;    //��վ����
	private String fullname;   //��վƴ��ȫ��
	private String shortname2; //��վ���2
	private String no;         //��� 
	

	public StationEntity(String shortname1, String name, String code_id, String fullname, String shortname2, String no) {
		super();
		this.shortname1 = shortname1;
		this.name = name;
		this.code_id = code_id;
		this.fullname = fullname;
		this.shortname2 = shortname2;
		this.no = no;
	}
	public String getShortname1() {
		return shortname1;
	}
	public void setShortname1(String shortname1) {
		this.shortname1 = shortname1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getShortname2() {
		return shortname2;
	}
	public void setShortname2(String shortname2) {
		this.shortname2 = shortname2;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}

}
