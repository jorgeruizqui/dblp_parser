package com.jrq.article.epxlorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocsApiService {

    public static final String FORMAT_CSV = "CSV";
    public static final String FORMAT_WORDCLOUD = "WORDCLOUD";
    public static final String SOURCE_ALL = "ALL";
    public static final String SOURCE_DBLP = "DBLP";
    public static final String SOURCE_IEEEXPLORE = "IEEEXplore";

    @Autowired
    private CsvService csvService;

    @Autowired
    private WordCloudService wordCloudService;

    public void explore(String source, String query, String format, String output) {
        if (SOURCE_ALL.equals(source)) {
            generateAllSources(format, query);
        } else if (SOURCE_DBLP.equals(format)) {
            generateDBLP(format, query);
        } else if (SOURCE_IEEEXPLORE.equals(format)) {
            generateIEEEXplore(format, query);
        }
    }

    private void generateAllSources(String format, String query) {
        if (FORMAT_CSV.equals(format)) {
            csvService.generateCsv(query);
        } else if (FORMAT_WORDCLOUD.equals(format)) {
            wordCloudService.generateWordCloud(query);
        }
    }
    private void generateDBLP(String format, String query) {
        if (FORMAT_CSV.equals(format)) {
            csvService.generateCsvDblp(query);
        } else if (FORMAT_WORDCLOUD.equals(format)) {
            wordCloudService.generateWordCloudDblp(query);
        }
    }

    private void generateIEEEXplore(String format, String query) {
        if (FORMAT_CSV.equals(format)) {
            csvService.generateCsvIeeeXplore(query);
        } else if (FORMAT_WORDCLOUD.equals(format)) {
            wordCloudService.generateWordCloudIeeeXplore(query);
        }
    }
}
