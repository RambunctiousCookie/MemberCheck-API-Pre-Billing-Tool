package RTT.billing.Util;

import RTT.billing.Config.DateConfig;
import RTT.billing.Config.SystemParameters;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

public class DateUtil {
    public static LocalDate[] getQuartileDates(int year, int yearQuarter){
        //[0] is start date, [1] is end date
        LocalDate[] returnDates = new LocalDate[2];
        //Retrieve from Params
        DateConfig dateConfig = HandlerYaml.parameters.getDateConfig();

        switch(yearQuarter){
            case 1:
                returnDates[0] = LocalDate.of(year, 1,1);
                returnDates[1] = LocalDate.of(year, 3,30);
                break;
            case 2:
                returnDates[0] = LocalDate.of(year, 4,1);
                returnDates[1] = LocalDate.of(year, 6,30);
                break;
            case 3:
                returnDates[0] = LocalDate.of(year, 7,1);
                returnDates[1] = LocalDate.of(year, 9,30);
                break;
            case 4:
                returnDates[0] = LocalDate.of(year, 10,1);
                returnDates[1] = LocalDate.of(year, 12,31);
                break;
            default:
                throw new IllegalArgumentException("Quarter can only be between 1-4");
        }
        return returnDates;
    }

//    public static LocalDate[] getQuartileDates(int year, int yearQuarter){
//        //[0] is start date, [1] is end date
//        LocalDate[] returnDates = new LocalDate[2];
//        //Retrieve from Params
//        SystemParameters parameters = HandlerYaml.parameters;
//
//        switch(yearQuarter){
//            case 1:
//                returnDates[0] = LocalDate.of(year, 1,1);
//                returnDates[1] = LocalDate.of(year, 3,30);
//                break;
//            case 2:
//                returnDates[0] = LocalDate.of(year, 4,1);
//                returnDates[1] = LocalDate.of(year, 6,30);
//                break;
//            case 3:
//                returnDates[0] = LocalDate.of(year, 7,1);
//                returnDates[1] = LocalDate.of(year, 9,30);
//                break;
//            case 4:
//                returnDates[0] = LocalDate.of(year, 10,1);
//                returnDates[1] = LocalDate.of(year, 12,31);
//                break;
//            default:
//                throw new IllegalArgumentException("Quarter can only be between 1-4");
//        }
//        return returnDates;
//    }
}
