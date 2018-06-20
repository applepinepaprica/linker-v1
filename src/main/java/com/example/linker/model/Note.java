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
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
	private File file;
	
	private Integer user_id = null;

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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (id != other.id)
			return false;
		if (maxNumberOfViews != other.maxNumberOfViews)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numberOfViews != other.numberOfViews)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
}
