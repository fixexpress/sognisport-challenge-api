package com.sognisport.challenge.api.resource;

import com.sognisport.challenge.api.model.ExchangeHistory;
import com.sognisport.challenge.api.service.ExchangeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/exchange")
public class ExchangeResource {

    @Inject
    ExchangeService exchangeService; 

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rate")
    public ExchangeHistory getExchangeRate(@QueryParam("fromCurrency") String fromCurrency, 
                                  @QueryParam("toCurrency") String toCurrency) {
        // Chama o método da ExchangeService para obter a taxa de conversão
        return exchangeService.getConversionRate(fromCurrency, toCurrency);
    }
}
