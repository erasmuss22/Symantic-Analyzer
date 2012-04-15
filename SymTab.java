import java.util.*;
///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  P1.java
// File:             SymTab.java
// Semester:         Spring 2012
//
// Author:           Erin Rasmussen  ejrasmussen2@wisc.edu
// CS Login:         rasmusse
// Lecturer's Name:  Somesh Jha
// Lab Section:      CS 536
//////////////////////////// 80 columns wide //////////////////////////////////

/**
  * This class represents a symbol table: a data structure that stores the 
  * identifiers declared in the program being compiled
  *
  * <p>Bugs: none known
  *
  * @author Erin Rasmussen
  */
public class SymTab {
	private List<HashMap<String, Sym>> symTab;
	
	/**
	  * This is the constructor; it should initialize the SymTab's List field
	  * to contain a single, empty HashMap.
	  *
	  */
	public SymTab() {
		symTab = new ArrayList<HashMap<String, Sym>>();
		symTab.add(new HashMap<String, Sym>());
	}
	
	/**
	  * If this SymTab's list is empty, throw an EmptySymTabException. If the
	  * first HashMap in the list already contains the given name as a key,
	  * throw a DuplicateException. Otherwise, add the given name and sym to 
	  * the first HashMap in the list.
	  *
	  * @param (name) (the name to be used as the key for storing in the table)
	  * @param (sym) (the symbol to be inserted into the table)
	  */
	public void insert(String name, Sym sym) throws DuplicateException, EmptySymTabException {
		if (symTab.size() == 0){
			throw new EmptySymTabException();
		}
		if (symTab.get(0).containsKey(name)){
			throw new DuplicateException();
		}
		symTab.get(0).put(name, sym);
	}
	
	/**
	  * Add a new, empty HashMap to the front of the list.
	  *
	  * 
	  */
	public void addMap() {
		symTab.add(0, new HashMap<String,Sym>());
	}
	
	/**
	  * If this SymTab's list is empty, return null. Otherwise, if the first
	  * HashMap in the list has name as a key, return the associated Sym; 
	  * otherwise, return null.
	  *
	  * @param (name) (the name to look for as a key)
	  * @return (null or the associated Sym with the key)
	  */
	public Sym localLookup(String name) {
		if (symTab.size() == 0){
			return null;
		}
		if (symTab.get(0).containsKey(name)){
			return symTab.get(0).get(name);
		}
		return null;
	}
	
	/**
	  * If any HashMap in the list has name as a key, return the first 
	  * associated Sym (i.e., the one from the HashMap that is closest 
	  * to the front of the list); otherwise, return null.
	  *
	  * @param (name) (the name to look for as a key)
	  * @return (null or the associated Sym with the key)
	  */
	public Sym globalLookup(String name) {
		Iterator<HashMap<String,Sym>> iter = symTab.iterator();
		HashMap<String,Sym> temp;
		while (iter.hasNext()){
			temp = iter.next();
			if (temp.containsKey(name)){
				return temp.get(name);
			}
		}
		return null;	
	}
	
	/**
	  * If this SymTab's list is empty, throw an EmptySymTabException; 
	  * otherwise, remove the HashMap from the front of the list.
	  *
	  */
	public void removeMap() throws EmptySymTabException {
		if (symTab.size() == 0){
			throw new EmptySymTabException();
		}
		symTab.remove(0);
	}
	
	/**
	  * This method is for debugging. First, print "\nSYMBOL TABLE\n". Then, 
	  * for each HashMap M in the list, print M.toString() followed by a 
	  * newline. Finally, print one more newline. All output should go to 
	  * System.out . 
	  *
	  */
	public void print() {
		System.out.print("\nSYMBOL TABLE\n");
		Iterator<HashMap<String,Sym>> iter = symTab.iterator();
		HashMap<String,Sym> temp;
		while (iter.hasNext()){
			temp = iter.next();
			System.out.print(temp.toString() + "\n");
		}
		System.out.print("\n");
	}
}
