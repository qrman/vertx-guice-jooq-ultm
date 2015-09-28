package io.github.qrman.potato.db.converter;

import org.joda.time.LocalDate;
import org.jooq.Converter;

import java.sql.Date;

public class LocalDateConverter implements Converter<Date, LocalDate> {


    @Override
    public LocalDate from(Date databaseObject) {
        if (databaseObject == null) {
            return null;
        }
        return new LocalDate(databaseObject.getTime());
    }

    @Override
    public Date to(LocalDate userObject) {
        if (userObject == null) {
            return null;
        }
        return new Date(userObject.toDate().getTime());
    }

    @Override
    public Class<Date> fromType() {
        return Date.class;
    }

    @Override
    public Class<LocalDate> toType() {
        return LocalDate.class;
    }

}
