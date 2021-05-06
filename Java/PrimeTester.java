public class PrimeTester{    
	public static void main(String[] args){  
		isPrime(Integer.parseInt(args[0]));
		
	}
	public static void isPrime(int n){
		int i;
		boolean check = true;
		
		if(n==0||n==1||n<2){  
			System.out.println(n+" is not prime");      
		}
		
		else{  
		
		for(i=2;i<n;i++){      

				if(n%i==0){      
					System.out.println(n+" is not prime");      
					check=false;      
					break;      
				}      
		}      

		if(check){ 
					System.out.println(n+" is prime number"); 
		}  
		}
	}
} 