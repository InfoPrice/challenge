package br.com.infoprice.business;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.infoprice.model.Pessoa;

public class PessoaBusinessTeste {

	
	public PessoaBusinessTeste() {
	}
	
	
	@Test
	public void testeCadastrarException() {
		PessoaBusiness pessoaBusiness = new PessoaBusiness();
		Pessoa pessoa = new Pessoa("Jadson Correa de Almeida", "jadsonteste@teste.com.br", "123456");
	
		
		Exception thrown = assertThrows(RuntimeException.class, () -> pessoaBusiness.cadastra(pessoa));
		
		assertTrue(thrown.getMessage().contains("Erro ao cadastrar nova pessoa"));
	}
	
	@Test
	public void testeCadastrarOk() throws Exception {
		PessoaBusiness pessoaBusiness = new PessoaBusiness();
		Pessoa pessoa = new Pessoa("Jadson Correa de Almeida", "jadsonteste@teste.com.br", "13451266709");
		Pessoa pessoaBd =  pessoaBusiness.cadastra(pessoa);
		assertTrue(pessoa.equals(pessoaBd));
	}
	
	



}
