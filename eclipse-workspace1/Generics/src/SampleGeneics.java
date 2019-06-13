import java.util.ArrayList;

public class SampleGeneics {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		
		
		
		//using generic
		ArrayList<String> abc =new ArrayList<String>();
		abc.add("Use generics");
		String get1= abc.get(0);
	    System.out.println(" " +get1);
	    
	    
	    
	    
		//without generic
	     ArrayList pqr = new ArrayList();
		pqr.add("use non generics");
		String get = (String) pqr.get(0);
		System.out.println("" + get);
		
		
		
		
	}
	
	
	
	
	
	
}
