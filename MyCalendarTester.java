
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MyCalendarTester {

	public static void main(String[] args) {
		MyCalendar calendar = new MyCalendar();
		LocalDate currentDate = LocalDate.now();
		calendar.displayCalendar(currentDate);

		List<Event> events = Event.loadEvents("events.txt");
		
		calendar.setEvents(events);
		System.out.println("\n\nLoading is done!");

		while (true) {
			System.out.println("\n\n[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
			Scanner calendarScanner = new Scanner(System.in);
			String calendarInput = calendarScanner.nextLine();
			if (calendarInput.equalsIgnoreCase("V")) {
				System.out.println("\n[D]ay view or [M]view ? ");
				Scanner viewScanner = new Scanner(System.in);
				String viewInput = viewScanner.nextLine();
				if (viewInput.equalsIgnoreCase("D")) {
					// Display Today's Events
					Date today = new Date();
					calendar.displayEventsByDay(today);
					System.out.println("\n[P]revious or [N]ext or [G]o back to the main menu ?");
					int n = 0;
					while(n<2) {
						
						Scanner nextScanner = new Scanner(System.in);
						String nextInput = nextScanner.nextLine();
						if (nextInput.equalsIgnoreCase("N")) {
							n++;
							if(n==0) {
								calendar.displayEventsByDay(today);
							}else {
								calendar.displayEventsByDay(Util.addDays(today, n));
							}
						}else if (nextInput.equalsIgnoreCase("P")) {
							n--;
							calendar.displayEventsByDay(Util.addDays(today, n));
						}else if (nextInput.equalsIgnoreCase("G")) {
							break;
						}
					}
				} else if (viewInput.equalsIgnoreCase("M")) {
					int currentMonth = LocalDate.now().getMonthValue();
					List<Event> matchingEvents = calendar.getEventsByMonth(currentMonth);
					calendar.displayEventsCalendar(matchingEvents, LocalDate.now());
					int n = 0;
					while(true) {
						System.out.println("\n[P]revious or [N]ext or [G]o back to main menu ?");
						Scanner nextScanner = new Scanner(System.in);
						String input = nextScanner.nextLine();
						if (input.equalsIgnoreCase("P")) {
							currentMonth--;
							n--;
							matchingEvents = calendar.getEventsByMonth(currentMonth);
							calendar.displayEventsCalendar(matchingEvents, LocalDate.now().plusMonths(n));
						}else if (input.equalsIgnoreCase("N")) {
							currentMonth++;
							n++;
							matchingEvents = calendar.getEventsByMonth(currentMonth);
							calendar.displayEventsCalendar(matchingEvents, LocalDate.now().plusMonths(n));
						}else if (input.equalsIgnoreCase("G")) {
							break;
						}
					}
				}
			} else if (calendarInput.equalsIgnoreCase("C")) {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Name: a string (doesn't have to be one word)");
				String eventName = scanner.nextLine();
				System.out.println("Date: MM/DD/YYYY");
				String eventDate = scanner.nextLine();
				System.out.println("Start time: 24 hour clock");
				String startTime = scanner.nextLine();
				System.out.println("End time: 24 hour clock");
				String endTime = scanner.nextLine();
				calendar.createEvent(eventName,eventDate,startTime,endTime);
			} else if (calendarInput.equalsIgnoreCase("G")) {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter Event Date (MM/DD/YY)");
				String eventDate = scanner.nextLine();
				calendar.printEventsByDate(eventDate);
			} else if (calendarInput.equalsIgnoreCase("E")) {
				calendar.showEvent();
			} else if (calendarInput.equalsIgnoreCase("D")) {
				System.out.println("[S]elected  [A]ll   [DR]");
				Scanner scanner = new Scanner(System.in);
				String input = scanner.nextLine();
				if (input.equalsIgnoreCase("S")) {
					System.out.println("Enter Event Date (MM/DD/YY)");
					String eventDate = scanner.nextLine();
					System.out.println("Enter Event Name");
					String eventName = scanner.nextLine();
					calendar.deleteEvent(eventDate, eventName);
				}else if (input.equalsIgnoreCase("A")) {
					System.out.println("Enter Event Date (MM/DD/YY)");
					String eventDate = scanner.nextLine();
					calendar.deleteAllOnetimeEvents(eventDate);
				}else if (input.equalsIgnoreCase("DR")) {
					System.out.println("Enter Event Name");
					String eventName = scanner.nextLine();
					calendar.deleteRecurringEvent(eventName);
				}
			} else if (calendarInput.equalsIgnoreCase("Q")) {
				System.out.println("Good Bye!");
				calendar.saveEvent();
				break;
			}
		}

	}

	

	

	
}
