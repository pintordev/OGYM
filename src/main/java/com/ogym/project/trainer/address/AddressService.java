package com.ogym.project.trainer.address;

import com.ogym.project.trainer.trainer.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public Address create(Integer zoneCode, String mainAddress, String subAddress, Double latitude, Double longitude, Trainer trainer) {
        Address address = new Address();
        address.setZoneCode(zoneCode);
        address.setMainAddress(mainAddress);
        address.setSubAddress(subAddress);
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        address.setTrainer(trainer);
        return addressRepository.save(address);
    }


}
