package com.nadia.contacts.webapi;

import java.util.List;

import com.nadia.contacts.business.repo.ContactRepo;
import com.nadia.contacts.model.Contact;

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

@Path("/contacts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactWebapi {

	@Inject
	private ContactRepo contactRepo;

	@POST
	@Path("/")
	@Transactional
	public Response save(Contact contact) {
		try {
			contactRepo.persist(contact);
			if (!contactRepo.isPersistent(contact)) {
				return Response.status(Status.BAD_REQUEST).entity("Not saved.").build();
			}
			return Response.status(Status.CREATED).entity(contact).build();
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
			long count = contactRepo.findAll().count();
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
			List<Contact> contact = contactRepo.findAll().page(pageIndex - 1, pageSize).list();
			return Response.status(Status.OK).entity(contact).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}

	}

	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") long id) {
		try {
			Contact contact = contactRepo.findByIdOptional(id).orElseThrow(() -> new Exception("Not found"));
			return Response.status(Status.OK).entity(contact).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}

	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") long id) {
		try {
			Contact contact = contactRepo.findByIdOptional(id).orElseThrow(() -> new Exception("Not found"));
			contactRepo.deleteById(id);
			return Response.status(Status.OK).entity(contact).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}
	}

	@PUT
	@Path("/")
	public Response update(Contact contact) {
		try {
			contactRepo.findByIdOptional(contact.getId()).orElseThrow(() -> new Exception("Not found"));
			contactRepo.persist(contact);
			return Response.status(Status.OK).entity(contact).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Something went wrong.").build();
		}
	}

}
