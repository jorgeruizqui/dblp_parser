package com.jrq.article.epxlorer.dblp.model;

import lombok.Data;

@Data
public class Info {
    private Authors authors;
    private String title;
    private String year;
}
