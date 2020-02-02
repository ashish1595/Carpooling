package com.carpool.server.service;

import java.util.List;
import java.util.Map;

import com.carpool.server.dto.RideInfoDTO;
import com.carpool.server.dto.OfficeAddressDTO;
import com.carpool.server.entity.ActionRideData;
import com.carpool.server.entity.GetRidesData;
import com.carpool.server.entity.MapPathRequest;
import com.carpool.server.entity.RequestRideData;
import com.carpool.server.exception.UtilityException;

public interface RequestService {

	List<OfficeAddressDTO> getOfficeAddress() throws UtilityException;
	Map<String, String> getPath(MapPathRequest pathRequest) throws UtilityException;
	OfficeAddressDTO saveOfficeAddress(OfficeAddressDTO addressDTO) throws UtilityException;
	Boolean saveDriver(RideInfoDTO driverInfo) throws UtilityException;
	List<RideInfoDTO> getRides(GetRidesData getRidesData) throws UtilityException;
	Boolean requestRide(RequestRideData requestRideData) throws UtilityException;
	Boolean actionOnRide(ActionRideData actionRideData) throws UtilityException;
}