package com.zhixin.zhfz.bacs.controller.security;

public class Face1vNResult {

	private String born_year="";
	private String face_image_id;
	private String face_image_url="image/logo.gif";
	private String gender="";
	private String name="";
	private String nation;
	private String address;
	private String person_id="";
	private String picture_url="";
	private String repository_id;
	private String similarity="";
	private String timestamp;
	@Override
	public String toString() {
		return "Face1vNResult [born_year=" + born_year + ", face_image_id=" + face_image_id + ", face_image_url="
				+ face_image_url + ", gender=" + gender + ", name=" + name + ", nation=" + nation + ", address="
				+ address + ", person_id=" + person_id + ", picture_url=" + picture_url + ", repository_id="
				+ repository_id + ", similarity=" + similarity + ", timestamp=" + timestamp + "]";
	}
	public String getBorn_year() {
		return born_year;
	}
	public void setBorn_year(String born_year) {
		this.born_year = born_year;
	}
	public String getFace_image_id() {
		return face_image_id;
	}
	public void setFace_image_id(String face_image_id) {
		this.face_image_id = face_image_id;
	}
	public String getFace_image_url() {
		return face_image_url;
	}
	public void setFace_image_url(String face_image_url) {
		this.face_image_url = face_image_url;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getPerson_id() {
		return person_id;
	}
	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public String getRepository_id() {
		return repository_id;
	}
	public void setRepository_id(String repository_id) {
		this.repository_id = repository_id;
	}
	public String getSimilarity() {
		return similarity;
	}
	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
