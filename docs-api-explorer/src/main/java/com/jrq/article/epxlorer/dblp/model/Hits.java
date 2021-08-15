package com.jrq.article.epxlorer.dblp.model;

import lombok.Data;

import java.util.List;

@Data
public class Hits {

    String total;

    List<Hit> hit;

}
