package com.cilut.coreapi.service.impl;

import com.cilut.coreapi.entity.Address;
import com.cilut.coreapi.repository.AddressRepository;
import com.cilut.coreapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public void saveAddress(Address address) {
        this.addressRepository.save(address);
    }
}
