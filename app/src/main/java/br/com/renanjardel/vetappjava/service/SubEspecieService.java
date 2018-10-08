package br.com.renanjardel.vetappjava.service;

import java.util.List;

import br.com.renanjardel.vetappjava.model.SubEspecie;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SubEspecieService {

    @POST("/subEspecies")
    Call<Void> salvar(@Body SubEspecie subEspecie);

    @GET("/subEspecies")
    Call<List<SubEspecie>> listar();

    @PUT("/subEspecies/{codigo}")
    Call<SubEspecie> editar(@Path("codigo") long codigo, @Body SubEspecie subEspecie);

    @GET("/subEspecies/{codigo}")
    Call<SubEspecie> buscarPorId(@Path("codigo") long codigo);

    @DELETE("/subEspecies/{codigo}")
    Call<Void> remover(@Path("codigo") Long codigo);
}
