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
@Table(name = "template_header")
@XmlRootElement
@JsonIgnoreProperties
public class TemplateHeader implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5448950898380114701L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_content_id_generator")
    @SequenceGenerator(name = "field_content_id_generator", sequenceName = "field_content_id_seq", allocationSize = 50)
    @Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "template_id")
	private Long templateId;
	
	@Column(name = "language_id")
	private Long languageId;
	
	@Column(name = "header")
    @Type(type = "text")
    private String header;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	public TemplateHeader() {
	}
	
	public TemplateHeader(Long id, Long templateId, Long languageId, String header, Boolean isDeleted) {
		super();
		this.id = id;
		this.templateId = templateId;
		this.languageId = languageId;
		this.header = header;
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
