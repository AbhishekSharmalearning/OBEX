
package com.application.innove.obex.ObexModel;

import android.os.Parcel;
import android.os.Parcelable;

//Refer to ARTIST
public class Document implements Parcelable {

    private String name;
    private boolean isFavorite;
    private String docType;
    private String docID;

    public Document(String name, String docType,String docID, boolean isFavorite) {
        this.name = name;
        this.docType = docType;
        this.docID = docID;
        this.isFavorite = isFavorite;
    }


    public Document(Parcel in) {
        name = in.readString();
        docType = in.readString();
        docID =in.readString();
        /*checkListName = in.readString ();
        doctype = in.readString ();
        docName = in.readString ()*/
        ;
    }

    public String getName() {
        return name;
    }

    public String getdoctype() {
        return docType;
    }

    public String getDocID() {
        return docID;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;

        Document document = (Document) o;

        if (isFavorite() != document.isFavorite()) return false;
        return getName() != null ? getName().equals(document.getName()) : document.getName() == null;

    }



    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (isFavorite() ? 1 : 0);
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(docType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };



    public void setDocType(String docType) {
        this.docType = docType;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

}
