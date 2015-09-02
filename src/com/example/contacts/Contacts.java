package com.example.contacts;

public class Contacts {
	String name;
	String phno;
	String address;
	String email;
	String circle;
	int image;
	String birthdate;
	String gender;
		
	public Contacts() {
		super();
	}
	
	public Contacts(String name, String phno, String address, String email, String circle, int image, String birthdate, String gender) {
		this.name=name;
		this.phno=phno;
		this.address=address;
		this.email=email;
		this.circle=circle;
		this.image=image;
		this.birthdate=birthdate;
		this.gender=gender;
	}
	
	public Contacts(String string) {
		this.name=string;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}
	
	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
		
}
