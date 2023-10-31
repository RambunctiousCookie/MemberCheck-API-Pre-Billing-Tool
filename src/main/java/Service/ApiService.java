package Service;
import Util.Status;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ApiService {
    //private String apiKey = "632b36d11b82451380165944921ce1ee";
    //TODO: use secrets, Spring-Vault
    private String apiKey;
    DateTimeFormatter dateTimeFormatter;

    public ApiService() {
        this.apiKey = "";
        this.dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ApiService(String apiKey) {
        this.apiKey = apiKey;
        this.dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

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

    public void validateApiKey(){
        if (apiKey == null || apiKey.isEmpty())
            throw new IllegalArgumentException("API key cannot be empty or null");
    }

    public JsonElement fetchSingleMemberScanData(String orgId, LocalDate sDate, LocalDate eDate) throws IOException {
        validateApiKey();

        //TODO: can filter the date directly in the queryparam
        String url = "https://demo.api.membercheck.com/api/v2/data-management/member-scans"
                +"?from="
                +sDate.format(dateTimeFormatter).replace("-","%2F")
                +"&to="
                +eDate.format(dateTimeFormatter).replace("-","%2F");
        HttpUriRequest request = createRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchSingleCorpScanData(String orgId,LocalDate sDate, LocalDate eDate) throws IOException {
        validateApiKey();

        //TODO: can filter the date directly in the queryparam
        String url = "https://demo.api.membercheck.com/api/v2/data-management/corp-scans"
                +"?from="
                +sDate.format(dateTimeFormatter).replace("-","%2F")
                +"&to="
                +eDate.format(dateTimeFormatter).replace("-","%2F");
        HttpUriRequest request = createRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchBatchMemberScanData(String orgId) throws IOException {
        validateApiKey();

        String url = "https://demo.api.membercheck.com/api/v2/member-scans/batch";
        HttpUriRequest request = createRequest(url, apiKey, orgId);

        //Can also use "https://demo.api.membercheck.com/api/v2/data-management/member-batch-scans"
        //identical return value
        //HOWEVER, member-batch-scans cannot input date range, whereas member-scans/batch can

        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchBatchCorpScanData(String orgId) throws IOException {
        validateApiKey();

        String url = "https://demo.api.membercheck.com/api/v2/corp-scans/batch";
        HttpUriRequest request = createRequest(url, apiKey, orgId);

        //Can also use "https://demo.api.membercheck.com/api/v2/data-management/corp-batch-scans"
        //identical return value
        //HOWEVER, corp-batch-scans cannot input date, corp-scans/batch can

        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchMonitoringMemberScanData(String orgId, Status status)  throws IOException {
        validateApiKey();

        //Status: On, Off, All
        String url = "https://demo.api.membercheck.com/api/v2/monitoring-lists/member?status=" + status.toString();
        HttpUriRequest request = createRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }

    public JsonElement fetchMonitoringCorpScanData(String orgId, Status status)  throws IOException {
        validateApiKey();

        //Status: On, Off, All
        String url = "https://demo.api.membercheck.com/api/v2/monitoring-lists/corp?status=" + status.toString();
        HttpUriRequest request = createRequest(url, apiKey, orgId);
        return JsonParser.parseString(fetchDataFromApi(request));
    }
}
