<?php

use PHPUnit\Framework\TestCase;

class GenerateResponseTest extends TestCase
{
    public function testGenerateResponse()
    {
        // Mock response from OpenAI API
        $mock_response = array(
            'choices' => array(
                array(
                    'message' => array(
                        'role' => 'assistant',
                        'content' => 'Hello, how can I help you?'
                    )
                )
            )
        );

        $message_content = 'Hello, I need help.';
        
        // Function call
        $response = generate_response($message_content);
        
        // Assertions
        $this->assertIsString($response);
        $this->assertEquals($mock_response['choices'][0]['message']['content'], $response);
    }

    public function testGenerateResponseWithEmptyMessage()
    {
        // Mock response from OpenAI API for an empty message
        $mock_response = array(
            'choices' => array(
                array(
                    'message' => array(
                        'role' => 'assistant',
                        'content' => 'I\'m sorry, but I can\'t assist without more information.'
                    )
                )
            )
        );

        $message_content = '';
        
        // Function call
        $response = generate_response($message_content);
        
        // Assertions
        $this->assertIsString($response);
        $this->assertEquals($mock_response['choices'][0]['message']['content'], $response);
    }

    private function generate_response($message_content) {
        $api_endpoint = "https://api.openai.com/v1/chat/completions";
        $api_key = "YOUR_OPENAI_API_KEY";
      
         // Generate a response from the ChatGPT model
         $ch = curl_init($api_endpoint);
     
         $headers = array(
             'Authorization: Bearer ' . $api_key,
             'Content-Type: application/json'
         );
     
         $data = array(
             'messages' => array(
                 array('role' => 'system', 'content' => 'You are a user'),
                 array('role' => 'user', 'content' => $message_content)
             )
         );
     
         $payload = json_encode($data);
     
         curl_setopt($ch, CURLOPT_POST, 1);
         curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
         curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
         curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
     
         $response = curl_exec($ch);
     
         // Parse the response and extract the generated message
         $response_data = json_decode($response, true);
         $generated_message = $response_data['choices'][0]['message']['content'];
     
         return $generated_message;
     }


}

