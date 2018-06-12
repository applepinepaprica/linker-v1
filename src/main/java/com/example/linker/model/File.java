package com.example.linker.model;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_id")
	private int id;
	
	private String name;
	
	private byte[] data;
	
	private int numberOfViews = 0;
	
	private int note_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public int getNote_id() {
		return note_id;
	}

	public void setNote_id(int note_id) {
		this.note_id = note_id;
	}
}
