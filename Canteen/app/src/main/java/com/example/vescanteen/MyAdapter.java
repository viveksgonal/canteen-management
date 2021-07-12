package com.example.vescanteen;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class MyAdapter extends FragmentPagerAdapter
{
    private int totalTabs;
    //Menus m = new Menus();
    //Extra e = new Extra();
    MyAdapter(FragmentManager fm, int totalTabs) {
        super( fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.totalTabs = totalTabs;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  new North();
            case 1:
                return  new South();
            case 2:
                return  new Snacks();
            case 3:
                return  new Beverages();
            case 4:
                return  new Desert();
            case 5:
                return  new Extra();
            default:
                return  new Extra();
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}
