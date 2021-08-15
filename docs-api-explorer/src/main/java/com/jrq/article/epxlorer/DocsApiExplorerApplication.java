package com.jrq.article.epxlorer;

import com.jrq.article.epxlorer.service.DocsApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DocsApiExplorerApplication implements CommandLineRunner {

	@Autowired
	private DocsApiService docsApiService;

	public static void main(String[] args) {
		SpringApplication.run(DocsApiExplorerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// java -jar docs-api-explorer-0.0.1.jar <query> [DBLP|IEEEXplore|ALL] [CSV|WORDCLOUD] <output>
		String query = args[1];
		String source = args[2];
		String format = args[3];
		String output = "console";

		long initTime = System.currentTimeMillis();

		if (args.length > 4) {
			output = args[4];
		}

		docsApiService.explore(source, query, format, output);

		log.info("Search performed in " + (System.currentTimeMillis() - initTime) + " milliseconds.");
		System.exit(0);
	}

}
