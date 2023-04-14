package br.com.botton.app.services;

import br.com.botton.app.exceptions.DivisaoPorZeroException;

public class Calculadora {

    private int resultado;
    public int soma(int a, int b) {

        resultado = a + b;

        return resultado;

    }

    public int subtracao(int a, int b) {
        return resultado = a-b;
    }

    public int dividir(int a, int b) throws DivisaoPorZeroException {
        if(b == 0){
            throw new DivisaoPorZeroException("Divisao por zero");
        }
        return a/b;
    }

    public int multiplicar(int a, int b) {
        return a*b;
    }
}
