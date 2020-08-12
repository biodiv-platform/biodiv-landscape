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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nimbusds.jose.jwk.ThumbprintUtils;

import io.swagger.annotations.ApiModel;

/**
 * The list of all the landscape module
 * @author vilay
 *
 */
@Entity
@Table(name = "landscape")
@XmlRootElement
@JsonIgnoreProperties
@ApiModel("Landscape")
public class Landscape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3735748655429322237L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "landscape_id_generator")
	@SequenceGenerator(name = "landscape_id_generator", sequenceName = "landscape_id_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "short_name")
	private String shortName;

	@Column(name = "site_number")
	private Long siteNumber;
	
	@Column(name = "geo_entity_id")
	private Long geoEntityId;
	
	@Column(name = "thumbnail_path")
	private String thumbnailPath;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	public Landscape() {
	}

	public Landscape(Long id, String shortName, Long siteNumber, Long geoEntityId, String thumbnailPath,
			Boolean isDeleted) {
		super();
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
