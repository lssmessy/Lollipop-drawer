package com.tifinnearme.priteshpatel.materialdrawer.material_test;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tifinnearme.priteshpatel.materialdrawer.R;
import com.tifinnearme.priteshpatel.materialdrawer.fragments.FragmentBoxOffice;
import com.tifinnearme.priteshpatel.materialdrawer.fragments.FragmentSearch;
import com.tifinnearme.priteshpatel.materialdrawer.fragments.FragmentUpcoming;
import com.tifinnearme.priteshpatel.materialdrawer.network.VolleySingleTon;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class ActivityUsingTabLibrary extends ActionBarActivity  implements MaterialTabListener{

    private Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager pager;
    private ViewPagerAdapter viewPagerAdapter;
    private static final int UPCOMING=0;
    private static final int BOX_OFFICE=1;
    private static final int SEARCH=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_tab_library);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        /*for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(viewPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }*/
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(viewPagerAdapter.getIcon(i))
                            .setTabListener(this)
            );
        }
    }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_activity_using_tab_library, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onTabSelected (MaterialTab materialTab){
            pager.setCurrentItem(materialTab.getPosition());
        }

        @Override
        public void onTabReselected (MaterialTab materialTab){

        }

        @Override
        public void onTabUnselected (MaterialTab materialTab){

        }
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
                /*RequestQueue requestQueue= VolleySingleTon.getsInstance().getRequestQueue();
                StringRequest stringRequest=new StringRequest(Request.Method.GET,"http://php.net/",new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Dialog dialog=new Dialog(getActivity());
                        dialog.setContentView(R.layout.dialog_view);
                        dialog.setTitle("Response");
                        TextView text=(TextView)dialog.findViewById(R.id.text_dialog);
                        text.setText(response);
                        dialog.setCancelable(true);
                       
                        dialog.show();


                        //Toast.makeText(getActivity(), "Response" + response, Toast.LENGTH_LONG).show();
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
                requestQueue.add(stringRequest);*/
                return layout;
            }
        }

        class ViewPagerAdapter extends FragmentStatePagerAdapter {
            int icons[]={R.drawable.home,R.drawable.account,R.drawable.magnify};
            //int icons[]={R.drawable.android_logo,R.drawable.android_logo,R.drawable.android_logo};

            String[] tabs;

            public ViewPagerAdapter(FragmentManager fm) {
                super(fm);
                tabs = getResources().getStringArray(R.array.tabs);
            }


            @Override
            public Fragment getItem(int position) {
                /*MyFragment myFragment = MyFragment.getInstance(position);
                return myFragment;*/
                Fragment fragment=null;
                switch (position){
                    case UPCOMING:
                        fragment= FragmentUpcoming.newInstance("","");
                        break;
                    case BOX_OFFICE:
                        fragment= FragmentBoxOffice.newInstance("","");

                        break;
                    case SEARCH:
                        fragment= FragmentSearch.newInstance("", "");
                        break;


                }
                return fragment;
            }

            @Override
            public int getCount() {
                //return tabs.length;
                return icons.length;
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
            private Drawable getIcon(int position){
                return getResources().getDrawable(icons[position]);
            }
        }


}
