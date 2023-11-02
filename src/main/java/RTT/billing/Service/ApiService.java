package RTT.billing.Service;
import RTT.billing.enumerable.Status;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ApiService {
    private String apiKey;
    private DateTimeFormatter dateTimeFormatter;
    private PoolingHttpClientConnectionManager connectionManager;

//    public ApiService() {
//        this.apiKey = "";
//        this.dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        this.connectionManager = new PoolingHttpClientConnectionManager();
//        connectionManager.setMaxTotal(10);
//        connectionManager.setDefaultMaxPerRoute(5);
//    }

    public ApiService(String apiKey) {
        this.apiKey = apiKey;
        this.dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        connectionManager.setDefaultMaxPerRoute(5);
    }

    public static HttpUriRequest createScanRequest(String apiUrl, String apiKey, String orgId) {
        HttpGet httpGet = new HttpGet(apiUrl);

        httpGet.addHeader(HttpHeaders.ACCEPT, "application/json");
        httpGet.addHeader("X-Request-OrgId", orgId);
        httpGet.addHeader("api-key", apiKey);

        return httpGet;
    }

    public static HttpUriRequest createOrgListRequest(String apiUrl, String apiKey) {
        HttpGet httpGet = new HttpGet(apiUrl);

        httpGet.addHeader(HttpHeaders.ACCEPT, "application/json");
        httpGet.addHeader("api-key", apiKey);

        return httpGet;
    }

    public String fetchDataFromApi(HttpUriRequest request) throws IOException {
//        //try(CloseableHttpClient httpClient = HttpClients.createDefault()){
//        try(CloseableHttpClient httpClient = HttpClients.custom()
//                .setConnectionManager(connectionManager)
//                .build()){
//            HttpResponse response = httpClient.execute(request);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                // Convert the response content to a JSON string and return it
//                return EntityUtils.toString(entity);
//            }
//            else {
//                throw new IOException("No data was received");
//            }
//        } catch (IOException e) {
//            throw new IOException(e);
//        }

        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();   //Speed up the query flow using connectionManager; TODO: attempt multi-threaded architecture
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // Convert the response content to a JSON string and return it
            return EntityUtils.toString(entity);
        }
        else {
            throw new IOException("No data was received");
        }
    }

    public void validateApiKey(){
        if (apiKey == null || apiKey.isEmpty())
            throw new IllegalArgumentException("API key cannot be empty or null");
    }

    public JsonElement fetchSingleMemberScanData(String orgId, LocalDate sDate, LocalDate eDate) throws IOException {
        validateApiKey();
        String url = "https://demo.api.membercheck.com/api/v2/data-management/member-scans"
                +"?from="
                +sDate.format(dateTimeFormatter).replace("-","%2F")
                +"&to="
                +eDate.format(dateTimeFormatter).replace("-","%2F");
        HttpUriRequest request = createScanRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchSingleCorpScanData(String orgId,LocalDate sDate, LocalDate eDate) throws IOException {
        validateApiKey();
        String url = "https://demo.api.membercheck.com/api/v2/data-management/corp-scans"
                +"?from="
                +sDate.format(dateTimeFormatter).replace("-","%2F")
                +"&to="
                +eDate.format(dateTimeFormatter).replace("-","%2F");
        HttpUriRequest request = createScanRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchBatchMemberScanData(String orgId, LocalDate sDate, LocalDate eDate) throws IOException {
        validateApiKey();

        String url = "https://demo.api.membercheck.com/api/v2/member-scans/batch"
                +"?from="
                +sDate.format(dateTimeFormatter).replace("-","%2F")
                +"&to="
                +eDate.format(dateTimeFormatter).replace("-","%2F");
        HttpUriRequest request = createScanRequest(url, apiKey, orgId);

        //Can also use "https://demo.api.membercheck.com/api/v2/data-management/member-batch-scans"
        //identical return value
        //HOWEVER, member-batch-scans cannot input date range, whereas member-scans/batch can

        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchBatchCorpScanData(String orgId, LocalDate sDate, LocalDate eDate) throws IOException {
        validateApiKey();

        String url = "https://demo.api.membercheck.com/api/v2/corp-scans/batch"
                +"?from="
                +sDate.format(dateTimeFormatter).replace("-","%2F")
                +"&to="
                +eDate.format(dateTimeFormatter).replace("-","%2F");
        HttpUriRequest request = createScanRequest(url, apiKey, orgId);

        //Can also use "https://demo.api.membercheck.com/api/v2/data-management/corp-batch-scans"
        //identical return value
        //HOWEVER, corp-batch-scans cannot input date, corp-scans/batch can

        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchMonitoringMemberScanData(String orgId, Status status)  throws IOException {
        validateApiKey();
        //TODO: need to filter manually
        //Status: On, Off, All
        String url = "https://demo.api.membercheck.com/api/v2/monitoring-lists/member?status=" + status.toString();
        HttpUriRequest request = createScanRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchMonitoringCorpScanData(String orgId, Status status)  throws IOException {
        validateApiKey();
        //TODO: need to filter manually
        //Status: On, Off, All
        String url = "https://demo.api.membercheck.com/api/v2/monitoring-lists/corp?status=" + status.toString();
        HttpUriRequest request = createScanRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchOrgListData() throws IOException {
        validateApiKey();
        String url = "https://demo.api.membercheck.com/api/v2/organisations";
        HttpUriRequest request = createOrgListRequest(url, apiKey);
        return JsonParser.parseString(fetchDataFromApi(request));
    }
}
