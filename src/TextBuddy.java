/*
This class allows users to edit contents in a text file. Users can input commands
such as 'ADD', 'DELETE', 'DISPLAY', 'CLEAR', 'EXIT'. This program will auto-save
after every change user makes.

Lim Chen Gim
A0113645L

*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;

public class TextBuddy{
 
 //Messages to user after user operations
 private static final String WELCOME_MSG = "Welcome to TextBuddy.\n ";
 private static final String COMMAND_PROMPT = "Command: ";
 private static final String INVALID_COMMAND = "Invalid Command.";
 private static final String INVALID_FILE = "Invalid File.";
 private static final String UNEXPECTED_ERR = "Unexpected error. Program will terminate.";
 private static final String EXIT_MSG = "Text Buddy exited.";
 private static final String INVALID_INDEX = " Invalid index.";
 //The possible command types
 private static final String ADD = "add";
 private static final String CLEAR = "clear";
 private static final String DELETE = "delete";
 private static final String DISPLAY = "display";
 private static final String SORT = "sort";
 private static final String SEARCH = "search";
 private static final String EXIT_CMD = "exit";
 //Variables
 private static final int INVALID_ARG = 400;
 private static final int EXIT = 200;
 
 static String file_name = null;
 
 static Scanner file_scanner = null;
 static File file_entry = null;
 static Scanner stdin = new Scanner(System.in);
 static PrintWriter file_writer = null;
 
 private static ArrayList<String> listOfContents = new ArrayList<String>();
 
 private class TbException extends Exception{
  private static final long serialVersionUID = 1L;
  private int errCode;
  
  public TbException(int errCode){
   super("");
   this.errCode = errCode;
  }
  
  public int getErrCode(){
   return errCode;
  }
 }
 // SLAP is used in the main method.
 public static void main(String[] args){
  exitIfFileIncorrect(args);
  initializeCommand();
 
 }

 private static void exitIfFileIncorrect(String[] args){
  try{
   checkInput(args);
   checkAndReadFile(args[0]);
   textBuddyMainLoop();
  }catch(TbException e){ 
  }catch(IOException e){
   System.out.println(INVALID_FILE);
  }catch(Exception e){
   System.out.println(UNEXPECTED_ERR);
   
   e.printStackTrace();
  }
 }
 
 private static void checkInput(String[] args) throws TbException{
  if(args.length != 1){
   System.out.println(WELCOME_MSG);
   throw(new TextBuddy()).new TbException(INVALID_ARG); 
  }
 }
 
 private static void checkAndReadFile(String args) throws IOException{
  file_name = args;
  
  file_entry = new File(file_name);
  if(!file_entry.exists()){
   file_entry.createNewFile();
  }
  file_scanner = new Scanner(file_entry);
  
  unserializable();
  
  file_scanner.close();
 }
 
 private interface executableAction{
  public boolean executeAction(String args) throws Exception;
 };
 
 static Map<String, executableAction> commands = new HashMap<String, executableAction>();
 
 private static Map<Integer, String> execute_list = new TreeMap<Integer, String>();
 private static Integer execute_counter = 0;
 
 private static void unserializable(){
  if(file_scanner == null) return;
  
  String line = null;
  execute_counter = 0;
  
  while(file_scanner.hasNextLine() && ((line = file_scanner.nextLine()) != null)){
   execute_list.put(execute_counter ++, line);
  }
  
 }
 
 private static void serializable() throws IOException{
  file_writer = new PrintWriter(new FileWriter(file_name));
  
  for(Map.Entry<Integer, String> element: execute_list.entrySet()){
   file_writer.println(element.getValue());
  }
  
  file_writer.close();
 }
 
 private static void addAndSave(String action) throws IOException{
  execute_list.put(execute_counter ++, action);
  
  serializable();
 }
 
 private static void deleteByIndexAndSave(int index) throws IOException{
  int counter = 0;
  Integer delete = 0;
  
  for(Map.Entry<Integer, String> element : execute_list.entrySet()){
   ++ counter;
   
   if(counter == index){
    delete = element.getKey();
   }
  }
  
  System.out.println("deleted from " + file_name + ": \"" + 
  execute_list.get(delete) + "\"");
  execute_list.remove(delete);
  
  serializable();
 }
 
 private static void clearAndSave() throws IOException{
  execute_list.clear();
  serializable();
 } 
 
 
// Commands input by user would be processed accordingly when file is correct. 
 private static void initializeCommand(){
  commands.put(ADD, new executableAction(){
   public boolean executeAction(String args) throws IOException{
    addAndSave(args);
    System.out.println(" added to " + file_name + ": \"" + args + "\"");
    
    return false;
   }
  });
  
  commands.put(DISPLAY, new executableAction(){
   public boolean executeAction(String args){
    if(execute_list.size()>0){
     Integer counter = 1;
     for(Map.Entry<Integer, String> element : execute_list.entrySet()){
      System.out.println(" " + (counter ++) + ". " + element.getValue());
     }
    }else{
     System.out.println(" " + file_name + " is empty");
    }
    
    return false;
   }
  });
  
  commands.put(DELETE, new executableAction(){
   public boolean executeAction(String args) throws IOException{
    try{
     int index = Integer.parseInt(args);
     if(index >= 1 && index <= execute_list.size()){
      deleteByIndexAndSave(index);
     }else{
      System.out.println(INVALID_INDEX); 
     }
     return false;
    }catch(NumberFormatException e){
     
    }
    System.out.println(INVALID_INDEX);
    
    return false;
   }
  });
  
  commands.put(CLEAR, new executableAction(){
   public boolean executeAction(String args) throws IOException{
    clearAndSave();
    System.out.println(" all content deleted from " + file_name);
    return false;
   }
  });
  
  commands.put(SORT, new executableAction(){
   public boolean executeAction(String args){
    if(execute_list.size()>0){
     Integer counter = 1;
     String text;
     for(Map.Entry<Integer, String> element : execute_list.entrySet()){
      text = element.getValue();
      listOfContents.add(text);
     }
     Collections.sort(listOfContents);
     execute_list.clear();
     for(String collections: listOfContents){
      execute_list.put(execute_counter ++, collections);
     }
     System.out.println(file_name + " is sorted in alphabetical order.");
    }else{
     System.out.println(" " + file_name + " is empty");
    }
    
    return false;
   }
  });
  
  commands.put(EXIT_CMD, new executableAction(){
   public boolean executeAction(String args){
    System.out.println(EXIT_MSG);
    return true;
   }
  });
 }
 
 private static void textBuddyMainLoop() throws Exception{
  String command = null;
  String arg = null;
  boolean terminate = false;
  
  while(terminate == false){
   System.out.println(COMMAND_PROMPT);
   
   if(stdin.hasNext() == false) break;
   String line = stdin.nextLine();
   
   String[] tokens = line.split("\\s+", 2);
   arg = (tokens.length > 1 ? tokens[1] : "");
   command = tokens[0];
   
   if(commands.containsKey(command)){
    terminate = commands.get(command).executeAction(arg);
   }else{
    System.out.println(INVALID_COMMAND);
   }
  }
 }
}
