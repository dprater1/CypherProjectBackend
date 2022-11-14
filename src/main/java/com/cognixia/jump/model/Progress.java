package com.cognixia.jump.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Progress implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // incrementation will use auto increment
	private Long id;
	
	@Column(nullable = false)
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "cypher_id")
	private Cyphers cypher;

	public Progress() {

	}

	public Progress(Long id, String status, User user, Cyphers cypher) {
		super();
		this.id = id;
		this.status = status;
		this.user = user;
		this.cypher = cypher;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cyphers getCypher() {
		return cypher;
	}

	public void setCypher(Cyphers cypher) {
		this.cypher = cypher;
	}

	@Override
	public String toString() {
		return "Progress [id=" + id + ", status=" + status + ", user=" + user + ", cypher=" + cypher + "]";
	}
	
	
	
	
}
