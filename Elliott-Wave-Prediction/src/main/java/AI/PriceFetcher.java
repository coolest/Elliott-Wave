package AI;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.stream.events.EndElement;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.net.URI;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PriceFetcher {
    private DefaultHighLowDataset dataset;

    private ChartBuilder chartBuilder;
    private HttpClient httpClient;

    public PriceFetcher(ChartBuilder chartBuilder){
        this.chartBuilder = chartBuilder;
        this.httpClient = HttpClients.createDefault();
    }

    public DefaultHighLowDataset getDataset(){
        return dataset;
    }

    public void fetchCryptoPrices(ArrayList<LocalDate> dateInterval) {
        LocalDate startDate = dateInterval.get(0);
        LocalDate endDate = dateInterval.get(1);

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        String interval = daysBetween < 10 ? "6H"
            : daysBetween < 30 ? "12H"
            : "1D";

        String url = String.format("https://data.alpaca.markets/v1beta3/crypto/us/bars?symbols=%s&timeframe=%s&start=%s&end=%s&limit=%d&sort=%s", 
                                    "BTC/USD,LTC/USD", interval, startDate.toString(), endDate.toString(), 1000, "asc");

        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("APCA-API-KEY-ID", "AK0JHD2IZ36SAE900Q82");
            request.addHeader("APCA-API-SECRET-KEY", "hnsYdr3gMpqVTKb2tS7fjPBerSaTfMBcyJnzFCej");
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity == null)
                    return;

                result = EntityUtils.toString(entity);

                JSONObject jsonResult = new JSONObject(result);
                JSONObject barsObject = jsonResult.getJSONObject("bars");
                JSONArray bars = barsObject.getJSONArray("BTC/USD");
            
                int barLength = bars.length();

                Date[] date = new Date[barLength];
                double[] high = new double[barLength];
                double[] low = new double[barLength];
                double[] open = new double[barLength];
                double[] close = new double[barLength];
                double[] volume = new double[barLength];

                double min = Double.MAX_VALUE;
                double max = Double.MIN_VALUE;
                for (int i = 0; i < bars.length(); i++){
                    JSONObject bar = bars.getJSONObject(i);

                    date[i] = Date.from(Instant.parse(bar.getString("t")));
                    high[i] = bar.getDouble("h");
                    low[i] = bar.getDouble("l");
                    open[i] = bar.getDouble("o");
                    close[i] = bar.getDouble("c");
                    volume[i] = 0;//bar.getDouble("vw") / bar.getDouble("v");

                    if (min > low[i])
                        min = low[i];

                    if (max < high[i])
                        max = high[i];
                }

                dataset = new DefaultHighLowDataset("BTC", date, high, low, open, close, volume);

                chartBuilder.updateDataset(dataset, min, max);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            System.out.println("Could not parse string into json! \nString:" + result);
        }
    }
}
