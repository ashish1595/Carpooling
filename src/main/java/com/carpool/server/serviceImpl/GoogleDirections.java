package com.carpool.server.serviceImpl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.carpool.server.exception.ResponseCode;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.utils.CommonUtility;

@Component
public class GoogleDirections {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public JSONObject calculateRoute(String startCoordinates, String endCoordinates, Boolean alternateRoute) {
		String method = "calculateRoute :: API call :: startCoordinates:: " + startCoordinates + " endCoordinates :: "
				+ endCoordinates;
		logger.info(method + " starts...");
		try {
			String url = "https://maps.googleapis.com/maps/api/directions/json?alternatives=true&origin={origin}"
					+ "&destination={destination}&sensor=false&mode=Driving";
			url = url.replace("{origin}", startCoordinates).replace("{destination}", endCoordinates);
			url = alternateRoute? url : url.replace("alternatives=true&", "");
			logger.info(method + " url :: " + url);
			@SuppressWarnings("restriction")
			URL googleUrl = new URL(null, url, new sun.net.www.protocol.https.Handler());

			HttpsURLConnection googleConnection = (HttpsURLConnection) googleUrl.openConnection();
			googleConnection.setConnectTimeout(30000);
			googleConnection.setRequestMethod("GET");
			googleConnection.connect();
			JSONObject jsonObject = new JSONObject(IOUtils.toString(googleConnection.getInputStream()));
			if (CommonUtility.isNullObject(jsonObject) || !jsonObject.getString("status").equalsIgnoreCase("OK")) {
				logger.error(method + "<<< google gave the following result for url :: " + url + " is :: " + jsonObject);
			}
			googleConnection.disconnect();
			logger.info(method + " ends...");
			return jsonObject;
		} catch (Exception e) {
			logger.error(method, e);
			return null;
		}
	}

	public Map<String, String> parseRoute(JSONObject jObject) throws UtilityException {
		String method = "parseRoute for trip data from google service :: ";
		logger.info(method + " starts...");
		Map<String, String> result = new HashMap<>();
		JSONArray routes = jObject.getJSONArray("routes");
		if (CommonUtility.isNullObject(routes)) throw new UtilityException(ResponseCode.NO_ROUTE_AVAILABLE);

		int routesLength = (routes.length() > 3)? 3 : routes.length();

		for (int k=0; k< routesLength ; k++) {
			result.put("polyline"+k, routes.getJSONObject(k).getJSONObject("overview_polyline").getString("points"));
			result.put("total_distance"+k, routes.getJSONObject(k).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getInt("value")+"");
		}
		result.put("start_address", routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getString("start_address"));
		result.put("end_address", routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getString("end_address"));

		return result;
	}
}
