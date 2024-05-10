package dev.czechitas.java.spring.freemarker;

import dev.czechitas.java.spring.freemarker.record.RecordModel;
import freemarker.ext.beans.BeansWrapperConfiguration;
import freemarker.ext.beans.DateModel;
import freemarker.template.*;

import java.time.*;
import java.util.Date;

public class Java17ObjectWrapper extends DefaultObjectWrapper {

    public static final ZoneId ZONE_ID = ZoneId.systemDefault();

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
            Date date = Date.from(
                    localDate.atStartOfDay()
                            .atZone(ZONE_ID)
                            .toInstant());
            return new DateModel(date, this);
        }
        if (obj instanceof LocalTime localTime) {
            Date date = Date.from(localTime.atDate(LocalDate.now())
                    .atZone(ZONE_ID)
                    .toInstant());
            return new DateModel(date, this);
        }
        if (obj instanceof OffsetTime offsetTime) {
            Date date = Date.from(
                    offsetTime.atDate(LocalDate.now())
                            .toInstant()
            );
            return new DateModel(date, this);
        }
        if (obj instanceof LocalDateTime localDateTime) {
            Date date = Date.from(
                    localDateTime
                            .atZone(ZONE_ID)
                            .toInstant()
            );
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
        if (obj instanceof Record record) {
            return new RecordModel(this, record);
        }
        return super.handleUnknownType(obj);
    }

}
