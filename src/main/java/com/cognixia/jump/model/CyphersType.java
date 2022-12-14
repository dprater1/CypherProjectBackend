package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class CyphersType implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // incrementation will use auto increment
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String introduction;	
	
	@JsonManagedReference
	@OneToMany(mappedBy = "cypherType", targetEntity = Cyphers.class)
	private List<Cyphers> cypher;
	
	public CyphersType() {

	}

	public CyphersType(Long id, String name, String introduction, List<Cyphers> cypher) {
		super();
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.cypher = cypher;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public List<Cyphers> getCypher() {
		return cypher;
	}

	public void setCypher(List<Cyphers> cypher) {
		this.cypher = cypher;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(cypher, id, introduction, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CyphersType other = (CyphersType) obj;
		return Objects.equals(cypher, other.cypher) && Objects.equals(id, other.id)
				&& Objects.equals(introduction, other.introduction) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "CyphersType [id=" + id + ", name=" + name + ", introduction=" + introduction + ", cypher=" + cypher
				+ "]";
	}
	
	
	
}
