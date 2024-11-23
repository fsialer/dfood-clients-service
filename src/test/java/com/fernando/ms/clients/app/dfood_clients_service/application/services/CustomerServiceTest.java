package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.CustomerPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerEmailAlreadyExistsException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerPersistencePort customerPersistencePort;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("When There Are Customers Expect A List Customers")
    void When_ThereAreCustomers_Expect_AListCustomers() {
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerPersistencePort.findAll()).thenReturn(Collections.singletonList(customer));
        List<Customer> customers = customerService.findAll();
        assertEquals(1, customers.size());
        Mockito.verify(customerPersistencePort, times(1)).findAll();
    }

    @Test
    @DisplayName("When There Are Not Customers Expect A List Void")
    void When_ThereAreNotCustomers_Expect_AListVoid() {
        when(customerPersistencePort.findAll()).thenReturn(Collections.emptyList());
        List<Customer> customers = customerService.findAll();
        assertEquals(0, customers.size());
        Mockito.verify(customerPersistencePort, times(1)).findAll();
    }

    @Test
    @DisplayName("When Customer By Id Is Correct Expect Customer Information")
    void When_CustomerByIdIsCorrect_Expect_CustomerInformation() {
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerPersistencePort.findById(anyLong())).thenReturn(Optional.of(customer));
        Customer customerRes = customerService.findById(1L);
        assertEquals(customer, customerRes);
        assertNotNull(customerRes);
        Mockito.verify(customerPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Expect CustomerNotFoundException When Customer By Id Is Unknown")
    void Expect_CustomerNotFoundException_When_CustomerByIdIsUnknown() {
        when(customerPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(1L));
        Mockito.verify(customerPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("When Customer Information Is Correct Expect Customer To Be Save")
    void When_CustomerInformationIsCorrect_Expect_CustomerToBeSave() {
        Customer customer = TestUtilsCustomer.buildCustomerAddressMock();
        when(customerPersistencePort.save(any(Customer.class))).thenReturn(customer);
        when(customerPersistencePort.existsByEmail(anyString())).thenReturn(false);
        Customer customerResponse = customerService.save(customer);
        assertEquals(customer, customerResponse);
        Mockito.verify(customerPersistencePort, times(1)).save(any(Customer.class));
        Mockito.verify(customerPersistencePort, times(1)).existsByEmail(anyString());
    }

    @Test
    @DisplayName("Expect CustomerEmailAlreadyExistsException When Customer Email To Saved Exists")
    void Expect_CustomerEmailAlreadyExistsException_When_CustomerEmailToSavedExists() {
        Customer customer = TestUtilsCustomer.buildCustomerAddressMock();
        when(customerPersistencePort.existsByEmail(anyString())).thenReturn(true);
        assertThrows(CustomerEmailAlreadyExistsException.class, () -> customerService.save(customer));
        Mockito.verify(customerPersistencePort, times(0)).save(any(Customer.class));
        Mockito.verify(customerPersistencePort, times(1)).existsByEmail(anyString());
    }

    @Test
    @DisplayName("When Customer Information Is Correct Expect Customer Information To be Updated")
    void When_CustomerInformationIsCorrect_Expect_CustomerInformationTobeUpdated() {
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        Customer customerEmail = TestUtilsCustomer.buildCustomerEmailChangedMock();
        when(customerPersistencePort.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(customerPersistencePort.save(any(Customer.class))).thenReturn(customerEmail);
        Customer customerResponse = customerService.update(1L, customerEmail);
        assertEquals(customerEmail, customerResponse);
        Mockito.verify(customerPersistencePort, times(1)).save(any(Customer.class));
        Mockito.verify(customerPersistencePort, times(1)).findById(anyLong());
        Mockito.verify(customerPersistencePort, times(1)).existsByEmail(anyString());
    }

    @Test
    @DisplayName("Expect CustomerNotFoundException When Customer Identifier Is Unknown")
    void Expect_CustomerNotFoundException_When_CustomerIdentifierIsUnknown() {
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.update(1L, customer));
        Mockito.verify(customerPersistencePort, times(0)).save(any(Customer.class));
        Mockito.verify(customerPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Expect CustomerEmailAlreadyExistsException When Customer Email To Updated Exists")
    void Expect_CustomerEmailAlreadyExistsException_When_CustomerEmailToUpdatedExists() {
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        Customer customerEmail = TestUtilsCustomer.buildCustomerEmailChangedMock();
        when(customerPersistencePort.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerPersistencePort.existsByEmail(anyString())).thenReturn(true);
        assertThrows(CustomerEmailAlreadyExistsException.class, () -> customerService.update(1L, customerEmail));
        Mockito.verify(customerPersistencePort, times(0)).save(any(Customer.class));
        Mockito.verify(customerPersistencePort, times(1)).findById(anyLong());
        Mockito.verify(customerPersistencePort, times(1)).existsByEmail(anyString());
    }

    @Test
    @DisplayName("When Customer Identifier Is Correct Expect Customer Information To Be Inactive")
    void When_CustomerIdentifierIsCorrect_Expect_CustomerInformationToBeInactive(){
        Customer customerNew = TestUtilsCustomer.buildCustomerInactiveMock();
        when(customerPersistencePort.save(any(Customer.class)))
                .thenReturn(customerNew);
        when(customerPersistencePort.findById(anyLong()))
                .thenReturn(Optional.of(customerNew));
        Customer customer =customerService.inactive(1L);
        assertEquals(customerNew.getStatusCustomer(), customer.getStatusCustomer());
        Mockito.verify(customerPersistencePort,times(1)).save(any(Customer.class));
        Mockito.verify(customerPersistencePort,times(1)).findById(anyLong());

    }

    @Test
    @DisplayName("Expect CustomerNotFoundException When Customer Identifier by Inactive Is Unknown")
    void Expect_CustomerNotFoundException_When_CustomerIdentifierByInactiveIsUnknown(){
        when(customerPersistencePort.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class,()->customerService.inactive(2L));
        Mockito.verify(customerPersistencePort,times(0)).save(any(Customer.class));
        Mockito.verify(customerPersistencePort,times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("When Customer Identifier Is Correct Expect Customer Information To Be Deleted")
    void When_CustomerIdentifierIsCorrect_Expect_CustomerInformationToBeDeleted(){
        Customer customerNew = TestUtilsCustomer.buildCustomerInactiveMock();
        doNothing().when(customerPersistencePort).delete(anyLong());
        when(customerPersistencePort.findById(anyLong()))
                .thenReturn(Optional.of(customerNew));
        customerService.delete(1L);
        Mockito.verify(customerPersistencePort,times(1)).delete(anyLong());
        Mockito.verify(customerPersistencePort,times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Expect CustomerNotFoundException When Customer Identifier By Delete Is Unknown")
    void Expect_CustomerNotFoundException_When_CustomerIdentifierByDeleteIsUnknown(){
        when(customerPersistencePort.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class,()->customerService.delete(1L));
        Mockito.verify(customerPersistencePort,times(0)).delete(anyLong());
        Mockito.verify(customerPersistencePort,times(1)).findById(anyLong());
    }
}


