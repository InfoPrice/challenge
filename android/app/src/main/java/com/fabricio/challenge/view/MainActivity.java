package com.fabricio.challenge.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;

import com.fabricio.challenge.R;
import com.fabricio.challenge.control.eventbus.MessageEvent;
import com.fabricio.challenge.view.adapters.ProductRecyclerAdapter;
import com.fabricio.challenge.control.DownloadImageTask;
import com.fabricio.challenge.control.RestService;
import com.fabricio.challenge.model.Banner;
import com.fabricio.challenge.model.Category;
import com.fabricio.challenge.model.Product;
import com.fabricio.challenge.model.RestResponse;
import com.fabricio.challenge.view.adapters.ViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fabricio.challenge.control.RestService.REST_SERVER_URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private static final int PRODUCT_LIST_PAGE = 0;
    private static final int PRODUCT_INFO_PAGE = 1;

    public static Context CONTEXT;
    public static Activity ACTIVITY;

    private static final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final RestService restService = new Retrofit.Builder()
            .baseUrl(REST_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(RestService.class);

    private Product selectedProduct = null;
    private boolean isProcessingButtonClick = false;

    /**
     * View components
     */
    private HeightWrappingViewPager mainPager;  // Has two pages product list and info
    private ViewPagerAdapter mainPagerAdapter;  // Adapter controller for the mainPager
    private View productListView;               // Store the list of products to be show
    private View productInfoView;               // Store the information of a given product

    private ViewPager bannerPager;              // Pages to show some banners
    private ViewPagerAdapter bannerAdapter;     // Adapter for the banner pager
    private TabLayout bannerTabs;               // Display how much banners the pager has

    private ViewPager bestSellerPager;          // Show the list os most sold products
    private ViewPagerAdapter bestSellerAdapter; // Adapter to the product list

    private ImageView productInfoImage;         // Image of the product in the info page
    private TextView productInfoNameAndDesc;    // Name and description of the product in the info page
    private TextView productInfoPriceOrginal;   // Product original price
    private TextView productInfoPriceDiscount;  // Product price with discount
    private TextView productInfoName;           // Name of the product in the info page
    private TextView productInfoDescription;    // Description of the product in the info page
    private FloatingActionButton floatButton;   // Button to reserve the product

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.CONTEXT = this;
        this.ACTIVITY = this;

        // Receive notifications to update the UI
        EventBus.getDefault().register(this);

        // Create the main pages of the activity (product list and product info)
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mainPager = findViewById(R.id.main_pager);
        mainPager.setPagingEnabled(false);

        // Load Activity View Elements to easy access
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        productListView = (View) inflater.inflate(R.layout.product_list_page, null, false);
        productInfoView = (View) inflater.inflate(R.layout.product_info_page, null, false);

        bannerPager = productListView.findViewById(R.id.banner_pager_view);
        bannerTabs = productListView.findViewById(R.id.banner_tabs_container);
        bestSellerPager = productListView.findViewById(R.id.best_seller_pager_view);

        // Info view
        productInfoImage = productInfoView.findViewById(R.id.product_image);
        productInfoNameAndDesc = productInfoView.findViewById(R.id.product_name_description);
        productInfoPriceOrginal = productInfoView.findViewById(R.id.product_original_price);
        productInfoPriceOrginal.setPaintFlags(productInfoPriceOrginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        productInfoPriceDiscount = productInfoView.findViewById(R.id.product_discount_price);
        productInfoName = productInfoView.findViewById(R.id.product_name);
        productInfoDescription = productInfoView.findViewById(R.id.product_full_description);

        // Populate adapters and objects necessary
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mainPagerAdapter = new ViewPagerAdapter(mainPager.getContext());
        mainPager.setAdapter(mainPagerAdapter);
        mainPagerAdapter.addView(productListView);
        mainPagerAdapter.addView(productInfoView);

        bannerTabs.setupWithViewPager(bannerPager, true);

        bestSellerAdapter = new ViewPagerAdapter(bestSellerPager.getContext());
        bestSellerPager.setAdapter(bestSellerAdapter);

        floatButton = findViewById(R.id.fab);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedProduct != null && !isProcessingButtonClick) {
                    isProcessingButtonClick = true;
                    restService.markProduct(selectedProduct.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.isSuccessful()) {
                                new AlertDialog.Builder(CONTEXT)
                                        .setMessage(R.string.product_reserved)
                                        .setPositiveButton(android.R.string.ok, null)
                                        .show();
                            } else {
                                new AlertDialog.Builder(CONTEXT)
                                        .setMessage(R.string.product_not_reserved)
                                        .setPositiveButton(android.R.string.ok, null)
                                        .show();
                            }
                            isProcessingButtonClick = false;
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            new AlertDialog.Builder(CONTEXT)
                                    .setMessage(R.string.product_not_reserved)
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                            isProcessingButtonClick = false;
                        }
                    });
                }

//                Snackbar.make(view, R.string.product_reserved, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onResume() {
        // Refresh all data fetching it from the server
        loadBanners();
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
        } else if (mainPager.getCurrentItem() == PRODUCT_INFO_PAGE) {
            floatButton.hide();
            mainPager.setCurrentItem(PRODUCT_LIST_PAGE);
        } else if(findViewById(R.id.about_page).getVisibility() == View.VISIBLE) {
            findViewById(R.id.about_page).setVisibility(View.GONE);
            findViewById(R.id.home_page).setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
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
            findViewById(R.id.about_page).setVisibility(View.GONE);
            findViewById(R.id.home_page).setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        } else if (id == R.id.nav_about) {
            findViewById(R.id.home_page).setVisibility(View.GONE);
            findViewById(R.id.about_page).setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Load all banner images, getting it from the REST API and adding in a pager view at the top
     * of the activity.
     * Note: There is no limit in the specifications of how much banners will be shown at most
     */
    private void loadBanners(){
        restService.getAllBanners().enqueue(new Callback<RestResponse<Banner>>() {
            @Override
            public void onResponse(Call<RestResponse<Banner>> call, Response<RestResponse<Banner>> response) {
                if(response != null){
                    try {
                        List<Banner> bannerList = response.body().getData();

                        // Refresh the pager, clear all old entries
                        bannerPager.setAdapter(null);
                        bannerAdapter = new ViewPagerAdapter(bannerPager.getContext());
                        bannerPager.setAdapter(bannerAdapter);

                        // Add new banners to the view, downloading the images in the assync task
                        for(Banner banner : bannerList){
                            View view = LayoutInflater.from(bannerPager.getContext()).inflate(R.layout.banner_item, null);
                            ImageView imageView = view.findViewById(R.id.image);
                            new DownloadImageTask(imageView).execute(banner.getUrlImage());
                            bannerAdapter.addView(imageView);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Could not load banners in the view", e);
                    }
                }

            }

            @Override
            public void onFailure(Call<RestResponse<Banner>> call, Throwable t) {
                Log.e(TAG, "Could not fetch the banners: ", t);
                // TODO handle retries
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
                        Log.e(TAG, "Could not parse categories: ", e);
                        // TODO handle retries
                    }
                }
            }

            @Override
            public void onFailure(Call<RestResponse<Category>> call, Throwable t) {
                Log.e(TAG, "Could not fetch categories: ", t);
                // TODO handle retries
            }
        });
    }

    /**
     * Load all most sold products, getting it from the REST API and showing in a list pager view
     * with at most R.integer.product_list_size per page
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
                        bestSellerAdapter = new ViewPagerAdapter(bestSellerPager.getContext());
                        bestSellerPager.setAdapter(bestSellerAdapter);


                        // Create recycler
                        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(bestSellerPager.getContext()).inflate(R.layout.best_seller_list, null);
                        bestSellerAdapter.addView(recyclerView);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(bestSellerPager.getContext());
                        recyclerView.setLayoutManager(layoutManager);

                        ProductRecyclerAdapter prodAdapter = new ProductRecyclerAdapter(ACTIVITY);
                        recyclerView.setAdapter(prodAdapter);


                        /*
                         * Loop through all products fetched from the server.
                         * If there is more than R.integer.product_list_size, then
                         * it is created a new page adapter with that size of products
                         */
                        int count = 0;
                        for(Product product : products){
                            prodAdapter.addProduct(product);

                            // Check if reached the limit for current list
                            if(++count >= getResources().getInteger(R.integer.product_list_size)) {
                                // Add new list in the pager
                                recyclerView = (RecyclerView) LayoutInflater.from(bestSellerPager.getContext()).inflate(R.layout.best_seller_list, null);
                                bestSellerAdapter.addView(recyclerView);
                                recyclerView.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(bestSellerPager.getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                prodAdapter = new ProductRecyclerAdapter(ACTIVITY);
                                recyclerView.setAdapter(prodAdapter);
                                count = 0;
                            }
                        }

                        // TODO evaluate if this is necessary
                        bestSellerPager.invalidate();

                    } catch (Exception e) {
                        Log.e(TAG, "Could not parse best sellers: ", e);
                        // TODO handle retries
                    }
                }
            }

            @Override
            public void onFailure(Call<RestResponse<Product>> call, Throwable t) {
                Log.e(TAG, "Could not fetch best sellers: ", t);
                // TODO handle retries
            }
        });
    }


    /**
     * Get events from any service or controller and parse to update the view accordingly
     * @param event is the event generated when some action occurred.
     *              Check MessageCode for more information
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what){
            case PRODUCT_SELECT_EVENT:
                if(event.args.length > 0 && event.args[0] instanceof Product) {
                    selectedProduct = (Product) event.args[0];
                    // TODO UPDATE INFO VIEW
                    new DownloadImageTask(productInfoImage).execute(selectedProduct.getUrlImage());
                    productInfoNameAndDesc.setText(
                        getResources().getString(R.string.format_product_name,
                                selectedProduct.getName(),
                                selectedProduct.getDescriptionFormatted())
                    );
                    productInfoPriceOrginal.setText(getResources().getString(
                            R.string.format_price,
                            getResources().getString(R.string.oritinal_price),
                            selectedProduct.getPriceOriginal()
                    ));
                    productInfoPriceDiscount.setText(getResources().getString(
                            R.string.format_price,
                            getResources().getString(R.string.discount_price),
                            selectedProduct.getPriceDiscount()
                    ));
                    productInfoName.setText(selectedProduct.getName());
                    productInfoDescription.setText(selectedProduct.getDescriptionFormatted());
                    mainPager.setCurrentItem(PRODUCT_INFO_PAGE);
                    mainPagerAdapter.notifyDataSetChanged();

                    floatButton.show();
                }
                break;
        }
    }
}
