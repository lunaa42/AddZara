package com.example.addzara;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ZaraItem implements Parcelable {
    private String product;
    private String size;
    private String colour;
    private String price;
    private String description;
    private String photo;

    public ZaraItem(String product, String size, String colour, String price, String description, String photo) {
        this.product = product;
        this.size = size;
        this.colour = colour;
        this.price = price;
        this.description = description;
        this.photo = photo;
    }

    public ZaraItem() {
    }
// Constructors, getters, setters, etc.

    // Parcelable implementation
    protected ZaraItem(Parcel in) {
        this.product = in.readString();
        this.price = in.readString();
        this.size = in.readString();
        this.colour = in.readString();
        this.description = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0; // This value can be modified if needed
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product);
        dest.writeString(size);
        dest.writeString(colour);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeString(photo);
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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "ZaraItem{" +
                "product='" + product + '\'' +
                ", size='" + size + '\'' +
                ", colour='" + colour + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}

