package com.example.accessingdatamysql.Classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Author {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String firstname;

	private String lastname;

	private String biography;

	private String publisher;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getFirstName(){
		return firstname;
	}

	public void setFirstName(String firstname){
		this.firstname = firstname;
	}

	public String getLastName(){
		return lastname;
	}

	public void setLastName(String lastname){
		this.lastname = lastname;
	}

	public String getBiography(){
		return biography;
	}

	public void setBiography(String biography){
		this.biography = biography;
	}

	public String getPublisher(){
		return publisher;
	}

	public void setPublisher(String publisher){
		this.publisher = publisher;
	}
}
