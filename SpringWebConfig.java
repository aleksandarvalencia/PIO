package com.projects.pio.config;

//import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/*import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring4.web.view.ThymeleafTilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;*/
//import org.springframework.format.support.FormattingConversionService;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;
//import org.springframework.data.repository.support.DomainClassConverter;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.projects.pio.config", "com.projects.pio.controller", "com.projects.pio.service",
		"com.projects.pio.exceptions", "com.projects.pio.validation" })
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    ServletContext servletContext;

        /*@Autowired
	private FormattingConversionService mvcConversionService;*/
        
        /*@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}*/
        
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
                registry.addResourceHandler("/include/**").addResourceLocations("/include/").setCachePeriod(31556926);
                registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
                viewResolver.setOrder(2);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
        @Bean
	public ViewResolver  getXmlViewResolver() {
            
                XmlViewResolver viewResolver = new XmlViewResolver();
		//viewResolver.setLocation(new ClassPathResource("/WEB-INF/views.xml"));
                viewResolver.setLocation(new ServletContextResource(servletContext,"/WEB-INF/views.xml"));
		viewResolver.setOrder(1);
                
                return viewResolver;
	}
        /*@Bean
	public ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver result = new ClassLoaderTemplateResolver();
		result.setPrefix("/resources/views/");
		result.setSuffix(".html");
		result.setTemplateMode("HTML5");
		return result;
	}*/

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
		rb.setBasenames(new String[] { "messages", "validation","sqlconfig"});
		return rb;
	}
        
        /*@Bean
	public ThymeleafTilesConfigurer tilesConfigurer() {
		ThymeleafTilesConfigurer tilesConfigurer = new ThymeleafTilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "classpath:tiles/tiles-def.xml" });
		return tilesConfigurer;
	}

	/*@Bean
	public SpringTemplateEngine templateEngine(
            ClassLoaderTemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(new TilesDialect());
		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setViewClass(ThymeleafTilesView.class);
		return viewResolver;
	}

	@Bean
	public DomainClassConverter<?> domainClassConverter() {
		return new DomainClassConverter<>(mvcConversionService);
	}*/
}

