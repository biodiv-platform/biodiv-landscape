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
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Represents a log entry for downloads within the landscape module.
 * 
 * @author vilay
 */
@Entity
@Table(name = "download_log")
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true, value = { "filePath" })
@Schema(name = "DownloadLog", description = "Represents log entries for downloads, including author, params, file path, status, and more.")
public class DownloadLog implements Serializable {

	private static final long serialVersionUID = 2163691267031587165L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	@Schema(description = "Unique download log identifier", example = "1")
	private Long id;

	@Column(name = "version", columnDefinition = "integer default 2", nullable = false)
	@Schema(description = "Version of the log entry", example = "2")
	private Long version;

	@Column(name = "author_id", nullable = false)
	@Schema(description = "ID of the user who triggered the download", example = "1001")
	private Long authorId;

	@Column(name = "created_on", nullable = false)
	@Schema(description = "Timestamp when this log was created", example = "2024-07-01T14:04:11.000Z")
	private Timestamp createdOn;

	@Column(name = "file_path")
	@Schema(description = "File path of the downloaded/generated file (hidden by @JsonIgnoreProperties if needed)", example = "/data/export/1001_report.csv")
	private String filePath;

	@Column(name = "filter_url", nullable = false)
	@Schema(description = "URL with filters applied for download", example = "https://yourhost/api/xyz?filter=1234")
	private String filterUrl;

	@Column(name = "notes")
	@Schema(description = "Notes/comments about the log entry", example = "Downloaded for annual report")
	private String notes;

	@Column(name = "params_map_as_text")
	@Schema(description = "Download parameters stored as serialized text", example = "{\"language\":\"en\",\"region\":\"IN\"}")
	private String paramsMapAsText;

	@Column(name = "status", nullable = false)
	@Schema(description = "Status of download (e.g. SUCCESS, FAILURE)", example = "SUCCESS")
	private String status;

	@Column(name = "type", nullable = false)
	@Schema(description = "Type/category of the download", example = "CSV")
	private String type;

	@Column(name = "source_type")
	@Schema(description = "Source type of data downloaded", example = "Landscape")
	private String sourceType;

	@Column(name = "offset_param", nullable = false)
	@Schema(description = "Offset used for download (for paged data)", example = "0")
	private Long offsetParam;

	public DownloadLog() {
		super();
	}

	// --- Standard Getters and Setters ---
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilterUrl() {
		return filterUrl;
	}

	public void setFilterUrl(String filterUrl) {
		this.filterUrl = filterUrl;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getParamsMapAsText() {
		return paramsMapAsText;
	}

	public void setParamsMapAsText(String paramsMapAsText) {
		this.paramsMapAsText = paramsMapAsText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Long getOffsetParam() {
		return offsetParam;
	}

	public void setOffsetParam(Long offsetParam) {
		this.offsetParam = offsetParam;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
