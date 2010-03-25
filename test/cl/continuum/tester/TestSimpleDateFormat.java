/**
 * 
 */
package cl.continuum.tester;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author israel
 * 
 */
public class TestSimpleDateFormat {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// Make a new Date object. It will be initialized to the
		// current time.
		Calendar cal = Calendar.getInstance();

		Date now = new Date();
		long nowL = now.getTime();
		int i = 0;
		while (i < 999999999) {
			i++;
		}

		Date other = new Date();
		long otherL = other.getTime();
		// Print the result of toString()
		String dateString = now.toString();
		System.out.println(" 1. " + dateString);

		// Make a SimpleDateFormat for toString()'s output. This
		// has short (text) date, a space, short (text) month, a space,
		// 2-digit date, a space, hour (0-23), minute, second, a space,
		// short timezone, a final space, and a long year.
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");

		System.out.println("Now " + format.format(now));
		System.out.println("Other " + format.format(other));
		System.out.println("Resta " + (otherL - nowL));
		// Print the result of formatting the now Date to see if the result
		// is the same as the output of toString()
		//System.out.println(" 3. ");

	}

}
