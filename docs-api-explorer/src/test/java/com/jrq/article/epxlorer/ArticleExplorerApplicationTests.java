package com.jrq.article.epxlorer;

import com.jrq.article.epxlorer.dblp.service.DblpApiService;
import com.jrq.article.epxlorer.iexplore.service.IEEEEXploreApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ArticleExplorerApplicationTests {

	@Autowired
	DblpApiService dblpApiService;

	@Autowired
	IEEEEXploreApiService ieeeeXploreApiService;

	@Test
	void dblpServiceLoaded() {
		assertThat(dblpApiService).isNotNull();
	}

	@Test
	void ieeeXploreServiceLoaded() {
		assertThat(ieeeeXploreApiService).isNotNull();
	}
}
