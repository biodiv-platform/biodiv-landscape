package com.strandls.landscape.pojo.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Recursive template tree structure for nested landscape templates.
 */
@Schema(name = "TemplateTreeStructure", description = "A recursive tree structure for representing nested template sections/fields. Each node can have child nodes.")
public class TemplateTreeStructure {

	@Schema(description = "Unique identifier for this node", example = "1")
	private Long id;

	@Schema(description = "Associated PageField id", example = "200")
	private Long pageFieldId;

	@Schema(description = "Header or title for the section/field", example = "Habitat Details")
	private String header;

	@Schema(description = "Content/detail of the template node", example = "This section describes ...")
	private String content;

	@Schema(description = "Child nodes of this tree node (may be empty)", implementation = TemplateTreeStructure.class)
	private List<TemplateTreeStructure> childs;

	public TemplateTreeStructure(Long id) {
		this(id, new ArrayList<>());
	}

	public TemplateTreeStructure(Long id, List<TemplateTreeStructure> childs) {
		this.id = id;
		this.childs = childs;
	}

	public Long getId() {
		return id;
	}

	public Long getPageFieldId() {
		return pageFieldId;
	}

	public void setPageFieldId(Long pageFieldId) {
		this.pageFieldId = pageFieldId;
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
