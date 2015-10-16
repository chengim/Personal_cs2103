package memori;

import java.util.Scanner;

public class MemoriUI {
	Scanner sc = new Scanner(System.in);
	
	public void displayToUser(String msg){
		StringBuilder sb = new StringBuilder();
		sb.append(msg);
		int i = 0;
		while((i = sb.indexOf(" ", i + 30)) != -1){
			sb.replace(i, i + 1, "\n");
		}
		sb.toString();
		System.out.println(sb);
	}
	
	public String takeInput(){
		return sc.nextLine();
	}
}
