package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import io.realm.RealmObject;

public class PojoSyncData  extends RealmObject implements Parcelable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int PojoSyncDataId;
	private String moduleName;
	private String modifiedDate;

	public PojoSyncData() {
	}

	public int getPojoSyncDataId() {
		return PojoSyncDataId;
	}

	public void setPojoSyncDataId(int pojoSyncDataId) {
		PojoSyncDataId = pojoSyncDataId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	protected PojoSyncData(Parcel in) {
		PojoSyncDataId = in.readInt();
		moduleName = in.readString();
		modifiedDate = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(PojoSyncDataId);
		dest.writeString(moduleName);
		dest.writeString(modifiedDate);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<PojoSyncData> CREATOR = new Creator<PojoSyncData>() {
		@Override
		public PojoSyncData createFromParcel(Parcel in) {
			return new PojoSyncData(in);
		}

		@Override
		public PojoSyncData[] newArray(int size) {
			return new PojoSyncData[size];
		}
	};
}