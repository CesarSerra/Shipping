package com.java102.cesar;

public class Address {

	public static final Address HOMELESS = new Address();
	
	private String address_;
	private String city_;
	private String state_;
	private String zip_;
	
	public Address() 
	{
		this("", "", "", "");
	}
	
	Address(String address, String city, String state, String zip) 
	{
		setAddress(address);
		setCity(city);
		setState(state);
		setZip(zip);
	}
	
	/**
	 * @return the streetName
	 */
	public String getAddress() 
	{
		return address_;
	}
	/**
	 * @param streetName the streetName to set
	 */
	public void setAddress(String address) 
	{
		address_ = address;
	}
	/**
	 * @return the city
	 */
	public String getCity() 
	{
		return city_;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) 
	{
		city_ = city;
	}
	/**
	 * @return the state
	 */
	public String getState() 
	{
		return state_;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) 
	{
		state_ = state;
	}
	/**
	 * @return the zip
	 */
	public String getZip() 
	{
		return zip_;
	}
	/**
	 * @param zip the zip to set
	 */
	public boolean setZip(String zip) {
		String zipRegex = "\\d{5}(-\\d{4})?";
		if (zip.matches(zipRegex)) {
			zip_ = zip;
		} 
		else 
		{
			return false;
		}
		return true;
	}

}
