import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ResponseGeneratorTest {

    @Test
    public void testGenerateResponseWithValidMessage() throws IOException {
        OkHttpClient mockClient = mock(OkHttpClient.class);
        Response mockResponse = mock(Response.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mock(Call.class));
        when(mockClient.newCall(any(Request.class)).execute()).thenReturn(mockResponse);
        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.body()).thenReturn(ResponseBody.create(MediaType.parse("application/json"),
                "{\"choices\":[{\"message\":{\"content\":\"Generated response\"}}]}"));

        ResponseGenerator generator = new ResponseGenerator(mockClient);

        String messageContent = "Hello, I need help.";
        String generatedMessage = generator.generateResponse(messageContent);

        assertEquals("Generated response", generatedMessage);
        verify(mockClient).newCall(any(Request.class));
    }

    @Test(expected = IOException.class)
    public void testGenerateResponseWithEmptyMessage() throws IOException {
        OkHttpClient mockClient = mock(OkHttpClient.class);
        ResponseGenerator generator = new ResponseGenerator(mockClient);

        String messageContent = "";
        generator.generateResponse(messageContent);
    }
}
