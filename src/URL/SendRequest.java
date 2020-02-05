package URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.apache.commons.codec.binary.Base64;

public class SendRequest {

    String url;
    String login;
    String password;
    JSONObject input;

    public SendRequest(String url, String login, String password, JSONObject input) {
        this.url = url;
        this.login = login;
        this.password = password;
        this.input = input;
    }

    public void SendToServer() {
        HttpURLConnection con = null;
        try {
            URL object = new URL(url);
            con = (HttpURLConnection) object.openConnection();
            String userCredentials = login + ":" + password;
            String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
            con.setRequestProperty("Authorization", basicAuth);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", basicAuth);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(input.toString());
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            System.out.println("response code = " + responseCode);
            BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());

        } catch (Exception ex) {
            System.out.println("ex = " + ex);
        } finally {
            con.disconnect();
        }

    }
}
