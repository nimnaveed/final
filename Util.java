import java.util.Calendar;
import java.util.Date;

public class Util {
	
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	public static String padLeft(int s, int n) {
		// return String.format("%" + n + "s", s);
		return String.format("%1$" + n + "s", s).replace(' ', ' ');
	}
}
