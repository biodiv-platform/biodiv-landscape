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

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "field_content")
@XmlRootElement
@JsonIgnoreProperties
public class FieldContent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4224737646969768647L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_content_id_generator")
    @SequenceGenerator(name = "field_content_id_generator", sequenceName = "field_content_id_seq", allocationSize = 50)
    @Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "field_id")
	private Long fieldId;
	
	@Column(name = "language_id")
	private Long languageId;
	
	@Column(name = "content")
    @Type(type = "text")
    private String content;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	public FieldContent() {
	}

	public FieldContent(Long id, Long fieldId, Long languageId, String content, Boolean isDeleted) {
		super();
		this.id = id;
		this.fieldId = fieldId;
		this.languageId = languageId;
		this.content = content;
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
