package com.example.linker.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "note")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "note_id")
	private int id;

	@Column(unique = true)
	private String url;

	@NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be less than 40 characters")
	private String name;

    @Size(max = 5000, message = "Text must be less than 5000 characters")
	private String text;

	private int numberOfViews = 0;

	@NotNull(message = "Max number of views cannot be null")
	@Min(value = 1, message = "Max number of views should not be less than 1")
    @Max(value = 65535, message = "Max number of views should not be greater than 65535")
	private Integer maxNumberOfViews;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "file_id")
	private File file;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user = null;
	
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

	public Integer getMaxNumberOfViews() {
		return maxNumberOfViews;
	}

	public void setMaxNumberOfViews(Integer maxNumberOfViews) {
		this.maxNumberOfViews = maxNumberOfViews;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		if (maxNumberOfViews == null) {
			if (other.maxNumberOfViews != null)
				return false;
		} else if (!maxNumberOfViews.equals(other.maxNumberOfViews))
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
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}