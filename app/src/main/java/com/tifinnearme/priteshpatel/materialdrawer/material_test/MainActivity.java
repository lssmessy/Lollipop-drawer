package com.tifinnearme.priteshpatel.materialdrawer.material_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tifinnearme.priteshpatel.materialdrawer.R;
import com.tifinnearme.priteshpatel.materialdrawer.tvdetails.TvShows;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener {
    public static Toolbar toolbar;
    private ViewPager pager;
    private MaterialTabHost tabHost;
    private ViewPagerAdapter viewPagerAdapter;
    //private SlidingTabLayout mTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_appbar);
        NavigationFragment navFrag=(NavigationFragment)getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);//Get the object of drawer layout
        toolbar=(Toolbar)findViewById(R.id.app_bar);
       setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        navFrag.setUp(R.id.drawer_fragment,drawerLayout,toolbar);
        //Create object for drawer layout
       /* pager=(ViewPager)findViewById(R.id.pager); // slidelayout vode
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTab=(SlidingTabLayout)findViewById(R.id.tabs);
        mTab.setDistributeEvenly(true);
        mTab.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
       // mTab.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mTab.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));
        mTab.setViewPager(pager);*/

        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        pager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(viewPagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(viewPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
        /*for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(viewPagerAdapter.getIcon(i))
                            .setTabListener(this)
            );
        }*/





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.navigate)
        {
            startActivity(new Intent(this,Subactivity.class));
        }
        if(id==R.id.action_tabs_using_library)
        {
            startActivity(new Intent(this,ActivityUsingTabLibrary.class));
        }
        if(id==R.id.vectorView)
        {
            startActivity(new Intent(this,VectorTestActivity.class));
        }
        if(id==R.id.tvShows)
        {
            startActivity(new Intent(this,TvShows.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        pager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }
    /*class MyPagerAdapter extends FragmentPagerAdapter{
        int icons[]={R.drawable.home,R.drawable.account,R.drawable.magnify};

        String[] tabs;
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs=getResources().getStringArray(R.array.tabs);
        }


        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment=MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable=getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan=new ImageSpan(drawable);
            SpannableString spannableString=new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }
    }
    public static class MyFragment extends Fragment{
        private TextView textView;
        public static MyFragment getInstance(int position){
            MyFragment myFragment=new MyFragment();
            Bundle args=new Bundle();
            args.putInt("postion",position);
            myFragment.setArguments(args);
            return myFragment;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout=inflater.inflate(R.layout.fragment_my,container,false);
            textView=(TextView)layout.findViewById(R.id.textView);
            Bundle bundle=getArguments();
            if(bundle!=null)
            {
                textView.setText("Fragment is "+ bundle.getInt("position"));
            }
            return layout;
        }
    }*/

    public static class MyFragment extends Fragment {
        private TextView textView;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_my, container, false);
            textView = (TextView) layout.findViewById(R.id.textView);
            Bundle bundle = getArguments();
            if (bundle != null) {
                textView.setText("Fragment is " + bundle.getInt("position"));
            }

            return layout;
        }


    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        //int icons[]={R.drawable.home,R.drawable.account,R.drawable.magnify};

        String[] tabs;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs_names);
        }


        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public int getCount() {
        /*        return tabs.length;*/
          //  return icons.length;
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /*Drawable drawable=getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan=new ImageSpan(drawable);
            SpannableString spannableString=new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;*/
            return tabs[position];
        }
        /*private Drawable getIcon(int position){
            return getResources().getDrawable(icons[position]);
        }*/
    }


}
