use reqwest::header;
use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
struct Message {
    role: String,
    content: String,
}

#[derive(Debug, Serialize, Deserialize)]
struct Request {
    messages: Vec<Message>,
}

#[derive(Debug, Serialize, Deserialize)]
struct Response {
    choices: Vec<Choice>,
}

#[derive(Debug, Serialize, Deserialize)]
struct Choice {
    message: Message,
}

async fn generate_response(message_content: &str) -> Result<String, reqwest::Error> {
    let api_endpoint = "https://api.openai.com/v1/chat/completions";  
    let api_key = "YOUR_OPENAI_API_KEY";  
    // Generate a response from the ChatGPT model
    let client = reqwest::Client::new();
    let request_body = Request {
        messages: vec![
            Message {
                role: "system".to_string(),
                content: "You are a user".to_string(),
            },
            Message {
                role: "user".to_string(),
                content: message_content.to_string(),
            },
        ],
    };
    let response = client
        .post(api_endpoint)
        .header(header::AUTHORIZATION, format!("Bearer {}", api_key))
        .header(header::CONTENT_TYPE, "application/json")
        .json(&request_body)
        .send()
        .await?;

    let response_data: Response = response.json().await?;
    let generated_message = response_data.choices[0].message.content;

    Ok(generated_message)
}

#[tokio::main]
async fn main() {
    let message_content = "Hello, world!";
    match generate_response(message_content).await {
        Ok(generated_message) => println!("{}", generated_message),
        Err(err) => eprintln!("Error: {}", err),
    }
}
