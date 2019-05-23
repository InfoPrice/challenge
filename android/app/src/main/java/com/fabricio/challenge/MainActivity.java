package com.fabricio.challenge;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.fabricio.challenge.view.adapters.ProductRecyclerAdapter;
import com.fabricio.challenge.control.DownloadImageTask;
import com.fabricio.challenge.control.RestService;
import com.fabricio.challenge.model.Banner;
import com.fabricio.challenge.model.Category;
import com.fabricio.challenge.model.Product;
import com.fabricio.challenge.model.RestResponse;
import com.fabricio.challenge.view.adapters.ViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private static Context CONTEXT;

    private static final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final RestService restService = new Retrofit.Builder()
            .baseUrl("https://alodjinha.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(RestService.class);

    private List<Banner> bannerList = new ArrayList<>();


    /**
     * View components
     */
    private ViewPager bannerPager;
    private ViewAdapter bannerAdapter;
    private TabLayout bannerTabs;

    private ViewPager bestSellerPager;
    private ViewAdapter bestSellerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.CONTEXT = this;

        bannerPager = findViewById(R.id.banner_pager_view);
        bannerTabs = findViewById(R.id.banner_tabs_container);
        bestSellerPager = findViewById(R.id.best_seller_pager_view);

        bannerTabs.setupWithViewPager(bannerPager, true);

        bestSellerAdapter = new ViewAdapter(bestSellerPager.getContext());
        bestSellerPager.setAdapter(bestSellerAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        loadBanners();
//        loadCategories();
        loadBestSeller();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            findViewById(R.id.home_page).setVisibility(View.VISIBLE);
            findViewById(R.id.about_page).setVisibility(View.GONE);
        } else if (id == R.id.nav_about) {
            findViewById(R.id.home_page).setVisibility(View.GONE);
            findViewById(R.id.about_page).setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Load all banner images, getting it from the REST API
     */
    private void loadBanners(){
        restService.getAllBanners().enqueue(new Callback<RestResponse<Banner>>() {
            @Override
            public void onResponse(Call<RestResponse<Banner>> call, Response<RestResponse<Banner>> response) {
                if(response != null){
                    bannerList.clear();
                    try {
                        bannerList.addAll(response.body().getData());
                    } catch (Exception e) {
                        Log.e(TAG, "Could not load banners in the view", e);
                    }
                }

                // Refresh the pager
                bannerPager.setAdapter(null);
                bannerAdapter = new ViewAdapter(bannerPager.getContext());
                bannerPager.setAdapter(bannerAdapter);
                for(Banner banner : bannerList){
                    View view = LayoutInflater.from(bannerPager.getContext()).inflate(R.layout.banner_item, null);
                    ImageView imageView = view.findViewById(R.id.image);
                    new DownloadImageTask(imageView).execute(banner.getLinkUrl());
                    bannerAdapter.addView(imageView);
                }

            }

            @Override
            public void onFailure(Call<RestResponse<Banner>> call, Throwable t) {
                Log.e(TAG, "Could not fetch the banners: ", t);
            }
        });
    }


    /**
     * Load all categories, getting it from the REST API
     */
    private void loadCategories(){
        restService.getAllCategories().enqueue(new Callback<RestResponse<Category>>() {
            @Override
            public void onResponse(Call<RestResponse<Category>> call, Response<RestResponse<Category>> response) {
                if(response != null){
                    try {

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<RestResponse<Category>> call, Throwable t) {

            }
        });
    }

    /**
     * Load all most sold products, getting it from the REST API
     */
    private void loadBestSeller(){
        restService.getBestSellers().enqueue(new Callback<RestResponse<Product>>() {
            @Override
            public void onResponse(Call<RestResponse<Product>> call, Response<RestResponse<Product>> response) {
                if(response != null){
                    try {
                        List<Product> products = response.body().getData();

                        // Refresh the pager
                        bestSellerPager.setAdapter(null);
                        bestSellerAdapter = new ViewAdapter(bestSellerPager.getContext());
                        bestSellerPager.setAdapter(bestSellerAdapter);


                        // Create recycler
                        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(bestSellerPager.getContext()).inflate(R.layout.best_seller_list, null);
                        bestSellerAdapter.addView(recyclerView);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(bestSellerPager.getContext());
                        recyclerView.setLayoutManager(layoutManager);

                        ProductRecyclerAdapter prodAdapter = new ProductRecyclerAdapter(bestSellerPager.getContext());
                        recyclerView.setAdapter(prodAdapter);


                        int count = 0;
                        for(Product product : products){
                            prodAdapter.addProduct(product);
                            if(++count >= 10) {
                                recyclerView = (RecyclerView) LayoutInflater.from(bestSellerPager.getContext()).inflate(R.layout.best_seller_list, null);
                                bestSellerAdapter.addView(recyclerView);
                                recyclerView.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(bestSellerPager.getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                prodAdapter = new ProductRecyclerAdapter(bestSellerPager.getContext());
                                recyclerView.setAdapter(prodAdapter);
                                count = 0;
                            }
                        }

                        bestSellerPager.invalidate();


                    } catch (Exception e) {
                        Log.e(TAG, "Could not parse best sellers: ", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<RestResponse<Product>> call, Throwable t) {

            }
        });
    }
}
