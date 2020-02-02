package com.carpool.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carpool.server.dto.OfficeAddressDTO;
import com.carpool.server.dto.RideInfoDTO;
import com.carpool.server.entity.ActionRideData;
import com.carpool.server.entity.GetRidesData;
import com.carpool.server.entity.MapPathRequest;
import com.carpool.server.entity.RequestRideData;
import com.carpool.server.entity.RestResponse;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.service.RequestService;
import com.carpool.server.utils.Constants;
import com.carpool.server.utils.RestUtils;

@RestController
@RequestMapping(Constants.URI.REQUEST)
public class RequestController {

	@Autowired
	private RequestService requestService;

	@RequestMapping(value=Constants.URI.OFFICE, method=RequestMethod.GET)
	public ResponseEntity<RestResponse<List<OfficeAddressDTO>>> getOfficeAddress() throws UtilityException {
		return RestUtils.successResponse(requestService.getOfficeAddress());
	}

	@RequestMapping(value=Constants.URI.OFFICE, method=RequestMethod.POST)
	public ResponseEntity<RestResponse<OfficeAddressDTO>> saveOfficeAddress(@RequestBody OfficeAddressDTO addressDTO) throws UtilityException {
		return RestUtils.successResponse(requestService.saveOfficeAddress(addressDTO));
	}

	@RequestMapping(value=Constants.URI.GOOGLE_PATH, method=RequestMethod.POST)
	public ResponseEntity<RestResponse<Map<String, String>>> getPath(@RequestBody MapPathRequest pathRequest) throws UtilityException {
		return RestUtils.successResponse(requestService.getPath(pathRequest));
	}

	@RequestMapping(value=Constants.URI.DRIVER, method=RequestMethod.POST)
	public ResponseEntity<RestResponse<Boolean>> saveDriver(@RequestBody RideInfoDTO driverInfo) throws UtilityException {
		return RestUtils.successResponse(requestService.saveDriver(driverInfo));
	}

	@RequestMapping(value=Constants.URI.RIDER, method=RequestMethod.GET)
	public ResponseEntity<RestResponse<List<RideInfoDTO>>> getRides(@RequestBody GetRidesData getRidesData) throws UtilityException {
		return RestUtils.successResponse(requestService.getRides(getRidesData));
	}

	@RequestMapping(value=Constants.URI.REQUEST_RIDE, method=RequestMethod.POST)
	public ResponseEntity<RestResponse<Boolean>> requestRide(
			@RequestBody RequestRideData requestRideData) throws UtilityException {
		return RestUtils.successResponse(requestService.requestRide(requestRideData));
	}

	@RequestMapping(value=Constants.URI.ACTION_RIDE, method=RequestMethod.POST)
	public ResponseEntity<RestResponse<Boolean>> actionOnRide(
			@RequestBody ActionRideData actionRideData) throws UtilityException {
		return RestUtils.successResponse(requestService.actionOnRide(actionRideData));
	}
}