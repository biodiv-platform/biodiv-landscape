package com.strandls.landscape.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Contains the actual text of the fields
 *
 * @author vilay
 */
@Entity
@Table(name = "field_content")
@XmlRootElement
@JsonIgnoreProperties
@Schema(name = "FieldContent", description = "Contains actual content text for a field, with language support and deletion flag.")
public class FieldContent implements Serializable {

	private static final long serialVersionUID = 4224737646969768647L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "field_content_id_generator")
	@SequenceGenerator(name = "field_content_id_generator", sequenceName = "field_content_id_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	@Schema(description = "Unique identifier for this field content", example = "1")
	private Long id;

	@Column(name = "field_id")
	@Schema(description = "FieldTemplate ID to which this content is attached", example = "10")
	private Long fieldId;

	@Column(name = "language_id")
	@Schema(description = "Language ID for this content", example = "1")
	private Long languageId;

	@Column(name = "content", columnDefinition = "text")
	@Schema(description = "Actual content/text for the field", example = "This species prefers wetlands.")
	private String content;

	@Column(name = "is_deleted")
	@Schema(description = "Flag showing if this content has been soft-deleted", example = "false")
	private Boolean isDeleted;

	public FieldContent() {
	}

	public FieldContent(Long id, Long fieldId, Long languageId, String content, Boolean isDeleted) {
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
