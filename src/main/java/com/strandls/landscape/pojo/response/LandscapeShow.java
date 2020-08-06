package com.strandls.landscape.pojo.response;

import java.util.List;

public class LandscapeShow {
	
	private String wktData;
	private List<List<Object>> boundingBox;
	private TemplateTreeStructure treeStructure;
	
	public LandscapeShow() {
		super();
	}
	
	public LandscapeShow(String wktData, List<List<Object>> boundingBox2, TemplateTreeStructure treeStructure) {
		super();
		this.wktData = wktData;
		this.boundingBox = boundingBox2;
		this.treeStructure = treeStructure;
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
	public TemplateTreeStructure getTreeStructure() {
		return treeStructure;
	}
	public void setTreeStructure(TemplateTreeStructure treeStructure) {
		this.treeStructure = treeStructure;
	}
}
