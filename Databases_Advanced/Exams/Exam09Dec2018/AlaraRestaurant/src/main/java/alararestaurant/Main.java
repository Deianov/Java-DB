package alararestaurant;

import alararestaurant.constant.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
//        "“dd/MM/yyyy HH:mm"

        LocalDateTime dateTime =
                LocalDateTime.parse("23/11/2017 16:25", DateTimeFormatter.ofPattern(Constants.ORDER_DATE_FORMAT));


        System.out.println(dateTime.toString());
    }
}
