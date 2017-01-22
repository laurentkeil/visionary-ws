package visionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import visionary.controllers.AppErrorController;

/**
 * Configuration used for JSON documentation 
 */ 
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
        
        @Override
        protected void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("**/*.css", "**/*.js", "**/*.map", "*.html").addResourceLocations("classpath:META-INF/resources/").setCachePeriod(0);
        }
        
        //init of Error class to display an error status and message when it's a bad path or request
        @Autowired
        private ErrorAttributes errorAttributes;
        @Bean
        public AppErrorController appErrorController(){
        	return new AppErrorController(errorAttributes);
        }

}