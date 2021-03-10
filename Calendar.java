import java.util.*;
import java.lang.Math;
public class Main {
	/* Name: Momin Chaudhry
	 * Date: 2021/3/10
	 * Purpose: The purpose of this program is to print a month's calendar depending on a user's inputs.
	 			A user inputs the first day of the month and then the month the calendar is for, with this
				information, the program knows where to start the calendar and how many days there are in
				that month. The program then uses recursion to assign increasing date values in a 2D array
				keeping in mind of the day the month starts as well as the total number of days in the month.
				At the end, the program also prints the holidays in that month.
				Descriptions are provided for each method.
	 * Example Input: "tuesday" and "december"
	 * Example Output: 	DECEMBER
						SUN	MON	TUE	WED	THU	FRI	SAT
								1	2	3	4	5
						6	7	8	9	10	11	12
						13	14	15	16	17	18	19
						20	21	22	23	24	25	26
						27	28	29	30	31

						HOLIDAYS:
						December 24: Christmas Eve
						December 25: Christmas Day
						December 31: New Year's Eve
	 */
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);

		//Obtain the first day of the month
		System.out.print("What is the first day of the month (Monday, Sunday): ");
        String day = input.next().toLowerCase();
        int startDayInt = firstDay(day);

		//Obtain the month to see how many days are in it
        System.out.print("What month is it? (January, December): ");
        String month = input.next().toUpperCase();

		//Find the number of dates in given month
		int dates = dates(month);

		//Find the number of weeks in that month
		int numWeeks = numWeeks(dates,startDayInt);

		//Initialize a calendar and load it up
        int [][] calendar = new int[numWeeks][7];
        calendar = calendarRecursionGen(dates, startDayInt, numWeeks, calendar);

		//Determine holidays in given month
		String [] holidays = holidays(month);

		//Print the calendar
		System.out.println("");
		System.out.println(month);
		System.out.println("SUN\tMON\tTUE\tWED\tTHU\tFRI\tSAT");
        printTwoD(calendar);

		//Print the holidays in that month
		System.out.println("");
		System.out.println("HOLIDAYS: ");
		printOneD(holidays);

   	}

	public static int [] innerLoopRecursion(int row, int column, int startDayInt, int num, int [] currArr, int dates) {
		/* This method obtains the row number, column number, the index the week starts, the current
		 * calendar number, the calendar and the number of dates in the month, then undergoes recursion to add
		 * an increasing number for each column and returns it */
		if (column < 7) {
			if (row == 0 && column == 0) {
				currArr[startDayInt] = num;
				column = startDayInt;
			}
			else if (num <= dates) {
				currArr[column]=num;
			}
			//The method is called again to add the next date in the week
			return innerLoopRecursion(row, column+1, startDayInt, num+1, currArr, dates);
		}
		return currArr;
	}

	public static int [][] outLoopRecursion(int numWeeks, int row, int startDayInt, int num, int [][] currArr, int dates) {
		/* This method obtains the number of weeks, the row number, the index the week starts, the current calendar number
		 * the total number of dates in the month, then calls on recursive methods for each row in the calendar, assigning
		 * those 1D row arrays to the 2D array calendar and returning it */
		if (row == 0) {
			//After the first row is called, the number the second row starts at is 8-start day int, the return method is done for the second row
			currArr[row]=innerLoopRecursion(row, 0, startDayInt, num, currArr[row], dates);
			return outLoopRecursion(numWeeks, row+1, startDayInt, 8-startDayInt, currArr, dates);
		}
		else if (row < numWeeks) {
			//After the second row, the number just increases by 7, outLoopRecursion is called with the date being 7 greater than before
			currArr[row]=innerLoopRecursion(row, 0, startDayInt, num, currArr[row], dates);
			return outLoopRecursion(numWeeks, row+1, startDayInt, num+7, currArr, dates);
		}
		return currArr;
	}

	public static int [][] calendarRecursionGen(int dates, int startDayInt, int numWeeks, int [][] calendar) {
		/* This method obtains the number of dates, the index of the starting date, the number of weeks
		 * and a 2D array to create the calendar for a month, then returns that 2D array */
		return outLoopRecursion(numWeeks,0,startDayInt,1,calendar,dates);
	}

    public static int firstDay(String day){
        /* This method obtains the first day of the month and assigns a variable
		 * the respective numerical value and returns it */
        int startDayInt = 0;
		String [] days = {"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
		for (int index = 0; index < 7; index++){
			if (day.equals(days[index])){
				startDayInt = index;
				break;
			}
		}
        return startDayInt;
    }

	public static int dates(String month) {
        /* This method receives the month and returns the number of dates in that month */
		int dates = 30;
		String [] months = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
		int [] days = {31,28,31,30,31,30,31,31,30,31,30,31};
		for (int index = 0; index < 12; index++){
			if (month.equals(months[index])){
				dates = days[index];
				break;
			}
		}
		return dates;
    }

	public static int numWeeks(int dates, int startDayInt) {
		/* This method receives the number of dates and the index of the first date of the month,
		 * adds those values and rounds it up to find the number of weeks */
		int numWeeks = (int)(Math.ceil((dates+startDayInt)/7.0));
		return numWeeks;
	}

	public static String [] holidays(String month) {
		/* This method receives the name of the month, sorts an irregular 2D array by index
		 * for the holidays in that month and returns it */
		String [] holiday = new String [1];
		String [] months = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
 		String [][] holidays = {
			{"Jan 1: New Year's Day"},
			{"Feb 14: Valentine's Day","Third Friday: Family Day"},
			{"March 17: Saint Patrick's Day"},
			{"First Friday: Good Friday", "First Sunday: Easter Sunday"},
			{"Second Sunday: Mother's Day"," Last Monday: Victoria Day"},
			{"No holidays in June :("},
			{"July 1: This programmer's birthday! And Canada Day"},
			{"No holidays in August :("},
			{"First Monday: Labour Day"},
			{"Second Monday: Canadian Thanksgiving", "October 31: Halloween"},
			{"November 11: Remembrance Day"},
			{"December 24: Christmas Eve", "December 25: Christmas Day", "December 31: New Year's Eve"}
		};
		for (int index = 0; index < 12; index++){
			if (month.equals(months[index])){
				holiday = holidays[index];
				break;
			}
		}
		return holiday;
	}

	public static void printOneD(String [] oneD){
		/* This method obtains a 1D string array and prints it */
		for (int column = 0; column<oneD.length; column++){
			System.out.println(oneD[column]);
		}
	}

    public static void printTwoD(int[][]twoD) {
        /* This method obtains a 2D integer array and prints it, ignoring any zeroes */
        for (int row = 0; row<twoD.length; row++){
            for (int col = 0; col<twoD[row].length; col++){
				if (twoD[row][col] == 0){
					System.out.print("\t");
				}
				else {
					System.out.print(twoD[row][col] + "\t");
				}
            }
        System.out.println();
        }
    }
}
