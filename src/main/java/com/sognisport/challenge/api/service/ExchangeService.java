package com.sognisport.challenge.api.service;

import java.time.Instant;
import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.sognisport.challenge.api.client.ExchangeRateClient;
import com.sognisport.challenge.api.dto.ExchangeDTO;
import com.sognisport.challenge.api.model.ExchangeHistory;
import com.sognisport.challenge.api.repository.ExchangeHistoryRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ExchangeService {

    @RestClient
    private ExchangeRateClient exchangeRateClient;

    @Inject
    private ExchangeHistoryRepository exchangeHistoryRepository; 

    @Transactional
    public ExchangeHistory getConversionRate(String fromCurrency, String toCurrency) {
        System.out.println("Solicitando taxa de câmbio de " + fromCurrency + " para " + toCurrency);
        
        // Chama o serviço externo para obter a taxa de câmbio
        ExchangeDTO exchangeDTO = exchangeRateClient.getExchangeRate(fromCurrency, toCurrency);
        
        // Verifica se a resposta foi bem-sucedida
        if ("success".equals(exchangeDTO.getResult())) {

            // Cria uma nova entrada de histórico de conversão
        	//A API deve armazenar a data e hora da consulta, a taxa de conversão obtida e as moedas de entrada em um banco de dados embutido.
            ExchangeHistory exchangeHistory = new ExchangeHistory();
            exchangeHistory.setBaseCode(exchangeDTO.getBase_code());
            exchangeHistory.setTargetCode(exchangeDTO.getTarget_code());
            exchangeHistory.setConversionRate(exchangeDTO.getConversion_rate());
            exchangeHistory.setRequestTimestamp(Instant.now());

            // Persistir o histórico no banco de dados
            exchangeHistoryRepository.persist(exchangeHistory); 

            // Retorna o histórico salvo
            return exchangeHistory;
        } else {
            // Caso a resposta não seja bem-sucedida, lança uma exceção específica
            throw new RuntimeException("Erro ao obter a taxa de conversão: " + exchangeDTO.getResult());
        }
    }
    
    public List<ExchangeHistory> listAllExchangeHistory() {     
        return exchangeHistoryRepository.listAll();
    }    
}
