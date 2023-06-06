// Function: Generate response using ChatGPT
function generateResponse(messageContent) {
    // Generate a response from the ChatGPT model
    const response = fetch(apiEndpoint, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${apiKey}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            messages: [
                { role: 'system', content: 'You are a user' },
                { role: 'user', content: messageContent }
            ]
        })
    });

    // Parse the response and extract the generated message
    const responseJson = response.json();
    const generatedMessage = responseJson.choices[0].message.content;

    return generatedMessage;
}
