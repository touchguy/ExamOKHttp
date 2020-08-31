package kr.co.munjanara.examokhttp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new HttpAsyncTask().execute( "https://goo.gl/eIXu9l");
    }

    private static class HttpAsyncTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null) {
                Log.d(TAG, s);
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            String strUrl = strings[0];
            List<Weather> weatherList = new ArrayList<>();

            try {
                Request request = new Request.Builder()
                        .url(strUrl)
                        .build();
                Response response = client.newCall(request).execute();

                Gson    gson = new Gson();

                Type listType = new TypeToken<ArrayList<Weather>>() {}.getType();
                weatherList = gson.fromJson( response.body().string(), listType);

//                JSONArray jsonArray = new JSONArray(response.body().string());
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String country = jsonObject.getString("country");
//                    String weather = jsonObject.getString("weather");
//                    String temperature = jsonObject.getString("temperature");
//                    Weather w = new Weather(country, weather, temperature);
//                    weatherList.add(w);
//                }
                Log.d(TAG, "doinbackground" + weatherList.toString() );
//                Log.d(TAG, "doinbackground" + weatherList.toString() );

            } catch (IOException e) {
                e.printStackTrace();;
            }

            return result;
        }
    }
}