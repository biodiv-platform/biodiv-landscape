package com.strandls.landscape.pojo.response;

import java.util.List;

public class LandscapeShow {

	private String wktData;
	private List<List<Object>> boundingBox;
	private TemplateTreeStructure contents;

	public LandscapeShow() {
		super();
	}

	public LandscapeShow(String wktData, List<List<Object>> boundingBox, TemplateTreeStructure contents) {
		super();
		this.wktData = wktData;
		this.boundingBox = boundingBox;
		this.contents = contents;
	}

	public String getWktData() {
		return wktData;
	}

	public void setWktData(String wktData) {
		this.wktData = wktData;
	}

	public List<List<Object>> getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(List<List<Object>> boundingBox) {
		this.boundingBox = boundingBox;
	}

	public TemplateTreeStructure getContents() {
		return contents;
	}

	public void setContents(TemplateTreeStructure contents) {
		this.contents = contents;
	}
}
