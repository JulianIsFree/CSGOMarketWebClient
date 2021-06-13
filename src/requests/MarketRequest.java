package requests;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class MarketRequest {
    private static final String prefix = "https://market.csgo.com/api/";
    private static int timeout = 5000; // in milliseconds
    private String url;

    protected MarketRequest(String command, String key, String... args) {
        url = formURLRequest(command, key, args);
    }

    protected static String formURLRequest(String command, String key, String[] args) {
        final String lastArg = args[args.length - 1];

        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(command).append("/");

        for(String arg : args) {
            builder.append(arg);
            builder.append("/");
        }

        builder.append("?key=").append(key);

        return builder.toString();
    }

    /**
     * Method performs requests and returns result if any
     * @return response from server
     * @throws MalformedURLException, check URL constructor to know more
     * @throws IOException, check URL.openConnection() method to know more
     *
     */
    public JSONObject sendRequest() throws MalformedURLException, IOException {
        URL requestURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            content.append(inputLine);

        return new JSONObject(content.toString());
    }

    @Override
    public String toString() {
        return url;
    }
}
