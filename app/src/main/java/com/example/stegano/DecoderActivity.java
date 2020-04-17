package com.example.stegano;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

public class DecoderActivity extends AppCompatActivity implements DecoderEventListener{
    private static final String TAG = "DecoderActivity";

    private DecoderViewPager decoderViewPager;
    private TabLayout decoderTabDots;
    private DecoderViewPagerAdapter decoderViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        decoderViewPager = findViewById(R.id.decoderViewPager);
        decoderViewPagerAdapter = new DecoderViewPagerAdapter(getSupportFragmentManager());
        decoderViewPager.setAdapter(decoderViewPagerAdapter);
        //decoderViewPager.setOffscreenPageLimit(decoderViewPagerAdapter.getCount() - 1); // Default 1

        decoderTabDots = findViewById(R.id.decoderTabDots);
        decoderTabDots.setupWithViewPager(decoderViewPager, true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }

    @Override
    public void imageSelected(boolean isSelected) {

    }
}
