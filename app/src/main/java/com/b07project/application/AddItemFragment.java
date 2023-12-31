package com.b07project.application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *=
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        Button backtomyshopbutton = view.findViewById(R.id.AddItemBackButton);
        Button additembutton = view.findViewById(R.id.AddItemToStoreButton);
        EditText itemName = view.findViewById(R.id.editTextText);
        EditText itemDescription = view.findViewById(R.id.editTextTextMultiLine);
        EditText itemSpecifications = view.findViewById(R.id.editTextTextMultiLine2);
        EditText itemPrice = view.findViewById(R.id.editTextNumberDecimal);
        itemName.setText("");

        backtomyshopbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                MyShopFragment fragment = MyShopFragment.newInstance(mParam1);
                fr.replace(R.id.StoreOwnerFrameLayout, fragment);
                fr.commit();
            }
        });

        additembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = itemName.getText().toString().trim();
                String description = itemDescription.getText().toString().trim();
                String specification = itemSpecifications.getText().toString().trim();
                float price = Float.valueOf(itemPrice.getText().toString().trim());

                DatabaseReference ref_item = MainActivity.db.getReference("Item");

                Query query = ref_item.orderByChild("name").equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for ( DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (mParam1.equals(snapshot1.child("brand").getValue(String.class))) {
                                    Toast.makeText(getContext(), "Item name already under use. Please choose another", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            Item newItem = new Item(name, description, price, mParam1, specification);
                            newItem.saveItem();
                            itemName.setText("");
                            itemDescription.setText("");
                            itemPrice.setText("");
                            itemSpecifications.setText("");
                            Toast.makeText(getContext(), "Add more items or click Back to go to previous page", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        return view;
    }
}