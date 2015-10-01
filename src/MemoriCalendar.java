package memori;

import java.util.ArrayList;

public class MemoriCalendar {
	private static final String MESSAGE_ADD = "adding";
	private static final String MESSAGE_DELETE = "deleting\n";
	private static final String MESSAGE_UPDATE = "updating";
	private static final String LINE_INDEX_DOES_NOT_EXISTS = "Line index does not exists\n";
	private static final String MESSAGE_EMPTYFILE = "File is Empty.\n";
	
	private ArrayList<MemoriEvent>  memoriCalendar;
	//added location/description/priority
	private static final String HEADER ="Name of Event:    Start:    End:    Location:    Description:    Priority:\n";
	
	
	public MemoriCalendar(){
		memoriCalendar = new ArrayList<MemoriEvent>();
	}
	public void add(MemoriCommand command){
		MemoriEvent event = new MemoriEvent(command.getName(),command.getStart(),command.getEnd(),
					command.getInternalId(), command.getExternalId(), command.getDescription());
		System.out.println(MESSAGE_ADD);
		memoriCalendar.add(event);
	}
	//What about priority/location? What is the difference between internalId and externalId?
	
	public String display(){
		String output = HEADER;	
			for(MemoriEvent e: memoriCalendar){
				output+= e.display() + "\n";
			}
		return output;
	}
	public void execute(MemoriCommand command) {
			switch(command.getType()){
			case ADD:
				add(command);
				break;
			case UPDATE:
				update(command);
				break;
			case DELETE:
				delete(command);
				break;
			}
		
	}
	private void delete(MemoriCommand command) {
		memoriCalendar.display();
		String deleteText;
			if(memoriCalendar.isEmpty()){
				System.out.println(MESSAGE_EMPTYFILE);
			}else{
				int index = Integer.parseInt(command.getIndex());
				//to implement getIndex in MemoriCommand that returns a String
				
				if(memoriCalendar.size() < index){
					System.out.println(LINE_INDEX_DOES_NOT_EXISTS);	
				}else{
					deleteText = memoriCalendar.get(index - 1);
					memoriCalendar.remove(index - 1);
					memoriCalendar.trimToSize();
					System.out.println(MESSAGE_DELETE + deleteText);	
				}
	
			}

		
	}
	private void update(MemoriCommand command) {
		memoriCalendar.display();
			if(memoriCalendar.isEmpty()){
				System.out.println(MESSAGE_EMPTYFILE);
			}else{
				int index = Integer.parseInt(command.getExternalCalId());
				
				if(memoriCalendar.size() < index){
					System.out.println(LINE_INDEX_DOES_NOT_EXISTS);
				}else{
					originalEvent = memoriCalendar.get(index - 1);
					MemoriEvent eventUpdate = originalEvent.update(command.getName(),
								command.getStart(), command.getEnd(), command.getDescription());
					System.out.println(MESSAGE_UPDATE);
					memoriCalendar.set(eventUpdate);
				}
			}
		
	}
}
