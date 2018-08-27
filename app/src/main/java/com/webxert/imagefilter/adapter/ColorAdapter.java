package com.webxert.imagefilter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webxert.imagefilter.Interface.ColorPickListener;
import com.webxert.imagefilter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 8/18/2018.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    //creating interface


    Context context;
    List<Integer> colorList;
    ColorPickListener listener;

    public ColorAdapter(Context context, ColorPickListener listener) {
        this.context = context;
        this.colorList = genColorList();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {

        holder.cardView.setCardBackgroundColor(colorList.get(position));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        View view;

        public ColorViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = view.findViewById(R.id.color_card);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onColorSelect(colorList.get(getAdapterPosition()));
                }
            });
        }
    }

    private List<Integer> genColorList() {

        List<Integer> colorlist = new ArrayList<>();
        colorlist.add(Color.parseColor("#800020"));
        colorlist.add(Color.parseColor("#ff8d00"));
        colorlist.add(Color.parseColor("#ffffb2"));
        colorlist.add(Color.parseColor("#204ed3"));
        colorlist.add(Color.parseColor("#f0f7ff"));
        colorlist.add(Color.parseColor("#ffe200"));
        colorlist.add(Color.parseColor("#a39298"));
        colorlist.add(Color.parseColor("#7eb19a"));
        colorlist.add(Color.parseColor("#efe5e9"));
        colorlist.add(Color.parseColor("#e7d8df"));
        colorlist.add(Color.parseColor("#dfcbd4"));
        colorlist.add(Color.parseColor("#d8beca"));
        colorlist.add(Color.parseColor("#d0b1bf"));
        colorlist.add(Color.parseColor("#c8a4b4"));
        colorlist.add(Color.parseColor("#c097aa"));
        colorlist.add(Color.parseColor("#feb4b1"));
        colorlist.add(Color.parseColor("#f69191"));
        colorlist.add(Color.parseColor("#c5eded"));
        colorlist.add(Color.parseColor("#222334"));
        colorlist.add(Color.parseColor("#5f72ff"));
        colorlist.add(Color.parseColor("#e3c5ab"));
        colorlist.add(Color.parseColor("#4f0d17"));
        colorlist.add(Color.parseColor("#ff288d"));

        return colorlist;


    }
}
