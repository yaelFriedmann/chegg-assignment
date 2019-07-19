package controller;

import dto.FileType;
import dto.Question;
import dto.RequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import services.DatParserService;
import services.QuestionsRetrieverService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
public class ApplicationController {

    @Autowired
    private DatParserService datParserService;

    @Autowired
    private QuestionsRetrieverService questionsRetrieverService;

    private static final String FILE_TYPES = "fileTypes";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String initForm(Model model) {
        model.addAttribute(FILE_TYPES, Arrays.asList(FileType.CSV, FileType.PNG, FileType.JSON));
        model.addAttribute("requestForm", new RequestForm());
        return "query-form";
    }

    @GetMapping("/parse")
    public Callable<String> parse(@RequestParam(value = "manifest") String manifest,
                                  @RequestParam(required = false) final List<FileType> fileTypeList, Model model) {
        return () -> {
            if (manifest.isEmpty()) {
                model.addAttribute("errorMessage", "You must fill the manifest field!");
                return "error";
            }
            List<FileType> searchValues = CollectionUtils.isEmpty(fileTypeList) ?
                    Arrays.asList(FileType.values()) : fileTypeList;
            try {
                List<String> filesUrls = datParserService.retrieveAllUrlsToParse(manifest);
                List<Question> questionResult = questionsRetrieverService.retrieveAllQuestions(filesUrls, searchValues);
                model.addAttribute("questions", questionResult);
            } catch (IOException e) {
                model.addAttribute("errorMessage", e);
                return "error";
            }
            return "result-form";
        };

    }
}
