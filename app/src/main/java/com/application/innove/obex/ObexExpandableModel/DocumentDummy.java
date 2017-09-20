package com.application.innove.obex.ObexExpandableModel;

import com.application.innove.obex.ObexModel.Document;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Abhishek on 9/3/2017.
 */

//Refer to Genre
public class DocumentDummy extends ExpandableGroup<Document> {
	private int iconResId;


	@SerializedName("CheckListID")
	@Expose
	private Integer checkListID;
	@SerializedName("DocID")
	@Expose
	private String docID;
	@SerializedName("CheckListName")
	@Expose
	private String checkListName;
	@SerializedName("Doctype")
	@Expose
	private String doctype;
	@SerializedName("DocName")
	@Expose
	private String docName;

	public Integer getCheckListID() {
		return checkListID;
	}

	public void setCheckListID(Integer checkListID) {
		this.checkListID = checkListID;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}

	public String getCheckListName() {
		return checkListName;
	}

	public void setCheckListName(String checkListName) {
		this.checkListName = checkListName;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}


	public DocumentDummy(String title, List<Document> items) {
		super(title, items);
		this.iconResId = iconResId;
	}

	public int getIconResId() {
		return iconResId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DocumentDummy)) return false;

		DocumentDummy doc = (DocumentDummy) o;

		return getIconResId() == doc.getIconResId();

	}

	@Override
	public int hashCode() {
		return getIconResId();
	}
}
