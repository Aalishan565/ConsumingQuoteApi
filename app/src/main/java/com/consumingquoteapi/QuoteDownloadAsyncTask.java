package com.consumingquoteapi;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aalishan on 7/11/16.
 */

public class QuoteDownloadAsyncTask extends AsyncTask<String, String, QuoteModel> {
    private QuoteDownloadListener quoteDownloadListener;
    private static final String QUOTE_URL = "http://quotes.rest/qod.json";
    private HttpURLConnection connection;
    private BufferedReader bufferedReader;

    public QuoteDownloadAsyncTask(QuoteDownloadListener quoteDownloadAsyncTask) {
        this.quoteDownloadListener = quoteDownloadAsyncTask;

    }

    @Override
    protected QuoteModel doInBackground(String... strings) {
        QuoteModel quoteModel = new QuoteModel();
        try {
            URL url = new URL(QUOTE_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            String finalStringData = buffer.toString();
            JSONObject jsonObject = new JSONObject(finalStringData);
            JSONObject jsonObject1 = jsonObject.getJSONObject("contents");
            JSONArray jsonArray = jsonObject1.getJSONArray("quotes");
            JSONObject jsonObject2 = jsonArray.getJSONObject(0);

            quoteModel.setQuote((String) jsonObject2.get("quote"));
            quoteModel.setAuthor((String) jsonObject2.get("author"));
            quoteModel.setBackground((String) jsonObject2.get("background"));
            return quoteModel;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return quoteModel;
    }

    @Override
    protected void onPostExecute(QuoteModel model) {
        if (model != null) {
            quoteDownloadListener.onQuoteDownloadSucess(model);
        } else {
            quoteDownloadListener.onQuoteDownloadFailure();
        }
    }
}
