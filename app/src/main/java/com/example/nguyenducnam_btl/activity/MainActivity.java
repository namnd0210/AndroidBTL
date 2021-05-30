package com.example.nguyenducnam_btl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.nguyenducnam_btl.R;
import com.example.nguyenducnam_btl.adapter.AsiaFoodAdapter;
import com.example.nguyenducnam_btl.adapter.PopularFoodAdapter;
import com.example.nguyenducnam_btl.adapter.ViewPagerAdapter;
import com.example.nguyenducnam_btl.model.AsiaFood;
import com.example.nguyenducnam_btl.model.PopularFood;
import com.example.nguyenducnam_btl.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomAppBar navigation;
    ViewPager viewPager;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        navigateAddActivity();
        handleViewPager();
    }

    private void handleViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        navigation.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    }

                    case 1: {
                        navigation.getMenu().findItem(R.id.map).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigation.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: {
                        viewPager.setCurrentItem(0);
                        break;
                    }

                    case R.id.map: {
                        viewPager.setCurrentItem(1);
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void navigateAddActivity() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        Add.class);
                startActivity(intent);
            }
        });
    }

    public void initView() {
        navigation = findViewById(R.id.bottomAppBar2);
        viewPager = findViewById(R.id.viewPager);
        fab = findViewById(R.id.mainFab);
    }

}