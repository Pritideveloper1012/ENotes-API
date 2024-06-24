package com.notes.entity;






import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 200)
	private String title;
	@Column(length = 1000)
	private String description;
	private Date createdAt; 
	
	 @ManyToOne
	 @JoinColumn(name="user_id",nullable = false)
	 private User user;
	 
	 
	 public Note() {
		this.createdAt =new Date(); 
		}
		
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		 return user;
	}
	public void setUser(User user) {
		 if (user != null) {
	            this.user = user;
	        } else {
	            throw new IllegalArgumentException("User must be provided for Note creation.");
	        }
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", description=" + description + ", createdAt=" + createdAt
				+  ", user=" + user + "]";
	}
	
	
	
	 
	
	

}
