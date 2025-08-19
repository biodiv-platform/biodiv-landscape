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
 * Represents the template header entity in the landscape module.
 */
@Entity
@Table(name = "template_header")
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "TemplateHeader", description = "Represents a header section in a landscape template associated with a language.")
public class TemplateHeader implements Serializable {

	private static final long serialVersionUID = -5448950898380114701L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "template_header_id_generator")
	@SequenceGenerator(name = "template_header_id_generator", sequenceName = "template_header_id_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	@Schema(description = "Unique identifier of the template header", example = "1")
	private Long id;

	@Column(name = "template_id")
	@Schema(description = "ID of the associated template", example = "5")
	private Long templateId;

	@Column(name = "language_id")
	@Schema(description = "Language ID associated with this header", example = "1")
	private Long languageId;

	@Column(name = "header", columnDefinition = "text")
	@Schema(description = "Header content text", example = "Introduction")
	private String header;

	@Column(name = "is_deleted")
	@Schema(description = "Indicates whether the header is deleted", example = "false")
	private Boolean isDeleted;

	public TemplateHeader() {
	}

	public TemplateHeader(Long id, Long templateId, Long languageId, String header, Boolean isDeleted) {
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
