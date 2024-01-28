package com.example.addzara;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class Zara implements Parcelable {
    private String Product;
    private String size;

    public Zara() {
    }

    protected Zara(Parcel in) {
        Product = in.readString();
        size = in.readString();
        colour = in.readString();
        price = in.readString();
        description = in.readString();
        photo = in.readString();
    }

    public static final Creator<Zara> CREATOR = new Creator<Zara>() {
        @Override
        public Zara createFromParcel(Parcel in) {
            return new Zara(in);
        }

        @Override
        public Zara[] newArray(int size) {
            return new Zara[size];
        }
    };

    @Override
    public String toString() {
        return "Zara{" +
                "Product='" + Product + '\'' +
                ", size='" + size + '\'' +
                ", colour='" + colour + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    public Zara(String product, String size, String colour, String price, String description, String photo) {
        Product = product;
        this.size = size;
        this.colour = colour;
        this.price = price;
        this.description = description;
        this.photo = photo;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
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

    private String colour;
    private String price;
    private String description;
    private String photo;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Product);
        parcel.writeString(size);
        parcel.writeString(colour);
        parcel.writeString(price);
        parcel.writeString(description);
        parcel.writeString(photo);
    }
}
