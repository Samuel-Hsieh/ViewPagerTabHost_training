package samuel_hsieh.deerlight.com.viewpager_training;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener
        ,TabHost.OnTabChangeListener{
    private ViewPager mViewPager;
    TabHost tabHost;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_viewpager_layout);
        initViewPager();
        initTabHost();

    }
    public class FakeContent implements TabHost.TabContentFactory{

        Context context;
        public FakeContent(Context context){
            this.context = context;
        }
        @Override
        public View createTabContent(String tag) {
            View fakeview = new View(context);
            fakeview.setMinimumHeight(0);
            fakeview.setMinimumWidth(0);
            return fakeview;
        }
    }
    private void initTabHost() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        final String[] tabNames = {"Tab1","Tab2","Tab3","Tab4","Tab5","Tab6"};
        for(i = 0;i<tabNames.length;i++){
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabNames[i]);
            tabSpec.setIndicator(tabNames[i]);
            tabSpec.setContent(new FakeContent(getApplicationContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
        /*tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId == tabNames[0]) {
                    mViewPager.setCurrentItem(0);
                } else if (tabId == tabNames[1]) {
                    mViewPager.setCurrentItem(1);
                } else if (tabId == tabNames[2]) {
                    mViewPager.setCurrentItem(2);
                }
            }
        });*/

    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> fragmentLists = new ArrayList<Fragment>();
        fragmentLists.add(new Page1_Fragment());
        fragmentLists.add(new Page2_Fragment());
        fragmentLists.add(new Page3_Fragment());
        fragmentLists.add(new Page1_Fragment());
        fragmentLists.add(new Page2_Fragment());
        fragmentLists.add(new Page3_Fragment());
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(),fragmentLists);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*Tab Listener*/
    @Override
    public void onTabChanged(String tabId) {
        int selectedItem = tabHost.getCurrentTab();
        mViewPager.setCurrentItem(selectedItem);
        HorizontalScrollView HscrollView = (HorizontalScrollView) findViewById(R.id.HscrollView);
        View tabView = tabHost.getCurrentTabView();
        int scorllPos = tabView.getLeft() - (HscrollView.getWidth() - tabView.getWidth()) / 2;
        HscrollView.smoothScrollTo(scorllPos,0);
    }
    /*ViewPager Listener*/
    @Override
    public void onPageScrollStateChanged(int state) {
        int selectedItem = mViewPager.getCurrentItem();
        tabHost.setCurrentTab(selectedItem);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int selectedItem) {
        mViewPager.setCurrentItem(selectedItem);
    }
}
