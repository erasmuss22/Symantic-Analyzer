import java.util.*;

// **********************************************************************
// The Sym class defines a symbol-table entry;
// an object that contains a type (a String).
// **********************************************************************
//
// Methods
// =======
//
// constructor
// -----------
// Sym(String S)         -- constructor: creates a Sym with the given type
//
// accessor
// --------
// type()             -- returns this symbol's type
//
// other
// -----
// makeComplete()    -- marks this sym as complete
// toString()        -- prints the values associated with this Sym

class Sym {
    public Sym(String T) {
        myType = T;
        myComplete = true;
        if (myType.equals("int")) this.offset = 4;
        else this.offset = 8;
        global = false;
        FPoffset = 0;
    }

    public Sym(String T, boolean comp) {
	this(T);
	myComplete = comp;
    }

    public String type() {
	return myType;
    }

    public String toString() {
	return "";
    }

    public int getOffset(){
	return this.offset;
    }

    public void setOffset(int o){
	this.offset = o;
    }
    
    public int getFPOffset(){
        return this.FPoffset;
    }
    
    public void setFPOffset(int o){
        this.FPoffset = o;
    }
    
    public boolean getGlobal(){
        return this.global;
    }
    
    public void setGlobal(){
        this.global = true;
    }

  // fields
    protected String myType;
    private boolean myComplete;
    private int offset;
    private int FPoffset;
    private boolean global;
}

// **********************************************************************
// The FnSym class is a subclass of the Sym class, just for functions.
// The myReturnType field holds the return type, and there are new fields
// to hold information about the parameters.
// **********************************************************************
class FnSym extends Sym {
    public FnSym(String T, int numparams) {
	super("->"+T);
	myReturnType = T;
	myNumParams = numparams;
	myParamTypes = null;
    }

    public void addFormals(LinkedList<Sym> L) {
	myParamTypes = new LinkedList<String>();
	// UPDATE TYPE STRING
	boolean first = true;
	Iterator<Sym> it = L.descendingIterator();
	while (it.hasNext()) {
	    Sym oneSym = it.next();
	    myParamTypes.add(0, oneSym.type());
	    if (first) {
		myType = oneSym.type() + myType;
		first = false;
	    } else {
		myType = oneSym.type() + "," + myType;
	    }
	}
    }
    
    public String returnType() {
	return myReturnType;
    }

    public int numparams() {
	return myNumParams;
    }

    public LinkedList<String> paramTypes() {
	return myParamTypes;
    }
    
    public void setParamOffset(int o){
        this.paramOffset = o;
    }

    public void setLocalOffset(int o){
        this.localOffset = o;
    }
    
    public int getParamOffset(){
        return this.paramOffset;
    }
    
    public int getLocalOffset(){
        return this.localOffset;
    }
    
    // new fields
    private String myReturnType;
    private int myNumParams;
    private LinkedList<String> myParamTypes;
    private int localOffset;
    private int paramOffset;
}


