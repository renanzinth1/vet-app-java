package br.com.renanjardel.vetappjava.service;

import java.util.List;

import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.SubEspecie;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EspecieService {

    @POST("/especies")
    Call<Void> salvar(@Body Especie cliente);

    @GET("/especies")
    Call<List<Especie>> listar();

    @PUT("/especies/{codigo}")
    Call<Especie> editar(@Path("codigo") long codigo, @Body Especie especie);

    @GET("/especies/{codigo}")
    Call<Especie> buscarPorId(@Path("codigo") long codigo);

    @DELETE("/especies/{codigo}")
    Call<Void> remover(@Path("codigo") long codigo);

    @GET("/especies/{codigo}/subEspecies")
    Call<List<SubEspecie>> listarSubEspecies(@Path("codigo") Long codigo);
}
