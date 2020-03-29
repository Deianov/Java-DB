package softuni.exam.adapters;

import softuni.exam.constant.Constants;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    // "dd/MM/yyyy"

    @Override
    public LocalDate unmarshal(String s) throws Exception {
        return LocalDate.parse(s, DateTimeFormatter.ofPattern(Constants.LOCAL_DATE_FORMAT));
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.toString();
    }
}
