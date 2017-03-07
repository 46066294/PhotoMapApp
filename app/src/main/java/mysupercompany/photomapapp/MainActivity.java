package mysupercompany.photomapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    CustomViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
        if (id == R.id.action_nota) {
            Intent i = new Intent(getApplicationContext(), NoteActivity.class);
            startActivity(i);
        } else if (id == R.id.action_foto) {
            Intent i = new Intent(getApplicationContext(), PhotoActivity.class);
            startActivity(i);
        } else if (id == R.id.action_video) {
            Intent i = new Intent(getApplicationContext(), VideoActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupViewPager(CustomViewPager upViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NoteFragmentAdapter(), "Notas");
        adapter.addFragment(new MapFragment(), "Mapa");
        adapter.addFragment(new PhotoFragmentAdapter(), "Fotos");
        adapter.addFragment(new VideoFragmentAdapter(), "Videos");

        upViewPager.setAdapter(adapter);
        upViewPager.setPagingEnabled(false);
    }
}