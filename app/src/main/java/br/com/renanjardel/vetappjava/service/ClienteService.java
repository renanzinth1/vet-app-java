package br.com.renanjardel.vetappjava.service;

import java.util.List;

import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.model.Pessoa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClienteService {

    @POST("/clientes")
    Call<Void> salvar(@Body Cliente cliente);

    @GET("/clientes")
    Call<List<Cliente>> listar();

    @PUT("/clientes/{codigo}")
    Call<Cliente> editar(@Path("codigo") long codigo, @Body Cliente cliente);

    @GET("/clientes/{codigo}")
    Call<Cliente> buscarPorId(@Path("codigo") long codigo);

    @DELETE("/clientes/{codigo}")
    Call<Void> remover(@Path("codigo") Long codigo);
}
