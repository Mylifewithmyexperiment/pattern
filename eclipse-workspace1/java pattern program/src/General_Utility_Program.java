import java.util.Scanner;

public class General_Utility_Program {
	static Scanner scanner;
	public static void main(String[] args) {
		
		  scanner = new Scanner(System.in);
		
	//	program1();
	//	program2();
	//	program3();//check
		program4();
		
		program5();
		
		program6();
		program7();
		progran8();
		program9();
		program10();
		scanner.close();
		
	}

	private static void program10() {
		// TODO Auto-generated method stub
		
	}

	private static void program9() {
		// TODO Auto-generated method stub
		
	}

	private static void progran8() {
		// TODO Auto-generated method stub
		
	}

	private static void program7() {
		// TODO Auto-generated method stub
		
	}

	private static void program6() {
		// TODO Auto-generated method stub
		
	}

	private static void program5() {
		// TODO Auto-generated method stub
		
	}

	private static void program4() {
		// TODO Auto-generated method stub
		
	}

	private static void program3() {
		// TODO Auto-generated method stub
		System.out.println("Enter the number  .");
	final	int n = scanner.nextInt();
	int k= n/2;
	for (int i = 2; i < n; i++) {
		System.out.println("inside the loop");
		if (n%i==0) {
			System.out.print("No is not prime");
			 
		}
		 
	}
	 
		
	}

	private static void program2() {
		// TODO Auto-generated method stub
		System.out.println("Enter the number ");
		Scanner sc = new Scanner(System.in);
	  // int a = sc.nextInt();
	   long a = (long) sc.nextDouble();
	  long c=1;
	  for (long i = a; i >1; i--) {
		c=c*i;
		  
	}System.out.print(c);
	
		
		
	}

	private static void program1() {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number");
		int a= scanner.nextInt();
		if(a%2==0)
		{
			System.out.println("Even no");
		}
		else
		{
			System.out.println("odd no");
		}
		
		
		
	}
	
	
	
}
