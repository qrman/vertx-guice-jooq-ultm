package io.github.qrman.potato.db.converter;

import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.jooq.Converter;

public class DateTimeConverter implements Converter<Timestamp, DateTime> {


    @Override
    public DateTime from(Timestamp databaseObject) {
        if (databaseObject == null) {
            return null;
        }
        return new DateTime(databaseObject.getTime());
    }

    @Override
    public Timestamp to(DateTime userObject) {
        if (userObject == null) {
            return null;
        }
        return new Timestamp(userObject.getMillis());
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<DateTime> toType() {
        return DateTime.class;
    }

}
