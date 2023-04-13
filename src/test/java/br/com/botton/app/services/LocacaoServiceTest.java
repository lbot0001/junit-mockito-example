package br.com.botton.app.services;


import br.com.botton.app.entities.Filme;
import br.com.botton.app.entities.Locacao;
import br.com.botton.app.entities.Usuario;
import br.com.botton.app.exceptions.FilmeSemEstoqueException;
import br.com.botton.app.exceptions.LocadoraException;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static br.com.botton.app.util.DataUtils.isMesmaData;
import static br.com.botton.app.util.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocacaoServiceTest {

    //garante a coleta do numero de testes com erro
    @Rule
    ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private LocacaoService service;

    private List<Filme> filmes;

    @BeforeEach
    public void setup(){
        service = new LocacaoService();
        filmes = new ArrayList<Filme>();
    }

    @AfterEach
    public void tearDown(){
        System.out.println("Nothing to do");
    }

    @BeforeAll
    public static void setupClass(){
        System.out.println("Before class");
    }

    @AfterAll
    public static void afterClass(){
        System.out.println("After class");
    }

    @Test
    public void testeLocacao_com_assertThat() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(locacao.getValor(), is(equalTo(5.0)));
        assertThat(locacao.getValor(), is(not(6.0)));
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test
    public void LocacaoServiceTest() throws Exception {

        //FIRST

        //cenario
        Usuario usuario = new Usuario("Luiz");
        filmes = Arrays.asList(new Filme("Jurassic Park", 2, 5.0));

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);
        //verificacao
        error.checkThat(locacao.getValor(), is(5.0));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(false));
        System.out.println(locacao.getDataRetorno());
    }

    @Test
    public void testeLocacao_filmeSemEstoque() throws LocadoraException {
        Usuario usuario = new Usuario("Luiz");
        filmes = Arrays.asList(new Filme("Jurassic Park", 0, 5.0));

        try {
            service.alugarFilme(usuario, filmes);
            Assert.fail("Deveria ter excessao");
        } catch (FilmeSemEstoqueException e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

    /*@Test //forma nova, nao funciona
    public void testeLocacao_filmeSemEstoque_2() throws Exception {
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Luiz");
        Filme filme = new Filme("Jurassic Park", 0, 5.0);

        exception.expect(FilmeSemEstoqueException.class);
        //exception.expectMessage("Filme sem estoque");

        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

    }*/

    @Test
    public void testLocacao_usuarioVazio_formaRobusta() throws FilmeSemEstoqueException {
        //cenario
        filmes = Arrays.asList(new Filme("Jurassic Park", 1, 5.0));

        //acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario nao informado"));
        }
    }

    @Test
    public void testLocacao_FilmeVazio_formaRobusta() throws FilmeSemEstoqueException {
        //cenario
        Usuario usuario = new Usuario("Luiz");

        //acao
        try {
            service.alugarFilme(usuario, null);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Filme nao informado"));
        }

    }

    /*@Test - forma nova, nao funciona
    public void testLocacao_FilmeVazio_formaNova() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        LocacaoService locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Luiz");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme nao informado");

        Locacao locacao = locacaoService.alugarFilme(usuario, null);

    }*/


}
