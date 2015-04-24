package com.tifinnearme.priteshpatel.materialdrawer.tvdetails;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.tifinnearme.priteshpatel.materialdrawer.R;
import com.tifinnearme.priteshpatel.materialdrawer.fragments.FragmentSearch;
import com.tifinnearme.priteshpatel.materialdrawer.interfaces.SortListener;
import com.tifinnearme.priteshpatel.materialdrawer.services.MyService;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobScheduler;

public class TvShows extends ActionBarActivity  implements MaterialTabListener,View.OnClickListener{
    private FloatingActionButton actionButton;
    private static final int JOB_ID = 100;
    private Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager pager;
    private ViewPagerAdapter viewPagerAdapter;
    private static final int UPCOMING=0;
    private static final int BOX_OFFICE=1;
    private static final int SEARCH=2;
    private static final String SORT_BY_NAME_TAG="sortbyname";
    private static final String SORT_BY_DATE_TAG="sortbydate";
    private static final String SORT_BY_RATINGS_TAG="sortbyratings";
    private FloatingActionMenu actionMenu;
    private JobScheduler mJobScheduler;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobScheduler=JobScheduler.getInstance(this);
        constructJob();
        setContentView(R.layout.activity_using_tab_library);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(viewPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
        /*Change xml into icons before showing with icons*/
       /* for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(viewPagerAdapter.getIcon(i))
                            .setTabListener(this)
            );
        }*/

        icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.plus);

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon).setBackgroundDrawable(R.drawable.plus_button_states)
                .build();

        ImageView sortByName = new ImageView(this); // Create an icon
        sortByName.setImageResource(R.drawable.atoz);

        ImageView sortByDate = new ImageView(this); // Create an icon
        sortByDate.setImageResource(R.drawable.calendar);

        ImageView sortByRatings = new ImageView(this); // Create an icon
        sortByRatings.setImageResource(R.drawable.star);


        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.plus_button_states));
        //icon.setImageResource(R.drawable.close);
        SubActionButton buttonsortByName = itemBuilder.setContentView(sortByName).build();
        SubActionButton buttonsortByDate = itemBuilder.setContentView(sortByDate).build();
        SubActionButton buttonsortByRatings = itemBuilder.setContentView(sortByRatings).build();


        buttonsortByName.setTag(SORT_BY_NAME_TAG);
        buttonsortByDate.setTag(SORT_BY_DATE_TAG);
        buttonsortByRatings.setTag(SORT_BY_RATINGS_TAG);

        buttonsortByName.setOnClickListener(this);
        buttonsortByDate.setOnClickListener(this);
        buttonsortByRatings.setOnClickListener(this);

        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonsortByName)
                .addSubActionView(buttonsortByDate)
                .addSubActionView(buttonsortByRatings)

                .attachTo(actionButton)
                .build();
        actionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                icon.setImageResource(R.drawable.close);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                icon.setImageResource(R.drawable.plus);
            }
        });


    }

    public void constructJob(){
        me.tatarka.support.job.JobInfo.Builder builder=new me.tatarka.support.job.JobInfo.Builder(JOB_ID,new ComponentName(this, MyService.class));
        //JobInfo.Builder builder=new JobInfo.Builder(JOB_ID,new ComponentName(this, MyService.class));
        //Name of the component which we want to call
        //PersistableBundle persistableBundle=new PersistableBundle();
        //store the state of activity across state changed i.e, screen rotate etc

        builder.setPeriodic(7000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        builder.setPersisted(true);

        mJobScheduler.schedule(builder.build());
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
            if(id==android.R.id.home)
            {
                NavUtils.navigateUpFromSameTask(this);
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

    @Override
    public void onClick(View v) {
        actionMenu.close(true);

        switch (v.getTag().toString())
        {
            case SORT_BY_NAME_TAG:
                //Toast.makeText(this,SORT_BY_NAME_TAG.toString(),Toast.LENGTH_SHORT).show();
                Fragment fragment= (Fragment) viewPagerAdapter.instantiateItem(pager,pager.getCurrentItem());
                if(fragment instanceof SortListener)
                {
                    ((SortListener) fragment).onSortByName();
                }
                break;
            case SORT_BY_DATE_TAG:
                //Toast.makeText(this,SORT_BY_DATE_TAG.toString(),Toast.LENGTH_SHORT).show();
                Fragment date_frag= (Fragment) viewPagerAdapter.instantiateItem(pager, pager.getCurrentItem());
                if(date_frag instanceof SortListener)
                {
                    ((SortListener) date_frag).onSortByDate();
                }
                break;
            case SORT_BY_RATINGS_TAG:
                //Toast.makeText(this,SORT_BY_RATINGS_TAG.toString(),Toast.LENGTH_SHORT).show();
                Fragment rate_frag=(Fragment) viewPagerAdapter.instantiateItem(pager, pager.getCurrentItem());
                if(rate_frag instanceof SortListener)
                {
                    ((SortListener) rate_frag).onSortByRatings();
                }
                break;


        }

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

                return layout;
            }
        }

        class ViewPagerAdapter extends FragmentStatePagerAdapter {
            //int icons[]={R.drawable.home,R.drawable.account,R.drawable.magnify};
            //int icons[]={R.drawable.android_logo,R.drawable.android_logo,R.drawable.android_logo};

            String[] tab_names;

            public ViewPagerAdapter(FragmentManager fm) {
                super(fm);
                tab_names = getResources().getStringArray(R.array.tv_tabs_names);
            }


            @Override
            public Fragment getItem(int position) {
                /*MyFragment myFragment = MyFragment.getInstance(position);
                return myFragment;*/
                Fragment fragment=null;
                switch (position){
                    case UPCOMING:
                        fragment= TvUpcoming_Latest.newInstance("","");
                        break;
                    case BOX_OFFICE:
                        fragment= TvBoxOffice.newInstance("","");

                        break;
                    case SEARCH:
                        fragment= FragmentSearch.newInstance("", "");
                        break;


                }
                return fragment;
            }

            @Override
            public int getCount() {
             //   return tab_names.length;
               //return icons.length;
                return tab_names.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
            /*Drawable drawable=getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan=new ImageSpan(drawable);
            SpannableString spannableString=new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;*/
                return tab_names[position];
            }
           /* private Drawable getIcon(int position){
                return getResources().getDrawable(icons[position]);
            }*/
        }


}
