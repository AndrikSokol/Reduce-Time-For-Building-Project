package application;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private Button findWayBtn;

    @FXML
    private TextField minCostTField;

    @FXML
    private TextField criticalWayTFied;

    @FXML
    private TextField setCriticalWayTField;

    @FXML
    private Button calculateBtn;
    
    @FXML
    private List<TextField> arrayOldTimeTField;
    
    @FXML
    private List<Label> arrayWayLabel;
    
    @FXML
    private List<TextField>arrayWayTField;
    
    @FXML
    private List<TextField> arrayCostForFirstDayTField;
    
    @FXML
    private List<TextField> arrayNewTimeTField;
    
    @FXML
    private List<TextField> arrayReduceFirstTimeTField;
    
    @FXML
    private List<TextField> arrayReduceTimeTField;
    
    @FXML
    private List<TextField> costForSecondDayTField;
    
    @FXML
    private List<TextField> arrayReduceSecondTimeTField;
    
    private String alphabet = "ABCDEFGHIJKLMNOPQRST";
    
    private List<Integer> timeList = new ArrayList<>();
    
    private List<Integer> newTimeList = new ArrayList<>();
    
    private int criticalTime =0;
    
    private int longerstCriticalTime;
    
    @FXML
    void CalculateBtn(ActionEvent event) {
    	createArrayCostForFirstDay();
    	createArrayCostForSecondDay();
    	createArrayWayValue();
    	createArrayReduceFirstTimeValue();
    	createArrayReduceSecondTimeValue();
    	createArrayReduceTimeValue();
    	int minCash =100;
    	int posWay =0,posLetterInWay =0;
    	int posLetterInAlphabet =0;
    	int posWay2 =0;
    	boolean secondDay= true;
    	while(criticalTime > getAvaliableCriticalWay() && longerstCriticalTime - criticalTime <10)
       	{ 
    		posLetterInWay =0;
    		posLetterInAlphabet =0;
        	posWay2 =0;
        	posWay =0;
    		for(int way=0;way<8;way++) {
    			minCash=100;
    			int wayValue = arrayWayValue.get(way);
				if(wayValue == criticalTime) {
					String wayText = arrayWayText.get(posWay);
					for(int i=0;i<wayText.length();i++) {
						char wayChar = wayText.charAt(i);
						outerloop:
	    				for(int q =0;q<alphabet.length();q++){
	        				char alphabetChar = alphabet.charAt(q);
	        				if(wayChar == alphabetChar) {
	        					if( minCash > arrayCostForFirstDay.get(q) && arrayCostForFirstDay.get(q)>0) {
	        						int reduceFirstTime =arrayReduceFirstTimeValue.get(q);
	        						if(reduceFirstTime ==0 ) {
	        							minCash = arrayCostForFirstDay.get(q);
		        						posLetterInWay =i;
		        						posLetterInAlphabet =q;
		        						posWay2 = posWay;
		        						secondDay = false;	
	        						}
	        					}
	        					break outerloop;
	        				}
	        			}
	    				
					}
					if(secondDay) {
						for(int i=0;i<wayText.length();i++) {
							char wayChar = wayText.charAt(i);
							outerloop1:
		    				for(int q =0;q<alphabet.length();q++){
		        				char alphabetChar = alphabet.charAt(q);
		        				if(wayChar == alphabetChar) {
		        					if( minCash > arrayCostForSecondDay.get(q) && arrayCostForSecondDay.get(q)>0) {
		        						int reduceSecondTime =arrayReduceSecondTimeValue.get(q);
		        						if(reduceSecondTime ==0 ) {
		        							minCash = arrayCostForSecondDay.get(q);
			        						posLetterInWay =i;
			        						posLetterInAlphabet =q;
			        						posWay2 = posWay;				        						
		        						}
		        					}
		        					break outerloop1;
		        				}
		        			}
						}
					}
					allCash += minCash;   
					if(secondDay) {
						arrayReduceSecondTimeValue.set(posLetterInAlphabet, 1);
						arrayReduceTimeValue.set(posLetterInAlphabet, 2);
						printArrayReduceSecondTime();
					}
					else {
						arrayReduceFirstTimeValue.set(posLetterInAlphabet, 1);
						arrayReduceTimeValue.set(posLetterInAlphabet, 1);
						printArrayReduceFirstTime();
						}
					createArrayNewTimeValue();
					printArrayReduceTime();
					printArrayNewTime();
					findWay();
		    	
				}
				secondDay = true;
				posWay++;

			}
    		criticalTime = newCriticalTime;
       	}
    	//System.out.println(posWay2+"  "+ posLetterInWay +" " + posLetterInAlphabet + " " +minCash);
    	minCostTField.setText(Integer.toString(allCash));
    }

    int allCash =0;
    void findWay() {
    	int resultTime =0;
    	for(Label elementLabel: arrayWayLabel) {
    		String wayText = elementLabel.getText();
    		for(int i=0;i<wayText.length();i++) {
    			char wayChar = wayText.charAt(i);
    			for(int q =0;q<alphabet.length();q++){
    				char alphabetChar = alphabet.charAt(q);
    				if(wayChar == alphabetChar) {
    					int newTime = arrayNewTimeValue.get(q);
    					resultTime += newTime;
    				}
    			}
    		}
    		newTimeList.add(resultTime);
    		resultTime =0;
    	}
    	int i=0;
    	newCriticalTime = 0;
    	for(TextField element:arrayWayTField){ 
    		int timevalue = newTimeList.get(i);
    		element.setText(Integer.toString(timevalue));
    		if(newCriticalTime <= timevalue) {
    			newCriticalTime = timevalue;
    		}
    		i++;
    	}
    	criticalWayTFied.setText(Integer.toString(newCriticalTime));
    	newTimeList.clear();
    	resetArrayWayValue();
    }
    private int newCriticalTime =0;
    @FXML
    void FindWayBtn(ActionEvent event) {
    	createArrayOldTimeValue();
    	int resultTime =0;
    	for(Label elementLabel: arrayWayLabel) {
    		String wayText = elementLabel.getText();
    		for(int i=0;i<wayText.length();i++) {
    			char wayChar = wayText.charAt(i);
    			for(int q =0;q<alphabet.length();q++){
    				char alphabetChar = alphabet.charAt(q);
    				if(wayChar == alphabetChar) {
    					TextField oldTimeTField = new TextField();
    					oldTimeTField = arrayOldTimeTField.get(q);
    					int time = Integer.parseInt(oldTimeTField.getText());
    					resultTime += time;
    				}
    			}
    		}
    		timeList.add(resultTime);
    		resultTime =0;
    	}
    	int i=0;
    	for(TextField element:arrayWayTField){ 
    		int timevalue = timeList.get(i);
    		element.setText(Integer.toString(timevalue));
    		if(criticalTime <= timevalue) {
    			criticalTime = timevalue;
    			 
    		}
    		i++;
    	}
    	findCriticalWay();
    	longerstCriticalTime = criticalTime;
    	findWayBtn.setDisable(true);
    	calculateBtn.setDisable(false);
    }
    
    void findCriticalWay() {
    	criticalWayTFied.setText(Integer.toString(criticalTime));
    	setAvaliableCriticalWay();
    }
    
    void setAvaliableCriticalWay() {
    	setCriticalWayTField.setText(Integer.toString(criticalTime -10));
    }
    
    int getAvaliableCriticalWay() {
    	return Integer.parseInt(setCriticalWayTField.getText());
    }
    
   
    
    
    private List<Integer> arrayCostForFirstDay = new ArrayList<>();
    void createArrayCostForFirstDay() {
    	for(TextField element: arrayCostForFirstDayTField) {
    		String text = element.getText();
    		if(text.isEmpty()) {
    			arrayCostForFirstDay.add( -1);
    		}
    		else {
    			int cash = Integer.parseInt(text);
    			arrayCostForFirstDay.add(cash);
    		}	
    	}
    }
    
    private List<Integer> arrayCostForSecondDay = new ArrayList<>();
    void createArrayCostForSecondDay() {
    	for(TextField element: costForSecondDayTField) {
    		String text = element.getText();
    		if(text.isEmpty()) {
    			arrayCostForSecondDay.add( -1);
    		}
    		else {
    			int cash = Integer.parseInt(text);
    			arrayCostForSecondDay.add(cash);
    		}	
    	}
    }
    
    private List<String> arrayWayText = new ArrayList<>();
    void createaArrayWayText() {
    	for(Label elementLabel: arrayWayLabel) {
    		arrayWayText.add(elementLabel.getText());
    	}
    }
    
    private List<Integer> arrayWayValue = new ArrayList<>();
    void createArrayWayValue() {
    	for(TextField element : arrayWayTField) {
    		arrayWayValue.add(Integer.parseInt(element.getText()));
    	}
    }
    
    void resetArrayWayValue() {
    	int i=0;
    	for(TextField element:arrayWayTField){
    		arrayWayValue.set(i,Integer.parseInt(element.getText()));
    		i++;
    	}
    }
    private List<Integer> arrayOldTimeValue = new ArrayList<>();
    void createArrayOldTimeValue() {
    	for(TextField element : arrayOldTimeTField) {
    		arrayOldTimeValue.add(Integer.parseInt(element.getText()));
    	}
    }
    
    private List<Integer> arrayReduceFirstTimeValue = new ArrayList<>();
    void createArrayReduceFirstTimeValue() {
    	for(TextField element : arrayReduceFirstTimeTField) {
    		arrayReduceFirstTimeValue.add(Integer.parseInt(element.getText()));
    	}
    }
    private List<Integer> arrayReduceTimeValue = new ArrayList<>();
    void createArrayReduceTimeValue() {
    	for(TextField  element: arrayReduceTimeTField) {
    		arrayReduceTimeValue.add(Integer.parseInt(element.getText()));
    	}
    }
    
    private List<Integer> arrayReduceSecondTimeValue = new ArrayList<>();
    void createArrayReduceSecondTimeValue() {
    	for(TextField  element: arrayReduceSecondTimeTField) {
    		arrayReduceSecondTimeValue.add(Integer.parseInt(element.getText()));
    	}
    }

    
    private List<Integer> arrayNewTimeValue = new ArrayList<>();
    void createArrayNewTimeValue(){
    	if(arrayNewTimeValue.isEmpty()) {
    		for(int i=0;i<20;i++) {
        		arrayNewTimeValue.add(arrayOldTimeValue.get(i)- arrayReduceTimeValue.get(i));
        	}
    	}
    	else {
    		for(int i=0;i<20;i++) {
    			arrayNewTimeValue.set(i,arrayOldTimeValue.get(i)- arrayReduceTimeValue.get(i));
        	}
    	}
    }
    
    void printArrayNewTime() {
    	int i=0;
    	for(TextField element: arrayNewTimeTField) {
    		element.setText(Integer.toString(arrayNewTimeValue.get(i)));
    		i++;
    	}
    }
    
    void printArrayReduceFirstTime() {
    	int f=0;
    	for(TextField element: arrayReduceFirstTimeTField) {
		element.setText(Integer.toString(arrayReduceFirstTimeValue.get(f)));
		f++;
    	}
    }
    
    void printArrayReduceSecondTime() {
    	int f=0;
    	for(TextField element: arrayReduceSecondTimeTField) {
		element.setText(Integer.toString(arrayReduceSecondTimeValue.get(f)));
		f++;
    	}
    }
    
    void printArrayReduceTime() {
    	int f=0;
    	for(TextField element: arrayReduceTimeTField) {
		element.setText(Integer.toString(arrayReduceTimeValue.get(f)));
		f++;
    	}
    }
    
    @FXML
    private Button cleansingBtn;
    
    @FXML
    void clear(ActionEvent e) {
    	for(int i=0;i<20;i++) {
    		clearTextField(arrayNewTimeTField.get(i));
    		if(i<8)
    			clearTextField(arrayWayTField.get(i));
    		setNull(arrayReduceFirstTimeTField.get(i));
    		setNull(arrayReduceSecondTimeTField.get(i));
    		setNull(arrayReduceTimeTField.get(i));
    	}
    	clearTextField(setCriticalWayTField);
    	clearTextField(criticalWayTFied);
    	clearTextField(minCostTField);
    	findWayBtn.setDisable(false);
    	calculateBtn.setDisable(true);
    	allCash =0;
    	arrayCostForFirstDay.clear();
    	arrayCostForSecondDay.clear();
    	arrayWayValue.clear();;
    	arrayReduceFirstTimeValue.clear();
    	arrayReduceSecondTimeValue.clear();
    	arrayReduceTimeValue.clear();
    	arrayOldTimeValue.clear();
    }
    
    void clearTextField(TextField textField) {
    	textField.clear();
    }
    
    void setNull(TextField textField) {
    	textField.setText("0");
    }
    @FXML
    void initialize() {
    	createaArrayWayText();
    }

}


