package com.study.ssumnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hhylu on 2016-02-04.
 */
public class TabFragment3 extends Fragment {
    private ImageView profileImage;
    private TextView name;
    private TextView age;
    private TextView gender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        profileImage = (ImageView) view.findViewById(R.id.profileImage);
        name = (TextView) view.findViewById(R.id.name);
        age = (TextView) view.findViewById(R.id.age);
        gender = (TextView) view.findViewById(R.id.gender);

        setInitialProfile();

        return view;
    }

    public void setInitialProfile() {
        //get extra from intent passed on from SignUpActivity.class
        Intent intent = (getActivity()).getIntent();
        String profileImageValue = intent.getStringExtra(SignUpActivity.PROFILEIMAGE_KEY);
        String nameValue = intent.getStringExtra(SignUpActivity.NAME_KEY);
        String ageValue = intent.getStringExtra(SignUpActivity.AGE_KEY);
        String genderValue = intent.getStringExtra(SignUpActivity.GENDER_KEY);

        new DownloadImageTask(profileImage).execute(profileImageValue);

        name.setText(nameValue);
        age.setText(ageValue);
        gender.setText(genderValue);
    }
}