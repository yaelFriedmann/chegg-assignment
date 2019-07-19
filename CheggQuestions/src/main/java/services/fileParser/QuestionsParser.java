package services.fileParser;

import dto.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionsParser {
    List<Question> retrieveQuestionsFromUrl(String fileUrl) throws IOException;
}
