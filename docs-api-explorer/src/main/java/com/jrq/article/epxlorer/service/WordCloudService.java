package com.jrq.article.epxlorer.service;

import com.jrq.article.epxlorer.dblp.model.Result;
import com.jrq.article.epxlorer.dblp.service.DblpApiService;
import com.jrq.article.epxlorer.iexplore.service.IEEEEXploreApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
@ConditionalOnProperty("wordcloud.generator.active")
public class WordCloudService {

    @Autowired
    DblpApiService dblpApiService;

    @Autowired
    IEEEEXploreApiService ieeeeXploreApiService;

    private static final List<String> EXCLUDED_WORDS = List.of(
            "if", "the", "an", "from", "and", "for", "to", "in", "on", "with", "as", "by", "is", "it", "when", "what", "has", "their", "can", "be", "of");

    private final Map<String, Integer> titleWordCloud = new HashMap<>();

    @PostConstruct
    public void generateWordCloud(String query) {

        generateWordCloudIeeeXplore(query);
        generateWordCloudDblp(query);

        printWordCloud();
    }

    public void generateWordCloudDblp(String query) {
        Result dblpCloud = dblpApiService.callDblp(query);
        fillTitleWordCloud(dblpCloud);
    }


    public void generateWordCloudIeeeXplore(String query) {
        com.jrq.article.epxlorer.iexplore.model.Result ieeeXploreCloud = ieeeeXploreApiService.callIeeeXplore(query);
        fillTitleWordCloudIeeeXplore(ieeeXploreCloud);
    }

    private void printWordCloud() {
        titleWordCloud.forEach((k,v) -> {
            if (v > 2) {
                System.out.println("\"" + v + "\";\"" + k + "\";");
            }
        });
    }

    private void fillTitleWordCloud(Result result) {
        result.getHits().getHit().forEach(h -> fillTitleWords(h.getInfo().getTitle()));
    }

    private void fillTitleWordCloudIeeeXplore(com.jrq.article.epxlorer.iexplore.model.Result result) {
        result.getArticles().forEach(a -> fillTitleWords(a.getTitle()));
    }

    private void fillTitleWords(String title) {
        String [] titleWords = title.split(" ");
        IntStream.range(0, titleWords.length).forEach(n -> {
            Optional<Integer> currentNumber = Optional.ofNullable(titleWordCloud.get(titleWords[n].toLowerCase()));
            if (titleWords[n].length() > 1 && !EXCLUDED_WORDS.contains(titleWords[n].toLowerCase())) {
                titleWordCloud.put(titleWords[n]
                                .replace(".", "")
                                .replace(",", "")
                                .replace("?", "")
                                .replace(")", "")
                                .replace("(", "")
                                .replace(";", "")
                                .replace("&apos;", "")
                                .replace("&quot;", "")
                                .replace("&amp;", "").toLowerCase(),
                        currentNumber.orElse(1) + 1);
            }
        });
    }
}
