package AI;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PriceFetch {

    public String fetchCryptoPrices(long startDate, long endDate) {
        try {
            String urlString = String.format(
                "https://api.coingecko.com/api/v3/coins/bitcoin/market_chart/range?vs_currency=usd&from=%d&to=%d",
                startDate, endDate);
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString(); 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
