import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

data class Message(val role: String, val content: String)
data class Request(val messages: List<Message>)
data class Choice(val message: Message)
data class Response(val choices: List<Choice>)

fun generateResponse(messageContent: String): String {
    val apiEndpoint = "https://api.openai.com/v1/chat/completions"
    val apiKey = "YOUR_OPENAI_API_KEY"
    // Generate a response from the ChatGPT model
    val client = OkHttpClient()
    val mediaType = MediaType.parse("application/json")
    val json = """
        {
            "messages": [
                {"role": "system", "content": "You are a user"},
                {"role": "user", "content": "$messageContent"}
            ]
        }
    """.trimIndent()
    val body = RequestBody.create(mediaType, json)
    val request = Request.Builder()
        .url(apiEndpoint)
        .post(body)
        .addHeader("Authorization", "Bearer $apiKey")
        .addHeader("Content-Type", "application/json")
        .build()

    val response = client.newCall(request).execute()

    // Parse the response and extract the generated message
    val responseData = response.body()?.string() ?: ""
    val generatedMessage = Json.decodeFromString<Response>(responseData).choices[0].message.content

    return generatedMessage
}

fun main() {
    val messageContent = "Hello, world!"
    val generatedMessage = generateResponse(messageContent)
    println(generatedMessage)
}
