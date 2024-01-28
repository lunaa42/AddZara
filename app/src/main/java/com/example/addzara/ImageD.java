package com.example.addzara;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageD {
        public static void downloadImageAndSaveToGallery(Context context, String imageUrl, String title, String description) {
            // Define the target directory
            File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(picturesDirectory, title + ".jpg");

            // Create a Picasso target
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    // Save the bitmap to the file
                    try {
                        OutputStream fos = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();

                        // Add image to the gallery
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, title);
                        values.put(MediaStore.Images.Media.DESCRIPTION, description);
                        values.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
                        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                        // Display a success message
                        Toast.makeText(context, "Image downloaded and saved to gallery", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        // Display an error message
                        Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    // Display an error message
                    Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    // You can handle placeholder preparation here if needed
                }
            };

            // Load the image using Picasso and pass the target
            Picasso.get().load(imageUrl).into(target);
        }
    }

