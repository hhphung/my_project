package coms309.MeetMe.Stringy;

public class Stringy {

    // https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
    // Returns a string of length n with random characters.
    public static String getRandom(int n) {
  
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }

    public static String success() {
        return "{\"message\":\"Success\"}";
    }

    public static String error(String reason) {
        return "{\"message\":\"Error\", \"reason\": \"" + reason + "\"}";
    }

}
