package services.fileParser;

import com.google.gson.Gson;
import dto.FileType;
import dto.QuestionsFromJsonFile;
import dto.Question;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonQuestionsParserService implements QuestionsParser {

    /**
     * retrieve all the question from json file
     * @param fileUrl
     * @return list of questions
     * @throws IOException
     */
    @Override
    public List<Question> retrieveQuestionsFromUrl(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            QuestionsFromJsonFile jsonRecords = new Gson().fromJson(reader, QuestionsFromJsonFile.class);
            return jsonRecords.getQuestions().stream()
                    .map(record -> new Question(record.getText(), FileType.JSON.getFileTypeName()))
                    .collect(Collectors.toList());
        }
    }
}
