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
 * Represents a language available in the system.
 */
@Entity
@Table(name = "language")
@XmlRootElement
@JsonIgnoreProperties
@Schema(name = "Language", description = "Represents a language, with ISO codes and regional info.")
public class Language implements Serializable {

	private static final long serialVersionUID = -2399965291228552167L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_id_generator")
	@SequenceGenerator(name = "language_id_generator", sequenceName = "language_id_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	@Schema(description = "Unique identifier for the language", example = "1")
	private Long id;

	@Column(name = "name")
	@Schema(description = "Human-readable language name", example = "Hindi")
	private String name;

	@Column(name = "three_letter_code", unique = true)
	@Schema(description = "ISO 639-2 three-letter code", example = "hin")
	private String threeLetterCode;

	@Column(name = "two_letter_code")
	@Schema(description = "ISO 639-1 two-letter code", example = "hi")
	private String twoLetterCode;

	@Column(name = "is_dirty")
	@Schema(description = "Flag used to indicate a dirty (unsynced/needs-check) language", example = "false")
	private Boolean isDirty;

	@Column(name = "region")
	@Schema(description = "Geographic or cultural region associated", example = "IN")
	private String region;

	public Language() {
	}

	public Language(Long id, String name, String threeLetterCode, String twoLetterCode, Boolean isDirty,
			String region) {
		this.id = id;
		this.name = name;
		this.threeLetterCode = threeLetterCode;
		this.twoLetterCode = twoLetterCode;
		this.isDirty = isDirty;
		this.region = region;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThreeLetterCode() {
		return threeLetterCode;
	}

	public void setThreeLetterCode(String threeLetterCode) {
		this.threeLetterCode = threeLetterCode;
	}

	public String getTwoLetterCode() {
		return twoLetterCode;
	}

	public void setTwoLetterCode(String twoLetterCode) {
		this.twoLetterCode = twoLetterCode;
	}

	public Boolean getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(Boolean isDirty) {
		this.isDirty = isDirty;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
