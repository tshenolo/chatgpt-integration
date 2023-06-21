import axios from 'axios';

interface Message {
    role: string;
    content: string;
}

interface Request {
    messages: Message[];
}

interface Choice {
    message: Message;
}

interface Response {
    choices: Choice[];
}

async function generateResponse(messageContent: string): Promise<string> {
    apiEndpoint = "https://api.openai.com/v1/chat/completions";
    apiKey = "YOUR_OPENAI_API_KEY";
    // Generate a response from the ChatGPT model
    const requestBody: Request = {
        messages: [
            { role: 'system', content: 'You are a user' },
            { role: 'user', content: messageContent }
        ]
    };

    const response = await axios.post(apiEndpoint, requestBody, {
        headers: {
            'Authorization': `Bearer ${apiKey}`,
            'Content-Type': 'application/json'
        }
    });

    // Parse the response and extract the generated message
    const responseData: Response = response.data;
    const generatedMessage: string = responseData.choices[0].message.content;

    return generatedMessage;
}

async function main() {
    const messageContent: string = 'Hello, world!';
    const generatedMessage: string = await generateResponse(messageContent);
    console.log(generatedMessage);
}

main();
