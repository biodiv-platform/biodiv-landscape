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

@Entity
@Table(name = "field_template")
@XmlRootElement
@JsonIgnoreProperties
public class FieldTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4224737646969768647L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_template_id_generator")
    @SequenceGenerator(name = "field_template_id_generator", sequenceName = "field_template_id_seq", allocationSize = 50)
    @Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "parent_id", nullable = false)
	private Long parentId;
	
	@Column(name = "field_index", nullable = false)
	private Long fieldIndex;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	public FieldTemplate() {
	}

	public FieldTemplate(Long id, Long parentId, Long fieldIndex, Timestamp createdOn, Timestamp modifiedOn,
			Boolean isDeleted) {
		super();
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
