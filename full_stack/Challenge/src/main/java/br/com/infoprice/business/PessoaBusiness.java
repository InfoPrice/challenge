package br.com.infoprice.business;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.infoprice.dao.PessoaDao;
import br.com.infoprice.model.Pessoa;

public class PessoaBusiness {
	private final PessoaDao pessoaDao = new PessoaDao();

	public PessoaBusiness() {
	}

	public Pessoa cadastra(Pessoa pessoaJson) throws Exception {

		try {
			validaCpf(pessoaJson.getCpf());
			Pessoa pessoa = new Pessoa(pessoaJson.getNome(), pessoaJson.getEmail(), pessoaJson.getCpf());

			return pessoaDao.salva(pessoa);
		} catch (RuntimeException ex) {
			throw new Exception("Erro ao cadastrar nova pessoa, " + ex.getMessage());
		} catch (Exception ex) {
			throw new Exception("Erro ao cadastrar nova pessoa.", ex);
		}

	}

	public List<Pessoa> buscaTodos() {
		return pessoaDao.buscaTodos();

	}

	public Pessoa buscaPorId(int id) throws Exception {
		try {
			Pessoa pessoaDb = pessoaDao.getPessoa(id);
			if (pessoaDb == null)
				throw new RuntimeException("Pessoa informada não encontrada no BD!");

			return pessoaDb;

		} catch (RuntimeException ex) {
			throw new RuntimeException("Erro ao buscar Pessoa, " + ex.getMessage());
		} catch (Exception ex) {
			throw new RuntimeException("Erro ao buscar Pessoa");
		}

	}

	public void deleta(int id) throws Exception {
		try {
			Pessoa pessoaDb = this.buscaPorId(id);

			pessoaDao.excluir(pessoaDb);
		} catch (RuntimeException ex) {
			throw new RuntimeException("Erro ao Deletar Pessoa, " + ex.getMessage());
		} catch (Exception ex) {
			throw new Exception("Erro ao Deletar Pessoa");
		}

	}

	public Pessoa altera(int id, Pessoa pessoa) throws Exception {
		try {
			Pessoa pessoaDb = this.buscaPorId(id);

			validaCpf(pessoa.getCpf());
			pessoaDb.setCpf(pessoa.getCpf());
			pessoaDb.setEmail(pessoa.getEmail());
			pessoaDb.setNome(pessoa.getNome());

			return pessoaDao.altera(pessoaDb);
		} catch (RuntimeException ex) {
			throw new RuntimeException("Erro ao alterar pessoa, " + ex.getMessage());

		} catch (Exception ex) {
			throw new RuntimeException("Erro ao alterar pessoa");
		}

	}

	public void validaCpf(String cpf) {

		String regex = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cpf);
		if (!matcher.matches())
			throw new RuntimeException("cpf inválido!");

	}

}
