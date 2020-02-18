package com.strandls.landscape.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

/**
 * This class represent the each page field with its template id, protected area id
 * @author vilay
 *
 */
@Entity
@Table(name = "page_field")
@XmlRootElement
@JsonIgnoreProperties
@ApiModel("PageField")
public class PageField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6322115578128247464L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_id_generator")
    @SequenceGenerator(name = "field_id_generator", sequenceName = "field_id_seq", allocationSize = 50)
    @Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "template_id")
	private Long templateId;
	
	@Column(name = "protected_area_id")
	private Long protectedAreaId;
	
	@Column(name = "author_id")
	private Long authorId;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	public PageField() {
	}
	
	public PageField(Long id, Long templateId, Long protectedAreaId, Long authorId, Timestamp createdOn,
			Timestamp modifiedOn, Boolean isDeleted) {
		super();
		this.id = id;
		this.templateId = templateId;
		this.protectedAreaId = protectedAreaId;
		this.authorId = authorId;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
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

	public Long getProtectedAreaId() {
		return protectedAreaId;
	}

	public void setProtectedAreaId(Long protectedAreaId) {
		this.protectedAreaId = protectedAreaId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
