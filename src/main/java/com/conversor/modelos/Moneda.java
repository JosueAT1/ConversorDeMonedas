package com.conversor.modelos;

import java.util.Map;

public record Moneda(
    String base_code,
    Map<String, Double> conversion_rates
) {
}
