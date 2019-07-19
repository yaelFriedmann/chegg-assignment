package services.fileParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.FileType;
import dto.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Service
public class PngQuestionsParserService implements QuestionsParser {
    @Value("${target-url}")
    private String TARGET_URL;
    @Value("${api-key}")
    private String API_KEY;
    @Value("${post-request}")
    private String POST_REQUEST;

    /**
     * retrieve text from png file, using google vision api
     * @param fileUrl
     * @return question
     * @throws IOException
     */
    @Override
    public List<Question> retrieveQuestionsFromUrl(String fileUrl) throws IOException {
        HttpURLConnection httpConnection = createImageTextRequest(fileUrl);
        if (httpConnection.getInputStream() == null) {
            return Collections.emptyList();
        }
        String textResult = mapResponseResult(httpConnection);
        return new ArrayList<>(Collections.singletonList(new Question(textResult, FileType.PNG.getFileTypeName())));
    }

    /**
     * Extract from the input stream result the text
     * @param httpConnection
     * @return text of the png image
     * @throws IOException
     */
    private String mapResponseResult(HttpURLConnection httpConnection) throws IOException {
        InputStream inputStream = httpConnection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, ArrayList<HashMap<String, HashMap<String, String>>>> jsonMap = mapper.readValue(inputStream, Map.class);
        ArrayList<HashMap<String, HashMap<String, String>>> responses = jsonMap.get("responses");
        HashMap<String, String> fullTextAnnotation = responses.get(0).get("fullTextAnnotation");
        return fullTextAnnotation.get("text");
    }

    /**
     * Create http request to google vision api, to retrieve text from png file
     * @param fileUrl
     * @return http connection with the json object result
     * @throws IOException
     */
    private HttpURLConnection createImageTextRequest(String fileUrl) throws IOException {
        URL serverUrl = new URL(String.format("%s%s", TARGET_URL, API_KEY));
        URLConnection urlConnection = serverUrl.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setDoOutput(true);

        BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream()));
        httpRequestBodyWriter.write(String.format(POST_REQUEST, fileUrl));
        httpRequestBodyWriter.close();
        return httpConnection;
    }
}