import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event{
	private String eventName;
	private TimeInterval timeInterval;
	private boolean recurring;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public TimeInterval getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(TimeInterval timeInterval) {
		this.timeInterval = timeInterval;
	}

	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	/**
	 * Loads all the events from event.txt
	 * 
	 * @param eventsFile
	 * @return
	 */
	public static List<Event> loadEvents(String eventsFile) {
		List<Event> events = new ArrayList<Event>();
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy HH:mm");

		try (BufferedReader br = new BufferedReader(new FileReader(eventsFile))) {
			String eventName;

			while ((eventName = br.readLine()) != null) {
				String schedule = br.readLine();
				//System.out.println(schedule);
				String[] scheduleTokens = schedule.split(" ");
				if (scheduleTokens[0].contains("/")) {
					//Non-Recurring event ----- 9/14/21 11:00 15:00
					Event event = new Event();
					event.setEventName(eventName);
					TimeInterval timeInterval = new TimeInterval();
					event.setRecurring(false);
					String strDate = scheduleTokens[0]+" "+scheduleTokens[1];
					Date startDate=sdf.parse(strDate);
					timeInterval.setStartDateTime(startDate);
					strDate = scheduleTokens[0]+" "+scheduleTokens[2];
					Date endDate=sdf.parse(strDate);
					timeInterval.setEndDateTime(endDate);
					event.setTimeInterval(timeInterval);
					events.add(event);
				} else {
					//Parse schedule line and set timeInterval
					//Recurring event ----  TR 10:30 11:45 8/19/21 12/6/21
					String daysStr = scheduleTokens[0];
					//Iterate each weekday to create the events
					for(int i=0; i<daysStr.length(); i++) {
						String weekday = String.valueOf(daysStr.charAt(i));
						//Find total week days between date range
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
					    LocalDate startDate = LocalDate.parse(scheduleTokens[3], formatter);
					    LocalDate endDate = LocalDate.parse(scheduleTokens[4], formatter);
					    //Iterate through event start date and end date and find all days of the week matching with weekday variable
					    for (LocalDate lDate = startDate; lDate.isBefore(endDate); lDate = lDate.plusDays(1))
					    {
					    	if(lDate.getDayOfWeek().name() == DaysOfWeek.get(weekday).name()) {
					    		//System.out.println(lDate.getDayOfWeek().name()+ " "+ lDate.toString());
					    		
					    		//Create New Event and TimeInterval
					    		Event event = new Event();
								event.setEventName(eventName);
								event.setRecurring(true);
								TimeInterval timeInterval = new TimeInterval();
								
								//Set Event Start Date and Time
					    		String strDate = lDate.format(formatter) + " " + scheduleTokens[1];
					    		Date eventStartDate=sdf.parse(strDate);
								timeInterval.setStartDateTime(eventStartDate);
								//Set Event End Date and Time
								strDate = lDate.format(formatter) + " " + scheduleTokens[2];
					    		Date eventEndDate=sdf.parse(strDate);
								timeInterval.setEndDateTime(eventEndDate);
								
								event.setTimeInterval(timeInterval);
								events.add(event);
					    	}
					    }
					}
					
				}
				
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return events;
	}
	
	

}
