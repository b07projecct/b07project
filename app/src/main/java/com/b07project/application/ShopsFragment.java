package com.b07project.application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.AbstractListDetailFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopsFragment extends Fragment implements ShopsAdapter.ViewShopClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<TestStore> storeArr;
    private RecyclerView shopsRecycler;

    public ShopsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopsFragment newInstance(String param1, String param2) {
        ShopsFragment fragment = new ShopsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shops, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();

        shopsRecycler = view.findViewById(R.id.ShopsRecyclerView);
        shopsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        shopsRecycler.setHasFixedSize(true);
        //Take note of this line right below
        ShopsAdapter shopsAdapter = new ShopsAdapter(getContext(),storeArr,this);
        shopsRecycler.setAdapter(shopsAdapter);
        shopsAdapter.notifyDataSetChanged();

    }

    private void dataInitialize(){

        storeArr = new ArrayList<>();
        storeArr.add(new TestStore("Store1"));
        storeArr.add(new TestStore("Store2"));
        storeArr.add(new TestStore("Store1"));
        storeArr.add(new TestStore("Store2"));
        storeArr.add(new TestStore("Store1"));
        storeArr.add(new TestStore("Store2"));
        storeArr.add(new TestStore("Store1"));
        storeArr.add(new TestStore("Store2"));
        storeArr.add(new TestStore("Store1"));
        storeArr.add(new TestStore("Store2"));
        storeArr.add(new TestStore("Store1"));
        storeArr.add(new TestStore("Store2"));
    }

    @Override
    public void ViewShopClick(String ViewStoreName) {
        Fragment fragment = ShopperShopFragment.newInstance(ViewStoreName);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ShopperFrameLayout, fragment, "ShopperViewShopView");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}