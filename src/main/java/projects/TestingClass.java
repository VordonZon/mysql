package projects;

import java.util.Scanner;

public class TestingClass {
	
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter a number");
		String input = scanner.nextLine();
		int num = Integer.valueOf(input);
		System.out.println(num);
	}
}
