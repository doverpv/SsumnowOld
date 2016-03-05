package com.study.ssumnow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.cardstack.CardStack;
import com.study.cardstack.DragGestureDetector;

/**
 * Created by hhylu on 2016-02-04.
 */
public class TabFragment1 extends Fragment {
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    private DragGestureDetector.CardTouchListener activity;

    public void setActivity(DragGestureDetector.CardTouchListener h) {
        activity = h;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        mCardStack = (CardStack)view.findViewById(R.id.card_container);

        mCardStack.setContentResource(R.layout.card_content);
        mCardStack.setActivity(activity);
        mCardStack.setStackMargin(20);

        mCardAdapter = new CardsDataAdapter(view.getContext());
        mCardAdapter.add("test1");
        mCardAdapter.add("test2");
        mCardAdapter.add("test3");
        mCardAdapter.add("test4");
        mCardAdapter.add("test5");


        mCardStack.setAdapter(mCardAdapter);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
