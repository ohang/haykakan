package com.ohanyan.goro.armfilms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Goro on 07.03.2018.
 */

public class GridAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] Version;
    Integer[] image;
    LayoutInflater inflater;

    public GridAdapter(Context context, String[] version, Integer[] image)
    {   this.context = context;
        this.Version = version;
        this.image = image;
    }
    @Override public int getCount() {
        return Version.length;
    }

    @Override public Object getItem(int position) { return Version[position]; }

    @Override public long getItemId(int position) { return 0; }
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView gridtext;
        ImageView gridimage;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.grid_item, parent, false);
        gridtext = (TextView) itemView.findViewById(R.id.griditem_text);
        gridimage = (ImageView) itemView.findViewById(R.id.griditem_image);
        gridtext.setText(Version[position]);




        Bitmap drawableImage = BitmapFactory.decodeResource(context.getResources(), this.image[position]);
        Bitmap bitmap = Bitmap.createScaledBitmap(drawableImage, 150, 250, false);
        Drawable drawableScaled = new BitmapDrawable(context.getResources(), bitmap);
       gridimage.setImageDrawable(drawableScaled);

       // Picasso.with(context).load("http://arm-film.ru/uploads/posts/2017-09/1505805385_super-mama-2.jpg").into(gridimage);


        return itemView;


    }





}
