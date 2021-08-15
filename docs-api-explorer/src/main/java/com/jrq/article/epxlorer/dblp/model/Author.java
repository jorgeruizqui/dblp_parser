package com.jrq.article.epxlorer.dblp.model;

import lombok.Data;

@Data
public class Author {
    private String text;

    public String toString() {
        return text;
    }
}
