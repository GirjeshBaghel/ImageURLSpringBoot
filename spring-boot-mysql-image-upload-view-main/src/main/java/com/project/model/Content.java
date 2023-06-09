package com.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contents")
public class Content {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String data;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Content [id=" + id + ", data=" + data + "]";
	}
	public Content(int id, String data) {
		super();
		this.id = id;
		this.data = data;
	}
	public Content() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
