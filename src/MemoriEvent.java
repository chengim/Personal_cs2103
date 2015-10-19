package memori;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemoriEvent {
	
	private static final int NAMECUTOFF = 20;
	public static final int INTERNAL_ID_WILDCARD = -1;
	private static final String[] ATTRIBUTESNAMES = {"name","start","end","internalId","description","externalCalId",};
	private static final String HEADER_READ = "Name of Event:%1$s\nStart:%2$s\nEnd:%3$s\nDescription:%4$s\nLocation:%5$s\n";
	private static final String HEADER_DISPLAY ="No:%1$s  Name of Event:%2$s	Start:%3$s		End:%4$s\n";
	
	private String name;
	private String description;
	private String location;
	private String externalCalId;
	private Date start;
	private Date end;
	private int internalId;
	
	
	
	public MemoriEvent(String name,Date start,Date end,int internalId,
			String externalCalId, String description,String Location){
		this.name = name;
		this.start = start;
		this.end = end;
		this.internalId = internalId;
		this.externalCalId = externalCalId;
		this.description = description;
		this.location = location;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getExternalCalId(){
		return externalCalId;
	}
	
	public int getInternalId(){
		return internalId;
	}
	
	public Date getStart(){
		return start;
	}
	
	public Date getEnd(){
		return end;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setExternalCalId(String id) {
		this.externalCalId = id;
		
	}
	
	public void update(String name,Date start, Date end, String description){	
		if(!name.isEmpty())
			this.name = name;
		if(start !=null)
			this.start = start;
		if(end != null)
			this.end = end;
		if(!name.isEmpty())
			this.description= description;
	}
	
	public String read(){
		return String.format(HEADER_READ, name, start, end, description, location);
	}
	public String display(){
 		if(this.name.length() > NAMECUTOFF){
 			return String.format(HEADER_DISPLAY, this.name.substring(0,NAMECUTOFF -1), start, end);
 		}else{	
			return String.format(HEADER_DISPLAY, name, start, end);
 		}	
	}
	
	public String toJson(){
		  GsonBuilder builder = new GsonBuilder();
	      Gson gson = builder.create();
	      return gson.toJson(this);
	}
	
	public static MemoriEvent fromJSON(String json){
		MemoriEvent event = new Gson().fromJson(json, MemoriEvent.class);
		return event;
	}
	
	public boolean equals(Object obj){
		 if (obj == null) {
		        return false;
		    }
		 else if(!(obj instanceof MemoriEvent)){
			return false;
		 }
		 else{
			MemoriEvent other = (MemoriEvent) obj;
			if(this.internalId != other.getInternalId())
				return false;
			else if(!this.name.equals(other.getName()))
				return false;
			else if(!this.description.equals(other.getDescription()))
				return false;
			else if(!this.start.equals(other.start))
				return false;
			else if(!this.end.equals(other.end))
				return false;
			else
				return true;
		 }
	}

	

	
}
