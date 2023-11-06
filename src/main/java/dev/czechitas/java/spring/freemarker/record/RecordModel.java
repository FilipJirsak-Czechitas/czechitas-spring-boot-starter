package dev.czechitas.java.spring.freemarker.record;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Filip Jirsák
 */
public class RecordModel implements TemplateHashModel {
  private final BeansWrapper objectWrapper;
  private final Record value;

  public RecordModel(BeansWrapper objectWrapper, Record value) {
    this.objectWrapper = objectWrapper;
    this.value = value;
  }

  @Override
  public TemplateModel get(String key) throws TemplateModelException {
    try {
      Object propertyValue = value.getClass()
              .getMethod(key)
              .invoke(value);
      return objectWrapper.wrap(propertyValue);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isEmpty() throws TemplateModelException {
    return false;
  }
}
