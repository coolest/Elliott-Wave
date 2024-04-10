package AI;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import org.jfree.data.xy.DefaultHighLowDataset;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;


import java.util.Date;
import java.text.SimpleDateFormat;  


import java.net.URI;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

public class PriceFetcher {

    private DefaultHighLowDataset dataset;

    private ChartBuilder chartBuilder;
    private DatesController datesController;
    private HttpClient httpClient;
    public PriceFetcher(DatesController datesController, ChartBuilder chartBuilder){
        this.datesController = datesController;
        this.chartBuilder = chartBuilder;
        this.httpClient = HttpClients.createDefault();

        // create http client
        
    }

    public void fetchCryptoPrices() {
        Date startDate = this.datesController.getDateInterval().get(0);
        Date endDate = this.datesController.getDateInterval().get(1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String url = String.format("https://data.alpaca.markets/v1beta3/crypto/us/bars?symbols=%s&timeframe=%s&start=%s&end=%s&limit=%d&sort=%s", 
                                    "BTC/USD,LTC/USD", "1D", formatter.format(startDate), formatter.format(endDate), 1000, "asc");

        System.out.println(url); // testing
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("APCA-API-KEY-ID", "AK0JHD2IZ36SAE900Q82");
            request.addHeader("APCA-API-SECRET-KEY", "hnsYdr3gMpqVTKb2tS7fjPBerSaTfMBcyJnzFCej");
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
