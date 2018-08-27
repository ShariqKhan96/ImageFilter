package com.webxert.imagefilter.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.webxert.imagefilter.Interface.AddTextFragmentListener;
import com.webxert.imagefilter.Interface.ColorPickListener;
import com.webxert.imagefilter.R;
import com.webxert.imagefilter.adapter.ColorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTextFragment extends BottomSheetDialogFragment implements ColorPickListener {

    EditText editText;
    RecyclerView recyclerView;
    Button button;

    AddTextFragmentListener addTextFragmentListener;

    public void setAddTextFragmentListener(AddTextFragmentListener addTextFragmentListener) {
        this.addTextFragmentListener = addTextFragmentListener;
    }

    int colorSelected = Color.parseColor("#000000");

    static AddTextFragment instance;

    public static AddTextFragment getInstance() {
        if (instance == null)
            instance = new AddTextFragment();
        return instance;
    }

    public AddTextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_text, container, false);
        button = view.findViewById(R.id.btn_add_text);
        recyclerView = view.findViewById(R.id.color_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        editText = view.findViewById(R.id.text_size);

        ColorAdapter colorAdapter = new ColorAdapter(getContext(), this);
        recyclerView.setAdapter(colorAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextFragmentListener.onAddTextButtonClick(editText.getText().toString(), colorSelected);
            }
        });


        return view;
    }

    @Override
    public void onColorSelect(int color) {
        colorSelected = color;
    }
}
