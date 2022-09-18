package com.javatechie.service;

import com.javatechie.dto.CustomerResponse;
import com.javatechie.entity.Customer;
import com.javatechie.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile(value = { "dev", "stg", "prod" })
public class CustomerService {

    @Value("${application.message}")
    private String message;

    @PostConstruct
    public void getMessageVal() {
        System.out.println("******" + message);
    }

    @Autowired
    private CustomerRepository repository;

    public List<Customer> addNewCustomers(List<Customer> customers) {
        return repository.saveAll(customers);
    }

    public List<CustomerResponse> getCustomers() {
        List<Customer> customerList = repository.findAll();
        return customerList.stream()
                .map(customer -> new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), getDate(customer.getDob())))
                .collect(Collectors.toList());
    }


    private String getDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

}
