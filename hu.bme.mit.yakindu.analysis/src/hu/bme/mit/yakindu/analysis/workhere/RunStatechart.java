package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		s.runCycle();
		Scanner sc=new Scanner(System.in);
		String st="";
		boolean exit=false;
		while(!exit) {
			System.out.print("Event: ");
			st=sc.nextLine();
			switch (st.toLowerCase()){
			//for from here
			case "white":
				s.raiseWhite();
				break;
			case "black" :
				s.raiseBlack();
				break;
			case "start":
				s.raiseStart();
				break;
			//from here its the same every time
			case "exit":
				exit=true;
				sc.close();
				break;
			default:
				System.out.println("Invalid argument");
				break;
			}
			s.runCycle();
			print(s);
		}
		
		System.exit(0);
	}
	//from here its again depends on the model
	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
	//end of dependence
}
