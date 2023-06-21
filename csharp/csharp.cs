using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

public static class Program
{
    // Function: Generate response using ChatGPT
    public static async Task<string> GenerateResponse(string messageContent)
    {
        using (HttpClient client = new HttpClient())
        {
            string apiEndpoint = "https://api.openai.com/v1/chat/completions";
            string apiKey = "YOUR_OPENAI_API_KEY";
        
            // Generate a response from the ChatGPT model
            var jsonContent = new StringContent(
                "{\"messages\":[" +
                "{\"role\":\"system\",\"content\":\"You are a user\"}," +
                "{\"role\":\"user\",\"content\":\"" + messageContent + "\"}]}",
                Encoding.UTF8,
                "application/json");

            client.DefaultRequestHeaders.Add("Authorization", "Bearer " + apiKey);
            client.DefaultRequestHeaders.Add("Content-Type", "application/json");

            HttpResponseMessage response = await client.PostAsync(apiEndpoint, jsonContent);

            // Parse the response and extract the generated message
            string responseString = await response.Content.ReadAsStringAsync();
            dynamic responseData = Newtonsoft.Json.JsonConvert.DeserializeObject(responseString);
            string generatedMessage = responseData.choices[0].message.content;

            return generatedMessage;
        }
    }

    public static void Main(string[] args)
    {
        string messageContent = "Hello, world!";
        string generatedMessage = GenerateResponse(messageContent).GetAwaiter().GetResult();
        Console.WriteLine(generatedMessage);
    }
}
