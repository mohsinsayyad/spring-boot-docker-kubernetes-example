package com.todo.mvc.tasks.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Task")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	public Task(Long id, String title, boolean completed) {
		super();
		this.id = id;
		this.title = title;
		this.completed = completed;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    private String title;
    
    private boolean completed;
    
    // added version to handle optimistic locking.
    @Version
    @JsonIgnore
    private int version;
    
}
