package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cyphers implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // incrementation will use auto increment
	private Long id;

	@Column(nullable = false)
	private String answer;
	
	@Column(nullable = false)
	private String question;
	
	@Column(nullable = false)
	private String hints;
	
	@Column(nullable = false)
	private String difficulty;
	
	@ManyToOne
	@JoinColumn(name = "cypherType_id")
	private CyphersType cypherType;
	
	@OneToMany(mappedBy = "cypher", targetEntity = Progress.class)
	private List<Progress> progress;
	
	public Cyphers() {
		
	}

	public Cyphers(Long id, String answer, String question, String hints, String difficulty,
			CyphersType cypherType, List<Progress> progress) {
		super();
		this.id = id;
		this.answer = answer;
		this.question = question;
		this.hints = hints;
		this.difficulty = difficulty;
		this.cypherType = cypherType;
		this.progress = progress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getHints() {
		return hints;
	}

	public void setHints(String hints) {
		this.hints = hints;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public CyphersType getCypherType() {
		return cypherType;
	}

	public void setCypherType(CyphersType cypherType) {
		this.cypherType = cypherType;
	}

	public List<Progress> getProgress() {
		return progress;
	}

	public void setProgress(List<Progress> progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "Cyphers [id=" + id + ", answer=" + answer + ", question=" + question + ", hints=" + hints
				+ ", difficulty=" + difficulty + ", cypherType=" + cypherType + ", progress=" + progress + "]";
	}
	
	
	
	
	
}
