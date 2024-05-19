package dev.czechitas.java.spring.freemarker.spring;

import dev.czechitas.java.spring.freemarker.Java17ObjectWrapper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperConfiguration;
import freemarker.template.Version;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@AutoConfiguration
@Import(CreateURL.class)
public class FreemarkerConfig implements BeanPostProcessor {

  private final CreateURL createURL;

  public FreemarkerConfig(CreateURL createURL) {
    this.createURL = createURL;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof FreeMarkerConfigurer configurer) {
      Java17ObjectWrapperConfiguration objectWrapperConfiguration = new Java17ObjectWrapperConfiguration(freemarker.template.Configuration.VERSION_2_3_32);
      Java17ObjectWrapper objectWrapper = new Java17ObjectWrapper(objectWrapperConfiguration, true);
      Configuration configuration = configurer.getConfiguration();
      configuration.setObjectWrapper(objectWrapper);
      configuration.setSharedVariable("createURL", createURL);
    }
    return bean;
  }

  private static class Java17ObjectWrapperConfiguration extends DefaultObjectWrapperConfiguration {
    protected Java17ObjectWrapperConfiguration(Version incompatibleImprovements) {
      super(incompatibleImprovements);
      setIterableSupport(true);
    }
  }
}
