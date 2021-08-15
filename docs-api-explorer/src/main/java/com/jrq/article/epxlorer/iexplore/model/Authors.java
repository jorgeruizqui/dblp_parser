package com.jrq.article.epxlorer.iexplore.model;

import lombok.Data;

import java.util.List;

@Data
public class Authors {
    List<Author> authors;

    public String toString() {
        StringBuffer rto = new StringBuffer();
        authors.forEach(a -> rto.append(a.getFull_name() + ", "));
        return rto.toString();
    }
}
