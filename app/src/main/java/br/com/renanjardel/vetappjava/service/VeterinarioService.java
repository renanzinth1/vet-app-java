package br.com.renanjardel.vetappjava.service;

import java.util.List;

import br.com.renanjardel.vetappjava.model.Veterinario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VeterinarioService {

    @POST("/veterinarios")
    Call<Void> salvar(@Body Veterinario veterinario);

    @GET("/veterinarios")
    Call<List<Veterinario>> listar();

    @PUT("/veterinarios/{codigo}")
    Call<Veterinario> editar(@Path("codigo") long codigo, @Body Veterinario veterinario);

    @GET("/veterinarios/{codigo}")
    Call<Veterinario> buscarPorId(@Path("codigo") long codigo);

    @DELETE("/veterinarios/{codigo}")
    Call<Void> remover(@Path("codigo") Long codigo);
}
