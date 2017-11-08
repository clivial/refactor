package com.clivial.web.refactor.conf;

import com.clivial.web.refactor.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.swing.text.Document;
import java.util.Date;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by clivia on 2017/11/7.

 * Springfox Swagger configuration.
 *
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use a specific Spring profile for this class, so that only front-end developers
 * have access to the Swagger view.
 */
@Configuration
@EnableSwagger2
@Profile("!"+ Constants.SPRING_PROFILE_NO_SWAGGER)
@ConditionalOnProperty(prefix = "ext.swagger", name="enabled")
public class SwaggerConfig {

    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Bean
    public Docket swaggerSpringfoxDocket(ExtProperties extProperties){
        logger.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                extProperties.getSwagger().getContactName(),
                extProperties.getSwagger().getContactUrl(),
                extProperties.getSwagger().getContactEmail());
        ApiInfo apiInfo = new ApiInfo(extProperties.getSwagger().getTitle(),
                extProperties.getSwagger().getDescription(),
                extProperties.getSwagger().getVersion(),
                extProperties.getSwagger().getTermsOfServiceUrl(),
                contact,
                extProperties.getSwagger().getLicense(),
                extProperties.getSwagger().getLicenseUrl());
//                new ApiInfoBuilder()
//                        // 文档标题
//                        .title("DigAg")
//                        // 文档描述
//                        .description("https://github.com/Yuicon")
//                        .termsOfServiceUrl("https://github.com/Yuicon")
//                        .version("v1")
//                        .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .select()
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
        watch.stop();
        logger.debug("Started Swagger in {}");
        return docket;


    }

}
