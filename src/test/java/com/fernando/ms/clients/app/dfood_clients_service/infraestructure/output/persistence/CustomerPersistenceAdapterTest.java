package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.CustomerPersistenceAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.CustomerPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.CustomerEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.CustomerJpaRepository;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtilsCustomer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerPersistenceAdapterTest {

    @Mock
    private CustomerJpaRepository customerJpaRepository;

    @Mock
    private CustomerPersistenceMapper customerPersistenceMapper;

    @InjectMocks
    private CustomerPersistenceAdapter clientPersistenceAdapter;

    @Test
    @DisplayName("When Addresses Information Exists Expect A List Customers Availability")
    void When_AddressesInformationExists_Expect_AListCustomersAvailability(){
        CustomerEntity customerEntity = TestUtilsCustomer.buildCustomerEntityMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerJpaRepository.findAll()).thenReturn(Collections.singletonList(customerEntity));
        when(customerPersistenceMapper.toCustomers(anyList())).thenReturn(Collections.singletonList(customer));

        List<Customer> customers =clientPersistenceAdapter.findAll();
        assertEquals(1, customers.size());
        Mockito.verify(customerJpaRepository,times(1)).findAll();
        Mockito.verify(customerPersistenceMapper,times(1)).toCustomers(anyList());

    }

    @Test
    @DisplayName("When Addresses Information Exists Expect A List Customers Availability")
    void When_AddressesInformationNotExists_Expect_AListVoid(){
        when(customerJpaRepository.findAll()).thenReturn(Collections.emptyList());
        when(customerPersistenceMapper.toCustomers(anyList())).thenReturn(Collections.emptyList());

        List<Customer> customers =clientPersistenceAdapter.findAll();
        assertEquals(0, customers.size());
        Mockito.verify(customerJpaRepository,times(1)).findAll();
        Mockito.verify(customerPersistenceMapper,times(1)).toCustomers(anyList());

    }

    @Test
    @DisplayName("When Customer Identifier Is Correct Expect Customer Information Successfully")
    void When_CustomerIdentifierIsCorrectExpectCustomerInformationSuccessfully(){
        CustomerEntity customerEntity = TestUtilsCustomer.buildCustomerEntityMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerJpaRepository.findById(anyLong())).thenReturn(Optional.of(customerEntity));
        when(customerPersistenceMapper.toCustomer(any(CustomerEntity.class))).thenReturn(customer);
        Optional<Customer> clientResponse=clientPersistenceAdapter.findById(1L);
        assertNotNull(clientResponse);
        Mockito.verify(customerJpaRepository,times(1)).findById(anyLong());
        Mockito.verify(customerPersistenceMapper,times(1)).toCustomer(any(CustomerEntity.class));

    }

    @Test
    @DisplayName("When Customer Information Is Correct Expect Customer Information To Be Saved Successfully")
    void When_CustomerInformationIsCorrect_Expect_CustomerInformationToBeSavedSuccessfully(){
        CustomerEntity customerEntity = TestUtilsCustomer.buildCustomerEntityMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerJpaRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        when(customerPersistenceMapper.toCustomerEntity(any(Customer.class))).thenReturn(customerEntity);
        when(customerPersistenceMapper.toCustomer(any(CustomerEntity.class))).thenReturn(customer);
        Customer customerNew =clientPersistenceAdapter.save(customer);
        assertNotNull(customerNew);
        assertEquals(customer, customerNew);
        Mockito.verify(customerJpaRepository,times(1)).save(any(CustomerEntity.class));
        Mockito.verify(customerPersistenceMapper,times(1)).toCustomerEntity(any(Customer.class));
        Mockito.verify(customerPersistenceMapper,times(1)).toCustomer(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("When Customer Information Is Correct Expect Customer Information To Be Saved Successfully")
    void When_CustomerAndAddressInformationIsCorrect_Expect_CustomerAndAddressInformationToBeSavedSuccessfully(){
        CustomerEntity customerEntity = TestUtilsCustomer.buildCustomerAddressesEntityMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerJpaRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
        when(customerPersistenceMapper.toCustomerEntity(any(Customer.class))).thenReturn(customerEntity);
        when(customerPersistenceMapper.toCustomer(any(CustomerEntity.class))).thenReturn(customer);
        Customer customerNew =clientPersistenceAdapter.save(customer);
        assertNotNull(customerNew);
        assertEquals(customer, customerNew);
        Mockito.verify(customerJpaRepository,times(1)).save(any(CustomerEntity.class));
        Mockito.verify(customerPersistenceMapper,times(1)).toCustomerEntity(any(Customer.class));
        Mockito.verify(customerPersistenceMapper,times(1)).toCustomer(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("When Customer Identifier Is Correct Expect Customer Information To Be Deleted Successfully")
    void When_CustomerIdentifierIsCorrect_Expect_CustomerInformationToBeDeletedSuccessfully(){
        doNothing().when(customerJpaRepository).deleteById(anyLong());
        clientPersistenceAdapter.delete(1L);
        Mockito.verify(customerJpaRepository,times(1)).deleteById(anyLong());
    }
}
