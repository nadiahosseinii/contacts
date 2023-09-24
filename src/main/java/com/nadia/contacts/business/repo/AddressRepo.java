package com.nadia.contacts.business.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nadia.contacts.model.Address;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressRepo implements PanacheRepository<Address> {
	public List<Address> getByContactId(long contanctid) {
		final String query = "#Address.getByContactId";
		Map<String, Object> params = new HashMap<>();
		params.put("contanctid", contanctid);
		return find(query, params).list();
	}

}
