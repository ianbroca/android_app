package com.example.almishop.io;

import androidx.annotation.AnyRes;

import com.example.almishop.model.ChangePassword;
import com.example.almishop.model.ChangePicture;
import com.example.almishop.model.ChangeProfile;
import com.example.almishop.model.Console;
import com.example.almishop.model.HistoryTransaction;
import com.example.almishop.model.Location;
import com.example.almishop.model.Login;
import com.example.almishop.model.Product;
import com.example.almishop.model.Register;
import com.example.almishop.model.Smartphone;
import com.example.almishop.model.Tablet;
import com.example.almishop.model.User;
import com.example.almishop.model.Videogame;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService
{
//    @GET("courses")
//    Call<ArrayList<Course>> getCourses();
//
//    @GET("subjects/course/{id}")
//    Call<ArrayList<Subject>> getSubjectsByCourse(
//            @Path("id") int id
//    );

    @GET("user/{id}")
    Call<User> getUserById(@Path("id") int id);

    @POST("user/login")
    Call<User> login(@Body Login data);

    @POST("user/register")
    Call<User> register(@Body Register data);

    @POST("user/location")
    Call<Integer> sendLocation(@Body Location data);

    @PUT("user/edit")
    Call<Integer> changeProfile(@Body ChangeProfile data);

    @PUT("user/edit")
    Call<Integer> changePicture(@Body ChangePicture data);

    @PUT("user/edit")
    Call<Integer> changePassword(@Body ChangePassword data);

    @GET("product/smartphones")
    Call<Product[]> getSmartphones();

    @GET("product/tablets")
    Call<Product[]> getTablets();

    @GET("product/consoles")
    Call<Product[]> getConsoles();


    @GET("product/videogames")
    Call<Product[]> getVideogames();


    @GET("product/smartphones/{id}")
    Call<Smartphone> getSmartphoneById(@Path("id") String id);

    @GET("product/videogames/{id}")
    Call<Videogame> getVideogamesById(@Path("id") String id);

    @GET("product/consoles/{id}")
    Call<Console> getConsolesById(@Path("id") String id);

    @GET("product/tablets/{id}")
    Call<Tablet> getTabletsById(@Path("id") String id);

    @GET("transaction/{id}")
    Call<ArrayList<HistoryTransaction>> getShoppingHistory(@Path("id") String id);

    @DELETE("user/archive/{id}")
    Call<Integer> deleteUser(@Path("id") String id);
}
