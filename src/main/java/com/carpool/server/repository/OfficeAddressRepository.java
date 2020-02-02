package com.carpool.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carpool.server.dto.OfficeAddressDTO;

@Repository
public interface OfficeAddressRepository extends MongoRepository<OfficeAddressDTO, String> {
}
