/* This is a multi-line 
 * for the LeapYear function,
 * which prints out whether a year 
 * is a leap year. */ 
public class LeapYear {
	public static void main(String[] args) {
		int year = 2000;
		if (year % 400 == 0) {
			System.out.println(year + " is a leap year.");
		} else if (year % 4 ==0) {
			if (year % 100 != 0) {
				System.out.println(year + " is a leap year.");
			} else {
				System.out.println(year + " is not a leap year.");
			}
		} else {
			System.out.println(year + " is not a leap year.");
		}
	}
}