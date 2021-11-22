package com.example.almishop.io;

import com.example.almishop.model.Login;
import com.example.almishop.model.Register;
import com.example.almishop.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
//    @GET("courses")
//    Call<ArrayList<Course>> getCourses();
//
//    @GET("subjects/course/{id}")
//    Call<ArrayList<Subject>> getSubjectsByCourse(
//            @Path("id") int id
//    );

    @POST("user/login")
    Call<User> login(@Body Login data);

    @POST("user/register")
    Call<User> register(@Body Register data);
}
