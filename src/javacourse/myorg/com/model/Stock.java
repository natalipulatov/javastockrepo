package javacourse.myorg.com.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Stock {
	
	private final static int BUY = 0;
	private final static int SELL = 1;
	private final static int REMOVE = 2;
	private final static int HOLD = 3;
	
String symbol;
float ask; 
float bid;
Date date;
private int recommendation;
private int stockQuantity;

//constructor
public Stock(String symbol, float ask, float bid, Date date) {
	super();
	this.symbol = symbol;
	this.ask = ask;
	this.bid = bid;
	this.date = date;
}
//Adding to all members getters and setters
private String getSymbol() {
	return symbol;
}
private void setSymbol(String symbol) {
	this.symbol = symbol;
}
private float getAsk() {
	return ask;
}
private void setAsk(float ask) {
	this.ask = ask;
}
private float getBid() {
	return bid;
}
private void setBid(float bid) {
	this.bid = bid;
}
private Date getDate() {
	return date;
}
private void setDate(Date date) {
	this.date = date;
}
public String getHtmlDescription(){
	SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY");//Format date
	String stocksDetails = "<b>Stock symbol</b>: " +getSymbol()+","+"<b> ask</b>: "+getAsk()+","+"<b> bid</b>: "+getBid()+","+"<b> date</b>: "+df.format(getDate()); 
	return stocksDetails;
}
}