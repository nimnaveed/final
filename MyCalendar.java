import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MyCalendar {
	List<Event> events;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	/**
	 * Displays todays events
	 * 
	 * @param inputEvents
	 * @param today
	 */
	public void displayEventsByDay(Date today) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		try {
			for (Event eachEvent : this.events) {
				Date eventDate = sdf.parse(sdf.format(eachEvent.getTimeInterval().getStartDateTime()));
				Date todayDate = sdf.parse(sdf.format(today));
				if (eventDate.equals(todayDate)) {
					TimeInterval ti = eachEvent.getTimeInterval();
					System.out.println(new SimpleDateFormat("E, MMMM dd, yyyy").format(ti.getStartDateTime()));
					System.out.println(eachEvent.getEventName() + " : " + new SimpleDateFormat("hh:mm").format(ti.getStartDateTime())
							+ " - " + new SimpleDateFormat("hh:mm").format(ti.getEndDateTime()));
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Prints events by given Date
	 * 
	 * @param inputEvents
	 * @param today
	 */
	public void printEventsByDate(String eventDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		try {
			Date inputDate = sdf.parse(eventDate);
			for (Event eachEvent : this.events) {
				Date eachDate = sdf.parse(sdf.format(eachEvent.getTimeInterval().getStartDateTime()));
				if (eachDate.equals(inputDate)) {
					System.out.println(eachEvent.getEventName() + " " + eachEvent.getTimeInterval().getStartDateTime());
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Gets events by given month
	 * 
	 * @param inputEvents
	 * @param today
	 */
	public List<Event> getEventsByMonth(int month) {
		List<Event> matchingEvents = new ArrayList<Event>();

		for (Event eachEvent : this.events) {
			Date startDateTime = eachEvent.getTimeInterval().getStartDateTime();
			int eventMonth = startDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();

			if (eventMonth == month) {
				matchingEvents.add(eachEvent);
			}
		}
		return matchingEvents;
	}

	/**
	 * Displays Calendar highlighting all events
	 * 
	 * @param currentDate
	 */
	public void displayEventsCalendar(List<Event> monthlyEvents, LocalDate month) {
		// Step 1 - Print current Month and year
		Month currentMonth = month.getMonth();
		int currentYear = month.getYear();
		System.out.println(" " + currentMonth + " " + currentYear);

		// Step 2 - Print Days of week
		String[] weekDays = { "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" };
		for (int i = 0; i < weekDays.length; i++) {
			System.out.print(weekDays[i] + " ");
		}
		System.out.println();

		// Step3 - Print Calendar
		// Print spaces for previous month's days
		LocalDate firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1);
		for (int i = 0; i < firstDayOfMonth.getDayOfWeek().getValue() % 7; i++) {
			System.out.print("   ");
		}
		// Print days of current month
		int totalDayOfMonth = month.lengthOfMonth();
		int counter = firstDayOfMonth.getDayOfWeek().getValue() % 7;
		for (int i = 1; i <= totalDayOfMonth; i++) {
			boolean dayMatched = false;
			for (Event eachEvent : monthlyEvents) {
				Date startDate = eachEvent.getTimeInterval().getStartDateTime();
				int eventDay = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth();
				if (eventDay == i) {
					dayMatched = true;
					break;
				}
			}
			if (dayMatched) {
				System.out.print("{");
			}
			System.out.print(Util.padLeft(i, 2));
			if (dayMatched) {
				System.out.print("}");
			}
			System.out.print(" ");
			counter++;
			if (counter == 7) {
				System.out.println();
				counter = 0;
			}
		}
	}

	/**
	 * Displays Calendar highlighting today's date
	 * 
	 * @param currentDate
	 */
	public void displayCalendar(LocalDate currentDate) {
		// Step 1 - Print current Month and year
		Month currentMonth = currentDate.getMonth();
		int currentYear = currentDate.getYear();
		System.out.println(" " + currentMonth + " " + currentYear);

		// Step 2 - Print Days of week
		String[] weekDays = { "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" };
		for (int i = 0; i < weekDays.length; i++) {
			System.out.print(weekDays[i] + " ");
		}
		System.out.println();

		// Step3 - Print Calendar
		// Print spaces for previous month's days
		LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
		for (int i = 0; i < firstDayOfMonth.getDayOfWeek().getValue(); i++) {
			System.out.print("   ");
		}
		// Print days of current month
		int totalDayOfMonth = currentDate.lengthOfMonth();
		int counter = firstDayOfMonth.getDayOfWeek().getValue();
		for (int i = 1; i <= totalDayOfMonth; i++) {
			if (currentDate.getDayOfMonth() == i) {
				System.out.print("[");
			}
			System.out.print(Util.padLeft(i, 2));
			if (currentDate.getDayOfMonth() == i) {
				System.out.print("]");
			}
			System.out.print(" ");
			counter++;
			if (counter == 7) {
				System.out.println();
				counter = 0;
			}
		}
	}

	/**
	 * Creates/add a new event to Calendar
	 * 
	 * @param inputEvents
	 */
	public void createEvent(String eventName,String eventDate,String startTime,String endTime) {
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy HH:mm");
		
		try {
			TimeInterval timeInterval = new TimeInterval();
			String strDate = eventDate+" "+startTime;
			Date startDate = sdf.parse(strDate);
			timeInterval.setStartDateTime(startDate);
			strDate = eventDate+" "+endTime;
			Date endDate=sdf.parse(strDate);
			timeInterval.setEndDateTime(endDate);
			//Check Date and Time Overlap 
			for(Event eachEvent: this.events) {
				TimeInterval ti = eachEvent.getTimeInterval();
				if(startDate.after(ti.getStartDateTime()) && startDate.before(ti.getEndDateTime())
						|| endDate.after(ti.getStartDateTime()) && endDate.before(ti.getEndDateTime())
						|| startDate.before(ti.getStartDateTime()) && endDate.after(ti.getEndDateTime())) {
					System.out.println("This event is conflicting with existing events");
					return;
				}
			}
			
			
			Event newEvent = new Event();
			newEvent.setEventName(eventName);
			newEvent.setRecurring(false);
			newEvent.setTimeInterval(timeInterval);
			this.events.add(newEvent);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Deletes an existing OneTime event from Calendar By matching date and name
	 * 
	 * @param eventName
	 */
	public void deleteEvent(String eventDate, String eventName) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		try {
			Date inputDate = sdf.parse(eventDate);
			Iterator<Event> itr = this.events.iterator(); 
			while (itr.hasNext()) { 
				Event eachEvent = itr.next(); 
				Date eachDate = sdf.parse(sdf.format(eachEvent.getTimeInterval().getStartDateTime()));
				if (eachDate.equals(inputDate) && eachEvent.getEventName().equalsIgnoreCase(eventName) && !eachEvent.isRecurring()) {
					itr.remove();
					break;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Deletes all OneTime events on the given event date
	 * 
	 * @param eventName
	 */
	public void deleteAllOnetimeEvents(String eventDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		try {
			Date inputDate = sdf.parse(eventDate);
			Iterator<Event> itr = this.events.iterator(); 
			while (itr.hasNext()) { 
				Event eachEvent = itr.next(); 
				Date eachDate = sdf.parse(sdf.format(eachEvent.getTimeInterval().getStartDateTime()));
				if (eachDate.equals(inputDate) && !eachEvent.isRecurring()) {
					itr.remove();
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Deletes Recurring event by given event name
	 * 
	 * @param eventName
	 */
	public void deleteRecurringEvent(String eventName) {

		Iterator<Event> itr = this.events.iterator(); 
		while (itr.hasNext()) { 
			Event eachEvent = itr.next(); 
			if (eachEvent.getEventName().equalsIgnoreCase(eventName) && eachEvent.isRecurring()) {
				itr.remove();
			}
		}
	}

	/**
	 * Save events to a file
	 */
	public void saveEvent() {
		try {
			File newTextFile = new File("output.txt");

			FileWriter fw = new FileWriter(newTextFile);
			for (Event event : this.events) {
				fw.write(event.getEventName() + " " + event.getTimeInterval().getStartDateTime() + " -> "
						+ event.getTimeInterval().getEndDateTime() + "\n");
			}

			fw.close();

		} catch (IOException iox) {
			// do stuff with exception
			iox.printStackTrace();
		}

	}

	/**
	 * Show events
	 */
	public void showEvent() {
		List<Event> oneTimeEvents = new ArrayList<Event>();
		List<Event> recurringEvents = new ArrayList<Event>();
				
		for (Event event : this.events) {
			if (event.isRecurring()) {
				recurringEvents.add(event);
			}else {
				oneTimeEvents.add(event);
			}
		}
		
		System.out.println("ONE TIME EVENTS\n");
		for (Event event : oneTimeEvents) {
			System.out.println(event.getEventName() + " " + event.getTimeInterval().getStartDateTime() + " -> "
					+ event.getTimeInterval().getEndDateTime());
		}
		
		System.out.println("\nRECURRING EVENTS\n");
		for (Event event : recurringEvents) {
			System.out.println(event.getEventName() + " " + event.getTimeInterval().getStartDateTime() + " -> "
					+ event.getTimeInterval().getEndDateTime());
		}

	}
}
