package com.elitecorelib.deal.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;

public class PojoDealTag  extends RealmObject implements Parcelable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8505990914988871312L;
	private Long tagId;
	private String tagName;
	private String imgPath;
	private String tagDescription;
	private String status;
	private String tagName_ar;

	public PojoDealTag() {
	}

	protected PojoDealTag(Parcel in) {
		if (in.readByte() == 0) {
			tagId = null;
		} else {
			tagId = in.readLong();
		}
		tagName = in.readString();
		imgPath = in.readString();
		tagDescription = in.readString();
		status = in.readString();
		tagName_ar = in.readString();
	}

	public static final Creator<PojoDealTag> CREATOR = new Creator<PojoDealTag>() {
		@Override
		public PojoDealTag createFromParcel(Parcel in) {
			return new PojoDealTag(in);
		}

		@Override
		public PojoDealTag[] newArray(int size) {
			return new PojoDealTag[size];
		}
	};

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getTagDescription() {
		return tagDescription;
	}

	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagName_ar() {
		return tagName_ar;
	}

	public void setTagName_ar(String tagName_ar) {
		this.tagName_ar = tagName_ar;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (tagId == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeLong(tagId);
		}
		dest.writeString(tagName);
		dest.writeString(imgPath);
		dest.writeString(tagDescription);
		dest.writeString(status);
		dest.writeString(tagName_ar);
	}
}
