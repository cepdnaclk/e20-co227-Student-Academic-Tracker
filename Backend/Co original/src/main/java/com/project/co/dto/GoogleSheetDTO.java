package com.project.co.dto;

import java.util.List;

public class GoogleSheetDTO {

	private String sheetName;
	private String type;
	private String Admin_id;

	private List<List<Object>> dataToBeUpdated;

	private List<String> emails;

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public String getSheetName() {
		return sheetName;
	}

	public String getadminId() {
		return Admin_id;
	}

	public void setadminId(String Admin_id) {
		this.Admin_id = Admin_id;
	}


	public String gettype() {
		return type;
	}

	public void settype(String type) {
		this.type = type;
	}

	public List<List<Object>> getDataToBeUpdated() {
		return dataToBeUpdated;
	}

	public void setDataToBeUpdated(List<List<Object>> dataToBeUpdated) {
		this.dataToBeUpdated = dataToBeUpdated;
	}


}