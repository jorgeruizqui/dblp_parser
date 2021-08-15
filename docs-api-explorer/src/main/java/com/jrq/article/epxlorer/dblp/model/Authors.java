package com.jrq.article.epxlorer.dblp.model;

import lombok.Data;

import java.util.List;

@Data
public class Authors {
    List<Author> author;

    public String toString(){
        StringBuffer rto = new StringBuffer();
        author.forEach(a -> rto.append(a.getText() + ", "));
        return rto.toString();
    }

}
