package com.carpool.server.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import com.carpool.server.dto.RideInfoDTO;
import com.carpool.server.dto.NotificationDTO;
import com.carpool.server.dto.OfficeAddressDTO;
import com.carpool.server.entity.ActionRideData;
import com.carpool.server.entity.GetRidesData;
import com.carpool.server.entity.MapPathRequest;
import com.carpool.server.entity.RequestRideData;
import com.carpool.server.entity.RideData;
import com.carpool.server.enums.ActionType;
import com.carpool.server.exception.ResponseCode;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.repository.RideInfoRepository;
import com.carpool.server.repository.NotificationRepository;
import com.carpool.server.repository.OfficeAddressRepository;
import com.carpool.server.service.RequestService;
import com.carpool.server.utils.CommonUtility;
import com.carpool.server.utils.Decode;
import com.carpool.server.utils.SearchAlgorithm;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private OfficeAddressRepository officeAddressRepository;

	@Autowired
	private GoogleDirections googleDirection;

	@Autowired
	private RideInfoRepository rideInfoRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public List<OfficeAddressDTO> getOfficeAddress() throws UtilityException {
		return officeAddressRepository.findAll();
	}

	@Override
	public Map<String, String> getPath(MapPathRequest pathRequest) throws UtilityException {
		validatePathData(pathRequest);
		return findGoogleRoute(pathRequest.getOriginLatitude(), pathRequest.getOriginLongitude(),
				pathRequest.getDestinationLatitude(), pathRequest.getDestinationLongitude(),pathRequest.getOriginAddress(), pathRequest.getDestinationAddress(), Boolean.TRUE);
	}

	@Override
	public Boolean saveDriver(RideInfoDTO rideInfo) throws UtilityException {
		validateDriverInfoData(rideInfo);
		rideInfo.setStartTime(new Date(rideInfo.getStartTimeLong()));
		Map<String, String> googleData = findGoogleRoute(rideInfo.getOriginLatitude(), rideInfo.getOriginLongitude(),
				rideInfo.getDestinationLatitude(), rideInfo.getDestinationLongitude(), null, null, Boolean.TRUE);
		rideInfo.setStartAddress(googleData.get("start_address"));
		rideInfo.setEndAddress(googleData.get("end_address"));
		rideInfo.setSelectedPolylineRoute(googleData.get("polyline"+rideInfo.getSelectedKey()));
		rideInfo.setTotalDistance(Long.parseLong(googleData.get("total_distance"+rideInfo.getSelectedKey())));
		rideInfoRepository.save(rideInfo);
		return Boolean.TRUE;
	}

	@Override
	public List<RideInfoDTO> getRides(GetRidesData getRidesData) throws UtilityException {
		Map<String, String> googleData = findGoogleRoute(getRidesData.getOriginLatitude(), getRidesData.getOriginLongitude(),
				getRidesData.getDestinationLatitude(), getRidesData.getDestinationLongitude(), null, null, Boolean.FALSE);

		List<RideInfoDTO> resultData = null;

		if (getRidesData.getJourneyType().equals("from")) {
			resultData = fromOfficeTrips(getRidesData, googleData);
		} else {
			resultData = fromHomeTrips(getRidesData, googleData);
		}

		return resultData;
	}

	private List<RideInfoDTO> fromOfficeTrips(GetRidesData getRidesData, Map<String, String> googleData) {
		Long riderDistance = Long.parseLong(googleData.get("total_distance0"));
		List<RideInfoDTO> resultData = rideInfoRepository.findByOriginLatitudeAndOriginLongitudeAndTotalDistanceGreaterThanAndStartTimeGreaterThan(
				getRidesData.getOriginLatitude(), getRidesData.getOriginLongitude(), riderDistance - 2500, 
				new Date(getRidesData.getStartTime()-900000));
		Boolean status = Boolean.FALSE;
		if (CommonUtility.isValidString(getRidesData.getIsOptimised())) {
			List<RideInfoDTO> optimisedData = new ArrayList<RideInfoDTO>();
			for (RideInfoDTO driverInfo : resultData) {
				List<GeoJsonPoint> decodedDriverPoints = Decode.decodePoly(driverInfo.getSelectedPolylineRoute());
				status = SearchAlgorithm.optimisedSearch(decodedDriverPoints, getRidesData.getDestinationLatitude(), getRidesData.getDestinationLongitude());
				if (status) optimisedData.add(driverInfo);
			}
			return optimisedData;
		}
		return resultData;
	}

	private List<RideInfoDTO> fromHomeTrips(GetRidesData getRidesData, Map<String, String> googleData) {
		Long riderDistance = Long.parseLong(googleData.get("total_distance0"));
		List<RideInfoDTO> resultData = rideInfoRepository.findByDestinationLatitudeAndDestinationLongitudeAndTotalDistanceGreaterThanAndStartTimeGreaterThan(
				getRidesData.getDestinationLatitude(), getRidesData.getDestinationLongitude(), riderDistance - 2500, 
				new Date(getRidesData.getStartTime()-900000));
		return resultData;
	}


	@Override
	public Boolean requestRide(RequestRideData requestRideData) throws UtilityException {
		if (!(CommonUtility.isValidString(requestRideData.getUserId()) && CommonUtility.isValidList(requestRideData.getRideDataList())))
			throw new UtilityException(ResponseCode.USER_DATA_NOT_FOUND_IN_REQUEST);
		List<NotificationDTO> notificationList = new ArrayList<>();
		for (RideData rideData : requestRideData.getRideDataList()) {
			NotificationDTO notification = new NotificationDTO();
			notification.setTargetUserId(rideData.getUserId());
			notification.setReadFlag(0);
			notification.setCreatedDate(new Date());
			notification.setType(1);
			Map<String, String> data = new HashMap<String, String>();
			data.put("rideId", rideData.getRideId());
			notification.setAdditionalInfo(data);
			notification.setMessage(requestRideData.getName()+" has sent you a request for Car-Pooling");
			notificationList.add(notification);
		}
		notificationRepository.saveAll(notificationList);
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean actionOnRide(ActionRideData actionRideData) throws UtilityException {
		if (actionRideData.getAction().intValue() == ActionType.ACCEPT.getType()) {
			// using rideid reduce the seat availibility
			//check using rider id if the rider is already engaged or not
		} else {
			
		}
		return Boolean.TRUE;
	}

	@Override
	public OfficeAddressDTO saveOfficeAddress(OfficeAddressDTO addressDTO) throws UtilityException {
		return officeAddressRepository.save(addressDTO);
	}

	private Map<String, String> findGoogleRoute(Double oLat, Double oLng, Double dLat, Double dLng, String oAddress, String dAddress, Boolean alternateRoute) {
		String startCoordinates = oLat+","+oLng;
		String endCoordinates = dLat+","+dLng;
		startCoordinates = (CommonUtility.isValidString(startCoordinates) && !startCoordinates.contains("null"))? startCoordinates : oAddress;
		endCoordinates = (CommonUtility.isValidString(endCoordinates) && !endCoordinates.contains("null"))? endCoordinates : dAddress;

		JSONObject rawOutput = googleDirection.calculateRoute(startCoordinates, endCoordinates, alternateRoute);
		if (CommonUtility.isNullObject(rawOutput)) throw new UtilityException(ResponseCode.GENRAL_ERROR);
		return googleDirection.parseRoute(rawOutput);
	}

	private void validateDriverInfoData(RideInfoDTO driverInfo) throws UtilityException {
		if (!(CommonUtility.isValidLong(driverInfo.getStartTimeLong()) && CommonUtility.isValidString(driverInfo.getUserId()) 
				&& CommonUtility.isValidDouble(driverInfo.getOriginLatitude()) && CommonUtility.isValidDouble(driverInfo.getOriginLongitude())
				&& CommonUtility.isValidDouble(driverInfo.getDestinationLatitude()) && CommonUtility.isValidDouble(driverInfo.getDestinationLongitude())))
			throw new UtilityException(ResponseCode.USER_DATA_NOT_FOUND_IN_REQUEST);
	}

	private void validatePathData(MapPathRequest pathRequest) {
		if (!(CommonUtility.isValidDouble(pathRequest.getOriginLatitude()) && CommonUtility.isValidDouble(pathRequest.getOriginLongitude())
				&& CommonUtility.isValidDouble(pathRequest.getDestinationLatitude()) && CommonUtility.isValidDouble(pathRequest.getDestinationLongitude())
				|| (CommonUtility.isValidString(pathRequest.getOriginAddress()) && CommonUtility.isValidString(pathRequest.getDestinationAddress()))))
			throw new UtilityException(ResponseCode.USER_DATA_NOT_FOUND_IN_REQUEST);
	}

}