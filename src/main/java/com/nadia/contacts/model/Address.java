package com.nadia.contacts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADDRESS")
@NamedQuery(name = "Address.getByContactId", query = "SELECT e FROM Address AS e WHERE e.contact.id = :contanctid")
public class Address extends AddtionalData {

}
