package com.nadia.contacts.business.repo;

import com.nadia.contacts.model.Contact;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContactRepo implements PanacheRepository<Contact> {

}
