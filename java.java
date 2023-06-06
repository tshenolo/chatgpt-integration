import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// Function: Generate response using ChatGPT
public String generateResponse(String messageContent) throws IOException {
    // Generate a response from the ChatGPT model
    String apiEndpoint = "https://api.openai.com/v1/chat/completions";
    String apiKey = "YOUR_OPENAI_API_KEY";
    OkHttpClient client = new OkHttpClient();
    MediaType mediaType = MediaType.parse("application/json");
    String json = "{\"messages\":[{\"role\":\"system\",\"content\":\"You are a user\"},{\"role\":\"user\",\"content\":\""
        + messageContent + "\"}]}";
    RequestBody body = RequestBody.create(mediaType, json);
    Request request = new Request.Builder()
        .url(apiEndpoint)
        .post(body)
        .addHeader("Authorization", "Bearer " + apiKey)
        .addHeader("Content-Type", "application/json")
        .build();

    Response response = client.newCall(request).execute();

    // Parse the response and extract the generated message
    String responseData = response.body().string();
    String generatedMessage = new JSONObject(responseData)
        .getJSONArray("choices")
        .getJSONObject(0)
        .getJSONObject("message")
        .getString("content");

    return generatedMessage;
}
