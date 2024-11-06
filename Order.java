package com.example.test3;

public class Order {

    private int id;
    private String CustomerName;
    private String MobileNumber;
    private PizzaSize PizzaSize; // Use the PizzaSize enum
    private int Toppings;

    public Order(int id, String CustomerName, String MobileNumber, PizzaSize pizzaSize, int Toppings) {
        this.id = id;
        this.CustomerName = CustomerName;
        this.MobileNumber = MobileNumber;
        this.PizzaSize = pizzaSize;
        this.Toppings = Toppings;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public PizzaSize getPizzaSize() {
        return PizzaSize;
    }

    public int getToppings() {
        return Toppings;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setCustomerName(String customerName) {
        this.CustomerName = customerName;
    }


    public void setMobileNumber(String mobileNumber) {
        this.MobileNumber = mobileNumber;
    }


    public void setPizzaSize(PizzaSize PizzaSize) {
        this.PizzaSize = PizzaSize;
    }


    public void setToppings(int toppings) {
        this.Toppings = toppings;
    }

    public enum PizzaSize {
        SMALL, MEDIUM, LARGE, XL;
    }

    public double calculateTotalBill() {
        double pizzaPrice = 0.0;

        switch (this.PizzaSize) {
            case LARGE:
                pizzaPrice = 12.00;
                break;
            case MEDIUM:
                pizzaPrice = 10.00;
                break;
            case SMALL:
                pizzaPrice = 8.00;
                break;
            case XL:
                pizzaPrice = 15.00;
                break;
        }

        // Calculate toppings price
        double toppingsPrice = this.Toppings * 1.50;

        // Calculate subtotal
        double subtotal = pizzaPrice + toppingsPrice;

        // Calculate HST (15%)
        double hst = subtotal * 0.15;

        // Total Bill
        return subtotal + hst;
    }
}
