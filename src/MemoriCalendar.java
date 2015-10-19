package memori;

import java.util.ArrayList;
import java.util.Collections;

public class MemoriCalendar {
	private static final String MESSAGE_ADD = "Added.\n";
	private static final String MESSAGE_DELETE = "Deleted.\n";
	private static final String MESSAGE_UPDATE = "Updated: \n";
	private static final String MESSAGE_READ = "Reading: \n"; 
	private static final String MESSAGE_SORT = "Sorted.\n";
	private static final String MESSAGE_CLEAR = "Task List cleared. List is now empty.\n";
	private static final String LINE_INDEX_DOES_NOT_EXISTS = "Line index does not exists.\n";
	private static final String MESSAGE_EMPTYFILE = "List is Empty.\n";
	private static final String MESSAGE_INVALID_KEYWORD = "Keyword not found.\n";
	
	private static final String EMPTY_STRING = "";
	
	private ArrayList<MemoriEvent>  memoriCalendar;
	private int maxId = 0;
	private boolean maxIdSet = false;
	//added location/description/priority
	private static final String HEADER ="No: Name of Event:    Start:    End:\n";
	
	
	public MemoriCalendar(){
		memoriCalendar = new ArrayList<MemoriEvent>();
	}
	private void findMaxId(){
		for(MemoriEvent e: memoriCalendar){
			int internalId = e.getInternalId();
			if(internalId > maxId){
				maxId = internalId;
			}
			
		}
		maxIdSet = true;
	}
	
	public String add(MemoriCommand command){
		if(maxIdSet == false)
			findMaxId();
		maxId++;
		MemoriEvent event = new MemoriEvent(command.getName(),command.getStart(),command.getEnd(),
									maxId,"google",command.getDescription(), command.getLocation());
		
		memoriCalendar.add(event);
		return MESSAGE_ADD;
	}
	//What about priority/location? What is the difference between internalId and externalId?
	
	public String display(){
		String output;
		int i=1;
			for(MemoriEvent e: memoriCalendar){
				output = i + " " +e.display() + "\n";
				i++;
			}
		return output;
	}
	
	public String execute(MemoriCommand command){
			switch(command.getType()){
			case ADD:
				return add(command);
			case UPDATE:
				return update(command);
			case DELETE:
				return delete(command);
			case READ:
				return read(command);
			case SORT:
				return sort(command);
			case SEARCH:
				return search(command);
			case CLEAR:
				return clear(command);
			case INVALID:
				return MESSAGE_INVALID;
			default:
				assert false: command.getType();//code should never reach here
				return "";
			}
		
	}
	private String read(MemoriCommand command){
		MemoriEvent displayText;
			if(memoriCalendar.isEmpty()){
				return MESSAGE_EMPTYFILE;
			}else{
				int index = command.getIndex();
			
				if(memoriCalendar.size() < index){
					return LINE_INDEX_DOES_NOT_EXISTS;
				}else{
					displayText = memoriCalendar.get(index - 1);
					return String.format(MESSAGE_READ, displayText.read());//location and description fields.
				}
			}	
	}
	private String delete(MemoriCommand command){
		MemoriEvent deleteText;
			if(memoriCalendar.isEmpty()){
				return MESSAGE_EMPTYFILE;
			}else{
				int index =  command.getIndex();
				//to implement getIndex in MemoriCommand that returns a String
				
				if(memoriCalendar.size() < index){
					return LINE_INDEX_DOES_NOT_EXISTS;	
				}else{
					deleteText = memoriCalendar.get(index - 1);
					memoriCalendar.remove(index - 1);
					return String.format(MESSAGE_DELETE, deleteText);	
				}
	
			}

		
	}
	private String update(MemoriCommand command){
		MemoriEvent originalEvent;
			if(memoriCalendar.isEmpty()){
				return MESSAGE_EMPTYFILE;
			}else{
				int index = command.getIndex();
				
				if(memoriCalendar.size() < index){
					return LINE_INDEX_DOES_NOT_EXISTS;
				}else{
					originalEvent = memoriCalendar.get(index - 1);
					originalEvent.update(command.getName(),
								command.getStart(), command.getEnd(), 
									command.getDescription(), command.getLocation());
					return String.format(MESSAGE_UPDATE, index);
				}
			}
		
	}
	private String sort(MemoriCommand command){
		if(memoriCalendar.isEmpty()){
			return MESSAGE_EMPTYFILE;
		}else{
			Collections.sort(memoriCalendar);//sort in natural order
			return MESSAGE_SORT;
		}	
	}
	private String search(MemoriCommand command){
		String text = command.getText();//implement getText in MemoriCommand
		if(memoriCalendar.isEmpty()){
			return MESSAGE_EMPTYFILE;
		}else if(!memoriCalendar.contains(text){
			return MESSAGE_INVALID_KEYWORD;	
		}else{
			for(int i = 0; i < memoriCalendar.size(); i++){
				String line = memoriCalendar.get(i);
				
				if(line.contains(text)){
					System.out.println((i + 1) + ". "
						+ memoriCalendar.get(i));
				}
			}
			return EMPTY_STRING;
		}
	}
	private String clear(MemoriCommand command){
		if(memoriCalendar.isEmpty()){
			return MESSAGE_EMPTYFILE;
		}else{
			memoriCalendar.clear();
			return MESSAGE_CLEAR;
		}
	}
	
}
