package com.nossogame.bancoimobiliario.service;

import java.util.Random;

public class CodigoAleatorio {

    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITOS = "0123456789";

    public static String gerarCodigo() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            codigo.append(DIGITOS.charAt(random.nextInt(DIGITOS.length())));
        }

        for (int i = 0; i < 4; i++) {
            codigo.append(LETRAS.charAt(random.nextInt(LETRAS.length())));
        }

        return embaralharString(codigo.toString(), random);
    }

    private static String embaralharString(String input, Random random) {
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length(); i++) {
            int j = random.nextInt(sb.length());
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));
            sb.setCharAt(j, temp);
        }
        return sb.toString();
    }
}
