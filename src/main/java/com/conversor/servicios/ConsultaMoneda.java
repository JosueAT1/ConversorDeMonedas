package com.conversor.servicios;

import com.conversor.modelos.Moneda;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoneda {

    // Se puede cambiar la KEY en caso de tener una propia
    private static final String API_KEY = "c296bcb83b4cc9169faf41e1";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public Moneda buscarMoneda(String monedaBase) {
        URI direccion = URI.create(API_URL + monedaBase);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            return new Gson().fromJson(response.body(), Moneda.class);
        } catch (Exception e) {
            throw new RuntimeException("No se encontró la moneda consultada: " + e.getMessage());
        }
    }
}
