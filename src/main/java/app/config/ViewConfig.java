package app.config;

import app.config.viewresolvers.JsonViewResolver;
import app.config.viewresolvers.PdfViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ViewConfig {

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public ViewResolver pdfViewResolver() {
        return new PdfViewResolver();
    }

    @Bean
    public ViewResolver jsonViewResolver(){
        return new JsonViewResolver();
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        resolver.setContentNegotiationManager(manager);
        List< ViewResolver > resolvers = new ArrayList< ViewResolver >();
        resolvers.add(jsonViewResolver());
        resolvers.add(pdfViewResolver());
        resolver.setViewResolvers(resolvers);
        return resolver;
    }
}
