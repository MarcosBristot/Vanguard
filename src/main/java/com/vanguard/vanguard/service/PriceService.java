package com.vanguard.vanguard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PriceService {

    private final RestTemplate restTemplate;

    public PriceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getCryptoPrice(String asset) {
        // Mapeamento de IDs amigáveis para os IDs da API
        Map<String, String> assetMapping = Map.of(
                "btc", "bitcoin",
                "eth", "ethereum",
                "doge", "dogecoin"
        );

        String apiAsset = assetMapping.getOrDefault(asset.toLowerCase(), asset.toLowerCase());
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + apiAsset + "&vs_currencies=usd";

        Map<String, Map<String, Object>> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey(apiAsset)) {
            Object price = response.get(apiAsset).get("usd");
            if (price instanceof Integer) {
                return ((Integer) price).doubleValue();
            } else if (price instanceof Double) {
                return (Double) price;
            } else {
                throw new RuntimeException("Formato de preço inesperado para o ativo: " + apiAsset);
            }
        }
        throw new RuntimeException("Preço não encontrado para o ativo: " + asset);
    }

}

