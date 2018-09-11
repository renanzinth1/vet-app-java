package br.com.renanjardel.vetappjava.services;

import br.com.renanjardel.vetappjava.modelo.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteService {

    @POST("clientes")
    Call<Void> insere(@Body Cliente cliente);
}
