package services.fileParser;

import dto.FileType;
import dto.Question;
import dto.Record;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CsvQuestionsParserService implements QuestionsParser {
    private static final String COMMA = ",";

    /**
     * retrieve all the lines from csv file
     *
     * @param fileUrl
     * @return list of question
     * @throws IOException
     */
    @Override
    public List<Question> retrieveQuestionsFromUrl(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            // skip the header of the csv
            return reader.lines()
                    .skip(1)
                    .map(mapToItem)
                    .map(record -> new Question(record.getText(), FileType.CSV.getFileTypeName()))
                    .collect(Collectors.toList());
        }

    }

    /**
     * Map each line in the csv file to record object
     */
    private Function<String, Record> mapToItem = (line) -> {
        String[] p = line.split(COMMA);// a CSV has comma separated lines
        Record item = new Record();
        if (p.length == 3) {
            item.setId(p[0]);
            item.setText(p[1]);
            item.setField(p[2]);
        }
        return item;
    };
}
