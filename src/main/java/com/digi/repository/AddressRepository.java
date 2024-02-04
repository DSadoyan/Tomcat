package com.digi.repository;

import com.digi.model.Address;

public interface AddressRepository {
    public Address saveAddress(Address address);

    public Address getAddressByUserId(int userId);

    public void deleteAddress(int address_id);
}
