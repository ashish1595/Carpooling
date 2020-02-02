package com.carpool.server.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class Decode {
	
	public static void main(String[] args) {
		System.out.println(decodePoly("atulDikiuMfAbAF?jD?BGv@mBEKiAeAUGKGMURW`@i@l@l@xBvBtChCjC|B|D`ETVk@|@{@rAYz@]tAk@rCg@~AQ\\q@nAw@nAuHbLoAhBi@t@[b@mDoDyBsBe@{@y@{@uAqACGE]mQwPuCiCmI{EoAy@m@i@g@i@kAwAePoT}DeFaCkDsUi[sBmCoImKwAcBqImIg\\{ZaMqLyO{NsGgGaAgAgC_CgNwMiBgBeGuFeOwM}HuGiGaF{HaGiBaAcBk@w@OqAMeBCo@@q@Fo@Lo@Pg@TWJiAj@yA`AuCdBaBt@aBp@cBd@eBViBDiBGyASo@Ms@ScAa@_CmAwQmKkBkA{I}EyD{BuD}BoReLcNiIcPsJiF_DaCcBo@i@c@g@i@u@w@{AqMg[iKyVi@kAcA_Be@g@i@c@_Am@}@c@kWcJgCmAmAw@uAyAg@q@e@_A}CkIiBaFe@aAg@}@aCkDSWeRmWgDyEqEaGiAmAeAo@kAk@cCu@gCq@kHmBiGqAiMcCaAMiHUob@iA{GOoBIkAMcB[q@UoAk@aAu@gAmAaAuA}AmCoEoIC]w@kB_BaEq@uByB}Ho@cBeA{AQ_@C{@I}AOk@CgCB_ATkC|@gIJaA|@cHrAqLLkB@i@?a@EaAQmAQ}@eFcQWkAS_@Ie@K_B?wAFkAFg@j@oBb@eA\\q@p@{@bA}@rKwGZi@DKDc@EiAaCgHoD_K{EsNuFgPgDqJwAsDeDyIYo@Uk@\\SvAk@p@Y|EcCIQpAo@tEaCP\\d@WxC}A`B{@dBkA^WhDcDjBiCrAaC^w@^gAX}@x@sBZg@\\{@RcA\\}H`@wHX}ELcA^}G^sHDyEEsAAeAG_EQsDm@iQG}COsG?uAPyE\\qQFoC@sAIiAS{@y@}AwAeCa@w@u@y@aAq@uAo@oBa@uCYm@MOMoBKsCYoASeCs@cCu@yCgA{EqAcEkA{Cs@cOsCuB[cAGuA?wGLeBGyAQcG_@sH[gAE}ADwERoB?W@ACCQMOy@_AeAeA{AwAeB}A_DyCwIcI_JqIcAaAa@e@c@_@eE_EeMkL_GmFWKg@YEBGBe@@c@E_@OW_@Ok@aAMqAA{@OaA@iH[sGSwCEyBEADEHMRQNSFM?UKOSCS?OBQHMDEqDqKYm@c@y@MOMGw@UWMaEMgFG_AAYQi@Uc@]c@u@g@qAs@uBe@yAiAoCi@qBCg@D_@Rm@`@}@jAeCvAsCvAwCBg@FUFi@Ac@Ke@mAwDkAcD}FyPMo@E{@@YR?jBDhA@FqDvA{C}AeAuAy@m@_@IKEKOqAFQBI"));
	}
	
	public static List<GeoJsonPoint> decodePoly(String encoded) {
        List<GeoJsonPoint> poly = new ArrayList<GeoJsonPoint>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            GeoJsonPoint p = new GeoJsonPoint((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
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
