package com.elitecorelib.core.pojo;

import java.io.Serializable;

public class PojoVersionData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -318273943898415660L;

    private Long versionId;
    private String versionType;
    private String version;
    private String releasedate;
    private String forceUpdate;
    private String url;

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReleaseDate() {
        return releasedate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releasedate = releaseDate;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String uRL) {
        url = uRL;
    }

    @Override
    public String toString() {
        return "VersionData [versionId=" + versionId + ", versionType=" + versionType + ", version=" + version + ", releaseDate=" + releasedate
                + ", forceUpdate=" + forceUpdate + ", URL=" + url + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((forceUpdate == null) ? 0 : forceUpdate.hashCode());
        result = prime * result + ((releasedate == null) ? 0 : releasedate.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((versionId == null) ? 0 : versionId.hashCode());
        result = prime * result + ((versionType == null) ? 0 : versionType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PojoVersionData other = (PojoVersionData) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (forceUpdate == null) {
            if (other.forceUpdate != null)
                return false;
        } else if (!forceUpdate.equals(other.forceUpdate))
            return false;
        if (releasedate == null) {
            if (other.releasedate != null)
                return false;
        } else if (!releasedate.equals(other.releasedate))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        if (versionId == null) {
            if (other.versionId != null)
                return false;
        } else if (!versionId.equals(other.versionId))
            return false;
        if (versionType == null) {
            if (other.versionType != null)
                return false;
        } else if (!versionType.equals(other.versionType))
            return false;
        return true;
    }

}
