package uk.ac.liv.comp201;

import static uk.ac.liv.comp201.ResponseCode.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Card {
	
	private static final int CARD_ID_LENGTH=9;
	private static final int CARD_OK=1;
	private static final int CARD_BLOCKED=2;
	/*
	 * CARD_NEW is a card with no fire code or burglar code
	 */
	private static final int CARD_NEW=3;
	
/**
 * The fire code must be between 10 and 14 characters
 * It is made up of alphanumeric characters only 	
 */
	private String cardFireCode="123";
/**
  * The burglary code must be between 8 and 10 characters
  * It is made up of numeric digits only 0 to 9 	
*/
	private String cardBurlaryCode="123";
/* If a CARD_STATUS = CARD_NEW   doing any form of authentication
 * (checkFireCode or checkBurglaryCode)
 * will throw the CardException exception, with  CARD_STATUS_BAD 
 */
	private int cardStatus=CARD_NEW;
	private int wrongFirePassword = 0;
	private int wrongBurglaryPassword = 0;
	
	/**
	 * User of card, this is a alpha string 9 characters long
	 * The id is case insensivite so SEBCOOPET = sebcoopet
	 */
	private String cardUsername="";

	
	public Card(String cardUsername) throws CardException {
		checkCardName(cardUsername);
		this.cardUsername=cardUsername;
	}
	
	 



	private void checkCardName(String cardUsername) throws CardException {
		if (cardUsername.length()!=CARD_ID_LENGTH) {
			throw new CardException(ResponseCode.INVALID_CARD_ID_LENGTH);			
		}
		if (!cardUserNameValid(cardUsername)) {
			throw new CardException(ResponseCode.INVALID_CARD_ID);			
		}
	}
	
	
	
	public static void createNewCard(String cardUsername) throws CardException {
		Card card=new Card(cardUsername);
		card.saveCard();
	}
		
	
	private boolean cardUserNameValid(String cardUsername) {
		boolean returnValue=true; // default to card is ok
		for (int idx=0;idx<cardUsername.length();idx++) {
			if (!Character.isAlphabetic(cardUsername.charAt(idx))) {
				returnValue=false;break;	// oops bad character
			}
		}
		return(returnValue);
	}
	
	
	private void saveCard() {
		try {
		      FileWriter fileWriter = new FileWriter(cardUsername);
		      fileWriter.write(cardFireCode+"\n");
		      fileWriter.write(cardBurlaryCode+"\n");
		      fileWriter.write(""+cardStatus+"\n");
			  fileWriter.write(wrongFirePassword+"\n");
			  fileWriter.write(wrongBurglaryPassword+"\n");
		      fileWriter.close();
		    } catch (IOException e) {
		 }
	}
	
	public static Card loadCard(String cardUsername) throws CardException {
		Card card=new Card(cardUsername);
		try {
		      File file = new File(cardUsername);
		      Scanner myReader = new Scanner(file);
		      if (myReader.hasNextLine()) {
		         card.cardFireCode = myReader.nextLine();		        
		      }
		      if (myReader.hasNextLine()) {
			     card.cardBurlaryCode = myReader.nextLine();		        
			  }
		      if (myReader.hasNextLine()) {
				 card.cardStatus =Integer.parseInt(myReader.nextLine());
		      }
			  if (myReader.hasNextLine()) {
				  card.wrongFirePassword = Integer.parseInt(myReader.nextLine());
			  }
			if (myReader.hasNextLine()) {
				card.wrongBurglaryPassword = Integer.parseInt(myReader.nextLine());
			}
		      myReader.close();
		    } catch (FileNotFoundException e) {
		    	throw new CardException(CARD_NOT_FOUND,cardUsername);
		    }
			return(card);
		  }



	public String getCardFireCode() {
		return cardFireCode;
	}



	private void setCardFireCode(String cardFireCode) throws CardException {

		if (cardFireCode.length() < 10 || cardFireCode.length() > 14){
			throw new CardException(ResponseCode.INVALID_FIRE_CODE);
		}

		for (int idx=0; idx < cardFireCode.length(); idx++){
			if(!Character.isAlphabetic(cardFireCode.charAt(idx))&&!Character.isDigit(cardFireCode.charAt(idx))){
				throw new CardException(ResponseCode.INVALID_FIRE_CODE);
			}
		}
		// TO DO
		// add in validation, throw Exception if 
		// code is invalid
		this.cardFireCode = cardFireCode;
	}



	public String getCardBurlaryCode() {
		return cardBurlaryCode;
	}



	private void setCardBurlaryCode(String cardBurglaryCode) {
		this.cardBurlaryCode = cardBurlaryCode;
	}
	
	public void setCodes(String cardFireCode,String cardBurglaryCode) throws CardException {
		setCardFireCode(cardFireCode);
		setCardBurlaryCode(cardBurglaryCode);
		cardStatus=Card.CARD_OK;
	}

	public int getWrongFirePassword() {
		return wrongFirePassword;
	}

	public void setWrongFirePassword(int wrongFirePassword) {
		this.wrongFirePassword = wrongFirePassword;
	}

	public int getWrongBurglaryPassword() {
		return wrongBurglaryPassword;
	}

	public void setWrongBurglaryPassword(int wrongBurglaryPassword) {
		this.wrongBurglaryPassword = wrongBurglaryPassword;
	}

	public int getCardStatus() {
		return cardStatus;
	}



	public void setCardStatus(int cardStatus) {
		this.cardStatus = cardStatus;
		saveCard();
	}
}
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                佛祖保佑                  BUG退散


