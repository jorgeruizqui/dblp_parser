package com.jrq.article.epxlorer.iexplore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Result {

    @JsonProperty("total_records")
    @org.codehaus.jackson.annotate.JsonProperty("total_records")
    private Integer totalRecords;

    List<Article> articles;
}
