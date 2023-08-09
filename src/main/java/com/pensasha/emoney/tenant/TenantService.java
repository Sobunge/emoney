package com.pensasha.emoney.tenant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    // Adding tenant
    public Tenant addTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    // Updating tenant
    public Tenant updateTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    // Getting a tenant
    public Tenant getTenant(int idNumber) {
        return tenantRepository.findById(idNumber).get();
    }

    // Getting all tenants
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    // Deleting a tenant
    public void deleteTenant(int idNumber) {
        tenantRepository.deleteById(idNumber);
    }

}
