package dev.czechitas.java.spring.freemarker;

import dev.czechitas.java.spring.freemarker.record.RecordModel;
import freemarker.ext.beans.BeansWrapperConfiguration;
import freemarker.ext.beans.DateModel;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperConfiguration;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Java17ObjectWrapper extends DefaultObjectWrapper {
  public Java17ObjectWrapper(Version incompatibleImprovements) {
    super(incompatibleImprovements);
  }

  public Java17ObjectWrapper(BeansWrapperConfiguration bwCfg, boolean writeProtected) {
    super(bwCfg, writeProtected);
  }

  public Java17ObjectWrapper(DefaultObjectWrapperConfiguration dowCfg, boolean writeProtected) {
    super(dowCfg, writeProtected);
  }

  @Override
  protected TemplateModel handleUnknownType(Object obj) throws TemplateModelException {
    if (obj instanceof LocalDate localDate) {
      Date date = Date.from(localDate.atStartOfDay()
              .atZone(ZoneId.systemDefault())
              .toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof LocalTime localTime) {
      Date date = Date.from(localTime.atDate(LocalDate.now())
              .atZone(ZoneId.systemDefault())
              .toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof OffsetDateTime offsetDateTime) {
      Date date = Date.from(offsetDateTime.toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof ZonedDateTime zonedDateTime) {
      Date date = Date.from(zonedDateTime.toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof OffsetTime offsetTime) {
      Date date = Date.from(offsetTime.atDate(LocalDate.now())
              .toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof Record record) {
      return new RecordModel(this, record);
    }
    return super.handleUnknownType(obj);
  }

}
