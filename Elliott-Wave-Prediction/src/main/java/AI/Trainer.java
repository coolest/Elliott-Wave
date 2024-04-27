package AI;

import java.io.IOException;
import java.time.LocalDate;

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
    private void partitionData(){

    }
    
    private void getTrainingData(){
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
                    return;

                result = EntityUtils.toString(entity);

                JSONObject jsonResult = new JSONObject(result);
                JSONObject barsObject = jsonResult.getJSONObject("bars");
                JSONArray bars = barsObject.getJSONArray("BTC/USD");
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            System.out.println("Could not parse string into json! \nString:" + result);
        }
    }

    public void train(){

    }
}
