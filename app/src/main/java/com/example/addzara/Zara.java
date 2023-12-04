package com.example.addzara;

public class Zara {
    private String product;
    private String size;
    private String colour;
    private String phone;

    public Zara(String product, String size, String colour, String phone) {
        this.product = product;
        this.size = size;
        this.colour = colour;
        this.phone = phone;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAddress() {
        return colour;
    }

    public void setAddress(String address) {
        this.colour = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Zara{" +
                "product='" + product + '\'' +
                ", size='" + size + '\'' +
                ", colour='" + colour + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Zara() {
    }
}
