package com.example.nader.grapesnberries;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    Retrofit retrofit;
    Gson gson;
    Callback Products;
    ProductAdapter mPAdapter;
    List<Product> product;
    gnbAPI API;
    int visibleItemCount, totalItemCount, pastVisibleItems;
    Boolean loading = true;
    int n = 11;
    int[] firstVisibleItems;
    ConnectivityManager connectivitymanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Products");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        gson = new Gson();
        firstVisibleItems = null;
        connectivitymanager = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        getPage(1);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mStaggeredGridLayoutManager.getChildCount();
                totalItemCount = mStaggeredGridLayoutManager.getItemCount();
                firstVisibleItems = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount+1) {
                        loading = false;
                        getPage(n);
                        n = n + 10;
                    }

                }
            }
        });

    }

    void getPage(int position){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://grapesnberries.getsandbox.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API = retrofit.create(gnbAPI.class);
        Call<List<Product>> callProducts = API.content(position);

        Products = new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    if (product == null) {
                        product = response.body();
                        mPAdapter = new ProductAdapter(product);
                        mRecyclerView.setAdapter(mPAdapter);
                        loading = true;
                    }
                    else{
                        product.addAll(response.body());
                        mPAdapter.notifyDataSetChanged();
                        loading = true;
                    }


                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        };
        if ( connectivitymanager.getActiveNetworkInfo() != null && connectivitymanager.getActiveNetworkInfo().isConnected()) {
            callProducts.enqueue(Products);
        } else  {

            new AlertDialog.Builder(this)
                    .setTitle("Connection Error")
                    .setMessage("Please check your Internet connection.")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            getPage(1);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }
}
