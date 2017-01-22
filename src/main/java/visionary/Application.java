package visionary;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to start the spring web service
 * Documentation is here : {basepath}/jsondoc-ui.html, insert {basepath}/jsondoc
 */
@SpringBootApplication
@EnableJSONDoc
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
