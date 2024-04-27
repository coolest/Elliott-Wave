package AI;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Trainer {
    private List<List<Bar>> partitionData(List<Bar> data, int windowSize, int stepSize){
        List<List<Bar>> partitions = new ArrayList<>();

        for (int i = 0; i < data.size() - windowSize + 1; i += stepSize) {
            List<Bar> window = data.subList(i, i + windowSize);

            partitions.add(window);
        }

        return partitions;
    }

    private List<Bar> getTrainingData(){
        String url = String.format("https://data.alpaca.markets/v1beta3/crypto/us/bars?symbols=%s&timeframe=%s&start=%s&end=%s&limit=%d&sort=%s", 
                                    "BTC/USD,LTC/USD", "1H", LocalDate.now().minusDays(365).toString(), LocalDate.now().toString(), Integer.MAX_VALUE, "asc");

        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("APCA-API-KEY-ID", "AK0JHD2IZ36SAE900Q82");
            request.addHeader("APCA-API-SECRET-KEY", "hnsYdr3gMpqVTKb2tS7fjPBerSaTfMBcyJnzFCej");
    
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity == null)
                    return null;

                result = EntityUtils.toString(entity);

                JSONObject jsonResult = new JSONObject(result);
                JSONObject barsObject = jsonResult.getJSONObject("bars");
                JSONArray barsJson = barsObject.getJSONArray("BTC/USD");

                ArrayList<Bar> bars = new ArrayList<>();
                for (int i = 0; i < barsJson.length(); i++){
                    JSONObject bar = barsJson.getJSONObject(i);

                    bars.add(new Bar(bar));
                }

                Collections.shuffle(bars);

                return bars;
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            System.out.println("Could not parse string into json! \nString:" + result);
        }

        return null;
    }

    public void train(){
        List<Bar> bars = getTrainingData();
        if (bars == null){
            System.out.println("Could not get training data!");

            return;
        }

        int totalSize = bars.size();
        int trainSize = (int) (totalSize * 0.7); // 70% for training
        int validationSize = (int) (totalSize * 0.15); // 15% for validation
        int testSize = totalSize - trainSize - validationSize; // 15% for testing

        List<Bar> trainData = bars.subList(0, trainSize);
        List<Bar> validationData = bars.subList(trainSize, trainSize + validationSize);
        List<Bar> testData = bars.subList(trainSize + validationSize, totalSize);
        
        List<List<Bar>> partitions = partitionData(bars, 50, 5);
    }
}
