package ThirstyPerson;
/**
 * The main method of this class plays out a scenario...
 * Jack has two cans of soda, RootBeer and GingerAle, 
 * Jill also has two cans, Cherry and Grape.
 * Jack opens his RootBeer first, and gives it to Jill.
 * Jill completely drinks the RootBeer. 
 * Jack asks her if she is still thirsty, Jill responds. 
 * Now Jill opens her Cherry soda and drinks it until
 * she is satisfied, then gives it to Jack. 
 * He takes a sip, but doesn't like it. 
 * Jill checks how much is left in her Cherry soda, but decides
 * not to drink any more. Jack asks Jill if he can try her Grape soda.
 * Jill gives it to Jack and he opens it.
 * Jack drinks about half of it, then stops and tells Jill how he now feels.
 * Finally, they check the amount available in all of the cans.
 */

public class Scenario {

	public static void main(String[] args){
		Person jack = new Person("Jack");
		Person jill = new Person("Jill");
		SodaCan rootS = new SodaCan("RootBeer");
		SodaCan gingerS = new SodaCan("GingerAle");
		SodaCan cherryS = new SodaCan("Cherry");
		SodaCan grapeS = new SodaCan("Grape");
		System.out.println("Jack has two cans of soda, RootBeer and GingerAle");
		System.out.println("Jill has two cans of soda, Cherry and Grape");
		rootS.open();
		System.out.println("Jack opens the RootBeer and gives it to Jill");
		while (rootS.amount !=0){
			jill.gulpFrom(rootS);
		}
		System.out.println("Jack asks if jill is still thirsty, Jill says she is " + jill.getThirstStatus());
		cherryS.open();
		while (jill.getThirstStatus() != "satisfied"){
			jill.sipFrom(cherryS);
		}
		System.out.println("Jill opens the cherry soda and drinks until she is satisfied. She gives it to jack");
		jack.sipFrom(cherryS);
		System.out.println("Jack takes a sip and doesn't like it");
		System.out.println("Jill checks how much is in the cherry can, " + cherryS.amount + ", but does not drink it");
		grapeS.open();
		while (grapeS.amount >= 250/2){
			jack.sipFrom(grapeS);
		}
		System.out.println("Jack opens Jills grape soda and drinks half and says he is " + jack.getThirstStatus());
		System.out.println("RootBeer has " +rootS.amount + " cc left");
		System.out.println("GingerAle has " +gingerS.amount + " cc left");
		System.out.println("Cherry has " +cherryS.amount + " cc left");
		System.out.println("Grape has " +grapeS.amount + " cc left");
			
	}
			
		
		
		
	}
