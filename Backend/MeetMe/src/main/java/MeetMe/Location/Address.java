package MeetMe.Location;

public class Address {
private double longtitubde;
private double latitude;
private String street;
private String city;
private String state;
private String country;
private int zipCode;

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

    public String getAdress(){
        return street +", " + city + ", " + state +", " +country + " " +zipCode;
    }
}
