package coms309.MeetMe.Location;

import javax.persistence.Embeddable;

@Embeddable
public class Location {
    private double longitude;
    private double latitude;
    private String street;
    private String city;
    private String state;
    private String country;
    private int zipcode;

    public Location(String street, String city, String state, int zipcode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
    }

    public Location() {
        this.street = "";
        this.city = "";
        this.state = "";
        this.zipcode = 0;
        this.country = "";
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipCode() {
        return zipcode;
    }

    public void setZipCode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return street + " " + city + ", " + state + " " + country + " " + zipcode;
    }
}
