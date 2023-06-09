package com.project.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "image_table")
public class Image {
	
	// image page1- 1 to 16
	//page 17-24
	//25-38
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private Blob image;

    private Date date = new Date();

    private String heading;
    private String content;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", image=" + image + ", date=" + date + ", heading=" + heading + ", content="
				+ content + "]";
	}
	public Image(Long id, Blob image, Date date, String heading, String content) {
		super();
		this.id = id;
		this.image = image;
		this.date = date;
		this.heading = heading;
		this.content = content;
	}
	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
