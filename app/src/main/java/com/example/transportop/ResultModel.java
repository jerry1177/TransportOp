package com.example.transportop;

/**
 * This class just holds the results that you
 * want to display to the user after you get
 * the list of stations to stop at
 */
public class ResultModel {
    private String streetAddres;
    private String price;
    private String amount;
    ResultModel() {
        streetAddres = "";
        price = "";
        amount = "";
    }
    ResultModel(String streetAddres, String price, String amount) {
        this.streetAddres = streetAddres;
        this.price = price;
        this.amount = amount;
    }

    // set methods
    public void setAmount(String amount) { this.amount = amount; }
    public void setPrice(String price) { this.price = price; }
    public void setStreetAddres(String streetAddres) { this.streetAddres = streetAddres; }

    // get methods
    public String getAmount() { return amount; }
    public String getPrice() { return price; }
    public String getStreetAddres() { return streetAddres; }
}
