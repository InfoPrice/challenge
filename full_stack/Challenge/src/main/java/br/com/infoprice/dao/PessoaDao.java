package br.com.infoprice.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.com.infoprice.model.Pessoa;

public class PessoaDao {
 
	private final EntityManagerFactory entityManagerFactory;
 
	private final EntityManager entityManager;
 
	public PessoaDao(){
 
		/*CRIANDO O NOSSO EntityManagerFactory COM AS PORPRIEDADOS DO ARQUIVO persistence.xml */
		this.entityManagerFactory = Persistence.createEntityManagerFactory("persistence_unit");
 
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
 
	public Pessoa salva(Pessoa pessoaEntity){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(pessoaEntity);
		this.entityManager.getTransaction().commit();
		
		
		return pessoaEntity;
	}
 
	public Pessoa altera(Pessoa pessoa){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(pessoa);
		this.entityManager.getTransaction().commit();
		
		return pessoa;
	}
 
	@SuppressWarnings("unchecked")
	public List<Pessoa> buscaTodos(){
 
		return this.entityManager.createQuery("SELECT p FROM Pessoa p ORDER BY p.nome").getResultList();
	}
 
	public Pessoa getPessoa(int id){
 
		return this.entityManager.find(Pessoa.class, id);
	}
 
	public void excluir(Pessoa pessoa){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(pessoa);
		this.entityManager.getTransaction().commit();
 
	}
}