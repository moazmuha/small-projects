package ThirstyPerson;

/**
 * Capture a person who can drink soda.
 * A person can sip or gulp from a can of soda. 
 * The person knows how much soda they have consumed,
 * and know how thirsty they are.
 */
public class Person {
	String name;
	String thirstStatus = "very thirsty";
	int amountDrunk = 0;
	/**
	 * Construct a new Person with the given name.
	 * So far they will have consumed 0 soda
	 * @param name the name given to this person
	 */
	public Person(String name){
		this.name = name;
	}

	/**
	 * Take a sip from s, modifying the amount this has consumed
	 * @param s the can of soda this will gulp from
	 */
	public void sipFrom(SodaCan s){
		amountDrunk += s.sip();
	}

	/**
	 * Take a gulp from s, modifying the amount this has consumed
	 * @param s the can of soda this will gulp from
	 */
	public void gulpFrom(SodaCan s){
		amountDrunk += s.gulp();
	}	 

	/**
	 * A person is 
 	 * "very thirsty" if they drank less than 175,
 	 * "thirsty" if they drank less than 375, 
 	 * "satisfied" if they drank at least 375
	 * @return the thirst status of this
	 */
	public java.lang.String getThirstStatus(){
		if (amountDrunk < 175){
			return this.thirstStatus;
		}else if (amountDrunk < 375){
			this.thirstStatus = "thirsty";
		}else{
			this.thirstStatus = "satisfied";
		}
		return this.thirstStatus;
	}
	/**
	 * @return a string representation of this
	 */
	public String toString(){
		return "I am "+this.name+", and I am "+this.getThirstStatus();
	}
}

