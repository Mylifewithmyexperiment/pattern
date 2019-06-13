
public class All_Patterns {

	static int n= 5;
	static int m= 5;
	static int c= 10;
     
	
	public static void main(String[] args) {
		System.out.println("     ");
		pattern1();
		System.out.println("     ");
		pattern2();
		System.out.println("     ");
		pattern3();
		System.out.println("     ");

		System.out.println("     ");
		pattern6();
		System.out.println("     ");
		pattern7();
		System.out.println("     ");
		pattern10();
		System.out.println("     ");
		pattern11();
		System.out.println("     ");
		pattern12();
		System.out.println("     ");
		pattern15();
		System.out.println("     ");
		pattern16();
		System.out.println("     ");
		pattern17();
		System.out.println("     ");
		pattern20();
		System.out.println("     ");
		pattern21();
		System.out.println("     ");
		pattern24();
		System.out.println("     ");
		pattern25();
		System.out.println("     ");
		pattern26();
		System.out.println("     ");
		pattern29();
		System.out.println("     ");
		pattern30();

		pattern31();

		pattern34();

		pattern35();   // pending

		pattern36();

		pattern39();

		pattern40();

	}

	private static void pattern40() {
		// TODO Auto-generated method stub
		int blank=5;
       for (int i = 1; i < c; i=i+2) {
		for (int j = blank; j >1; j--) {
			System.out.print(" ");
		}blank--;
		for (int k = i; k>=1; k--) {
			System.out.print(k);
		}System.out.println();
    	   
	}
		
		
	}

	private static void pattern39() {
		// TODO Auto-generated method stub
		int space=5;
		for (int i = 1; i <=c ; i=i+2) {
			for (int j = 0; j < space; j++) {
				System.out.print(" ");
			}space--;
			for (int k = 1; k <=i; k++) {
				System.out.print(k);
			}System.out.println();
			
			
		}
		
		

	}

	private static void pattern36() {
		// TODO Auto-generated method stub
		int o=5;
		for (int i = 1; i < c; i=i+2) {
			//int l=i;
			for (int j = o; j>1; j--) {
				System.out.print(" ");
			}o--;
			 
			for (int k = 1; k <=i; k++) {
				System.out.print(i);
			}System.out.println();
			
		}
		

	}

	private static void pattern35() {
		// TODO Auto-generated method stub
		 
		int p=5;
		 
			
			
	 
		
		 

	}

	private static void pattern34() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= c; i = i + 2) {

			 for (int j = n; j >1; j--) {
				System.out.print(" ");
			}n--;

			for (int m = 1; m <= i; m++) {
				System.out.print("*");
			}
			System.out.println();

		}

	}

	private static void pattern31() {
		// TODO Auto-generated method stub

		for (int i = 1; i < n; i++) {

			{
				for (int j = 1; j < i; j++) {
					System.out.print(" ");
				}
				for (int k = 1; k <= n + 1 - i; k++) {
					System.out.print(k);
				}
				System.out.println();
			}
		}

	}

	private static void pattern30() {
		// TODO Auto-generated method stub

		for (int i = n; i >= 1; i--) {
			for (int j = n; j > i; j--) {
				System.out.print(" ");
			}
			for (int k = 1; k <= i; k++) {
				System.out.print(i);
			}
			System.out.println();

		}

	}

	private static void pattern29() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= n; i++)

		{
			for (int j = 1; j < i; j++) {
				System.out.print(" ");
			}
			for (int j = n; j >= i; j--) {
				System.out.print("*");
			}
			System.out.println();
		}

	}

	private static void pattern26() {
		for (int i = 1; i <= n; i++) {

			for (int j = n; j >= i; j--) {

				System.out.print(" ");
			}
			for (int j = 1; j <= i; j++) {
				System.out.print(j);
			}
			System.out.println();
		}

	}

	private static void pattern25() {
		// TODO Auto-generated method stub

		for (int i = 1; i <= n; i++) {
			for (int j = n; j > i; j--) {
				System.out.print(" ");
			}
			for (int j = 1; j <= i; j++) {
				System.out.print(i);
			}
			System.out.println();

		}

	}

	private static void pattern24() {
		// TODO Auto-generated method stub

		for (int i = 1; i <= n; i++) {
			for (int k = n; k > i; k--) {
				System.out.print(" ");
			}
			for (int j = 1; j <= i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}

	}

	private static void pattern21() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= n; i++) {
			for (int j = n; j >= i; j--) {
				System.out.print(j);
			}
			System.out.println();
		}

	}

	private static void pattern20() {
		// TODO Auto-generated method stub
		for (int i = n; i >= 1; i--) {
			for (int j = 1; j <= i; j++) {
				System.out.print(i);
			}
			System.out.println();
		}

	}

	private static void pattern17() {
		// TODO Auto-generated method stub
		for (int i = n; i >= 1; i--) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j);
			}
			System.out.println();
		}
	}

	private static void pattern16() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= n; i++) {
			for (int j = n; j >= i; j--) {
				System.out.print(i);
			}
			System.out.println();
		}

	}

	private static void pattern15() {
		// TODO Auto-generated method stub
		for (int i = 0; i < n; i++) {
			for (int j = n; j > i; j--) {
				System.out.print("*");
			}
			System.out.println();
		}

	}

	private static void pattern12() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j);
			}
			System.out.println();
		}
	}

	private static void pattern11() {
		// TODO Auto-generated method stub

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(i);
			}
			System.out.println();
		}

	}

	private static void pattern10() {
		// TODO Auto-generated method stub

		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}

	}

	private static void pattern7() {
		// TODO Auto-generated method stub
		for (int i = n; i > 0; i--) {
			for (int j = n; j > 0; j--) {
				System.out.print(j);
			}
			System.out.println();
		}

	}

	private static void pattern6() {
		// TODO Auto-generated method stub

		for (int i = n; i > 0; i--) {

			for (int j = n; j > 0; j--) {

				System.out.print(i);
			}
			System.out.println();
		}

		/*
		 * for (int i = 1; i <= n; i++) { for (int j = 0; j < n; j++) {
		 * 
		 * System.out.print(n-i+1); } System.out.println(); }
		 */

	}

	private static void pattern3() {
		// TODO Auto-generated method stub
		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= n; j++) {
				System.out.print(j);
			}
			System.out.println();
		}

	}

	private static void pattern2() {

// TODO Auto-generated method stub
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < n; j++) {

				System.out.print(i);
			}
			System.out.println();
		}
	}

	static void pattern1() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print("*");
			}
			System.out.println();
		}

	}

}
