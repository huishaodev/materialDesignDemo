package com.bugull.materialdesigndemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Fruit[] mFruits = {new Fruit("叮当1",R.drawable.a),
            new Fruit("叮当2",R.drawable.b),
            new Fruit("叮当3",R.drawable.c),
            new Fruit("叮当4",R.drawable.d),
            new Fruit("叮当5",R.drawable.e),
            new Fruit("叮当6",R.drawable.f)};

    private List<Fruit> mFruitList = new ArrayList<>();

    private FruitAdapter mFruitAdapter;

    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //给toolbar左边加一个导航按钮，默认是一个返回的箭头
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this,"Call",Toast.LENGTH_LONG).show();
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"data Delete",Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"Click FAB",Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
        initFruits();
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        mFruitAdapter = new FruitAdapter(MainActivity.this,mFruitList);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,2);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(mFruitAdapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        mFruitAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        mFruitList.clear();
        for (int i = 0; i <50 ; i++) {
            Random random = new Random();
            int index = random.nextInt(mFruits.length);
            mFruitList.add(mFruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
               mDrawerLayout.openDrawer(GravityCompat.START);
                break;
//            case R.id.backup:
//                Toast.makeText(MainActivity.this,"You Click Backup",Toast.LENGTH_LONG).show();
//                break;
            case R.id.delete:
//                Toast.makeText(MainActivity.this,"You Click Delete",Toast.LENGTH_LONG).show();
                break;
            case R.id.settings:
                Toast.makeText(MainActivity.this,"You Click Settings",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
