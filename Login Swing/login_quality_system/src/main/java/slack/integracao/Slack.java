package slack.integracao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class Slack {
    private static HttpClient client = HttpClient.newHttpClient();
    private static final String url = "https://hooks.slack.com/services/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";
    
    public void enviarMensagem(JSONObject content) throws IOException,InterruptedException{
    HttpRequest request = HttpRequest.newBuilder(URI.create(url))
            .header("accept", "aplication/json")
            .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
            .build();
    
    HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Response: %s", response.body()));
    }
    
}
