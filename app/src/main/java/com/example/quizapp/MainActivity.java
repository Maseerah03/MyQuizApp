package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.Adapters.CategoryAdapter;
import com.example.quizapp.Models.CategoryModel;
import com.example.quizapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;
    ArrayList<CategoryModel> list;
    CategoryAdapter adapter;
    ProgressDialog progressDialog;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);

        if(loadingDialog.getWindow()!=null){

            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadingDialog.setCancelable(false);
        }

        loadingDialog.show();

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.recyCategory.setLayoutManager(layoutManager);

        adapter = new CategoryAdapter(this,list);
        binding.recyCategory.setAdapter(adapter);


        database.getReference().child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String categoryName = dataSnapshot.child("categoryName").getValue(String.class);
                        String categoryImage = dataSnapshot.child("categoryImage").getValue(String.class);
                        String key = dataSnapshot.getKey();
                        Integer setNum = dataSnapshot.child("setNum").getValue(Integer.class);

                        if (categoryName != null && categoryImage != null && key != null && setNum != null) {
                            list.add(new CategoryModel(categoryName, categoryImage, key, setNum));
                        } else {
                            // Handle the case when any value is null
                            // You might want to log a message or take appropriate action
                        }
                    }
                    adapter.notifyDataSetChanged();

                    loadingDialog.dismiss();


                } else {
                    Toast.makeText(MainActivity.this, "Category does not exist", Toast.LENGTH_SHORT).show();

                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}