package com.example.esiapp.Interface;

import com.example.esiapp.Modelo.POST;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CromasBD {

    @GET("users")
    Call<List<POST>> getPost();
}
