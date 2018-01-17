package com.example.webservice.interfaces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ian on 1/16/2018.
 */

public class Details {
    @SerializedName("idworkspace")
        @Expose
        private String idworkspace;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("adress")
        @Expose
        private String adress;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("town")
        @Expose
        private String town;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("user_iduser")
        @Expose
        private String userIduser;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    @SerializedName("email")
        @Expose
        private String email;

        public String getIdworkspace() {
            return idworkspace;
        }

        public void setIdworkspace(String idworkspace) {
            this.idworkspace = idworkspace;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getUserIduser() {
            return userIduser;
        }

        public void setUserIduser(String userIduser) {
            this.userIduser = userIduser;
        }


}
