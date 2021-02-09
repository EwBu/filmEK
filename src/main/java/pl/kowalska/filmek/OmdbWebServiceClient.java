package pl.kowalska.filmek;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class OmdbWebServiceClient {

    @Value("${api.key}")
    private static String APIKEY;

    public static final String SEARCH_URL = "http://www.omdbapi.com/?s=TITLE&apikey=6ac1c188";
    public static final String SEARCH_BY_IMDB_URL = "http://www.omdbapi.com/?i=IMDB&apikey=6ac1c188";

    public static String sendGetRequest(String requestUrl){
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/json");

            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) !=null){
                System.out.println(line);
//                response.append(line);
            }
//            buffer.close();
//            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public static String searchMovieByTitle(String title) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String reguestUrl = SEARCH_URL
                .replaceAll("TITLE", title);


        return sendGetRequest(reguestUrl);
    }


    public static String searchMovieByIMDB(String imdb){
        String requestUrl = SEARCH_BY_IMDB_URL
                .replaceAll("IMDB", imdb);
        return sendGetRequest(requestUrl);
    }

}
