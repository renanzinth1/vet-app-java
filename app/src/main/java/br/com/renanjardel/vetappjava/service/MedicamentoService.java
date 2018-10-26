package br.com.renanjardel.vetappjava.service;

import java.util.List;

import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.Medicamento;
import br.com.renanjardel.vetappjava.model.SubEspecie;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MedicamentoService {

    @POST("/medicamentos")
    Call<Void> salvar(@Body Medicamento medicamento);

    @GET("/medicamentos")
    Call<List<Medicamento>> listar();

    @PUT("/medicamentos/{codigo}")
    Call<Medicamento> editar(@Path("codigo") long codigo, @Body Medicamento medicamento);

    @GET("/medicamentos/{codigo}")
    Call<Medicamento> buscarPorId(@Path("codigo") long codigo);

    @DELETE("/medicamentos/{codigo}")
    Call<Void> remover(@Path("codigo") long codigo);

//    @GET("/especies/{codigo}/subEspecies")
//    Call<List<SubEspecie>> listarSubEspecies(@Path("codigo") Long codigo);
}
