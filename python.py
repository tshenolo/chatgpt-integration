import requests

# Function: Generate response using ChatGPT
def generate_response(message_content):
    api_endpoint = "https://api.openai.com/v1/chat/completions"
    api_key = "YOUR_OPENAI_API_KEY"
    # Generate a response from the ChatGPT model
    response = requests.post(
        api_endpoint,
        headers={
            'Authorization': f'Bearer {api_key}',
            'Content-Type': 'application/json'
        },
        json={
            'messages': [
                {'role': 'system', 'content': 'You are a user'},
                {'role': 'user', 'content': message_content}
            ]
        }
    )

    # Parse the response and extract the generated message
    response_data = response.json()
    generated_message = response_data['choices'][0]['message']['content']

    return generated_message
