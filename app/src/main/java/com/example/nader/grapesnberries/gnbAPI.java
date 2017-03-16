package com.example.nader.grapesnberries;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface gnbAPI {
    @GET("new_products?count=10")
    Call<List<Product>> content(@Query("from") int id);
}
