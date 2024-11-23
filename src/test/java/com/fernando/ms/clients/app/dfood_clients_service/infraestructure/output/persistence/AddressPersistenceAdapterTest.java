package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.AddressPersistenceAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.AddressPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.CustomerPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.CustomerEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.AddressJpaRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressPersistenceAdapterTest {
    @Mock
    private AddressJpaRepository addressJpaRepository;

    @Mock
    private AddressPersistenceMapper addressPersistenceMapper;

    @Mock
    private CustomerPersistenceMapper customerPersistenceMapper;

    @InjectMocks
    private AddressPersistenceAdapter addressPersistenceAdapter;

    @Test
    @DisplayName("When Addresses Information Exists  Expect A List Addresses Availability")
    void When_AddressesInformationExists_Expect_AListAddressesAvailability(){
        AddressEntity addressEntity= TestUtilsAddress.buildAddressEntityMock();
        Address address= TestUtilsAddress.buildAddressMock();
        when(addressJpaRepository.findAll()).thenReturn(Collections.singletonList(addressEntity));
        when(addressPersistenceMapper.toAddresses(anyList())).thenReturn(Collections.singletonList(address));

        List<Address> addresses=addressPersistenceAdapter.findAll();
        assertEquals(1,addresses.size());
        Mockito.verify(addressJpaRepository,times(1)).findAll();
        Mockito.verify(addressPersistenceMapper,times(1)).toAddresses(anyList());

    }

    @Test
    @DisplayName("When Address Information No Exists Expect A List Void")
    void When_AddressInformationNoExists_Expect_AListVoid(){
        when(addressJpaRepository.findAll()).thenReturn(Collections.emptyList());
        when(addressPersistenceMapper.toAddresses(anyList())).thenReturn(Collections.emptyList());

        List<Address> addresses=addressPersistenceAdapter.findAll();
        assertEquals(0,addresses.size());
        Mockito.verify(addressJpaRepository,times(1)).findAll();
        Mockito.verify(addressPersistenceMapper,times(1)).toAddresses(anyList());

    }

    @Test
    @DisplayName("When Address Identifier Is Correct Expect Address Information Successfully")
    void When_AddressIdentifierIsCorrect_Expect_AddressInformationSuccessfully(){
        AddressEntity addressEntity= TestUtilsAddress.buildAddressEntityMock();
        Address address= TestUtilsAddress.buildAddressMock();
        when(addressJpaRepository.findById(anyLong())).thenReturn(Optional.of(addressEntity));
        when(addressPersistenceMapper.toAddress(any(AddressEntity.class))).thenReturn(address);
        Optional<Address> addressResponse=addressPersistenceAdapter.findById(1L);
        assertNotNull(addressResponse);
        Mockito.verify(addressJpaRepository,times(1)).findById(anyLong());
        Mockito.verify(addressPersistenceMapper,times(1)).toAddress(any(AddressEntity.class));

    }

    @Test
    @DisplayName("When Address Information Is Correct Expect Address Information To Be Saved Or Updated Successfully")
    void When_AddressInformationIsCorrectExpectAddressInformationToBeSavedOrUpdatedSuccessfully(){
        AddressEntity addressEntity= TestUtilsAddress.buildAddressEntityMock();
        Address address= TestUtilsAddress.buildAddressMock();
        when(addressJpaRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        when(addressPersistenceMapper.toAddressEntity(any(Address.class))).thenReturn(addressEntity);
        when(addressPersistenceMapper.toAddress(any(AddressEntity.class))).thenReturn(address);
        Address addressNew=addressPersistenceAdapter.save(address);
        assertNotNull(addressNew);
        assertEquals(address,addressNew);
        Mockito.verify(addressJpaRepository,times(1)).save(any(AddressEntity.class));
        Mockito.verify(addressPersistenceMapper,times(1)).toAddressEntity(any(Address.class));
        Mockito.verify(addressPersistenceMapper,times(1)).toAddress(any(AddressEntity.class));
    }

    @Test
    @DisplayName("When Address And Customer Information Is Correct Expect Address And Customer Information To Be Saved Or Updated Successfully")
    void When_AddressAndCustomerInformationIsCorrectExpectAddressAndCustomerInformationToBeSavedSuccessfully(){
        AddressEntity addressEntity= TestUtilsAddress.buildAddressEntityMock();
        CustomerEntity customerEntity = TestUtilsCustomer.buildCustomerEntityMock();
        Address address= TestUtilsAddress.buildAddressMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        when(customerPersistenceMapper.toCustomerEntity(any(Customer.class))).thenReturn(customerEntity);
        when(addressPersistenceMapper.toAddressEntity(any(Address.class))).thenReturn(addressEntity);
        when(addressJpaRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        when(addressPersistenceMapper.toAddress(any(AddressEntity.class))).thenReturn(address);
        Address addressNew=addressPersistenceAdapter.save(address, customer);
        assertNotNull(addressNew);
        assertEquals(address,addressNew);
        Mockito.verify(addressJpaRepository,times(1)).save(any(AddressEntity.class));
        Mockito.verify(addressPersistenceMapper,times(1)).toAddressEntity(any(Address.class));
        Mockito.verify(addressPersistenceMapper,times(1)).toAddress(any(AddressEntity.class));
        Mockito.verify(customerPersistenceMapper,times(1)).toCustomerEntity(any(Customer.class));
    }

    @Test
    @DisplayName("When Address Identifier Is Correct Expect Address Information To Be Deleted Successfully")
    void When_AddressIdentifierIsCorrect_Expect_AddressInformationToBeDeletedSuccessfully(){
        doNothing().when(addressJpaRepository).deleteById(anyLong());
        addressPersistenceAdapter.delete(1L);
        Mockito.verify(addressJpaRepository,times(1)).deleteById(anyLong());
    }
}
