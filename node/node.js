const axios = require('axios');

async function generateResponse(messageContent) {
  // Generate a response from the ChatGPT model
  const requestBody = {
    messages: [
      { role: 'system', content: 'You are a user' },
      { role: 'user', content: messageContent }
    ]
  };

  const headers = {
    'Authorization': `Bearer ${apiKey}`,
    'Content-Type': 'application/json'
  };

  try {
    const response = await axios.post(apiEndpoint, requestBody, { headers });
    const responseData = response.data;
    const generatedMessage = responseData.choices[0].message.content;
    return generatedMessage;
  } catch (error) {
    console.error('Error generating response:', error.message);
    throw error;
  }
}

async function main() {
  const messageContent = 'Hello, world!';
  const generatedMessage = await generateResponse(messageContent);
  console.log(generatedMessage);
}

main();
