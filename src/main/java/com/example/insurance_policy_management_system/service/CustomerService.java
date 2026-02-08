package com.example.insurance_policy_management_system.service;

import com.example.insurance_policy_management_system.entity.Customer;
import com.example.insurance_policy_management_system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }
    
    public Customer updateCustomer(Integer id, Customer customer) {
        Customer existing = getCustomerById(id);
        if (existing != null) {
            existing.setFirstName(customer.getFirstName());
            existing.setLastName(customer.getLastName());
            existing.setEmail(customer.getEmail());
            existing.setPhone(customer.getPhone());
            existing.setAddress(customer.getAddress());
            return customerRepository.save(existing);
        }
        return null;
    }
    
    public boolean deleteCustomer(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
