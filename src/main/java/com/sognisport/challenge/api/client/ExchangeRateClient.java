package com.sognisport.challenge.api.client;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.sognisport.challenge.api.dto.ExchangeDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pair")
@RegisterRestClient(configKey="exchange-rate-client")
@RegisterProvider(LoggingFilter.class) 
public interface ExchangeRateClient {

    /**
     * Método para obter a taxa de conversão entre duas moedas.
     * @param fromCurrency Código da moeda de origem (ex: "USD").
     * @param toCurrency Código da moeda de destino (ex: "BRL").
     * @return Um DTO contendo as informações da taxa de câmbio.
     */
    @GET
    @Path("/{fromCurrency}/{toCurrency}")
    @Produces(MediaType.APPLICATION_JSON)
    ExchangeDTO getExchangeRate(@PathParam("fromCurrency") String fromCurrency, 
                                @PathParam("toCurrency") String toCurrency);
}
