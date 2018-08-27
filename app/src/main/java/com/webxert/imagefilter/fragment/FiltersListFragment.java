package com.webxert.imagefilter.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webxert.imagefilter.Interface.FilterPressedListener;
import com.webxert.imagefilter.Interface.FiltersListFragmentListener;
import com.webxert.imagefilter.MainActivity;
import com.webxert.imagefilter.R;
import com.webxert.imagefilter.adapter.ThumbnailAdapter;
import com.webxert.imagefilter.utils.BitmapUtils;
import com.webxert.imagefilter.utils.SpaceItemDecorator;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiltersListFragment extends Fragment implements FiltersListFragmentListener {

    RecyclerView recyclerView;
    List<ThumbnailItem> thumbnailItemList;
    private FilterPressedListener filterPressedListener;

    ThumbnailAdapter adapter;

    FiltersListFragmentListener listFragmentListener;

    public void setListFragmentListener(FiltersListFragmentListener listFragmentListener) {
        this.listFragmentListener = listFragmentListener;
    }

    public FiltersListFragment() {
        // Required empty public constructor
    }

    public void setFilterPressedListener(FilterPressedListener filterPressedListener) {
        this.filterPressedListener = filterPressedListener;
       // this.filterPressedListener.onFilterPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filters_list, container, false);

        thumbnailItemList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.filters_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        recyclerView.addItemDecoration(new SpaceItemDecorator(space));
        adapter = new ThumbnailAdapter(thumbnailItemList, this, getActivity());

        recyclerView.setAdapter(adapter);

        displayThumbnail(null);
        return view;
    }

    public void displayThumbnail(final Bitmap bitmap) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Bitmap thumbImage;
                if (bitmap == null) {
                    thumbImage = BitmapUtils.getBitmapFromAsset(getActivity(), MainActivity.pictureName, 100, 100);
                } else
                    thumbImage = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

                if (thumbImage == null)
                    return;
                ThumbnailsManager.clearThumbs();
                thumbnailItemList.clear();

                //adding normal thumbnail first

                ThumbnailItem thumbnailItem = new ThumbnailItem();
                thumbnailItem.image = thumbImage;
                thumbnailItem.filterName = "Normal";
                ThumbnailsManager.addThumb(thumbnailItem);

                List<Filter> filters = FilterPack.getFilterPack(getActivity());

                for (Filter f : filters
                        ) {
                    ThumbnailItem thumb = new ThumbnailItem();
                    thumb.image = thumbImage;
                    thumb.filter = f;
                    thumb.filterName = f.getName();
                    ThumbnailsManager.addThumb(thumb);

                }

                thumbnailItemList.addAll(ThumbnailsManager.processThumbs(getActivity()));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        };

        new Thread(r).start();
    }

    @Override
    public void onFilterSelected(Filter filter) {

        if (listFragmentListener != null)
            listFragmentListener.onFilterSelected(filter);
    }
}
