package coms309.MeetMe.Location;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private double longtitubde;
    private double latitude;
    private String street;
    private String city;
    private String state;
    private String country;
    private int zipCode;

    // TODO: decompile address variables (string will be formatted in some way)
    public Address(String address) {
        this.street = null;
    }

    public Address() {
        this.street = null;
    }

    public double getLongtitubde() {
        return longtitubde;
    }

    public void setLongtitubde(double longtitubde) {
        this.longtitubde = longtitubde;
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
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return street + " " + city + ", " + state + " " + country + " " + zipCode;
    }
}
