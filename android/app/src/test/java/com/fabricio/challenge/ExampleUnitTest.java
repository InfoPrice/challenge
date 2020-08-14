package com.fabricio.challenge;

import com.fabricio.challenge.control.RestService;
import com.fabricio.challenge.control.eventbus.MessageCode;
import com.fabricio.challenge.control.eventbus.MessageEvent;
import com.fabricio.challenge.model.Banner;
import com.fabricio.challenge.model.Category;
import com.fabricio.challenge.model.Product;
import com.fabricio.challenge.model.RestResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fabricio.challenge.control.RestService.REST_SERVER_URL;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 * This UnitTest checks for problems in the EventBus and the REST Api calls.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private CountDownLatch latch = new CountDownLatch(1);
    private boolean eventBusMessageReceived = false;

    @Test
    public void checkEventBusProductSelection() throws InterruptedException {
        assertNotNull(EventBus.getDefault());
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new MessageEvent(MessageCode.PRODUCT_SELECT_EVENT, new Product()));
        latch.await(500, TimeUnit.MILLISECONDS);
        if(!eventBusMessageReceived) {
            throw new AssertionError("EventBus not receive the message or it is wrong");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        assertNotNull(event);
        assertEquals(event.what, MessageCode.PRODUCT_SELECT_EVENT);
        assertNotNull(event.args);
        assertNotNull(event.args[0]);
        eventBusMessageReceived = true;
        latch.countDown();
    }

    @Test
    public void checkRestApi() throws InterruptedException {

        // Simple way to call the REST API service
        RestService restService = new Retrofit.Builder()
                .baseUrl(REST_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RestService.class);

        assertNotNull(restService);

        // Check getAllBanners
        latch = new CountDownLatch(4);

        Call<RestResponse<Banner>> bannerCall = restService.getAllBanners();
        assertNotNull(bannerCall);
        bannerCall.enqueue(new Callback<RestResponse<Banner>>() {
            @Override
            public void onResponse(Call<RestResponse<Banner>> call, Response<RestResponse<Banner>> response) {
                assertEquals(call.isExecuted(), true);
                assertNotNull(response);
                assertNotNull(response.body());
                latch.countDown();
            }

            @Override
            public void onFailure(Call<RestResponse<Banner>> call, Throwable t) {
                throw new AssertionError("REST API: getAllBanners Failed");
            }
        });

        // Check getAllBanners
        Call<RestResponse<Category>> categoriesCall = restService.getAllCategories();
        assertNotNull(categoriesCall);
        categoriesCall.enqueue(new Callback<RestResponse<Category>>() {
            @Override
            public void onResponse(Call<RestResponse<Category>> call, Response<RestResponse<Category>> response) {
                assertEquals(call.isExecuted(), true);
                assertNotNull(response);
                assertNotNull(response.body());
                latch.countDown();
            }

            @Override
            public void onFailure(Call<RestResponse<Category>> call, Throwable t) {
                throw new AssertionError("REST API: getAllCategories Failed");
            }
        });

        // Check getAllBanners
        Call<RestResponse<Product>> productCall = restService.getAllProducts();
        assertNotNull(productCall);
        productCall.enqueue(new Callback<RestResponse<Product>>() {
            @Override
            public void onResponse(Call<RestResponse<Product>> call, Response<RestResponse<Product>> response) {
                assertEquals(call.isExecuted(), true);
                assertNotNull(response);
                assertNotNull(response.body());
                latch.countDown();
            }

            @Override
            public void onFailure(Call<RestResponse<Product>> call, Throwable t) {
                throw new AssertionError("REST API: getAllProducts Failed");
            }
        });

        // Check getAllBanners
        productCall = restService.getBestSellers();
        assertNotNull(productCall);
        productCall.enqueue(new Callback<RestResponse<Product>>() {
            @Override
            public void onResponse(Call<RestResponse<Product>> call, Response<RestResponse<Product>> response) {
                assertEquals(call.isExecuted(), true);
                assertNotNull(response);
                assertNotNull(response.body());
                latch.countDown();
            }

            @Override
            public void onFailure(Call<RestResponse<Product>> call, Throwable t) {
                throw new AssertionError("REST API: getBestSellers Failed");
            }
        });

        // Wait all latch return
        latch.await(5, TimeUnit.SECONDS);

    }

}