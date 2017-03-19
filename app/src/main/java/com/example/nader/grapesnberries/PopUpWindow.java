package com.example.nader.grapesnberries;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class PopUpWindow extends DialogFragment{

    private TextView popUpText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.pop_up_view, container);

        popUpText = (TextView)view.findViewById(R.id.pop_up_desc);

        byte[] b = getArguments().getByteArray("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
        view.setBackground(ob);

        popUpText.setText(getArguments().getCharSequence("description"));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(500,800);
    }
}
