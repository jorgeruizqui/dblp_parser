package com.jrq.article.epxlorer.service;

import com.jrq.article.epxlorer.dblp.model.Result;
import com.jrq.article.epxlorer.dblp.service.DblpApiService;
import com.jrq.article.epxlorer.iexplore.service.IEEEEXploreApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("csv.generator.active")
public class CsvService {

    @Autowired
    private DblpApiService dblpApiService;

    @Autowired
    private IEEEEXploreApiService ieeeeXploreApiService;

    public void generateCsv(String query) {
        generateCsvDblp(query);
        generateCsvIeeeXplore(query);
    }

    public void generateCsvIeeeXplore(String query) {
        com.jrq.article.epxlorer.iexplore.model.Result queryResults = ieeeeXploreApiService.callIeeeXplore("article_title=" + query);
        printResult(query, queryResults);
    }

    public void generateCsvDblp(String query) {
        Result queryResult = dblpApiService.callDblp(query);
        printResult(query, queryResult);
    }

    private void printResult(String query, Result result) {
        System.out.println(";;DBLP: " + query + " - TOTAL: " + result.getHits().getTotal());
        result.getHits().getHit().forEach(h -> System.out.println(
                          ";" + h.getInfo().getYear()
                        + ";" + h.getInfo().getTitle()
                                  .replace("&apos;", "'")
                                  .replace("&quot;", "'")
                                  .replace("&amp;", "&")
                        + ";" + (
                        h.getInfo().getAuthors() != null
                                ? h.getInfo().getAuthors().toString()
                                .replace("&apos;", "'")
                                .replace("&quot;", "'")
                                .replace("&amp;", "&")
                                : "")));
    }

    private void printResult(String query, com.jrq.article.epxlorer.iexplore.model.Result result) {
        System.out.println(";;IEEEXPLORE: " + query + " - TOTAL: " + result.getTotalRecords());
        result.getArticles().forEach(a -> System.out.println(
                        ";" + a.getPublicationYear()
                        + ";" + a.getTitle()
                                .replace("&apos;", "'")
                                .replace("&quot;", "'")
                                .replace("&amp;", "&")
                        + ";" + (
                        a.getAuthors() != null
                                ? a.getAuthors().toString()
                                .replace("&apos;", "'")
                                .replace("&quot;", "'")
                                .replace("&amp;", "&")
                                : "")));
    }
}
