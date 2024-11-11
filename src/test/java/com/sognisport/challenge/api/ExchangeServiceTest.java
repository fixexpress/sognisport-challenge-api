package com.sognisport.challenge.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sognisport.challenge.api.client.ExchangeRateClient;
import com.sognisport.challenge.api.dto.ExchangeDTO;
import com.sognisport.challenge.api.model.ExchangeHistory;
import com.sognisport.challenge.api.repository.ExchangeHistoryRepository;
import com.sognisport.challenge.api.service.ExchangeService;

public class ExchangeServiceTest {

    @InjectMocks
    ExchangeService exchangeService;

    @Mock
    ExchangeRateClient exchangeRateClient;

    @Mock
    ExchangeHistoryRepository exchangeHistoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetConversionRateSuccess() {
    	
        // Simular o retorno da API externa (ExchangeRateClient)
        ExchangeDTO mockExchangeDTO = new ExchangeDTO();
        mockExchangeDTO.setBase_code("USD");
        mockExchangeDTO.setTarget_code("BRL");
        mockExchangeDTO.setConversion_rate(5.50);
        mockExchangeDTO.setResult("success");

        // Mockar o comportamento do método getExchangeRate
        when(exchangeRateClient.getExchangeRate("USD", "BRL")).thenReturn(mockExchangeDTO);

        // Chamar o método do serviço
        ExchangeHistory result = exchangeService.getConversionRate("USD", "BRL");

        // Verificar se o resultado está correto
        assertNotNull(result);
        assertEquals("USD", result.getBaseCode());
        assertEquals("BRL", result.getTargetCode());
        assertEquals(5.50, result.getConversionRate());
        assertNotNull(result.getRequestTimestamp());

        // Verificar se o histórico foi persistido no banco de dados
        verify(exchangeHistoryRepository, times(1)).persist(any(ExchangeHistory.class));
    }

    @Test
    public void testGetConversionRateFailure() {

    	// Simular o retorno da API externa com erro
        ExchangeDTO mockExchangeDTO = new ExchangeDTO();
        mockExchangeDTO.setResult("error");

        // Mockar o comportamento do método getExchangeRate
        when(exchangeRateClient.getExchangeRate("USD", "BRL")).thenReturn(mockExchangeDTO);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            exchangeService.getConversionRate("USD", "BRL");
        });

        assertEquals("Erro ao obter a taxa de conversão: error", exception.getMessage());
    }

    @Test
    public void testListAllExchangeHistory() {
    	
        // Simular o retorno de múltiplos históricos de conversão
        ExchangeHistory mockHistory1 = new ExchangeHistory();
        mockHistory1.setBaseCode("USD");
        mockHistory1.setTargetCode("BRL");
        mockHistory1.setConversionRate(5.50);
        mockHistory1.setRequestTimestamp(Instant.now());

        ExchangeHistory mockHistory2 = new ExchangeHistory();
        mockHistory2.setBaseCode("EUR");
        mockHistory2.setTargetCode("BRL");
        mockHistory2.setConversionRate(6.20);
        mockHistory2.setRequestTimestamp(Instant.now());

        List<ExchangeHistory> mockHistoryList = List.of(mockHistory1, mockHistory2);

        // Mockar o comportamento do repositório para retornar uma lista
        when(exchangeHistoryRepository.listAll()).thenReturn(mockHistoryList);

        // Chamar o método de listagem
        List<ExchangeHistory> result = exchangeHistoryRepository.listAll();
        
        // Listar todos os históricos no console
        System.out.println("Lista de Históricos de Câmbio:");
        for (ExchangeHistory history : result) {
            System.out.println("TESTE RETORNO BASE --> " + history.getBaseCode() +
                               ", TargetCode: " + history.getTargetCode() +
                               ", ConversionRate: " + history.getConversionRate() +
                               ", Timestamp: " + history.getRequestTimestamp());
        }

        // Verificar se os dados estão corretos
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getBaseCode());
        assertEquals("EUR", result.get(1).getBaseCode());
    }
}
