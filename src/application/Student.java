/*
 * Derrick Fox
 * CS 214 - Advanced Java
 * Project 11 - Table View
 * April 29, 2015
 * Java 1.8 JavaFX 2.2
 */

package application;

import java.util.Date;

public class Student {
	String ssn = "";
	String firstName = "";
	String MI = "";
	String lastName = "";
	String phone = "";
	Date birthDate;
	String street = "";
	String zipCode = "";
	String deptId = "";
	
	public Student() {
		this.ssn = "";
		this.firstName = "";
		this.MI = "";
		this.lastName = "";
		this.phone = "";
		this.birthDate = new Date();
		this.street = "";
		this.zipCode = "";
		this.deptId = "";
	}
	
	public Student(String ssn, String firstName, String MI, String lastName, String phone, Date birthDate, String street, String zipcode, String deptId) {
		this.ssn = ssn;
		this.firstName = firstName;
		this.MI = MI;
		this.lastName = lastName;
		this.phone = phone;
		this.birthDate = birthDate;
		this.street = street;
		this.zipCode = zipcode;
		this.deptId = deptId;
	}
	
	public void setSsn(String sSN){
		this.ssn = sSN;
	}
	
	public String getSsn(){
		return ssn;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public void setMi(String MI){
		this.MI = MI;
	}
	
	public String getMi(){
		return MI;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setBirthDate(Date birthDate){
		this.birthDate = birthDate;
	}
	
	public Date getBirthDate(){
		return birthDate;
	}
	
	public void setStreet(String street){
		this.street = street;
	}
	
	public String getStreet(){
		return street;
	}
	
	public void setZipCode(String zipcode){
		this.zipCode = zipcode;
	}
	
	public String getZipCode(){
		return zipCode;
	}
	
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}
	
	public String getDeptId(){
		return deptId;
	}
}
