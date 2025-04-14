package com.nossogame.bancoimobiliario.model.enuns;

public enum CorPropriedade {
    ROXO("#800080"),
    CIANO("#00FFFF"),
    ROSA("#FF69B4"),
    LARANJA("#FFA500"),
    VERMELHO("#FF0000"),
    AMARELO("#FFFF00"),
    VERDE("#008000"),
    AZUL("#0000FF");

    private final String rgb;

    CorPropriedade(String rgb) {
        this.rgb = rgb;
    }

    public String getRgb() {
        return rgb;
    }
}
