package es.didiez.idea253694.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import java.util.Map;
import java.util.Random;

@Configuration
public class ThymeleafConfiguration {

    @Bean
    SpringResourceTemplateResolver fallbackTemplateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        templateResolver.setPrefix("classpath:/fallback/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    SpringResourceTemplateResolver baseTemplateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setOrder(0);
        templateResolver.setPrefix("classpath:/base/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    FixedResourceTemplateResolver fixedTemplateResolver(){
        FixedResourceTemplateResolver templateResolver = new FixedResourceTemplateResolver();
        templateResolver.setOrder(-1);
        templateResolver.setPrefix("classpath:/overrides/");
        templateResolver.setSuffix(".html");
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    DynamicTemplateResolver dynamicTemplateResolver(){
        DynamicTemplateResolver templateResolver = new DynamicTemplateResolver();
        templateResolver.setOrder(-2);
        templateResolver.setPrefix("classpath:/overrides/");
        templateResolver.setSuffix(".html");
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);  // important to force dynamic template resolution everytime
        return templateResolver;
    }

    static class FixedResourceTemplateResolver extends SpringResourceTemplateResolver {
        @Override
        protected String computeResourceName(IEngineConfiguration configuration, String ownerTemplate, String template, String prefix, String suffix, boolean forceSuffix, Map<String, String> templateAliases, Map<String, Object> templateResolutionAttributes) {
            prefix += "fixed/templates/";  // constant prefix
            return super.computeResourceName(configuration, ownerTemplate, template, prefix, suffix, forceSuffix, templateAliases, templateResolutionAttributes);
        }
    }

    @Slf4j
    static class DynamicTemplateResolver extends SpringResourceTemplateResolver {

        public static final Random RANDOM = new Random();

        @Override
        protected String computeResourceName(IEngineConfiguration configuration, String ownerTemplate, String template, String prefix, String suffix, boolean forceSuffix, Map<String, String> templateAliases, Map<String, Object> templateResolutionAttributes) {
            String color = RANDOM.nextBoolean() ? "green" : "red";  // mock dynamic template resolution
            prefix += color + "/templates/";  // variable prefix
            String computed = super.computeResourceName(configuration, ownerTemplate, template, prefix, suffix, forceSuffix, templateAliases, templateResolutionAttributes);
            log.info("computed: {}", computed);
            return computed;
        }
    }

}
