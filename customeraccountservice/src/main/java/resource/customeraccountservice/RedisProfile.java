package resource.customeraccountservice;

import java.util.Date;



public class RedisProfile {
	
	String _id;
    String firstName;
    String lastName;
    String address;
    String city;
    String state;
    String zip;
    String homePhone;
    String workPhone;
    String mobilePhone;
    String gender;
    String birthDate;
    String fileName;
    String email;
    String creationDate;
    String photo;
    
    public RedisProfile()
    {
    	
    }
    
    public RedisProfile(String _id, String firstName, String lastName, String address, String city, String state, String zip, String homePhone, String workPhone, String mobilePhone, String gender,String birthDate, String fileName, String email, String creationDate, String photo)
    {
    	this._id = _id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.address = address;
    	this.city = city;
    	this.state = state;
    	this.zip = zip;
    	this.homePhone = homePhone;
    	this.workPhone = workPhone;
    	this.mobilePhone = mobilePhone;
    	this.gender = gender;
    	this.birthDate = birthDate;
    	this.fileName = fileName;
    	this.email = email;
    	this.creationDate = creationDate;
    	this.photo = photo;
    }

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
