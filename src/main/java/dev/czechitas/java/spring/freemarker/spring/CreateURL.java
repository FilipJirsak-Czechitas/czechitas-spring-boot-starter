package dev.czechitas.java.spring.freemarker.spring;

import freemarker.template.TemplateHashModelEx2;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelListSequence;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

@Service
public class CreateURL implements TemplateMethodModelEx {
  private final HttpServletRequest request;

  @Autowired
  public CreateURL(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public String exec(List list) throws TemplateModelException {
    TemplateHashModelEx2 map = (TemplateHashModelEx2) list.get(0);
    TemplateHashModelEx2.KeyValuePairIterator iterator = map.keyValuePairIterator();
    UriComponentsBuilder uriBuilder = UriComponentsBuilder
            .newInstance()
            .path(UrlPathHelper.defaultInstance.getOriginatingRequestUri(request))
            .query(UrlPathHelper.defaultInstance.getOriginatingQueryString(request));
    while (iterator.hasNext()) {
      TemplateHashModelEx2.KeyValuePair keyValuePair = iterator.next();
      String key = keyValuePair.getKey().toString();
      TemplateModel value = keyValuePair.getValue();
      if (value instanceof TemplateModelListSequence sequenceValue) {
        uriBuilder.replaceQueryParam(key, (List) sequenceValue.getWrappedObject());
      } else {
        uriBuilder.replaceQueryParam(key, value.toString());
      }

    }
    return uriBuilder.build().toString();
  }
}
