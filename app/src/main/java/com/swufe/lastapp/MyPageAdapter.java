package com.swufe.lastapp;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager manager){
        super(manager);
    }
    @NonNull

    @Override

    public Fragment getItem(int position) {
        if(position == 0){
            return new FirstFragment();
        }else{
            return new SecondFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Brent";
        }else{
            return "WTI";
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}

