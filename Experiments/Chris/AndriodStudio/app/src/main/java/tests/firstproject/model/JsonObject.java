package tests.firstproject.model;

//Class to represent the object we received from the server. (Based on the postman JsonObject Whitney created)
public class JsonObject {
    //The @SerializedName tag allows you to retrieve a tag from the body of the JSON response
    //and call it something else in the program.
    //If I un-comment line 90, I could rename line 91 to anything I want.

    //@SerializedName("name")
    private String name;

    //@SerializedName("Number")
    private Long Number;

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Long number) {
        Number = number;
    }

    public String getName(){
        return name;
    }

    public Long getNumber(){
        return Number;
    }
}
