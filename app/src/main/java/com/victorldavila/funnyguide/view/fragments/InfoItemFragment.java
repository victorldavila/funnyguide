package com.victorldavila.funnyguide.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.R;

public class InfoItemFragment extends Fragment {

  public InfoItemFragment() {
    // Required empty public constructor
  }

  public static InfoItemFragment newInstance() {
    InfoItemFragment fragment = new InfoItemFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {

    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_detail_item, container, false);
  }
}