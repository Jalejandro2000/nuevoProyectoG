package com.example.nuevoproyectog.interfaces;

import com.example.nuevoproyectog.modelo.revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface revistaAPI {
    @GET("ws/issues.php?j_id=1")

    Call<List<revista>> getrevista();
}
