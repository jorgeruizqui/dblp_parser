package com.jrq.article.epxlorer.iexplore.model;

import lombok.Data;

@Data
public class Author {
    private String full_name;

    public String toString() {
        return full_name;
    }
}
