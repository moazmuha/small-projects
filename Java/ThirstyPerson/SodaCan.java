package ThirstyPerson;
/**
 * Capture a Can of Soda.
 * A Can of Soda has a type, amount (initially 250) and is initially closed.
 * Once opened, you can sip (take at most 10) or gulp (take at most 50) from
 * the can. Obviously, at all times, the amount of soda in the can is between 0 and 250.
 * An opened can can not be closed.
 */
public class SodaCan {
	int amount = 250;
	int state = -1;
	String type;
	
	
	/**
	 * Construct a new SodaCan of the specified type.
	 * The new can has 250 units in it, and is closed.
	 * @param t the type of soda, for example "RootBeer", "Coke", "Cherry"
	 */
	public SodaCan(String type){
		this.type = type;
	}

        /**
         * open this SodaCan
         */
	public void open(){
		this.state = 1;
	}

	/**
	 * @return whether this is open
	 */
	 public boolean isOpen(){
		 return this.state == 1;
	 }
	
        /**
         * remove up to 10cc of soda from this, provided this is open
         * @return the amount of soda actually removed
         */
	public int sip(){
		if (this.state == 1&this.amount >= 10){
			this.amount -= 10;
			return 10;
		}else if (this.state == 1){
			int temp = this.amount;
			this.amount = 0;
			return temp;
		}
		return 0;	
	}

        /**
         * remove up to 50cc of soda from this, provided this is open
         * @return the amount of soda actually removed
         */
	public int gulp(){
		if (this.state == 1&this.amount >= 50){
			this.amount -= 50;
			return 50;
		}else if (this.state == 1){
			int temp = this.amount;
			this.amount = 0;
			return temp;
		}
		return 0;	
	}

        /**
         * @return the amount of soda left in this
         */
	public int getAmount(){
		return this.amount;
	}

        /**
         * @return a string representation of this
         */
        public String toString(){
		String openString = (this.isOpen())?"open":"closed";
		return this.type+" "+openString+" "+this.amount;
        }
	
	public static void main(String [] args){
		SodaCan s1 = new SodaCan("RootBeer");
		SodaCan s2 = new SodaCan("Coke");
		SodaCan s3 = new SodaCan("Cherry");
		s1.open();
		s2.open();
		s3.open();
		s1.sip();
		s2.sip();
		s3.sip();
		s1.gulp();
		s2.gulp();
		s3.gulp();
	}
}
