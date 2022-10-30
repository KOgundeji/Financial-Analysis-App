package com.kunle.finanalysistooljavafx.Support;


import javafx.scene.control.Alert;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class PricingAPI {

    String base = "https://api.tdameritrade.com/v1/marketdata/quotes?" +
            "apikey=AUACA2TFPC2IGMLALX2DANMIYWWASCJR&symbol=";
    HashMap<String, ArrayList<String>> stock_map = null;
    ArrayList<String> stock_info_list = new ArrayList<>();


    public HashMap<String, ArrayList<String>> connectToStockAPI(String query, ArrayList<String> stock_name_list) {

        try {
            String encoded_query = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            URL url = new URL(base + encoded_query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode != 200) {
                System.out.println("Request didn't process correctly");
                return null;
            }

            JSONObject json = (JSONObject) new JSONParser().parse(inputStream);
            stock_map = new HashMap<>();
            for (String stock : stock_name_list) {
                JSONObject stock_info = (JSONObject) json.get(stock);

                String symbol = (String) stock_info.get("symbol");
                String name = (String) stock_info.get("description");
                Double lastPrice = (Double) stock_info.get("regularMarketLastPrice");
                Double change_per = (Double) stock_info.get("regularMarketPercentChangeInDouble");
                Double change_abs = (Double) stock_info.get("regularMarketNetChange");

                stock_info_list.add(symbol);
                stock_info_list.add(name);
                stock_info_list.add(String.valueOf(lastPrice));
                stock_info_list.add(String.valueOf(change_per));
                stock_info_list.add(String.valueOf(change_abs));

                stock_map.put(symbol, stock_info_list);
                stock_info_list = new ArrayList<>();

//                System.out.println("Ticker: " + symbol + ", company: " + name +
//                        ", price: " + lastPrice + ", change(%): " + change_per +
//                        ", change($): " + change_abs);

            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stock_map;
    }

    public boolean checkNewStock(String ticker) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        boolean flag = false;
        try {
            URL url = new URL(base + ticker);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            JSONObject json = (JSONObject) new JSONParser().parse(inputStream);

            if (responseCode != 200) {
                System.out.println("Error with connection");
                alert.setContentText("Error with connection or didn't enter ticker");
                alert.show();
            } else if (json.get(ticker) == null) {
                System.out.println("Error with new stock");
                alert.setContentText("Stock symbol doesn't exit. Please try again");
                alert.show();
            } else {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
