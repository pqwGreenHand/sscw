package com.zhixin.zhfz.bacs.controller.serial;

public class Face1v1Result {

	private String isTheSamePerson;
	
	private String similarity;

	@Override
	public String toString() {
		return "Face1v1Result [isTheSamePerson=" + isTheSamePerson + ", similarity=" + similarity + "]";
	}

	public String getIsTheSamePerson() {
		return isTheSamePerson;
	}

	public void setIsTheSamePerson(String isTheSamePerson) {
		this.isTheSamePerson = isTheSamePerson;
	}

	public String getSimilarity() {
		return similarity;
	}

	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}

	
	
}
