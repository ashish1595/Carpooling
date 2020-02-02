package com.carpool.server.utils;

import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class SearchAlgorithm {

	static int binarySearch(int arr[], int x)
    {
        int l = 0, r = arr.length - 1;
        while (l <= r)
        {
            int m = l + (r-l)/2;
 
            // Check if x is present at mid
            if (arr[m] == x)
                return m;
 
            // If x greater, ignore left half
            if (arr[m] < x)
                l = m + 1;
 
            // If x is smaller, ignore right half
            else
                r = m - 1;
        }
 
        // if we reach here, then element was 
        // not present
        return -1;
    }
 
    // Driver method to test above
    public static void main(String args[])
    {
        int arr[] = {2, 3, 4, 10, 40};
        int x = 10;
        int result = binarySearch(arr, x);
        if (result == -1)
            System.out.println("Element not present");
        else
            System.out.println("Element found at index " + result);
    }

	public static Boolean optimisedSearch(List<GeoJsonPoint> decodedDriverPoints, double riderLat, double riderLong) {
		int thresholdPointsCount = (int) (0.2* decodedDriverPoints.size());
		double thresholdDistance = 1500; //threshold distance to consider trip as optimized
		double minDistanceCalculated = 1000000000;
		double distanceCalculated;
		int wrongsPointCounter = 0;
		for (int i = 0; i < decodedDriverPoints.size() - 1; i = i + 4) {
			distanceCalculated = formulaDistance(riderLat, riderLong, decodedDriverPoints.get(i).getX(), decodedDriverPoints.get(i).getY());
			if (distanceCalculated < minDistanceCalculated) {
				minDistanceCalculated = distanceCalculated;
				wrongsPointCounter = 0;
			} else {
				wrongsPointCounter++;
			}
			if (minDistanceCalculated <= thresholdDistance) return Boolean.TRUE;
			if (wrongsPointCounter == thresholdPointsCount) return Boolean.FALSE;
		}
		return Boolean.FALSE;
	}

	public static double formulaDistance(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}

}