package services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatParserService {

    /**
     * Read a file of type .dat and retrieve all the content
     * @param fileUrl
     * @return all the text in the file
     * @throws IOException
     */
    public List<String> retrieveAllUrlsToParse(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
