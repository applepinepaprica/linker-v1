package com.example.linker.model;

import javax.persistence.*;

@Entity
@Table(name = "note")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "note_id")
	private int id;
	
	@Column(unique = true)
	private String url;
	
	private String name;

	private String text;
	
	private int numberOfViews = 0;
	
	private int maxNumberOfViews;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public int getMaxNumberOfViews() {
		return maxNumberOfViews;
	}

	public void setMaxNumberOfViews(int maxNumberOfViews) {
		this.maxNumberOfViews = maxNumberOfViews;
	}
}
