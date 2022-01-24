import java.util.HashMap;
import java.util.Map;

public enum DaysOfWeek {

	SUNDAY("S"), MONDAY("M"), TUESDAY("T"), WEDNESDAY("W"), THURSDAY("R"), FRIDAY("F"), SATURDAY("A");

	private final String abbreviation;

    // Reverse-lookup map for getting a day from an abbreviation
    private static final Map<String, DaysOfWeek> lookup = new HashMap<String, DaysOfWeek>();

    static {
        for (DaysOfWeek d : DaysOfWeek.values()) {
            lookup.put(d.getAbbreviation(), d);
        }
    }

    private DaysOfWeek(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static DaysOfWeek get(String abbreviation) {
        return lookup.get(abbreviation);
    }

	@Override
	public String toString() {
		return abbreviation;
	}
}
