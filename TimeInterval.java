
import java.time.Instant;
import java.util.Date;

public class TimeInterval {
	private Date startDateTime;
	private Date endDateTime;
	
	
	public boolean isOverlap(TimeInterval interval1, TimeInterval interval2) {
		//TODO: check if the 2 given Interval is being Overlap
		return true;
	}


	public Date getStartDateTime() {
		return startDateTime;
	}


	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}


	public Date getEndDateTime() {
		return endDateTime;
	}


	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}


	


	
	
}
