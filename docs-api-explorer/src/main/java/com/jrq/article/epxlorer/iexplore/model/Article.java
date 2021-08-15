package com.jrq.article.epxlorer.iexplore.model;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Article {
    private Authors authors;
    private String title;

    @JsonProperty("publication_year")
    private Integer publicationYear;
}
