package Util;

import Service.ApiService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.LocalDate;

public class CalculationUtil {
    public static int getTotalScansByOrgId(ApiService apiService, String orgId, LocalDate[] desiredDate) throws IOException {

        int singleMemberScans = apiService.fetchSingleMemberScanData(orgId, desiredDate[0], desiredDate[1]).getAsJsonArray().size();

        int singleCorpScans = apiService.fetchSingleCorpScanData(orgId, desiredDate[0], desiredDate[1]).getAsJsonArray().size();

        int batchMemberScans = 0;
        JsonElement batchMemberScanData = apiService.fetchBatchMemberScanData(orgId, desiredDate[0], desiredDate[1]);
        for (JsonElement scans : batchMemberScanData.getAsJsonArray()) {
            if (scans.isJsonObject()) {
                JsonObject jsonObject = scans.getAsJsonObject();
                batchMemberScans += Integer.parseInt(String.valueOf(jsonObject.get("membersScanned")));
            }
        }

        int batchCorpScans = 0;
        JsonElement batchCorpScanData = apiService.fetchBatchCorpScanData(orgId, desiredDate[0], desiredDate[1]);
        for (JsonElement scans : batchCorpScanData.getAsJsonArray()) {
            if (scans.isJsonObject()) {
                JsonObject jsonObject = scans.getAsJsonObject();
                batchCorpScans += Integer.parseInt(String.valueOf(jsonObject.get("companiesScanned")));
            }
        }

        return singleMemberScans + singleCorpScans + batchMemberScans + batchCorpScans;
    }
}
