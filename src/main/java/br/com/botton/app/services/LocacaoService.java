package br.com.botton.app.services;

import br.com.botton.app.entities.Filme;
import br.com.botton.app.entities.Locacao;
import br.com.botton.app.entities.Usuario;
import br.com.botton.app.exceptions.FilmeSemEstoqueException;
import br.com.botton.app.exceptions.LocadoraException;

import java.util.Date;
import java.util.List;

import static br.com.botton.app.util.DataUtils.adicionarDias;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		double valorPrecoLocacaoTotal = 0;

		if(filmes == null || filmes.isEmpty()){
			throw new LocadoraException("Filme nao informado");
		}

		for (Filme filme: filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
			valorPrecoLocacaoTotal+=filme.getPrecoLocacao();
		}



		if(usuario == null){
			throw new LocadoraException("Usuario nao informado");
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(valorPrecoLocacaoTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}


}