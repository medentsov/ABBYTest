package com.example.abbytest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.abbytest.Helper.Cat;
import com.example.abbytest.R;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Cat> {
    private Context Context;
    private List<Cat> cats;
    private int resource;

    /**
     * constructor of the class
     * @param context
     * @param resource
     * @param objects
     */
    public CustomAdapter(Context context, int resource, List<Cat> objects) {
        super(context, resource, objects);
        this.Context = context;
        this.resource = resource;
        this.cats = objects;
    }

    /**
     * various methods used to get specified fields
     * and atributes of pojo class
     * @param position
     * @return
     */
    public Bitmap getCatPhoto(int position) { return cats.get(position).getCatPhoto(); }

    public String getCatName(int position) { return cats.get(position).getCatName(); }

    public String getCatDescription(int position) { return cats.get(position).getCatDescription(); }

    public int getCount() {return cats.size();}

    public Cat getItem(int arg0) { return cats.get(arg0); }

    public long getItemId(int position) {return position;}

    /**
     * this method creates viewholder and defines the separate
     * view items of the listview and loads resources to
     * them
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(Context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.catName);
            holder.photo = convertView.findViewById(R.id.catPhoto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cat singleCat = getItem(position);

        holder.photo.setImageBitmap(singleCat.getCatPhoto());
        holder.name.setText(singleCat.getCatName());

        return convertView;
    }
    /**
     * pojo class to hold views
     */
    class ViewHolder {
        private TextView name;
        private ImageView photo;
    }
}
