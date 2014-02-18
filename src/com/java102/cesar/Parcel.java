package com.java102.cesar;

public class Parcel
{
	private String time;
	private Person onePerson;
	private int ID;
	
	public Parcel()
	{
		this(0,"",Person.NOBODY);
	}
	public Parcel(int ID, String time, Person onePerson)
	{
		this.ID = ID;
		this.time = time;
		setPerson(onePerson);
	}
	public void setPerson(Person person) 
	{
		if (person == null) {
			onePerson = Person.NOBODY;
		}
		else 
		{
			onePerson = person;
		}
	}
	public String getTime()
	{
		return time;
	}
	public int getID()
	{
		return ID;
	}
	public Person getPerson()
	{
		return onePerson;
	}
}
