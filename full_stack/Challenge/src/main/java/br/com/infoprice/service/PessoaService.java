package br.com.infoprice.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.infoprice.business.PessoaBusiness;
import br.com.infoprice.model.Pessoa;

@Path("/pessoa")
public class PessoaService {

	private final PessoaBusiness pessoaBO = new PessoaBusiness();

	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response cadastra(Pessoa pessoa) {

		try {
			return Response.ok(pessoaBO.cadastra(pessoa)).build();
		} catch (Exception ex) {
			return Response.ok(ex.getMessage()).status(400).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaTodos() {

		Object pessoas = pessoaBO.buscaTodos();

		return Response.ok(new Gson().toJson(pessoas)).build();

	}

	@GET
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/{id}")
	public Response busca(@PathParam("id") Integer id) throws Exception {

		try {
			return Response.ok(pessoaBO.buscaPorId(id)).build();
		} catch (RuntimeException ex) {
			return Response.ok(ex.getMessage()).status(400).build();
		}
		
	}

	@PUT
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/{id}")
	public Response altera(@PathParam("id") Integer id, Pessoa pessoa) throws Exception {

		try {
			return Response.ok(pessoaBO.altera(id, pessoa)).build();
		} catch (RuntimeException ex) {
			return Response.ok(ex.getMessage()).status(400).build();
		}

	}

	@DELETE
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/{id}")
	public Response deleta(@PathParam("id") Integer id) throws Exception {

		try {
			pessoaBO.deleta(id);
			return Response.ok().build();
		} catch (RuntimeException ex) {
			return Response.ok(ex.getMessage()).status(400).build();
		}

	}

	@GET
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/teste")
	public Response teste() {

		Pessoa pessoa = new Pessoa("Jadson", "jadson.correa@gmail.com", "13451266709");
		return Response.ok(pessoa).build();

	}
}
