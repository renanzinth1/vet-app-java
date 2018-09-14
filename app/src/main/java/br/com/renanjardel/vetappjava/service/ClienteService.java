package br.com.renanjardel.vetappjava.service;

import java.util.List;

import br.com.renanjardel.vetappjava.model.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClienteService {

    @POST("clientes")
    Call<Void> registrar(@Body Cliente cliente);

    @GET("clientes")
    Call<List<Cliente>> listar();
}
