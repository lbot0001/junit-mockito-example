package br.com.botton.app.services;

import br.com.botton.app.exceptions.DivisaoPorZeroException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculadoraTeste {

    @Test
    public void deveSomarDoisValores(){
        int a = 5;
        int b = 2;

        Calculadora calculadora = new Calculadora();

        Assert.assertEquals(7, calculadora.soma(a, b));
    }

    @Test
    public void deveSubtrairDoisValores(){
        //cenario
        int a = 5;
        int b = 2;

        Calculadora calculadora = new Calculadora();
        //acao
        var resposta = calculadora.subtracao(a,b);
        //verificacao
        Assert.assertEquals(3, resposta);
    }

    @Test
    public void deveDividirDoisValores() throws DivisaoPorZeroException {
        //cenario
        int a = 4;
        int b = 2;

        Calculadora calculadora = new Calculadora();
        //acao
        var resposta = calculadora.dividir(a,b);
        //verificacao
        Assert.assertEquals(2, resposta);
    }

    @Test
    public void deveLancarExcessaoAoDividirPorZero(){
        int a = 4;
        int b = 0;

        Calculadora calculadora = new Calculadora();

        try {
            var resposta = calculadora.dividir(a,b);
            Assert.fail();
        } catch (DivisaoPorZeroException e) {
            assertThat(e.getMessage(), is("Divisao por zero"));
        }
    }

}
