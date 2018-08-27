package com.webxert.imagefilter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.webxert.imagefilter.Interface.FiltersListFragmentListener;
import com.webxert.imagefilter.R;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 8/7/2018.
 */

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.MyViewHolder> {

    List<ThumbnailItem> thumbnailItems = new ArrayList<>();
    int selectedIndex = 0;
    FiltersListFragmentListener listener;
    Context context;

    public ThumbnailAdapter(List<ThumbnailItem> thumbnailItems, FiltersListFragmentListener listener, Context context) {

        this.thumbnailItems = thumbnailItems;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final ThumbnailItem item = thumbnailItems.get(position);

        holder.thumbImage.setImageBitmap(item.image);
        holder.thumbImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFilterSelected(item.filter);
                selectedIndex = position;
                notifyDataSetChanged();
            }
        });

        holder.filterName.setText(item.filterName);

        if (selectedIndex == position)
            holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.selected_filter_color));
        else
            holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.normal_color));
    }

    @Override
    public int getItemCount() {
        return thumbnailItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbImage;
        TextView filterName;

        public MyViewHolder(View itemView) {
            super(itemView);
            thumbImage = itemView.findViewById(R.id.thumbnail_image);
            filterName = itemView.findViewById(R.id.filter_name);

        }
    }
}
