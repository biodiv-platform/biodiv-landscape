package com.strandls.landscape.pojo.response;

import java.util.ArrayList;
import java.util.List;

public class TemplateTreeStructure {

	private Long id;
	
	private String header;

	private String content;
	
	private List<TemplateTreeStructure> childs;
	
	public TemplateTreeStructure(Long id) {
		this(id, new ArrayList<TemplateTreeStructure>());
	}
	public TemplateTreeStructure(Long id, List<TemplateTreeStructure> childs) {
		this.id = id;
		this.childs = childs;
	}

	public Long getId() {
		return id;
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<TemplateTreeStructure> getChilds() {
		return childs;
	}
	public void setChilds(List<TemplateTreeStructure> childs) {
		this.childs = childs;
	}
	public void addChild(TemplateTreeStructure child) {
		this.childs.add(child);
	}
}
