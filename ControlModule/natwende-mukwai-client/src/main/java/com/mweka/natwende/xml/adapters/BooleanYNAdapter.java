package com.mweka.natwende.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanYNAdapter extends XmlAdapter<String, Boolean> {
    @Override
    public Boolean unmarshal( String s )
    {
        return s == null ? null : s.equalsIgnoreCase("Y");
    }

    public String marshal( Boolean c )
    {
        return c == null ? null : c ? "Y" : "N";
    }
}
