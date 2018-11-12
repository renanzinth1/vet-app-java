package br.com.renanjardel.vetappjava.service;

import java.util.List;

import br.com.renanjardel.vetappjava.model.Animal;
import br.com.renanjardel.vetappjava.model.Animal;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AnimalService {

    @POST("/animal")
    Call<Void> salvar(@Body Animal animal);

    @GET("/animais")
    Call<List<Animal>> listar();

    @PUT("/animais/{codigo}")
    Call<Animal> editar(@Path("codigo") long codigo, @Body Animal animal);

    @GET("/animais/{codigo}")
    Call<Animal> buscarPorId(@Path("codigo") long codigo);

    @DELETE("/animais/{codigo}")
    Call<Void> remover(@Path("codigo") Long codigo);
}
