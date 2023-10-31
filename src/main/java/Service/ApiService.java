package Service;
import Util.Status;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class ApiService {
    //private String apiKey = "632b36d11b82451380165944921ce1ee";
    //TODO: use secrets, Spring-Vault
    private String apiKey;

    public static HttpUriRequest createRequest(String apiUrl, String apiKey, String orgId) {
        HttpGet httpGet = new HttpGet(apiUrl);

        httpGet.addHeader(HttpHeaders.ACCEPT, "application/json");
        httpGet.addHeader("X-Request-OrgId", orgId);
        httpGet.addHeader("api-key", apiKey);

        return httpGet;
    }

    public static String fetchDataFromApi(HttpUriRequest request) throws IOException {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // Convert the response content to a JSON string and return it
                return EntityUtils.toString(entity);
            }
            else {
                throw new IOException("No data was received");
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static JsonElement fetchSingleMemberScanData(String apiKey, String orgId) throws IOException {
        //TODO: can filter the date directly in the queryparam
        String url = "https://demo.api.membercheck.com/api/v2/data-management/member-scans";
        HttpUriRequest request = createRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public static JsonElement fetchSingleCorpScanData(String apiKey, String orgId) throws IOException {
        //TODO: can filter the date directly in the queryparam
        String url = "https://demo.api.membercheck.com/api/v2/data-management/corp-scans";
        HttpUriRequest request = createRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchBatchMemberScanData(String apiKey, String orgId) throws IOException {
        String url = "https://demo.api.membercheck.com/api/v2/member-scans/batch";
        HttpUriRequest request = createRequest(url, apiKey, orgId);

        //Can also use "https://demo.api.membercheck.com/api/v2/data-management/member-batch-scans"
        //HOWEVER, member-batch-scans cannot input date range, whereas member-scans/batch can

        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchBatchCorpScanData(String apiKey, String orgId) throws IOException {
        String url = "https://demo.api.membercheck.com/api/v2/corp-scans/batch";
        HttpUriRequest request = createRequest(url, apiKey, orgId);

        //Can also use "https://demo.api.membercheck.com/api/v2/data-management/corp-batch-scans";
        //HOWEVER, corp-batch-scans cannot input date, corp-scans/batch can

        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchMonitoringMemberScanData(String apiKey, String orgId, Status status)  throws IOException {
        //Status: On, Off, All
        String url = "https://demo.api.membercheck.com/api/v2/monitoring-lists/member?status=" + status.toString();
        HttpUriRequest request = createRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchMonitoringCorpScanData(String apiKey, String orgId, Status status)  throws IOException {
        //Status: On, Off, All
        String url = "https://demo.api.membercheck.com/api/v2/monitoring-lists/corp?status=" + status.toString();
        HttpUriRequest request = createRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }
}
