package AI;

import java.net.HttpURLConnection;
import java.net.URL;

import org.jfree.data.xy.DefaultHighLowDataset;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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

    public DefaultHighLowDataset getDataset(){
        return dataset;
    }
    
    public void populatePrices() {
        long startDate = 0;
        long endDate = 0;

        // use http client
        // get prices
        
        // put them in DefaultHighLowDataset

        // set that in chartBuilder

        try {
            String urlString = String.format(
                "https://api.coingecko.com/api/v3/coins/bitcoin/market_chart/range?vs_currency=usd&from=%d&to=%d",
                startDate, endDate);
            
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request)

            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            jsonResponse = new JSONObject(responseString);

            // chartBuilder.setDataset()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
