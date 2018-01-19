package gofish;

public class Card {
	String suit;
	int value;
	
	//default constructor sets card to value=0 and suit=""
	public Card(){
		this.suit="";
		this.value=0;
	}
	
	//toString (working)
	public String toString(){
		String temp = "";
		if (value == 13){
			temp = temp + "King ";
		}		 
		else if(value == 12){
			temp = temp + "Queen ";
		}
		else if(value == 11){
			temp = temp + "Jack ";
		}
		else if(value == 1) {
			temp = temp + "Ace ";
		}
		else{
			temp = temp + value + " ";
		}
		temp = temp + "of " + suit;
		return (temp);
	}

	//copy (creates copy of itself) (working)
	//proper utilisation:
	/*
	 * Card newOne = new Card();
	 * newOne = test.Copy();
	*/
	public Card copy(){
		Card copied = new Card();
		copied.setSuit(this.suit);
		copied.setValue(this.value);
		return copied;
	}
	
	//get Suit (working)
	public String getSuit(){
		return (this.suit);
	}
	
	//get Value (working)
	public int getValue(){
		return (this.value);
	}
	
	//set Suit (working)
	public void setSuit(String suit){
		this.suit = suit;
	}
	
	//set Value (working)
	public void setValue(int value){
		this.value = value;
	}

}
