///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  P1.java
// File:             Sym.java
// Semester:         Spring 2012
//
// Author:           Erin Rasmussen  ejrasmussen2@wisc.edu
// CS Login:         rasmusse
// Lecturer's Name:  Somesh Jha
// Lab Section:      CS 536
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;
/**
  * This class represents the type of identifier for the compiler.
  *
  * <p>Bugs: none known
  *
  * @author Erin Rasmussen
  */
public class Sym {
	private String identifier;
	private String name;	
	/**
	  * This is the constructor; it should initialize the Sym to have the given
	  * type.
	  *
	  * @param (type) (The type of identifier, ie int, double, char, ...)
	  */
	public Sym(String identifier){ 	
		this.identifier = identifier;
	}

	public Sym(String identifier, String name){
		this.identifier = identifier;
		this.name = name;
	}
	
	/**
	  * Return this Sym's type.
	  *
	  * @return (the type of identifier)
	  */
	public String getType(){
		return this.identifier;
	}
	
	public String getName(){
		return this.name;
	}

	/**
	  * Return this Sym's type. (This method will be changed
	  * later when more information is stored in a Sym.)
	  *
	  * @return (the string form of the object)
	  */
	public String toString(){
		return(this.identifier + " " + this.name);
	}
}

class FunctionSym extends Sym {
	private int paramAmount;
	private LinkedList paramIdents;

	public FunctionSym(String type, String name, int paramAmount) {
		super(type, name);
		this.paramAmount = paramAmount;
	}

	public void addParams(LinkedList params){
		this.paramIdents = params;
	}

	public int getParamAmount() {
		return this.paramAmount;
	}

	public LinkedList getParams(){
		return paramIdents;
	}
}
