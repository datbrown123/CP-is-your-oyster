public class MapEvent{
	
	public String eventName;
	public String description;
	public double latitude;
	public double longitude;
	public String locationName;
	public String time;
	
	public MapEvent(String en, String d, double lat, double lon, String ln, String t){
		eventName=en;
		description=d;
		latitude=lat;
		longitude=lon;
		locationName=ln;
		time=t;
	}	
}