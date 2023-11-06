package dev.czechitas.java.spring.freemarker.spring;

import dev.czechitas.java.spring.freemarker.Java17ObjectWrapper;
import freemarker.template.DefaultObjectWrapperConfiguration;
import freemarker.template.Version;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class FreemarkerConfig implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof FreeMarkerConfigurer configurer) {
      Java17ObjectWrapperConfiguration objectWrapperConfiguration = new Java17ObjectWrapperConfiguration(freemarker.template.Configuration.VERSION_2_3_32);
      Java17ObjectWrapper objectWrapper = new Java17ObjectWrapper(objectWrapperConfiguration, true);
      configurer.getConfiguration().setObjectWrapper(objectWrapper);
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
