package com.strandls.landscape.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "language")
@XmlRootElement
@JsonIgnoreProperties
public class Language implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2399965291228552167L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_id_generator")
	@SequenceGenerator(name = "language_id_generator", sequenceName = "language_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "three_letter_code")
	private String threeLetterCode;
	
	@Column(name = "two_letter_code")
	private String twoLetterCode;
	
	@Column(name = "is_dirty")
	private Boolean isDirty;
	
	@Column(name = "region")
	private String region;
	
}
