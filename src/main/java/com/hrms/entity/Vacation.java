package com.hrms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table
public class Vacation implements Serializable {

	private static final long serialVersionUID = 460559650376521906L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	private Date fromDate;

	private Date toDate;

	@CreatedDate
	private Date createDate;

}
