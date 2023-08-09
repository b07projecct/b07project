package com.b07project.application;

import static android.content.Intent.getIntent;

import static com.b07project.application.StoreOwnerMain.brandon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private RecyclerView ordersRecyclerView;
    String brand;
    ArrayList<Order> orders;

    public static OrdersFragment newInstance(String brand){
        OrdersFragment fragment = new OrdersFragment();
        fragment.brand = brand;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        this.brand = brandon;//((StoreOwnerMain) getActivity()).getBrand();
        orders = new ArrayList<Order>();
        //TODO: Perform a database fetch using the brand as a query parameter once success callback is called please create an array with each order object
        //TODO: initialize the 'orders' array with the information from database after array creation please call initList();
        ordersRecyclerView = rootView.findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initList();
        return rootView;
    }
    //for testing
    public void initList ()
    {
        OrdersAdapter adapter = new OrdersAdapter(orders);
        ordersRecyclerView.setAdapter(adapter);
    }
    private Order[] createSampleOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add (new Order ("bob1", "mike", "shoe", "Completed",  0));
        orders.add (new Order ("bob2", "chen", "sock", "Ordered",  1));
        Order[] arr = new Order[orders.size()];
        return orders.toArray(arr);
    }
}