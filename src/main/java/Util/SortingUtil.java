package Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import data.TreeNode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortingUtil {

    public static void sortMonitoringJsonArray(List<JsonArray> monitoringScanArrayDetails) {
        //MODIFIES INPLACE
        for(JsonArray jsonArray : monitoringScanArrayDetails){
            JsonArray sortedArray = Arrays.stream(new Gson().fromJson(jsonArray, JsonObject[].class))
                    .sorted(Comparator.comparing(x -> x.get("monitor").getAsBoolean(), Comparator.reverseOrder()))
                    .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);

            monitoringScanArrayDetails.set(monitoringScanArrayDetails.indexOf(jsonArray), sortedArray);
        }
    }
}
