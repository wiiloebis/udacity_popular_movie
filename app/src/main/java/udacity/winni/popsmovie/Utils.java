package udacity.winni.popsmovie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by winniseptiani on 6/26/17.
 */

public class Utils {

    public static int getYearFromDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(stringDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String formatReleaseDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date newDate = format.parse(stringDate);
            format = new SimpleDateFormat("MMM dd, yyyy");
            return format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
