package com.jrq.article.epxlorer.dblp.service;

import com.jrq.article.epxlorer.dblp.model.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DblpApiServiceTest {

    @InjectMocks
    DblpApiService service;

    @Test
    public void serviceMakeHttpRequestToDblp() {
        Result result = service.callDblp("query");

        assertThat(result).isNotNull();
        assertThat(result.getQuery()).isEqualTo("query*");
    }

    @Test
    public void serviceMakeHttpRequestToDblpSeveralTerms() {
        Result result = service.callDblp("query1+query2");

        assertThat(result).isNotNull();
        assertThat(result.getQuery()).isEqualTo("query1* query2*");
    }

    @Test
    public void serviceMakeHttpRequestToDblpComputationalCreativity() {
        Result result = service.callDblp("computational+creativity");

        assertThat(result).isNotNull();

        System.out.println("Computational Creativity Search:");
        System.out.println("Total results:" + result.getHits().getTotal());
        result.getHits().getHit().forEach(h -> System.out.println(
                "Year:" + h.getInfo().getYear()
                        + ";Title:" + h.getInfo().getTitle()
                        + ";Authors:" + (
                                h.getInfo().getAuthors() != null
                                        ? h.getInfo().getAuthors().toString()
                                        : "")));
    }

    @Test
    public void serviceMakeHttpRequestToDblpCreativityWrite() {
        Result result = service.callDblp("write+creativity");

        assertThat(result).isNotNull();

        System.out.println("Writing Creativity Search:");
        System.out.println("Total results:" + result.getHits().getTotal());
        result.getHits().getHit().forEach(h -> System.out.println(
                "Year:" + h.getInfo().getYear()
                        + ";Title:" + h.getInfo().getTitle()
                        + ";Authors:" + (
                        h.getInfo().getAuthors() != null
                                ? h.getInfo().getAuthors().toString()
                                : "")));
    }

    @Test
    public void serviceMakeHttpRequestToDblpCreativityPaint() {
        Result result = service.callDblp("paint+creativity");

        assertThat(result).isNotNull();

        System.out.println("Paint Creativity Search:");
        System.out.println("Total results:" + result.getHits().getTotal());
        result.getHits().getHit().forEach(h -> System.out.println(
                "Year:" + h.getInfo().getYear()
                        + ";Title:" + h.getInfo().getTitle()
                        + ";Authors:" + (
                        h.getInfo().getAuthors() != null
                                ? h.getInfo().getAuthors().toString()
                                : "")));
    }

    @Test
    public void serviceMakeHttpRequestToDblpCreativityMusic() {
        Result result = service.callDblp("music+creativity");

        assertThat(result).isNotNull();

        System.out.println("Paint Creativity Search:");
        System.out.println("Total results:" + result.getHits().getTotal());
        result.getHits().getHit().forEach(h -> System.out.println(
                "Year:" + h.getInfo().getYear()
                        + ";Title:" + h.getInfo().getTitle()
                        + ";Authors:" + (
                        h.getInfo().getAuthors() != null
                                ? h.getInfo().getAuthors().toString()
                                : "")));
    }
}
