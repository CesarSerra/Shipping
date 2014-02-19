package com.java102.cesar;


public class Person 
{
	
	public static final Person NOBODY = new Person();
	
	private String firstName_;
	private String lastName_;
	private Address address_;
	private String date_;
	
	public Person() 
	{
		this("","","", Address.HOMELESS);
	}
	
	public Person (String first, String last, String date, Address address) 
	{
		setFirstName(first);
		setLastName(last);
		setAddress(address);
		setDate(date);
	}

	/**
	 * @return the firstName_
	 */
	public String getFirstName() 
	{
		return firstName_;
	}

	/**
	 * @param firstName_ the firstName_ to set
	 */
	public void setFirstName(String firstName) 
	{
		if (firstName == null) 
		{
			firstName_ = "";
		} 
		else 
		{
			firstName_ = firstName;
		}
	}

	/**
	 * @return the lastName_
	 */
	public String getLastName() 
	{
		return lastName_;
	}

	/**
	 * @param lastName_ the lastName_ to set
	 */
	public void setLastName(String lastName) 
	{
		if (lastName == null) 
		{
			lastName_ = "";
		} 
		else 
		{
			lastName_ = lastName;
		}
	}
	public void setDate(String date)
	{
		if (date == null) 
		{
			date_ = "";
		} 
		else 
		{
			date_ = date;
		}
	}
	public String getDate()
	{
		return date_;
	}
	/**
	 * @return the address_
	 */
	public Address getAddress() 
	{
		return address_;
	}

	/**
	 * @param address_ the address_ to set
	 */
	public void setAddress(Address address) 
	{
		if (address == null) 
		{
			address_ = Address.HOMELESS;
		}else {
			address_ = address;
		}		
	}
	
	public boolean equals(Object other) 
	{
		if (other == null || !(other instanceof Person)) 
		{
			return false;
		}
		
		Person otherPerson = (Person) other;
		return firstName_.equalsIgnoreCase(otherPerson.getFirstName())
				&& lastName_.equalsIgnoreCase(otherPerson.getLastName())
				&& address_.equals(otherPerson.getAddress());
	}
	
	public String getName() 
	{
		return firstName_ + " " + lastName_;
	}
}
