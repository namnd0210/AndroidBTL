package com.example.nguyenducnam_btl.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nguyenducnam_btl.fragment.Home;
import com.example.nguyenducnam_btl.fragment.Map;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager, int behavior) {
        super(manager, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new Home();

            case 1: return new Map();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
