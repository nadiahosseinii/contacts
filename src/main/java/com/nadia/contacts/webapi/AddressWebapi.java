package com.nadia.contacts.webapi;

import java.util.List;

import com.nadia.contacts.business.repo.AddressRepo;
import com.nadia.contacts.model.Address;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/address")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressWebapi {

	@Inject
	private AddressRepo addressRepo;

	@POST
	@Path("/")
	@Transactional
	public Response save(Address address) {
		try {
			addressRepo.persist(address);
			if (!addressRepo.isPersistent(address)) {
				return Response.status(Status.BAD_REQUEST).entity("Not saved.").build();
			}
			return Response.status(Status.CREATED).entity(address).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}
	}

	// Bi Mored
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount() {
		try {
			long count = addressRepo.findAll().count();
			return Response.status(Status.OK).entity(count).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}
	}

	// Bi Mored
	@GET
	@Path("/")
	public Response getAll(@DefaultValue("1") @QueryParam("pageIndex") int pageIndex,
			@DefaultValue("10") @QueryParam("pageSize") int pageSize) {
		try {
			List<Address> address = addressRepo.findAll().page(pageIndex - 1, pageSize).list();
			return Response.status(Status.OK).entity(address).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}

	}

	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") long id) {
		try {
			Address address = addressRepo.findByIdOptional(id).orElseThrow(() -> new Exception("Not found"));
			return Response.status(Status.OK).entity(address).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}

	}

	@GET
	@Path("/contacts/{idContact}")
	public Response getAllByContactId(@PathParam("idContact") long idContact) {
		try {
			List<Address> address = addressRepo.getByContactId(idContact);
			return Response.status(Status.OK).entity(address).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") long id) {
		try {
			Address address = addressRepo.findByIdOptional(id).orElseThrow(() -> new Exception("Not found"));
			addressRepo.deleteById(id);
			return Response.status(Status.OK).entity(address).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}
	}

	@PUT
	@Path("/")
	public Response update(Address address) {
		try {
			addressRepo.findByIdOptional(address.getId()).orElseThrow(() -> new Exception("Not found"));
			addressRepo.persist(address);
			return Response.status(Status.OK).entity(address).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}
	}

}
