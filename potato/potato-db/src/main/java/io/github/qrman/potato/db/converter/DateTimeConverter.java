package io.github.qrman.potato.db.converter;

import java.sql.Date;
import org.joda.time.DateTime;
import org.jooq.Converter;

public class DateTimeConverter implements Converter<Date, DateTime> {


    @Override
    public DateTime from(Date databaseObject) {
        if (databaseObject == null) {
            return null;
        }
        return new DateTime(databaseObject.getTime());
    }

    @Override
    public Date to(DateTime userObject) {
        if (userObject == null) {
            return null;
        }
        return new Date(userObject.getMillis());
    }

    @Override
    public Class<Date> fromType() {
        return Date.class;
    }

    @Override
    public Class<DateTime> toType() {
        return DateTime.class;
    }

}
