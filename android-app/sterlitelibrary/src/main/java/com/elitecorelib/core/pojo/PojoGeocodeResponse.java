package com.elitecorelib.core.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PojoGeocodeResponse {
    private String status;
    private List<PojoGeocode> results = new ArrayList<PojoGeocode>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PojoGeocode> getResults() {
        return results;
    }

    public void setResults(List<PojoGeocode> results) {
        this.results = results;
    }

    public class PojoGeocode {
        private Collection<String> types = new ArrayList<String>();
        private String formatted_address;
        private Collection<PojoGeoAddressComponent> address_components = new ArrayList<PojoGeoAddressComponent>();
        private PojoGeometry geometry;
        private boolean partialMatch;

        public Collection<String> getTypes() {
            return types;
        }

        public void setTypes(Collection<String> types) {
            this.types = types;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public Collection<PojoGeoAddressComponent> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(Collection<PojoGeoAddressComponent> address_components) {
            this.address_components = address_components;
        }

        public PojoGeometry getGeometry() {
            return geometry;
        }

        public void setGeometry(PojoGeometry geometry) {
            this.geometry = geometry;
        }

        public boolean isPartialMatch() {
            return partialMatch;
        }

        public void setPartialMatch(boolean partialMatch) {
            this.partialMatch = partialMatch;
        }
    }

    public class PojoGeoAddressComponent {


        private String long_name;
        private String short_name;
        private Collection<String> types = new ArrayList<String>();

        public String getLong_name() {
            return long_name;
        }

        public void setLong_name(String long_name) {
            this.long_name = long_name;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }


        public Collection<String> getTypes() {
            return types;
        }

        public void setTypes(Collection<String> types) {
            this.types = types;
        }
    }

    public class PojoGeometry {
        private PojoGeoLocation location;
        private String locationType;
        private PojoGeoArea viewport;
        private PojoGeoArea bounds;

        public PojoGeoLocation getLocation() {
            return location;
        }

        public void setLocation(PojoGeoLocation location) {
            this.location = location;
        }

        public String getLocationType() {
            return locationType;
        }

        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }

        public PojoGeoArea getViewport() {
            return viewport;
        }

        public void setViewport(PojoGeoArea viewport) {
            this.viewport = viewport;
        }

        public PojoGeoArea getBounds() {
            return bounds;
        }

        public void setBounds(PojoGeoArea bounds) {
            this.bounds = bounds;
        }
    }

    public class PojoGeoArea {
        private PojoGeoLocation southWest;
        private PojoGeoLocation northEast;

        public PojoGeoLocation getSouthWest() {
            return southWest;
        }

        public void setSouthWest(PojoGeoLocation southWest) {
            this.southWest = southWest;
        }

        public PojoGeoLocation getNorthEast() {
            return northEast;
        }

        public void setNorthEast(PojoGeoLocation northEast) {
            this.northEast = northEast;
        }
    }

    public class PojoGeoLocation {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }


}


