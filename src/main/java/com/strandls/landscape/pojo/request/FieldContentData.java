package com.strandls.landscape.pojo.request;

public class FieldContentData {

	private Long protectedAreaId;
	private Long languageId;
	private Long templateId;
	private String content;

	public FieldContentData(Long protectedAreaId, Long languageId, Long templateId, String content) {
		super();
		this.protectedAreaId = protectedAreaId;
		this.languageId = languageId;
		this.templateId = templateId;
		this.content = content;
	}

	public Long getProtectedAreaId() {
		return protectedAreaId;
	}

	public void setProtectedAreaId(Long protectedAreaId) {
		this.protectedAreaId = protectedAreaId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templageId) {
		this.templateId = templageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
