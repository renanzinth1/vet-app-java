package br.com.renanjardel.vetappjava.retrofit;

import br.com.renanjardel.vetappjava.service.AnimalService;
import br.com.renanjardel.vetappjava.service.ClienteService;
import br.com.renanjardel.vetappjava.service.EspecieService;
import br.com.renanjardel.vetappjava.service.MedicamentoService;
import br.com.renanjardel.vetappjava.service.SubEspecieService;
import br.com.renanjardel.vetappjava.service.VeterinarioService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {

    private final Retrofit retrofit;

    public RetrofitInicializador(){
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.31.180:8080")
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public ClienteService getClienteService() {
        return retrofit.create(ClienteService.class);
    }

    public VeterinarioService getVeterinarioService() {
        return retrofit.create(VeterinarioService.class);
    }

    public EspecieService getEspecieService() {
        return retrofit.create(EspecieService.class);
    }

    public MedicamentoService getMedicamentoService(){
        return retrofit.create(MedicamentoService.class);
    }

    public SubEspecieService getSubEspecieService() {
        return retrofit.create(SubEspecieService.class);
    }

    public AnimalService getAnimalService() {
        return retrofit.create(AnimalService.class);
    }
}