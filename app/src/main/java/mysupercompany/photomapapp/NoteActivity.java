package mysupercompany.photomapapp;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NoteActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    NoteActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fragment = (NoteActivityFragment) fragmentManager.findFragmentById(R.id.listFragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.addNota();
            }
        });

    }
}
