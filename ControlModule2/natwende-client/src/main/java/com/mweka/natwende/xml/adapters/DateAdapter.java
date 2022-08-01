package com.mweka.natwende.xml.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    @Override
    public Date unmarshal(String s) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return dateFormat.parse(s);
        } catch (ParseException e) {
            try {
                SimpleDateFormat expiryFirstDateFormat = new SimpleDateFormat("yyyy-MM");
                return expiryFirstDateFormat.parse(s);
            } catch (ParseException e2) {
                try {
                    SimpleDateFormat expiryFirstDateFormat = new SimpleDateFormat("yyyy/MM");
                    return expiryFirstDateFormat.parse(s);
                } catch (ParseException e3) {
                    try {
                        SimpleDateFormat expiryFirstDateFormat = new SimpleDateFormat("yyyyMMdd");
                        return expiryFirstDateFormat.parse(s);
                    } catch (ParseException e4) {
                        return null;
                    }
                }
            }
        }
    }

    @Override
    public String marshal(Date c) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ssXX");
        return dateFormat.format(c);
    }
}
