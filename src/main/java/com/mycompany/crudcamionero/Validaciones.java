package com.mycompany.crudcamionero;

import java.math.BigDecimal;

/** Validaciones que se repiten en varias tablas. */
public class Validaciones {
    public static String texto(String valor, String campo, int maximo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        valor = valor.trim();
        if (valor.length() > maximo) {
            throw new IllegalArgumentException(campo + " admite hasta " + maximo + " caracteres.");
        }
        return valor;
    }

    public static void id(int valor, String campo) {
        if (valor <= 0) {
            throw new IllegalArgumentException(campo + " debe ser un número entero mayor que cero.");
        }
    }

    public static void salario(double valor) {
        if (!Double.isFinite(valor) || valor < 0 || valor >= 10000000000000000.0) {
            throw new IllegalArgumentException("El salario debe ser un número no negativo menor que 10000000000000000.");
        }
        BigDecimal numero = BigDecimal.valueOf(valor).stripTrailingZeros();
        if (numero.scale() > 2) {
            throw new IllegalArgumentException("El salario admite hasta 2 decimales.");
        }
    }
}
