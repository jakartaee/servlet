package servlet.tck.common.request;

import java.util.ArrayList;
import java.util.List;

public class Header {

    private String name;

    private List<String> values = new ArrayList<>();


    public Header(String name, String value) {
        this.name = name;
        this.values.add(value);
    }

    public Header(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }

    public String getValue() {
        return String.join(",", values);
    }

    @Override
    public String toString() {
        return "Header{"
                + '\'' + name + '\'' +
                ":" + String.join(",",values) +
                '}';
    }
}
