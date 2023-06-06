require 'net/http'
require 'json'

# Function: Generate response using ChatGPT
def generate_response(message_content)
    api_endpoint = "https://api.openai.com/v1/chat/completions"
    api_key = "YOUR_OPENAI_API_KEY"
    # Generate a response from the ChatGPT model
    uri = URI(api_endpoint)
    http = Net::HTTP.new(uri.host, uri.port)
    http.use_ssl = true

    headers = {
        'Authorization' => "Bearer #{api_key}",
        'Content-Type' => 'application/json'
    }

    request = Net::HTTP::Post.new(uri.path, headers)
    request.body = {
        messages: [
            { role: 'system', content: 'You are a user' },
            { role: 'user', content: message_content }
        ]
    }.to_json

    response = http.request(request)

    # Parse the response and extract the generated message
    response_data = JSON.parse(response.body)
    generated_message = response_data['choices'][0]['message']['content']

    return generated_message
end
