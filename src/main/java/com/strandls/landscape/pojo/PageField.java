package com.strandls.landscape.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Represents each page field associated with a template and protected area.
 * 
 * @author vilay
 */
@Entity
@Table(name = "page_field")
@JsonIgnoreProperties
@Schema(name = "PageField", description = "Represents a page field, mapping its template, protected area, author, and audit info.")
public class PageField implements Serializable {

	private static final long serialVersionUID = 6322115578128247464L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "page_field_id_generator")
	@SequenceGenerator(name = "page_field_id_generator", sequenceName = "page_field_id_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	@Schema(description = "Unique identifier for the page field", example = "1")
	private Long id;

	@Column(name = "template_id")
	@Schema(description = "ID of the associated field template", example = "10")
	private Long templateId;

	@Column(name = "protected_area_id")
	@Schema(description = "ID of the protected area this field belongs to", example = "123")
	private Long protectedAreaId;

	@Column(name = "author_id")
	@Schema(description = "Authoring user ID", example = "1001")
	private Long authorId;

	@Column(name = "created_on")
	@Schema(description = "Creation timestamp", example = "2024-07-24T10:15:30.456Z")
	private Timestamp createdOn;

	@Column(name = "modified_on")
	@Schema(description = "Last modified timestamp", example = "2024-07-24T12:45:10.888Z")
	private Timestamp modifiedOn;

	@Column(name = "is_deleted")
	@Schema(description = "True if the field is deleted", example = "false")
	private Boolean isDeleted;

	public PageField() {
	}

	public PageField(Long id, Long templateId, Long protectedAreaId, Long authorId, Timestamp createdOn,
			Timestamp modifiedOn, Boolean isDeleted) {
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
