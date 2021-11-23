package com.example.almishop.io;

import androidx.annotation.AnyRes;

import com.example.almishop.model.ChangePassword;
import com.example.almishop.model.Location;
import com.example.almishop.model.Login;
import com.example.almishop.model.Register;
import com.example.almishop.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
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
    Call<AnyRes> sendLocation(@Body Location data);

    @PUT("user/edit")
    Call<User> changePassword(@Body ChangePassword data);
}
