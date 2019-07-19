package services;

import dto.FileType;
import dto.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import services.fileParser.CsvQuestionsParserService;
import services.fileParser.JsonQuestionsParserService;
import services.fileParser.PngQuestionsParserService;
import services.fileParser.QuestionsParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionsRetrieverService {

    @Autowired
    private PngQuestionsParserService pngQuestionsParserService;

    @Autowired
    private CsvQuestionsParserService csvQuestionsParserService;

    @Autowired
    private JsonQuestionsParserService jsonQuestionsParserService;

    /**
     * Retrieve all the questions from the selected types
     *
     * @param filesUrls
     * @param fileTypes
     * @return list of questions
     */
    public List<Question> retrieveAllQuestions(List<String> filesUrls, List<FileType> fileTypes) {
        List<Question> questions = new ArrayList<>();
        fileTypes.parallelStream().forEach(fileType -> {
            questions.addAll(parse(filesUrls, fileType));
        });
        return questions;
    }

    /**
     * retrieve all the question of specific type
     *
     * @param filesUrls
     * @param fileType
     * @return list of questions
     */
    private List<Question> parse(List<String> filesUrls, FileType fileType) {
        List<Question> questions = new ArrayList<>();
        QuestionsParser parser = getParserByType(fileType);
        List<String> urls = getUrlsByFileType(fileType, filesUrls);
        urls.parallelStream().forEach(url -> {
            try {
                questions.addAll(parser.retrieveQuestionsFromUrl(url).stream()
                        .filter(q -> !StringUtils.isEmpty(q.getQuestion()))
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                //TODO: handle if there was error while parsing the file
                e.printStackTrace();
            }
        });
        return questions;
    }

    private QuestionsParser getParserByType(FileType parsingType) {
        switch (parsingType) {
            case CSV:
                return csvQuestionsParserService;
            case PNG:
                return pngQuestionsParserService;
            case JSON:
                return jsonQuestionsParserService;
            default:
                throw new RuntimeException("This format is not supported");
        }
    }

    private List<String> getUrlsByFileType(FileType fileType, List<String> filesUrls) {
        return filesUrls.stream().filter(url -> url.toLowerCase().endsWith(fileType.getFileTypeName())).collect(Collectors.toList());
    }
}
