package RTT.billing.Util;

import RTT.billing.Service.ApiService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import RTT.billing.enumerable.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculationUtil {

    private static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

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

    public static int getTotalMonitoringScansByOrgId(ApiService apiService, String orgId, LocalDate[] desiredDate, Status status) throws IOException{
        //Json date format should be "dateAdded" -> {JsonPrimitive@3165} ""2023-10-26T12:25:27""

        JsonArray monitoringMemberScanArray = apiService.fetchMonitoringMemberScanData(orgId,status).getAsJsonArray();
        int monitoringMemberScans = filterMonitoringScansByDate(monitoringMemberScanArray, desiredDate).size();

        JsonArray monitoringCorpScanArray = apiService.fetchMonitoringCorpScanData(orgId,status).getAsJsonArray();
        int monitoringCorpScans = filterMonitoringScansByDate(monitoringCorpScanArray, desiredDate).size();

        return monitoringMemberScans + monitoringCorpScans;
    }

    private static List<JsonObject> filterMonitoringScansByDate(JsonArray jsonArray, LocalDate[] desiredDate){
        Stream<JsonObject> jsonObjectStream = Arrays.stream(new Gson().fromJson(jsonArray, JsonObject[].class));
        return jsonObjectStream.filter(x->
                LocalDateTime.parse(x.get("dateAdded").getAsString(), inputFormatter).isBefore(desiredDate[1].atStartOfDay())  &&
                        LocalDateTime.parse(x.get("dateAdded").getAsString(), inputFormatter).isAfter(desiredDate[0].atStartOfDay())).collect(Collectors.toList());
    }
}
