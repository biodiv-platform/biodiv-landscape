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
 * The list of all the landscape module
 * 
 * @author vilay
 */
@Entity
@Table(name = "landscape")
@XmlRootElement
@JsonIgnoreProperties
@Schema(name = "Landscape", description = "Represents a landscape entity, used in the landscape module.")
public class Landscape implements Serializable {

	private static final long serialVersionUID = -3735748655429322237L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "landscape_id_generator")
	@SequenceGenerator(name = "landscape_id_generator", sequenceName = "landscape_id_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	@Schema(description = "Unique identifier of the landscape", example = "1")
	private Long id;

	@Column(name = "short_name")
	@Schema(description = "Short name for the landscape", example = "Sundarbans")
	private String shortName;

	@Column(name = "site_number")
	@Schema(description = "Site number for the landscape", example = "12")
	private Long siteNumber;

	@Column(name = "geo_entity_id")
	@Schema(description = "ID of the connected geo-entity", example = "248")
	private Long geoEntityId;

	@Column(name = "thumbnail_path")
	@Schema(description = "Path to the landscape's thumbnail image", example = "/img/landscape_19.jpg")
	private String thumbnailPath;

	@Column(name = "is_deleted")
	@Schema(description = "True if deleted (soft delete)", example = "false")
	private Boolean isDeleted;

	public Landscape() {
	}

	public Landscape(Long id, String shortName, Long siteNumber, Long geoEntityId, String thumbnailPath,
			Boolean isDeleted) {
		this.id = id;
		this.shortName = shortName;
		this.siteNumber = siteNumber;
		this.geoEntityId = geoEntityId;
		this.thumbnailPath = thumbnailPath;
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Long getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(Long siteNumber) {
		this.siteNumber = siteNumber;
	}

	public Long getGeoEntityId() {
		return geoEntityId;
	}

	public void setGeoEntityId(Long geoEntityId) {
		this.geoEntityId = geoEntityId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
