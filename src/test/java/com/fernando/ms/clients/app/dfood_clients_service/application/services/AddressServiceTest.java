package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.AddressPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.CustomerPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.AddressNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtilsAddress;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressPersistencePort addressPersistencePort;

    @Mock
    private CustomerPersistencePort customerPersistencePort;

    @InjectMocks
    private AddressService addressService;

    @Test
    @DisplayName("When There Are Addresses Expect A List Addresses")
    void When_ThereAreAddresses_Expect_AListAddresses() {
        Address address = TestUtilsAddress.buildAddressMock();
        when(addressPersistencePort.findAll()).thenReturn(Collections.singletonList(address));
        List<Address> addresses = addressService.findAll();
        assertEquals(1, addresses.size());
        Mockito.verify(addressPersistencePort, times(1)).findAll();
    }

    @Test
    @DisplayName("When There Are Not Address Expect A List Void")
    void When_ThereAreNotAddress_Expect_AListVoid() {
        when(addressPersistencePort.findAll()).thenReturn(Collections.emptyList());
        List<Address> addresses = addressService.findAll();
        assertEquals(0, addresses.size());
        Mockito.verify(addressPersistencePort, times(1)).findAll();
    }

    @Test
    @DisplayName("When Address By Id Is Correct Expect Address Information")
    void When_AddressByIdIsCorrect_Expect_AddressInformation() {
        Address address = TestUtilsAddress.buildAddressMock();
        when(addressPersistencePort.findById(anyLong())).thenReturn(Optional.of(address));
        Address addressRes = addressService.findById(1L);
        assertEquals(address, addressRes);
        assertNotNull(addressRes);
        Mockito.verify(addressPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Expect AddressNotFoundException When User By Id Is Unknown")
    void Expect_AddressNotFoundException_When_UserByIdIsUnknown() {
        when(addressPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AddressNotFoundException.class, () -> addressService.findById(1L));
        Mockito.verify(addressPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("When Address Information Is Correct Expect Address To Be Saved")
    void When_AddressInformationIsCorrect_Expect_AddressToBeSaved() {
        Address address = TestUtilsAddress.buildAddressMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerPersistencePort.findById(anyLong())).thenReturn(Optional.of(customer));
        when(addressPersistencePort.save(any(Address.class),any(Customer.class))).thenReturn(address);
        Address addressResponse = addressService.save(address);
        assertEquals(address, addressResponse);
        Mockito.verify(addressPersistencePort, times(1)).save(any(Address.class),any(Customer.class));
        Mockito.verify(customerPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Expect CustomerNotFoundException When Address Information Your Customer Is Unknown")
    void Expect_CustomerNotFoundException_When_AddressInformationYourCustomerIsUnknown() {
        Address address = TestUtilsAddress.buildAddressMock();
        when(customerPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class,()->addressService.save(address));
        Mockito.verify(addressPersistencePort, times(0)).save(any(Address.class),any(Customer.class));
        Mockito.verify(customerPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("When Address Information Is Correct Expect Address To Be Updated")
    void When_AddressInformationIsCorrect_Expect_AddressToBeUpdated() {
        Address address = TestUtilsAddress.buildAddressMock();
        Address addressUpdated=TestUtilsAddress.buildAddressUpdatedMock();
        when(addressPersistencePort.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressPersistencePort.save(any(Address.class))).thenReturn(addressUpdated);
        Address clientResponse = addressService.update(1L, addressUpdated);
        assertEquals(addressUpdated, clientResponse);
        Mockito.verify(addressPersistencePort, times(1)).save(any(Address.class));
        Mockito.verify(addressPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Expect AddressNotFoundException When Address By Id Is Unknown")
    void Expect_AddressNotFoundException_When_AddressByIdIsUnknown() {
        Address address = TestUtilsAddress.buildAddressMock();
        when(addressPersistencePort.findById(anyLong())).thenReturn(Optional.of(address));
        assertThrows(AddressNotFoundException.class, () -> addressService.update(1L, address));
        Mockito.verify(addressPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("When Address Identifier Is Correct Expect Address To Be Deleted")
    void When_AddressIdentifierIsCorrect_Expect_AddressToBeDeleted(){
        Address clientNew=TestUtilsAddress.buildAddressMock();
        doNothing().when(addressPersistencePort).delete(anyLong());
        when(addressPersistencePort.findById(anyLong()))
                .thenReturn(Optional.of(clientNew));
        addressService.delete(1L);
        Mockito.verify(addressPersistencePort,times(1)).delete(anyLong());
        Mockito.verify(addressPersistencePort,times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Expect AddressNotFoundException When Address Identifier Is Unknown")
    void Expect_AddressNotFoundException_When_AddressIdentifierIsUnknown(){
        when(addressPersistencePort.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(AddressNotFoundException.class, () -> addressService.delete(2L));
        Mockito.verify(addressPersistencePort,times(1)).findById(anyLong());
        Mockito.verify(addressPersistencePort,times(0)).delete(anyLong());
    }
}
