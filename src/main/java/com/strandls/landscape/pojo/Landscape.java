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

import io.swagger.annotations.ApiModel;

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
	@SequenceGenerator(name = "landscape_id_generator", sequenceName = "landscape_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "short_name")
	private String shortName;

	@Column(name = "north")
	private Double north;

	@Column(name = "west")
	private Double west;

	@Column(name = "south")
	private Double south;

	@Column(name = "east")
	private Double east;

	@Column(name = "site_number")
	private Long siteNumber;

	public Landscape() {
	}

	public Landscape(Long id, String shortName, Double north, Double west, Double south, Double east, Long siteNumber) {
		super();
		this.id = id;
		this.shortName = shortName;
		this.north = north;
		this.west = west;
		this.south = south;
		this.east = east;
		this.siteNumber = siteNumber;
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

	public Double getNorth() {
		return north;
	}

	public void setNorth(Double north) {
		this.north = north;
	}

	public Double getWest() {
		return west;
	}

	public void setWest(Double west) {
		this.west = west;
	}

	public Double getSouth() {
		return south;
	}

	public void setSouth(Double south) {
		this.south = south;
	}

	public Double getEast() {
		return east;
	}

	public void setEast(Double east) {
		this.east = east;
	}

	public Long getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(Long siteNumber) {
		this.siteNumber = siteNumber;
	}

}
