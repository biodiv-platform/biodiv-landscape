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
 * Represents the position of each field in the landscape module. parentId is 0
 * for the root element.
 *
 * @author vilay
 */
@Entity
@Table(name = "field_template")
@JsonIgnoreProperties // to ignore unknown JSON properties
@Schema(name = "FieldTemplate", description = "Represents a field's location in a landscape template. 'parentId' is 0 for the root/root-level element.")
public class FieldTemplate implements Serializable {

	private static final long serialVersionUID = 4224737646969768647L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_template_id_generator")
	@SequenceGenerator(name = "field_template_id_generator", sequenceName = "field_template_id_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	@Schema(description = "Unique identifier of the field template", example = "1")
	private Long id;

	@Column(name = "parent_id", nullable = false)
	@Schema(description = "Parent field ID (0 for root)", example = "0")
	private Long parentId;

	@Column(name = "field_index", nullable = false)
	@Schema(description = "Index of the field among siblings", example = "5")
	private Long fieldIndex;

	@Column(name = "created_on")
	@Schema(description = "Creation timestamp", example = "2024-06-20T12:23:34.456Z")
	private Timestamp createdOn;

	@Column(name = "modified_on")
	@Schema(description = "Last modification timestamp", example = "2024-07-01T10:41:16.123Z")
	private Timestamp modifiedOn;

	@Column(name = "is_deleted")
	@Schema(description = "Flag indicating if the record is deleted", example = "false")
	private Boolean isDeleted;

	public FieldTemplate() {
	}

	public FieldTemplate(Long id, Long parentId, Long fieldIndex, Timestamp createdOn, Timestamp modifiedOn,
			Boolean isDeleted) {
		this.id = id;
		this.parentId = parentId;
		this.fieldIndex = fieldIndex;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(Long fieldIndex) {
		this.fieldIndex = fieldIndex;
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
