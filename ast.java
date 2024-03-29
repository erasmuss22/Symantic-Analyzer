import java.io.*;
import java.util.*;

// **********************************************************************
// The ASTnode class defines the nodes of the abstract-syntax tree that
// represents a Little program.
//
// Internal nodes of the tree contain pointers to children, organized
// either in a list (for nodes that may have a variable number of children)
// or as a fixed set of fields.
//
// The nodes for literals and ids contain line and character number
// information; for string literals and identifiers, they also contain a
// string; for integer literals, they also contain an integer value.
//
// Here are all the different kinds of AST nodes and what kinds of children
// they have.  All of these kinds of AST nodes are subclasses of "ASTnode".
// Indentation indicates further subclassing:
//
//     Subclass            Kids
//     --------            ----
//     ProgramNode         DeclListNode
//     DeclListNode        list of DeclNode
//     DeclNode:
//       VarDeclNode       TypeNode, IdNode, int
//       FnDeclNode        TypeNode, IdNode, FormalsListNode, FnBodyNode
//       FormalDeclNode    TypeNode, IdNode
//     FormalsListNode     list of FormalDeclNode
//     FnBodyNode          DeclListNode, StmtListNode
//     StmtListNode        list of StmtNode
//     ExpListNode         list of ExpNode
//
//     TypeNode:
//       IntNode           -- none --
//       DblNode           -- none --
//       VoidNode          -- none --
//
//     StmtNode:
//       AssignStmtNode      AssignNode
//       PreIncStmtNode      IdNode
//       PreDecStmtNode      IdNode
//       PostIncStmtNode     IdNode
//       PostDecStmtNode     IdNode
//       ReadIntStmtNode     IdNode
//       ReadDblStmtNode     IdNode
//       WriteIntStmtNode    ExpNode
//       WriteDblStmtNode    ExpNode
//       WriteStrStmtNode    ExpNode
//       IfStmtNode          ExpNode, DeclListNode, StmtListNode
//       IfElseStmtNode      ExpNode, DeclListNode, StmtListNode,
//                                    DeclListNode, StmtListNode
//       WhileStmtNode       ExpNode, DeclListNode, StmtListNode
//       CallStmtNode        CallExpNode
//       ReturnStmtNode      ExpNode
//
//     ExpNode:
//       IntLitNode          -- none --
//       DblLitNode          -- none --
//       StringLitNode       -- none --
//       IdNode              -- none --
//       CallExpNode         IdNode, ExpListNode
//       UnaryExpNode        
//         UnaryMinusNode    ExpNode
//         NotNode           ExpNode
//         PlusPlusNode      IdNode
//         MinusMinusNode    IdNode
//       BinaryExpNode       ExpNode, ExpNode
//         AssignNode
//         ArithBinExpNode
//           PlusNode     
//           MinusNode
//           TimesNode
//           DivideNode
//         EqualityBinExpNode
//           EqualsNode
//           NotEqualsNode
//           LessNode
//           GreaterNode
//           LessEqNode
//           GreaterEqNode
//         LogicalBinExpNode
//           AndNode
//           OrNode
//
// Here are the different kinds of AST nodes again, organized according to
// whether they are leaves, internal nodes with lists of kids, or internal
// nodes with a fixed number of kids:
//
// (1) Leaf nodes:
//        IntNode,   DblNode,  VoidNode,  IntLitNode,  DblLitNode, StringLitNode,
//        IdNode
//
// (2) Internal nodes with (possibly empty) lists of children:
//        DeclListNode, FormalsListNode, StmtListNode, ExpListNode
//
// (3) Internal nodes with fixed numbers of kids:
//       ProgramNode,      VarDeclNode,     FnDeclNode,       FormalDeclNode,
//       FnBodyNode,       TypeNode,        AssignStmtNode,
//       PreIncStmtNode,   PreDecStmtNode,  PostIncStmtNode,  PostDecStmtNode,
//       ReadIntStmtNode,  ReadDblStmtNode, WriteIntStmtNode, WriteDblStmtNode
//       WriteStrStmtNode  IfStmtNode,      IfElseStmtNode,   WhileStmtNode,
//       CallStmtNode,     ReturnStmtNode,  CallExpNode,
//       UnaryExpNode,     UnaryMinusNode,  NotNode,          PlusPlusNode,
//       MinusMinusNode    BinaryExpNode    AssignNode,
//       ArithmeticBinExpNode               PlusNode,         MinusNode,
//       TimesNode,        DivideNode,      EqualityBinExpNode,
//       EqualsNode,       NotEqualsNode,
//       LessNode,         GreaterNode,     LessEqNode,       GreaterEqNode,
//       LogicalBinExpNode,                 AndNode,          OrNode
//
// **********************************************************************

// **********************************************************************
// ASTnode class (base class for all other kinds of nodes)
// **********************************************************************
abstract class ASTnode { 
    protected static final boolean DEBUG = false;
    protected static final String INT_TYPE = "int";
    protected static final String DBL_TYPE = "double";
    protected static final String ERR_TYPE = "ERR";
    protected static final String VOID_TYPE = "void";

    // every subclass must provide an unparse operation
    abstract public void unparse(PrintWriter p, int indent);

    // this method can be used by the unparse methods to do indenting
    protected void doIndent(PrintWriter p, int indent) {
	for (int k=0; k<indent; k++) p.print(" ");
    }

    // methods for type checking
    protected static boolean isErrType(String T) {
	return T.equals(ERR_TYPE);
    }

    protected static boolean isVoidType(String T) {
	return T.equals(VOID_TYPE);
    }

    protected static boolean isFnType(String T) {
	return T.indexOf("->") != -1;
    }

    protected static boolean isIntType(String T) {
	return T.equals(INT_TYPE);
    }

    protected static boolean isDblType(String T) {
	return T.equals(DBL_TYPE);
    }

    protected static boolean isNumericType(String T) {
	return T.equals(INT_TYPE) || T.equals(DBL_TYPE);
    }

    protected static boolean compatibleTypes(String T1, String T2) {
	// T1 is a formal's type, T2 is an actual's type
	// OK iff same type or dbl, int
	return T1.equals(T2) || (isDblType(T1) && isIntType(T2));
    }
}

// **********************************************************************
// ProgramNode,  DeclListNode, FormalsListNode, FnBodyNode,
// StmtListNode, ExpListNode
// **********************************************************************
class ProgramNode extends ASTnode {
    public ProgramNode(DeclListNode L) {
	myDeclList = L;
    }

    /** processNames
     *
     * create an empty symbol table for the outermost scope, then
     * process all of the globals and functions in the program
     **/
    public void processNames() {
        SymTab S = new SymTab();
        myDeclList.processNames(S, "global");
        Sym main = S.globalLookup("main");
        if (main == null) {
            Errors.fatal(0, 0, "No main function");
        }
    }

    /** typeCheck **/
    public void typeCheck() {
	myDeclList.typeCheck();
    }

    public void unparse(PrintWriter p, int indent) {
	myDeclList.unparse(p, indent);
    }

    public void codeGen(){
        Codegen.generate(".text");
        Codegen.generateWithComment("b", "Branch so program starts at main", "main");
        myDeclList.codeGen();
    }
    
    // 1 kid
    private DeclListNode myDeclList;
}

class DeclListNode extends ASTnode {
    public DeclListNode(List<DeclNode> L) {
	myDecls = L;
    }

    /** processNames
     *
     * given: a symbol table S
     * do:    process all of the decls in the list
     **/
    public void processNames(SymTab S) {
	Iterator it = myDecls.iterator();
	try {
	    while (it.hasNext()) {
		((DeclNode)it.next()).processNames(S);
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in DeclListNode.processNames");
	    System.exit(-1);
	}
	
    }
    
    public void processNames(SymTab S, String status) {
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).processNames(S, status);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.processNames");
            System.exit(-1);
        }
        
    }
    
    public void processNames(SymTab S, int offset) {
        Iterator it = myDecls.iterator();
        try {
            offset = 0;
            while (it.hasNext()) {
                offset += ((DeclNode)it.next()).processNames(S, offset);
            }
            localOffset = offset;
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.processNames");
            System.exit(-1);
        }
        
    }

    /** typeCheck **/
    public void typeCheck() {
	Iterator it = myDecls.iterator();
	try {
	    while (it.hasNext()) {
		((DeclNode)it.next()).typeCheck();
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in DeclListNode.typeCheck");
	    System.exit(-1);
	}
	
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	Iterator it = myDecls.iterator();
	try {
	    while (it.hasNext()) {
		((DeclNode)it.next()).unparse(p, indent);
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in DeclListNode.unparse");
	    System.exit(-1);
	}
    }

    public void codeGen(){
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).codeGen();
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.codeGen");
            System.exit(-1);
        }
    }
    
    public int getLocalOffset(){
        return this.localOffset;
    }
    
    // list of kids (DeclNodes)
    private List<DeclNode> myDecls;
    private int localOffset;
}

class FormalsListNode extends ASTnode {
    public FormalsListNode(List<FormalDeclNode> L) {
        myFormals = L;
        }

        /** processNames
         *
         * given: a symbol table S
         * do:    process all of the formals in the list
         **/
        public LinkedList processNames(SymTab S) {
        LinkedList L = new LinkedList();
            if (myFormals != null){
                Iterator it = myFormals.iterator();
                try {
                    paramOffset = 0;
                    int paramFPOffset = 0;
                    while (it.hasNext()) {
                        Sym sym = ((FormalDeclNode)it.next()).processNames(S);
                        if (sym != null){
                            if (sym.type().equals("int")){
                                sym.setOffset(paramOffset);
                                sym.setFPOffset(paramFPOffset);
                                paramFPOffset -= 4;
                                paramOffset -= 4;
                                sym.setParam();
                            }
                            else {
                                sym.setOffset(paramOffset);
                                sym.setFPOffset(paramFPOffset);
                                paramFPOffset -= 8;
                                paramOffset -= 8;
                            }
                        }
                    if (sym != null) L.add(sym);
                    }
                } catch (NoSuchElementException ex) {
                    System.err.println("unexpected NoSuchElementException in FormalsListNode.processNames");
                    System.exit(-1);
                }
            }
        return L;
        }
    
    public int getParamOffset(){
        return this.paramOffset;
    }

        /** length **/
        public int length() {
            int count = 0;
            if (myFormals != null) return myFormals.size();
            else return count;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	Iterator it = myFormals.iterator();
	try {
	    while (it.hasNext()) {
		((FormalDeclNode)it.next()).unparse(p, indent);
		if (it.hasNext()) {
		    p.print(", ");
		}
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in FormalsListNode.unparse");
	    System.exit(-1);
	}
    }

    // list of kids (FormalDeclNodes)
    private List<FormalDeclNode> myFormals;
    private int paramOffset;
}

class FnBodyNode extends ASTnode {
    public FnBodyNode(DeclListNode declList, StmtListNode stmtList) {
	myDeclList = declList;
	myStmtList = stmtList;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myDeclList.processNames(S);
	myStmtList.processNames(S);
    }
    
    public void processNames(SymTab S, int offset) {
        myDeclList.processNames(S, offset);
        myStmtList.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String returnType) {
	myStmtList.typeCheck(returnType);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	if (myDeclList != null) myDeclList.unparse(p, indent+2);
	if (myStmtList != null) myStmtList.unparse(p, indent+2);
    }
    
    public DeclListNode getDeclList(){
        return this.myDeclList;
    }
    
    public void codeGen(){
        if (myStmtList != null){
            myStmtList.codeGen();
        }
    }
    
    
    public void codeGen(String prologue){
        if (myStmtList != null){
            myStmtList.codeGen(prologue);
        }
    }
    
    public void codeGen(String prologue, int localOffset){
        if (myStmtList != null){
            myStmtList.codeGen(prologue, localOffset);
        }
    }
    
    // 2 kids
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class StmtListNode extends ASTnode {
    public StmtListNode(List<StmtNode> L) {
	myStmts = L;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	Iterator it = myStmts.iterator();
	try {
	    while (it.hasNext()) {
		((StmtNode)it.next()).processNames(S);
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in StmtListNode.processNames");
	    System.exit(-1);
	}
    }

    /** typeCheck **/
    public void typeCheck(String returnType) {
	Iterator it = myStmts.iterator();
	try {
	    while (it.hasNext()) {
		((StmtNode)it.next()).typeCheck(returnType);
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in StmtListNode.processNames");
	    System.exit(-1);
	}
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	// indent for each stmt is done here
	// each stmt is expected to end with a newline
	Iterator it = myStmts.iterator();
	try {
	    while (it.hasNext()) {
		doIndent(p, indent);
		((StmtNode)it.next()).unparse(p, indent);
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in StmtListNode.unparse");
	    System.exit(-1);
	}
    }

    public void codeGen() {
        Iterator it = myStmts.iterator();
        try {
            while (it.hasNext()) {
                ((StmtNode)it.next()).codeGen();
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in StmtListNode.codeGen");
            System.exit(-1);
        }
    }
    
    
    public void codeGen(String prologue) {
        Iterator it = myStmts.iterator();
        try {
            while (it.hasNext()) {
                ((StmtNode)it.next()).codeGen(prologue);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in StmtListNode.codeGen");
            System.exit(-1);
        }
    }
    
    public void codeGen(String prologue, int localOffset) {
        Iterator it = myStmts.iterator();
        try {
            while (it.hasNext()) {
                ((StmtNode)it.next()).codeGen(prologue, localOffset);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in StmtListNode.codeGen");
            System.exit(-1);
        }
    }
    
    // list of kids (StmtNodes)
    private List<StmtNode> myStmts;
}

class ExpListNode extends ASTnode {
    public ExpListNode(List<ExpNode> L) {
	myExps = L;
    }

    /** typeCheck **/
    public void typeCheck(LinkedList<String> L) {
	int k=0;
	Iterator it = myExps.iterator();
	try {
	    while (it.hasNext()) {
		ExpNode exp = (ExpNode)it.next();
		String actualT = exp.typeCheck();
		if (!isErrType(actualT)) {
		    String paramT = L.get(k);
		    if (!compatibleTypes(paramT, actualT)) {
			Errors.fatal(exp.linenum(), exp.charnum(),
				     "Type of actual does not match type of formal");
		    }
		}
		k++;
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in ExpListNode.processNames");
	    System.exit(-1);
	}
    }

    /** processNames **/
    public void processNames(SymTab S) {
	Iterator it = myExps.iterator();
	try {
	    while (it.hasNext()) {
		((ExpNode)it.next()).processNames(S);
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in ExpListNode.processNames");
	    System.exit(-1);
	}
    }

    /** length **/
    public int length() {
	return myExps.size();
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	Iterator it = myExps.iterator();
	try {
	    while (it.hasNext()) {
		((ExpNode)it.next()).unparse(p, 0);
		if (it.hasNext()) {
		    p.print(", ");
		}
	    }
	} catch (NoSuchElementException ex) {
	    System.err.println("unexpected NoSuchElementException in ExpListNode.unparse");
	    System.exit(-1);
	}
    }
    
    public void codeGen(){
        Iterator it = myExps.iterator();
        try {
            while (it.hasNext()) {
                ExpNode exp = (ExpNode)it.next();
                exp.codeGen();
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in ExpListNode.processNames");
            System.exit(-1);
        }

    }
    
    public void codeGen(int localOffset){
        Iterator it = myExps.iterator();
        try {
            while (it.hasNext()) {
                ExpNode exp = (ExpNode)it.next();
                exp.codeGen(localOffset);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in ExpListNode.processNames");
            System.exit(-1);
        }
        
    }
    
    public void codeGen(String prologue){
        Iterator it = myExps.iterator();
        try {
            while (it.hasNext()) {
                ExpNode exp = (ExpNode)it.next();
                exp.codeGen();
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in ExpListNode.processNames");
            System.exit(-1);
        }
        
    }
    
    // list of kids (ExpNodes)
    private List<ExpNode> myExps;
}

// **********************************************************************
// DeclNode and its subclasses
// **********************************************************************
abstract class DeclNode extends ASTnode {
    // note: only a formal decl needs to return a Sym
    //       but since we must declare the method here,
    //       we make all decl nodes return something
    //       (for non formal decls, the returned value
    //       is simply ignored)
    abstract public Sym processNames(SymTab S);
    abstract public Sym processNames(SymTab S, String status);
    abstract public int processNames(SymTab S, int offset);
    abstract public void codeGen();
    // default version of typeCheck for var and formal decls
    public void typeCheck() {
    }
}

class VarDeclNode extends DeclNode {
    public VarDeclNode(TypeNode type, IdNode id) {
	myType = type;
	myId = id;
    }

    /** processNames
     *
     * given: a symbol table
     * do: if this name is declared void, error!
     *     if this name has already been declared in this scope, error!
     *     if no error, add name to local symbol table
     **/
    public Sym processNames(SymTab S) {
	String name = myId.name();
	boolean badDecl = false;
	if (isVoidType(myType.type())) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Non-function declared void");
	    badDecl = true;
	}
	if (S.localLookup(name) != null) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Multiply declared identifier");
	    badDecl = true;
	}
	if (! badDecl) {
	    try {
		Sym sym = new Sym(myType.type());
		S.insert(name, sym);
		myId.link(sym);
	    } catch (DuplicateException ex) {
		System.err.println("unexpected DuplicateException in VarDeclNode.processNames");
		System.exit(-1);
	    } catch (EmptySymTabException ex) {
		System.err.println("unexpected EmptySymTabException in VarDeclNode.processNames");
		System.exit(-1);
	    }
	}
	return null;  // return value ignored
    }
    
    /*
        Same function, but used to set a variable as a global
     */
    
    public Sym processNames(SymTab S, String status) {
        String name = myId.name();
        boolean badDecl = false;
        if (isVoidType(myType.type())) {
            Errors.fatal(myId.linenum(), myId.charnum(),
                         "Non-function declared void");
            badDecl = true;
        }
        if (S.localLookup(name) != null) {
            Errors.fatal(myId.linenum(), myId.charnum(),
                         "Multiply declared identifier");
            badDecl = true;
        }
        if (! badDecl) {
            try {
                Sym sym = new Sym(myType.type());
                sym.setGlobal();
                S.insert(name, sym);
                myId.link(sym);
            } catch (DuplicateException ex) {
                System.err.println("unexpected DuplicateException in VarDeclNode.processNames");
                System.exit(-1);
            } catch (EmptySymTabException ex) {
                System.err.println("unexpected EmptySymTabException in VarDeclNode.processNames");
                System.exit(-1);
            }
        }
        return null;  // return value ignored
    }
    
    
    public int processNames(SymTab S, int offset) {
        String name = myId.name();
        boolean badDecl = false;
        if (isVoidType(myType.type())) {
            Errors.fatal(myId.linenum(), myId.charnum(),
                         "Non-function declared void");
            badDecl = true;
        }
        if (S.localLookup(name) != null) {
            Errors.fatal(myId.linenum(), myId.charnum(),
                         "Multiply declared identifier");
            badDecl = true;
        }
        if (! badDecl) {
            try {
                Sym sym = new Sym(myType.type());
                if (myType.type().equals("int")){
                    sym.setFPOffset(offset);
                    offset = -4;
                }
                else {
                    sym.setFPOffset(offset);
                    offset -= 8;
                }
                S.insert(name, sym);
                myId.link(sym);
                return offset;
            } catch (DuplicateException ex) {
                System.err.println("unexpected DuplicateException in VarDeclNode.processNames");
                System.exit(-1);
            } catch (EmptySymTabException ex) {
                System.err.println("unexpected EmptySymTabException in VarDeclNode.processNames");
                System.exit(-1);
            }
        }
        return 0;  // return value ignored
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	doIndent(p, indent);
	myType.unparse(p, 0);
	p.print(" ");
	myId.unparse(p, 0);
	p.println(";");
    }

    public void codeGen() {
        if (myId.sym().getGlobal()){
            Codegen.generate(".data");
            Codegen.generate(".align 2");
            if (myId.type().equals("int")){
                Codegen.generateLabeled("_"+myId.name(), ".space " + 4, "GLOBAL", "");
            }    else{
                Codegen.generateLabeled("_"+myId.name(), ".space " + 8, "GLOBAL", "");
            }
        }
    }
    
    // 2 kids
    private TypeNode myType;
    private IdNode myId;
}

class FnDeclNode extends DeclNode {
    public FnDeclNode(TypeNode type,
		      IdNode id,
		      FormalsListNode formalList,
		      FnBodyNode body) {
	myType = type;
	myId = id;
	myFormalsList = formalList;
	myBody = body;
    }

    /** processNames
     *
     * given: a symbol table S
     * do:    If this name has already been declared in this scope
     *        then error
     *        else add name to local symbol table.
     *        In any case:
     *             enter new scope
     *             process formals
     *             if this fn not multiply decld
     *                update symtab entry with types of formals
     *             process body
     *             exit scope
     **/
    public Sym processNames(SymTab S) {
	String name = myId.name();
	FnSym sym = null;
	if (S.localLookup(name) != null) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Multiply declared identifier");
	}
	else {
	    try {
		sym = new FnSym(myType.type(),
				myFormalsList.length());
		S.insert(name, sym);
		myId.link(sym);
	    } catch (DuplicateException ex) {
		System.err.println("unexpected DuplicateException in FnDeclNode.processNames");
		System.exit(-1);
	    } catch (EmptySymTabException ex) {
		System.err.println("unexpected EmptySymTabException in FnDeclNode.processNames");
		System.exit(-1);
	    }
	}
	S.addMap();
	LinkedList L = myFormalsList.processNames(S);
	if (sym != null) sym.addFormals(L);
	myBody.processNames(S);
	try {
	    S.removeMap();
	} catch (EmptySymTabException ex) {
		System.err.println("unexpected EmptySymTabException in FnDeclNode.processNames");
		System.exit(-1);
	    }
	return null;
    }
    
    public Sym processNames(SymTab S, String status) {
        String name = myId.name();
        FnSym sym = null;
        if (S.localLookup(name) != null) {
            Errors.fatal(myId.linenum(), myId.charnum(),
                         "Multiply declared identifier");
        }
        else {
            try {
                sym = new FnSym(myType.type(),
                                myFormalsList.length());
                S.insert(name, sym);
                myId.link(sym);
            } catch (DuplicateException ex) {
                System.err.println("unexpected DuplicateException in FnDeclNode.processNames");
                System.exit(-1);
            } catch (EmptySymTabException ex) {
                System.err.println("unexpected EmptySymTabException in FnDeclNode.processNames");
                System.exit(-1);
            }
        }
        S.addMap();
        paramOffset = 0;
        LinkedList L = myFormalsList.processNames(S);
        paramOffset = myFormalsList.getParamOffset();
        if (sym != null) sym.addFormals(L);
        localOffset = 0;
        myBody.processNames(S, localOffset);
        localOffset = myBody.getDeclList().getLocalOffset();
        sym.setParamOffset(paramOffset);
        sym.setLocalOffset(localOffset);
        try {
            S.removeMap();
        } catch (EmptySymTabException ex) {
            System.err.println("unexpected EmptySymTabException in FnDeclNode.processNames");
            System.exit(-1);
	    }
        return null;
    }
    
    public int processNames(SymTab S, int offset) {
        return 0;
    }

    /** typeCheck **/
    public void typeCheck() {
	myBody.typeCheck(myType.type());
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.println();
	doIndent(p, indent);
	myType.unparse(p, 0);
	p.print(" ");
	myId.unparse(p, 0);
	p.print("(");
	if (myFormalsList != null) myFormalsList.unparse(p, 0);
	p.println(") {");
	if (myBody != null) myBody.unparse(p, indent);
	doIndent(p, indent);
	p.println("}");
    }

    public void codeGen() {
        Codegen.generate(".text");
        if (myId.name().equals("main")) {
            Codegen.generate(".globl main");
            Codegen.generateLabeled("main", "", "FUNCTION ENTRY", "");
        } else {
            Codegen.generateLabeled("_" + myId.name(), "", "FUNCTION ENTRY", "");
        }
        Codegen.genPush(Codegen.RA, 4);
        Codegen.genPush(Codegen.FP, 4);
        FnSym sym = (FnSym) myId.sym();
        Codegen.generate("addu",  Codegen.FP, Codegen.SP, (-1 * paramOffset) + 8);
        if (localOffset < 0){
            Codegen.generate("subu", Codegen.SP, Codegen.SP, (-1 * localOffset));
        }
        String prologue = Codegen.nextLabel();

        myBody.codeGen(prologue, paramOffset);

        Codegen.generateLabeled(prologue, "", "FUNCTION EXIT", "");
        Codegen.generateIndexed("lw", Codegen.RA, Codegen.FP, paramOffset, "load return address");
        Codegen.generateWithComment("move", "save control link", Codegen.T0, Codegen.FP);
        Codegen.generateIndexed("lw", Codegen.FP, Codegen.FP, (paramOffset - 4), "restore FP");
        Codegen.generateWithComment("move", "restore SP", Codegen.SP, Codegen.T0);
        Codegen.generateWithComment("jr", "return", Codegen.RA);
    }
    
    // 4 kids
    private TypeNode myType;
    private IdNode myId;
    private FormalsListNode myFormalsList;
    private FnBodyNode myBody;
    public int localOffset;
    public int paramOffset;
}

class FormalDeclNode extends DeclNode {
    public FormalDeclNode(TypeNode type, IdNode id) {
	myType = type;
	myId = id;
    }

    /** processNames
     *
     * given: a symbol table S
     * do:    if this formal is declared void, error!
     *        else if this formal is multiply declared
     *        then give an error msg and return null
     *        else add a new entry to S and also return that Sym
     **/
    public Sym processNames(SymTab S) {
	String name = myId.name();
	boolean badDecl = false;
	Sym sym = null;
	if (isVoidType(myType.type())) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Non-function declared void");
	    badDecl = true;
	}
	if (S.localLookup(name) != null) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Multiply declared identifier");
	    badDecl = true;
	}
	if (! badDecl) {
	    try {
		sym = new Sym(myType.type());
		S.insert(name, sym);
		myId.link(sym);
	    } catch (DuplicateException ex) {
		System.err.println("unexpected DuplicateException in FormalDeclNode.processNames");
		System.exit(-1);
	    } catch (EmptySymTabException ex) {
		System.err.println("unexpected EmptySymTabException in FormalDeclNode.processNames");
		System.exit(-1);
	    }
	}
	return sym;
    }

    public int processNames(SymTab S, int offset) {
        return 0;
    }
    
    public Sym processNames(SymTab S, String status) {
        return null;
    }
    
    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	doIndent(p, indent);
	myType.unparse(p, indent);
	p.print(" ");
	myId.unparse(p, indent);
    }

    public void codeGen(){}
    
    // 2 kids
    private TypeNode myType;
    private IdNode myId;
}

// **********************************************************************
// TypeNode and its Subclasses
// **********************************************************************
abstract class TypeNode extends ASTnode {
    /* all subclasses must provide a type method */
    abstract public String type();
}

class IntNode extends TypeNode {
    public IntNode() {
    }

    /** type **/
    public String type() {
	return INT_TYPE;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print(INT_TYPE);
    }
}

class DblNode extends TypeNode {
    public DblNode() {
    }

    /** type **/
    public String type() {
	return DBL_TYPE;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print(DBL_TYPE);
    }
}

class VoidNode extends TypeNode {
    public VoidNode() {
    }

    public String type() {
	return VOID_TYPE;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print(VOID_TYPE);
    }
}

// **********************************************************************
// StmtNode and its subclasses
// **********************************************************************

abstract class StmtNode extends ASTnode {
    abstract public void processNames(SymTab S);
    abstract public void typeCheck(String T);
    public void codeGen(){}
    public void codeGen(String prologue){}
    public void codeGen(String prologue, int localOffset){}
}

class AssignStmtNode extends StmtNode {
    public AssignStmtNode(AssignNode e) {
	myExp = e;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myExp.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	myExp.typeCheck();
    }

    public void unparse(PrintWriter p, int indent) {
	myExp.unparse(p,0,false);
	p.println(";");
    }

    public void codeGen(){
        myExp.codeGen();
        int varSize = myExp.sizeOfVar();
        Codegen.genPop(Codegen.T0, varSize);
    }
    
    public void codeGen(String prologue){
        myExp.codeGen();
        int varSize = myExp.sizeOfVar();
        Codegen.genPop(Codegen.T0, varSize);
    }
    
    public void codeGen(String prologue, int localOffset){
        myExp.codeGen(localOffset);
        int varSize = myExp.sizeOfVar();
        Codegen.genPop(Codegen.T0, varSize);
    }
    
    // 1 kid
    private AssignNode myExp;
}

class PreIncStmtNode extends StmtNode {
    public PreIncStmtNode(IdNode id) {
	myId = id;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myId.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myId.typeCheck();
	if (!isErrType(T)) {
	    if (!isIntType(T)) {
		Errors.fatal(myId.linenum(), myId.charnum(),
			     "Non-int identifier used with ++ or --");
	    }
	}
    }

    /** unparse **/
    public void unparse(PrintWriter p, int indent) {
	p.print("++");
	myId.unparse(p,0);
	p.println(";");
    }

    public void codeGen(){
        myId.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("addi", "increase by 1", Codegen.T1, Codegen.T2, "1");
        myId.assignVar();
    }
    
    
    public void codeGen(String prologue){
        myId.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("addi", "increase by 1", Codegen.T1, Codegen.T2, "1");
        myId.assignVar();
    }
    
    public void codeGen(String prologue, int localOffset){
        myId.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("addi", "increase by 1", Codegen.T1, Codegen.T2, "1");
        myId.assignVar(localOffset);
    }
    
    // 1 kid
    private IdNode myId;
}

class PreDecStmtNode extends StmtNode {
    public PreDecStmtNode(IdNode id) {
	myId = id;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myId.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myId.typeCheck();
	if (!isErrType(T)) {
	    if (!isIntType(T)) {
		Errors.fatal(myId.linenum(), myId.charnum(),
			     "Non-int identifier used with ++ or --");
	    }
	}
    }

    /** unparse **/
    public void unparse(PrintWriter p, int indent) {
	p.print("--");
	myId.unparse(p,0);
	p.println(";");
    }
    
    public void codeGen(){
        myId.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("sub", "decrease by 1", Codegen.T1, Codegen.T1, "1");
        myId.assignVar();
    }
    public void codeGen(String prologue){
        myId.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("sub", "decrease by 1", Codegen.T1, Codegen.T1, "1");
        myId.assignVar();
    }
    
    public void codeGen(String prologue, int localOffset){
        myId.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("sub", "decrease by 1", Codegen.T1, Codegen.T1, "1");
        myId.assignVar(localOffset);
    }

    // 1 kid
    private IdNode myId;
}

class PostIncStmtNode extends StmtNode {
    public PostIncStmtNode(IdNode id) {
	myId = id;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myId.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myId.typeCheck();
	if (!isErrType(T)) {
	    if (!isIntType(T)) {
		Errors.fatal(myId.linenum(), myId.charnum(),
			     "Non-int identifier used with ++ or --");
	    }
	}
    }

    /** unparse **/
    public void unparse(PrintWriter p, int indent) {
	myId.unparse(p,0);
	p.println("++;");
    }
    

    public void codeGen(){
        myId.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("addi", "increase by 1", Codegen.T1, Codegen.T2, "1");
        myId.assignVar();
        Codegen.genPush(Codegen.T2, 4);
    }
    
    
    public void codeGen(String prologue){
        myId.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("addi", "increase by 1", Codegen.T1, Codegen.T2, "1");
        myId.assignVar();
        Codegen.genPush(Codegen.T2, 4);
    }
    
    public void codeGen(String prologue, int localOffset){
        myId.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("addi", "increase by 1", Codegen.T1, Codegen.T2, "1");
        myId.assignVar(localOffset);
        Codegen.genPush(Codegen.T2, 4);
    }
    
    
    // 1 kid
    private IdNode myId;
}

class PostDecStmtNode extends StmtNode {
    public PostDecStmtNode(IdNode id) {
	myId = id;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myId.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myId.typeCheck();
	if (!isErrType(T)) {
	    if (!isIntType(T)) {
		Errors.fatal(myId.linenum(), myId.charnum(),
			     "Non-int identifier used with ++ or --");
	    }
	}
    }

    /** unparse **/
    public void unparse(PrintWriter p, int indent) {
	myId.unparse(p,0);
	p.println("--;");
    }
    
    public void codeGen(){
        myId.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("sub", "decrease by 1", Codegen.T1, Codegen.T1, "1");
        myId.assignVar();
        Codegen.genPush(Codegen.T2, 4);
    }
    public void codeGen(String prologue){
        myId.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("sub", "decrease by 1", Codegen.T1, Codegen.T1, "1");
        myId.assignVar();
        Codegen.genPush(Codegen.T2, 4);
    }
    
    public void codeGen(String prologue, int localOffset){
        myId.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("sub", "decrease by 1", Codegen.T1, Codegen.T1, "1");
        myId.assignVar(localOffset);
        Codegen.genPush(Codegen.T2, 4);
    }

    // 1 kid
    private IdNode myId;
}

class ReadIntStmtNode extends StmtNode {
    public ReadIntStmtNode(IdNode id) {
	myId = id;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myId.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myId.typeCheck();
	if (!isIntType(T)) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Attempt to read a non-int id with an int format");
	}
    }

    /** unparse **/
    public void unparse(PrintWriter p, int indent) {
	p.print("scanf(\"%d\", &");
	myId.unparse(p,0);
	p.println(");");
    }

    public void codeGen(String prologue){
        Codegen.generate("li", Codegen.V0, 5);
        Codegen.generateWithComment("syscall", "read in Integer");
        Codegen.genPush(Codegen.V0, 4);
        Codegen.genPop(Codegen.T1, 4);
        myId.assignVar();
    }
    
    public void codeGen(String prologue, int localOffset){
        Codegen.generate("li", Codegen.V0, 5);
        Codegen.generateWithComment("syscall", "read in Integer");
        Codegen.genPush(Codegen.V0, 4);
        Codegen.genPop(Codegen.T1, 4);
        myId.assignVar(localOffset);
    }
    
    // 1 kid
    private IdNode myId;
}

class ReadDblStmtNode extends StmtNode {
    public ReadDblStmtNode(IdNode id) {
	myId = id;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myId.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myId.typeCheck();
	if (!isDblType(T)) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Attempt to read a non-double id with a dbl format");
	}
    }

    /** unparse **/
    public void unparse(PrintWriter p, int indent) {
	p.print("scanf(\"%f\", &");
	myId.unparse(p,0);
	p.println(");");
    }

    // 1 kid
    private IdNode myId;
}

class WriteIntStmtNode extends StmtNode {
    public WriteIntStmtNode(ExpNode exp) {
	myExp = exp;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myExp.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myExp.typeCheck();
	if (!isIntType(T) && !isErrType(T)) {
	    Errors.fatal(myExp.linenum(), myExp.charnum(),
			 "Attempt to write a non-int value with an int format");
	}
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("printf(\"%d\", ");
	myExp.unparse(p,0);
	p.println(");");
    }

    public void codeGen(){
        myExp.codeInt();
        Codegen.genPop(Codegen.A0, 4);
        Codegen.generate("li", Codegen.V0, 1);
        Codegen.generateWithComment("syscall", "write an Integer");
    }
    
    public void codeGen(String prologue){
        myExp.codeInt();
        Codegen.genPop(Codegen.A0, 4);
        Codegen.generate("li", Codegen.V0, 1);
        Codegen.generateWithComment("syscall", "write an Integer");
    }
    
    public void codeGen(String prologue, int localOffset){
        myExp.codeInt(localOffset);
        Codegen.genPop(Codegen.A0, 4);
        Codegen.generate("li", Codegen.V0, 1);
        Codegen.generateWithComment("syscall", "write an Integer");
    }
    
    // 1 kid
    private ExpNode myExp;
}

class WriteDblStmtNode extends StmtNode {
    public WriteDblStmtNode(ExpNode exp) {
	myExp = exp;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myExp.processNames(S);
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myExp.typeCheck();
	if (!isDblType(T) && !isErrType(T)) {
	    Errors.fatal(myExp.linenum(), myExp.charnum(),
			 "Attempt to write a non-double value with a dbl format");
	}
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("printf(\"%d\", ");
	myExp.unparse(p,0);
	p.println(");");
    }

    public void codeGen(){
        myExp.codeGen();
        Codegen.genPop(Codegen.F12, 8);
        Codegen.generate("li", Codegen.V0, 3);
        Codegen.generate("syscall");
    }
    
    public void codeGen(String prologue){
        myExp.codeGen();
        Codegen.genPop(Codegen.F12, 8);
        Codegen.generate("li", Codegen.V0, 3);
        Codegen.generate("syscall");
    }
    
    public void codeGen(String prologue, int localOffset){
        myExp.codeGen(localOffset);
        Codegen.genPop(Codegen.F12, 8);
        Codegen.generate("li", Codegen.V0, 3);
        Codegen.generate("syscall");
    }
    
    // 1 kid
    private ExpNode myExp;
}

class WriteStrStmtNode extends StmtNode {
    public WriteStrStmtNode(ExpNode exp) {
	myExp = exp;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	/* only a stringliteral is possible, so no need to check */
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	/* only a stringliteral is possible, so no need to typecheck */
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("printf(");
	myExp.unparse(p,0);
	p.println(");");
    }

    public void codeGen(){
        Codegen.generateWithComment("", "WRITE STR");
        myExp.codeGen();
        Codegen.genPop(Codegen.A0, 4);
        Codegen.generate("li", Codegen.V0, 4);
        Codegen.generateWithComment("syscall", "write a string");
    }
    
    public void codeGen(String prologue){
        Codegen.generateWithComment("", "WRITE STR");
        myExp.codeGen();
        Codegen.genPop(Codegen.A0, 4);
        Codegen.generate("li", Codegen.V0, 4);
        Codegen.generateWithComment("syscall", "write a string");
    }
    
    public void codeGen(String prologue, int localOffset){
        Codegen.generateWithComment("", "WRITE STR");
        myExp.codeGen(localOffset);
        Codegen.genPop(Codegen.A0, 4);
        Codegen.generate("li", Codegen.V0, 4);
        Codegen.generateWithComment("syscall", "write a string");
    }
    
    // 1 kid
    private ExpNode myExp;
}

class IfStmtNode extends StmtNode {
    public IfStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
	myDeclList = dlist;
	myExp = exp;
	myStmtList = slist;
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myExp.typeCheck();
	if (! isIntType(T) && ! isErrType(T)) {
	    Errors.fatal(myExp.linenum(), myExp.charnum(),
			 "Non-int expression used as an if condition");
	}
	myStmtList.typeCheck(retType);
    }

    /** processNames
     *  
     *  process the condition, then enter scope; process decls & stmts;
     *  exit scope
     *
     **/
    public void processNames(SymTab S) {
	myExp.processNames(S);
	S.addMap();
	myDeclList.processNames(S);
	myStmtList.processNames(S);
	try {
	    S.removeMap();
	} catch (EmptySymTabException ex) {
		System.err.println("unexpected EmptySymTabException in IfStmtNode.processNames");
		System.exit(-1);
	    }
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("if (");
	myExp.unparse(p,0);
	p.println(") {");
	if (myDeclList != null) myDeclList.unparse(p,indent+2);
	if (myStmtList != null) myStmtList.unparse(p,indent+2);
	doIndent(p, indent);
	p.println("}");
    }

    public void codeGen() {
        String trueLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        myExp.genJumpCode(trueLab, doneLab);
        Codegen.genLabel(trueLab);
        myStmtList.codeGen();
        Codegen.genLabel(doneLab);
    }
    
    public void codeGen(String prologue) {
        String trueLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        myExp.genJumpCode(trueLab, doneLab);
        Codegen.genLabel(trueLab);
        myStmtList.codeGen(prologue);
        Codegen.genLabel(doneLab);
    }
    
    public void codeGen(String prologue, int localOffset) {
        String trueLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        myExp.genJumpCode(trueLab, doneLab, localOffset);
        Codegen.genLabel(trueLab);
        myStmtList.codeGen(prologue, localOffset);
        Codegen.genLabel(doneLab);
    }
    
    // 3 kids
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class IfElseStmtNode extends StmtNode {
    public IfElseStmtNode(ExpNode exp, DeclListNode dlist1,
			  StmtListNode slist1, DeclListNode dlist2,
			  StmtListNode slist2) {
	myExp = exp;
	myThenDeclList = dlist1;
	myThenStmtList = slist1;
	myElseDeclList = dlist2;
	myElseStmtList = slist2;
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myExp.typeCheck();
	if (! isIntType(T) && ! isErrType(T)) {
	    Errors.fatal(myExp.linenum(), myExp.charnum(),
			 "Non-int expression used as an if condition");
	}
	myThenStmtList.typeCheck(retType);
	myElseStmtList.typeCheck(retType);
    }

    /** processNames
     *  
     *  process the condition, then enter scope; process decls & stmts
     *  in "then" part; then exit scope; enter scope; process decls &
     *  stmts in "else" part; exit scope
     *
     **/
    public void processNames(SymTab S) {
	myExp.processNames(S);
	S.addMap();
	myThenDeclList.processNames(S);
	myThenStmtList.processNames(S);
	try {
	    S.removeMap();
	} catch (EmptySymTabException ex) {
		System.err.println("unexpected EmptySymTabException in IfElseStmtNode.processNames");
		System.exit(-1);
	    }
	S.addMap();
	myElseDeclList.processNames(S);
	myElseStmtList.processNames(S);
	try {
	    S.removeMap();
	} catch (EmptySymTabException ex) {
		System.err.println("unexpected EmptySymTabException in IfElseStmtNode.processNames");
		System.exit(-1);
	    }
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("if (");
	myExp.unparse(p,0);
	p.println(") {");
	if (myThenDeclList != null) myThenDeclList.unparse(p,indent+2);
	if (myThenStmtList != null) myThenStmtList.unparse(p,indent+2);
	doIndent(p, indent);
	p.println("}");
	doIndent(p, indent);
	p.println("else {");
	if (myElseDeclList != null) myElseDeclList.unparse(p,indent+2);
	if (myElseStmtList != null) myElseStmtList.unparse(p,indent+2);
	doIndent(p, indent);
	p.println("}");
    }

    public void codeGen() {
        String trueLab = Codegen.nextLabel();
        String elseLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        myExp.genJumpCode(trueLab, elseLab);
        Codegen.genLabel(trueLab);
        myThenDeclList.codeGen();
        myThenStmtList.codeGen();
        Codegen.generateWithComment("b", "skip else statements", doneLab);
        Codegen.genLabel(elseLab);
        myElseDeclList.codeGen();
        myElseStmtList.codeGen();
        Codegen.genLabel(doneLab);
        
    }    
    
    public void codeGen(String prologue) {
        String trueLab = Codegen.nextLabel();
        String elseLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        myExp.genJumpCode(trueLab, elseLab);
        Codegen.genLabel(trueLab);
        myThenDeclList.codeGen();
        myThenStmtList.codeGen(prologue);
        Codegen.generateWithComment("b", "skip else statements", doneLab);
        Codegen.genLabel(elseLab);
        myElseDeclList.codeGen();
        myElseStmtList.codeGen(prologue);
        Codegen.genLabel(doneLab);
    } 
    
    public void codeGen(String prologue, int localOffset) {
        String trueLab = Codegen.nextLabel();
        String elseLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        myExp.genJumpCode(trueLab, elseLab, localOffset);
        Codegen.genLabel(trueLab);
        myThenDeclList.codeGen();
        myThenStmtList.codeGen(prologue, localOffset);
        Codegen.generateWithComment("b", "skip else statements", doneLab);
        Codegen.genLabel(elseLab);
        myElseDeclList.codeGen();
        myElseStmtList.codeGen(prologue, localOffset);
        Codegen.genLabel(doneLab);
    } 
    
    // 5 kids
    private ExpNode myExp;
    private DeclListNode myThenDeclList;
    private StmtListNode myThenStmtList;
    private StmtListNode myElseStmtList;
    private DeclListNode myElseDeclList;
}

class WhileStmtNode extends StmtNode {
    public WhileStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
	myExp = exp;
	myDeclList = dlist;
	myStmtList = slist;
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	String T = myExp.typeCheck();
	if (! isIntType(T) && ! isErrType(T)) {
	    Errors.fatal(myExp.linenum(), myExp.charnum(),
			 "Non-int expression used as a while condition");
	}
	myStmtList.typeCheck(retType);
    }

    /** processNames
     *  
     *  process the condition, then enter scope; process decls & stmts;
     *  exit scope
     *
     **/
    public void processNames(SymTab S) {
	myExp.processNames(S);
	S.addMap();
	myDeclList.processNames(S);
	myStmtList.processNames(S);
	try {
	    S.removeMap();
	} catch (EmptySymTabException ex) {
		System.err.println("unexpected EmptySymTabException in WhileStmtNode.processNames");
		System.exit(-1);
	    }
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("while (");
	myExp.unparse(p,0);
	p.println(") {");
	if (myDeclList != null) myDeclList.unparse(p,indent+2);
	if (myStmtList != null) myStmtList.unparse(p,indent+2);
	doIndent(p, indent);
	p.println("}");
    }

    public void codeGen() {
        String whileLab = Codegen.nextLabel();
        String trueLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        Codegen.genLabel(whileLab);
        myExp.genJumpCode(trueLab, doneLab);
        Codegen.genLabel(trueLab);
        myStmtList.codeGen();
        Codegen.generateWithComment("b", "check while condition", whileLab);
        Codegen.genLabel(doneLab);
    }
    
    public void codeGen(String prologue) {
        String whileLab = Codegen.nextLabel();
        String trueLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        Codegen.genLabel(whileLab);
        myExp.genJumpCode(trueLab, doneLab);
        Codegen.genLabel(trueLab);
        myStmtList.codeGen(prologue);
        Codegen.generateWithComment("b", "check while condition", whileLab);
        Codegen.genLabel(doneLab);
    }
    
    public void codeGen(String prologue, int localOffset) {
        String whileLab = Codegen.nextLabel();
        String trueLab = Codegen.nextLabel();
        String doneLab = Codegen.nextLabel();
        Codegen.genLabel(whileLab);
        myExp.genJumpCode(trueLab, doneLab, localOffset);
        Codegen.genLabel(trueLab);
        myStmtList.codeGen(prologue, localOffset);
        Codegen.generateWithComment("b", "check while condition", whileLab);
        Codegen.genLabel(doneLab);
    }
    
    // 3 kids
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class CallStmtNode extends StmtNode {
    public CallStmtNode(CallExpNode call) {
	myCall = call;
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	myCall.typeCheck();
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myCall.processNames(S);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	myCall.unparse(p,indent);
	p.println(";");
    }

    public void codeGen(){
        myCall.codeGen();
    }
    
    public void codeGen(String prologue){
        myCall.codeGen();
    }
    
    public void codeGen(String prologue, int localOffset){
        myCall.codeGen(localOffset);
    }
    
    // 1 kid
    private CallExpNode myCall;
}

class ReturnStmtNode extends StmtNode {
    public ReturnStmtNode(ExpNode exp) {
	myExp = exp;
    }

    /** typeCheck **/
    public void typeCheck(String retType) {
	if (myExp != null) {
	    // return with a value
	    // error if
	    // (a) fn return type is void, or
	    // (b) value type is non-numeric, or
	    // (c) value type is dbl and return type is int
	    String T = myExp.typeCheck();
	    if (isVoidType(retType)) {
		Errors.fatal(myExp.linenum(), myExp.charnum(),
			     "Return with a value in a void function");
		return;
	    }
	    if (isErrType(T)) return;
	    if (! isNumericType(T) || (isDblType(T) && isIntType(retType))) {
		Errors.fatal(myExp.linenum(), myExp.charnum(),
			     "Bad return value");
	    }
	}
	else {
	    // return w/o value
	    // error if fn return type is NOT void
	    if (! isVoidType(retType)) {
		Errors.fatal(0, 0, "Missing return value");
	    }
	}
    }

    /** processNames **/
    public void processNames(SymTab S) {
	if (myExp != null) myExp.processNames(S);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("return");
	if (myExp != null) {
	    p.print(" ");
	    myExp.unparse(p,0);
	}
	p.println(";");
    }

    public void codeGen(){
        if (myExp != null){
            myExp.codeGen();
            Codegen.genPop(Codegen.V0, 4);
        }
    }
    
    public void codeGen(String prologue){
        if (myExp != null){
            myExp.codeGen();
            Codegen.genPop(Codegen.V0, 4);
            Codegen.genPush(Codegen.V0, 4);
        }
        Codegen.generateWithComment("j", "jump to function end", prologue);
    }
    
    public void codeGen(String prologue, int localOffset){
        if (myExp != null){
            myExp.codeGen(localOffset);
            Codegen.genPop(Codegen.V0, 4);
            Codegen.genPush(Codegen.V0, 4);
        }
        Codegen.generateWithComment("j", "jump to function end", prologue);
    }
    
    // 1 kid
    private ExpNode myExp;
}

// **********************************************************************
// ExpNode and its subclasses
// **********************************************************************

abstract class ExpNode extends ASTnode {
    // default version of processNames (for nodes with no names)
    public void processNames(SymTab S) {}

    abstract public String typeCheck();
    abstract public int linenum();
    abstract public int charnum();
    abstract public void codeGen();
    public void codeInt(){}
    public void assignVar(){}
    public void codeInt(int localOffset){}
    public void assignVar(int localOffset){}
    public void codeGen(int localOffset){}
    public int getValue(){return 0;}
    public void genJumpCode(String trueLab, String doneLab){}
    public void genJumpCode(String trueLab, String doneLab, int localOffset){}
    public int sizeOfVar(){
        if (typeCheck().equals("double")) return 8;
        else return 4;
    }
}

class IntLitNode extends ExpNode {
    public IntLitNode(int lineNum, int charNum, int intVal) {
	myLineNum = lineNum;
	myCharNum = charNum;
	myIntVal = intVal;
    }

    /** typeCheck **/
    public String typeCheck() {
	return INT_TYPE;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print(myIntVal);
    }

    /** linenum **/
    public int linenum() {
	return myLineNum;
    }

    /** charnum **/
    public int charnum() {
	return myCharNum;
    }

    public void codeGen(){
        String s = "" + myIntVal;
        Codegen.generate("li", Codegen.T1, s);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        String s = "" + myIntVal;
        Codegen.generate("li", Codegen.T1, s);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeInt(){
        String s = "" + myIntVal;
        Codegen.generate("li", Codegen.T0, s);
        Codegen.genPush(Codegen.T0, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
            Codegen.generateWithComment("li", "generate jump code", Codegen.T0, "" + myIntVal);
            Codegen.generate("beqz", Codegen.T0, doneLab);
            Codegen.generate("b", trueLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        Codegen.generateWithComment("li", "generate jump code", Codegen.T0, "" + myIntVal);
        Codegen.generate("beqz", Codegen.T0, doneLab);
        Codegen.generate("b", trueLab);
    }
    
    public int getValue(){
        return myIntVal;
    }
    
    private int myLineNum;
    private int myCharNum;
    private int myIntVal;
}

class DblLitNode extends ExpNode {
    public DblLitNode(int lineNum, int charNum, double dblVal) {
	myLineNum = lineNum;
	myCharNum = charNum;
	myDblVal = dblVal;
    }

    /** typeCheck **/
    public String typeCheck() {
	return DBL_TYPE;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print(myDblVal);
    }

    /** linenum **/
    public int linenum() {
	return myLineNum;
    }

    /** charnum **/
    public int charnum() {
	return myCharNum;
    }

    public void codeGen(){
        String s = "" + myDblVal;
        Codegen.generate("li.d", Codegen.F0, s);
        Codegen.genPush(Codegen.F0, 8);
    }
    
    public void codeGen(int localOffset){
        String s = "" + myDblVal;
        Codegen.generate("li.d", Codegen.F0, s);
        Codegen.genPush(Codegen.F0, 8);
    }
    
    private int myLineNum;
    private int myCharNum;
    private double myDblVal;
}

class StringLitNode extends ExpNode {
    public StringLitNode(int lineNum, int charNum, String strVal) {
	myLineNum = lineNum;
	myCharNum = charNum;
	myStrVal = strVal;
    }

    /** typeCheck **/
    public String typeCheck() {
	return "string";
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print(myStrVal);
    }

    /** linenum **/
    public int linenum() {
	return myLineNum;
    }

    /** charnum **/
    public int charnum() {
	return myCharNum;
    }

    public void codeGen(){
        Codegen.generate(".data");
        String label = Codegen.nextLabel();
        Codegen.generateLabeled(label, ".asciiz", "", myStrVal);
        Codegen.generate(".text");
        Codegen.generate("la", Codegen.T0, label);
        Codegen.genPush(Codegen.T0, 4);
    }
    
    public void codeGen(int localOffset){
        Codegen.generate(".data");
        String label = Codegen.nextLabel();
        Codegen.generateLabeled(label, ".asciiz", "", myStrVal);
        Codegen.generate(".text");
        Codegen.generate("la", Codegen.T0, label);
        Codegen.genPush(Codegen.T0, 4);
    }
    
    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
}

class IdNode extends ExpNode {
    public IdNode(int lineNum, int charNum, String strVal) {
	myLineNum = lineNum;
	myCharNum = charNum;
	myStrVal = strVal;
    }

    /** typeCheck **/
    public String typeCheck() {
	if (mySym != null) return mySym.type();
	else {
	    System.err.println("ID with null sym field in IdNode.typeCheck");
	    System.exit(-1);
	}
	return null;
    }

    /** processNames
     *
     * check for use of an undeclared name
     * if OK, link to symtab entry
     *
     **/
    public void processNames(SymTab S) {
	Sym sym = S.globalLookup(myStrVal);
	if (sym  == null) {
	    Errors.fatal(myLineNum, myCharNum,
			 "Undeclared identifier");
	} else {
	    link(sym);
	}
    }

    /** link **/
    public void link(Sym sym) {
	mySym = sym;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print(myStrVal);
		if (mySym == null){
			p.print("(void)");
		} else{
			p.print("(" + mySym.type() + ")");
		}
    }

    /** name **/
    public String name() {
	return myStrVal;
    }

    /** type **/
    public String type() {
	if (mySym != null) return mySym.type();
	else {
	    System.err.println("ID with null sym field");
	    System.exit(-1);
	}
	return null;
    }

    /** symbol-table entry */
    public Sym sym() {
	return mySym;
    }

    /** line num **/
    public int linenum() {
	return myLineNum;
    }

    /** char num **/
    public int charnum() {
	return myCharNum;
    }

    public void codeGen(){
        if (mySym.type().equals("int")){
            if (mySym.getGlobal()){
                Codegen.generate("lw", Codegen.T1, "_" + myStrVal);
                Codegen.genPush(Codegen.T1, 4);
            }
            else{
                Codegen.generateIndexed("lw", Codegen.T1, Codegen.FP, mySym.getFPOffset() - 8);
                Codegen.genPush(Codegen.T1, 4);
            }
        }
        else{
            if (mySym.getGlobal()){
                Codegen.generate("l.d", Codegen.F0, "_" + myStrVal);
            }
            else{
                Codegen.generateIndexed("l.d", Codegen.F0, Codegen.FP, mySym.getFPOffset() - 8);
            }
        }
    }
    
    public void codeGen(int localOffset){
        if (mySym.type().equals("int")){
            if (mySym.getGlobal()){
                Codegen.generate("lw", Codegen.T1, "_" + myStrVal);
                Codegen.genPush(Codegen.T1, 4);
            }
            else{
                int temp = localOffset + mySym.getFPOffset() - 8;
                if (mySym.getParam()){
                    Codegen.generateIndexed("lw", Codegen.T1, Codegen.FP, mySym.getFPOffset());
                }
                    else {
                Codegen.generateIndexed("lw", Codegen.T1, Codegen.FP, localOffset + mySym.getFPOffset() - 8);
                    }
                Codegen.genPush(Codegen.T1, 4);
            }
        }
        else{
            if (mySym.getGlobal()){
                Codegen.generate("l.d", Codegen.F0, "_" + myStrVal);
            }
            else{
                Codegen.generateIndexed("l.d", Codegen.F0, Codegen.FP, mySym.getFPOffset() - 8);
            }
        }
    }
    
    public void assignVar(){
        if (mySym.type().equals("int")){
            if (mySym.getGlobal()){
                Codegen.genPush(Codegen.T1, 4);
                Codegen.generate("sw", Codegen.T1, "_" + myStrVal);
            }
            else{
                Codegen.genPush(Codegen.T1, 4);
                Codegen.generateIndexed("sw", Codegen.T1, Codegen.FP, mySym.getFPOffset() - 8);
            }
        }
        else{
            if (mySym.getOffset() <= 0){
                Codegen.generate("l.d", Codegen.F0, "_" + myStrVal);
            }
            else{
                Codegen.generateIndexed("l.d", Codegen.F0, Codegen.FP, mySym.getFPOffset() - 8);
            }
        }
    }
    
    public void assignVar(int localOffset){
        if (mySym.type().equals("int")){
            if (mySym.getGlobal()){
                Codegen.genPush(Codegen.T1, 4);
                Codegen.generate("sw", Codegen.T1, "_" + myStrVal);
            }
            else{
                Codegen.genPush(Codegen.T1, 4);
                int temp = localOffset + mySym.getFPOffset() - 8;
                if (mySym.getParam()){
                 Codegen.generateIndexed("sw", Codegen.T1, Codegen.FP, mySym.getFPOffset());
                }
                    else {
                Codegen.generateIndexed("sw", Codegen.T1, Codegen.FP, localOffset + mySym.getFPOffset() - 8);
                    }
            }
        }
        else{
            if (mySym.getOffset() <= 0){
                Codegen.generate("l.d", Codegen.F0, "_" + myStrVal);
            }
            else{
                Codegen.generateIndexed("l.d", Codegen.F0, Codegen.FP, mySym.getFPOffset() - 8);
            }
        }
    }
    
    public void codeInt(){
        if (mySym.type().equals("int")){
            if (mySym.getGlobal()){
                Codegen.generate("lw", Codegen.T0, "_" + myStrVal);
                Codegen.genPush(Codegen.T0, 4);
            }
            else{
                Codegen.generateIndexed("lw", Codegen.T0, Codegen.FP, mySym.getFPOffset() - 8);
                Codegen.genPush(Codegen.T0, 4);
            }
        }
        else{
            if (mySym.getOffset() <= 0){
                Codegen.generate("l.d", Codegen.F0, "_" + myStrVal);
            }
            else{
                Codegen.generateIndexed("l.d", Codegen.F0, Codegen.FP, mySym.getFPOffset() - 8);
            }
        }
    }
    
    public void codeInt(int localOffset){
        if (mySym.type().equals("int")){
            if (mySym.getGlobal()){
                Codegen.generate("lw", Codegen.T0, "_" + myStrVal);
                Codegen.genPush(Codegen.T0, 4);
            }
            else{
                int temp = localOffset + mySym.getFPOffset() - 8;
                if (mySym.getParam()){
                    Codegen.generateIndexed("lw", Codegen.T0, Codegen.FP, mySym.getFPOffset());
                }
                    else {
                Codegen.generateIndexed("lw", Codegen.T0, Codegen.FP, localOffset + mySym.getFPOffset() - 8);
                    }
                Codegen.genPush(Codegen.T0, 4);
            }
        }
        else{
            if (mySym.getOffset() <= 0){
                Codegen.generate("l.d", Codegen.F0, "_" + myStrVal);
            }
            else{
                Codegen.generateIndexed("l.d", Codegen.F0, Codegen.FP, mySym.getFPOffset() - 8);
            }
        }
    }
    
    public void genJumpAndLink(){
        if (myStrVal.equals("main")) Codegen.generate("jal", "main");
        else Codegen.generateWithComment("jal", "jump to function", "_" + myStrVal);
    }
    
    public void genAddr(){
        if (mySym.getGlobal()){
            Codegen.generate("la", Codegen.T0, "_" + myStrVal);
            Codegen.generate("sw", Codegen.T1, "0(" + Codegen.T0 + ")");
            
        }
        else{
            Codegen.generateIndexed("la", Codegen.T0, Codegen.FP, -1 * mySym.getOffset());
        }
    }
    
    
    public void genJumpCode(String trueLab, String doneLab){
        if (mySym.getGlobal()){
            Codegen.generate("lw", Codegen.T0, "_" + myStrVal);
            Codegen.generate("beqz", Codegen.T0, doneLab);
            Codegen.generate("b", trueLab);
        }
        else {
            Codegen.generateIndexed("lw", Codegen.T0, Codegen.FP, mySym.getFPOffset() - 8);
            Codegen.generate("beqz", Codegen.T0, doneLab);
            Codegen.generate("b", trueLab);
        }
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        if (mySym.getGlobal()){
            Codegen.generate("lw", Codegen.T0, "_" + myStrVal);
            Codegen.generate("beqz", Codegen.T0, doneLab);
            Codegen.generate("b", trueLab);
        }
        else {
            if (mySym.getParam()){
              Codegen.generateIndexed("lw", Codegen.T0, Codegen.FP, mySym.getFPOffset());
            }
              else  {
                    Codegen.generateIndexed("lw", Codegen.T0, Codegen.FP, mySym.getFPOffset() - 8);
                }
            
            Codegen.generate("beqz", Codegen.T0, doneLab);
            Codegen.generate("b", trueLab);
        }
    }
    
    // fields
    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
    private Sym mySym;
}

class CallExpNode extends ExpNode {
    public CallExpNode(IdNode name, ExpListNode elist) {
	myId = name;
	myExpList = elist;
    }

    /** typeCheck **/
    public String typeCheck() {
	String T = myId.typeCheck();
	// check that ID is a fn
	if (! isFnType(T)) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Attempt to call a non-function");
	    return ERR_TYPE;
	}

	// check number of args
	FnSym s = (FnSym)myId.sym();
	if (s == null) {
	    System.exit(-1);
	}

	int numParams = s.numparams();
	if (numParams != myExpList.length()) {
	    Errors.fatal(myId.linenum(), myId.charnum(),
			 "Function call with wrong number of args");
	    return s.returnType();
	}

	// check type of each arg
	myExpList.typeCheck(s.paramTypes());
	return s.returnType();
    }

    /** processNames 
     *
     * process name of called fn and all actuals
     **/
    public void processNames(SymTab S) {
	myId.processNames(S);
	myExpList.processNames(S);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	myId.unparse(p,0);
	p.print("(");
	if (myExpList != null) myExpList.unparse(p,0);
	p.print(")");
    }

    /** linenum **/
    public int linenum() {
	return myId.linenum();
    }

    /** charnum **/
    public int charnum() {
	return myId.charnum();
    }
    
    public void codeGen(){
        myExpList.codeGen();
        myId.genJumpAndLink();
        Codegen.genPush(Codegen.V0, 4);
    }
    
    public void codeGen(int localOffset){
        myExpList.codeGen(localOffset);
        myId.genJumpAndLink();
        Codegen.genPush(Codegen.V0, 4);
    }

    // 2 kids
    private IdNode myId;
    private ExpListNode myExpList;
}

abstract class UnaryExpNode extends ExpNode {
    public UnaryExpNode(ExpNode exp) {
	myExp = exp;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myExp.processNames(S);
    }

    /** linenum **/
    public int linenum() {
	return myExp.linenum();
    }

    /** charnum **/
    public int charnum() {
	return myExp.charnum();
    }

    
    public void codeGen(){
        myExp.codeGen();
    }
    
    public void codeGen(int localOffset){
        myExp.codeGen(localOffset);
    }
    
    // one kid
    protected ExpNode myExp;
}

abstract class BinaryExpNode extends ExpNode {
    public BinaryExpNode(ExpNode exp1, ExpNode exp2) {
	myExp1 = exp1;
	myExp2 = exp2;
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myExp1.processNames(S);
	myExp2.processNames(S);
    }

    /** linenum **/
    public int linenum() {
	return myExp1.linenum();
    }

    /** charnum **/
    public int charnum() {
	return myExp1.charnum();
    }

    public void codeGen(){
        
    }
    
    public void codeGen(int localOffset){
        
    }
    
    // two kids
    protected ExpNode myExp1;
    protected ExpNode myExp2;
}

// **********************************************************************
// Subclasses of UnaryExpNode
// **********************************************************************

class PlusPlusNode extends UnaryExpNode {
    public PlusPlusNode(IdNode id) {
	super(id);
    }

    /** typeCheck **/
    public String typeCheck() {
	String T = myExp.typeCheck();
	if (! isErrType(T)) {
	    if (! isIntType(T)) {
		Errors.fatal(myExp.linenum(), myExp.charnum(),
			     "Non-int identifier used with ++ or --");
		return ERR_TYPE;
	    }
	    return INT_TYPE;
	} else return T;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp.unparse(p, 0);
	p.print("++");
	p.print(")");
    }
    
    public void codeGen(){
        myExp.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("addi", "increase by 1", Codegen.T1, Codegen.T2, "1");
        myExp.assignVar();
        Codegen.genPush(Codegen.T2, 4);
    }
    
    public void codeGen(int localOffset){
        myExp.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("addi", "increase by 1", Codegen.T1, Codegen.T2, "1");
        myExp.assignVar(localOffset);
        Codegen.genPush(Codegen.T2, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp.genJumpCode(trueLab, doneLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp.genJumpCode(trueLab, doneLab, localOffset);
    }
}

class MinusMinusNode extends UnaryExpNode {
    public MinusMinusNode(IdNode id) {
	super(id);
    }

    /** typeCheck **/
    public String typeCheck() {
	String T = myExp.typeCheck();
	if (! isErrType(T)) {
	    if (! isIntType(T)) {
		Errors.fatal(myExp.linenum(), myExp.charnum(),
			     "Non-int identifier used with ++ or --");
		return ERR_TYPE;
	    }
	    return INT_TYPE;
	} else return T;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp.unparse(p, 0);
	p.print("--");
	p.print(")");
    }
    
    public void codeGen(){
        myExp.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("sub", "decrease by 1", Codegen.T1, Codegen.T1, "1");
        myExp.assignVar();
        Codegen.genPush(Codegen.T2, 4);
    }
    
    public void codeGen(int localOffset){
        myExp.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.generateWithComment("sub", "decrease by 1", Codegen.T1, Codegen.T1, "1");
        myExp.assignVar(localOffset);
        Codegen.genPush(Codegen.T2, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp.genJumpCode(trueLab, doneLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp.genJumpCode(trueLab, doneLab, localOffset);
    }
}

class UnaryMinusNode extends UnaryExpNode {
    public UnaryMinusNode(ExpNode exp) {
	super(exp);
    }

    /** typeCheck **/
    public String typeCheck() {
	String T = myExp.typeCheck();
	if (! isNumericType(T) && ! isErrType(T)) {
	    Errors.fatal(myExp.linenum(), myExp.charnum(),
			 "Illegal use of non-numeric operand");
	    return ERR_TYPE;
	}
	return T;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(-");
	myExp.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp.codeGen();
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("li", Codegen.T2, "-1");
        Codegen.generate("mult", Codegen.T1, Codegen.T2);
        Codegen.generateWithComment("mflo", "move from lo after multiplying", Codegen.T1);
        Codegen.genPush(Codegen.T1, 4);
        myExp.assignVar();
    }
    
    public void codeGen(int localOffset){
        myExp.codeGen(localOffset);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("li", Codegen.T2, "-1");
        Codegen.generate("mult", Codegen.T1, Codegen.T2);
        Codegen.generateWithComment("mflo", "move from lo after multiplying", Codegen.T1);
        Codegen.genPush(Codegen.T1, 4);
        myExp.assignVar(localOffset);
    }
}

class NotNode extends UnaryExpNode {
    public NotNode(ExpNode exp) {
	super(exp);
    }

    /** typeCheck **/
    public String typeCheck() {
	String T = myExp.typeCheck();
	if (! isIntType(T) && ! isErrType(T)) {
	    Errors.fatal(myExp.linenum(), myExp.charnum(),
			 "Logical operator applied to non-int operand");
	    return ERR_TYPE;
	}
	return T;
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(!");
	myExp.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp.codeGen();
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("not", Codegen.T1, Codegen.T1);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp.codeGen(localOffset);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("not", Codegen.T1, Codegen.T1);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp.genJumpCode(doneLab, trueLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp.genJumpCode(doneLab, trueLab, localOffset);
    }

}

// **********************************************************************
// Subclasses of BinaryExpNode
// **********************************************************************

class AssignNode extends BinaryExpNode {
    private static final boolean DEBUG = false;

    public AssignNode(ExpNode lhs, ExpNode exp) {
	super(lhs, exp);
    }

    /** typeCheck **/
    public String typeCheck() {
	String retType;
	String T1 = myExp1.typeCheck();
	String T2 = retType = myExp2.typeCheck();
	if (! isNumericType(T1) && ! isErrType(T1)) {
	    Errors.fatal(myExp1.linenum(), myExp1.charnum(),
			 "Illegal use of non-numeric operand");
	    T1 = ERR_TYPE;
	}
	if (! isNumericType(T2) && ! isErrType(T2)) {
	    Errors.fatal(myExp2.linenum(), myExp2.charnum(),
			 "Illegal use of non-numeric operand");
	    return ERR_TYPE;
	}
	if (isErrType(T1) || isErrType(T2)) return ERR_TYPE;
	if (isIntType(T1) && isDblType(T2)) {
	    Errors.fatal(myExp2.linenum(), myExp2.charnum(),
			 "Possible loss of precision");
	    return ERR_TYPE;
	}
	return T1;
    }

    // ** unparse **
    //
    // Two versions: One called from the unparse method of
    // AssignStmtNode -- do NOT enclose this assignment in parens;
    // The other called whenever this assignment really is an
    // expression, not a stmt, so DO enclose this assignment in
    // parens.
    
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print(" = ");
	myExp2.unparse(p,0);
	p.print(")");
    }

    /** processNames **/
    public void processNames(SymTab S) {
	myExp1.processNames(S);
	myExp2.processNames(S);
    }

    public void unparse(PrintWriter p, int indent, boolean b) {
	myExp1.unparse(p, 0);
	p.print(" = ");
	myExp2.unparse(p,0);
    }
    
    public void codeGen(){
        myExp2.codeGen();                                   // put value on top of stack
        Codegen.genPop(Codegen.T1, myExp2.sizeOfVar());
        //Codegen.genPush(Codegen.T1, myExp2.sizeOfVar());    // value in T1 and on TOS
        if (((IdNode)myExp1).sym().getGlobal()){
            ((IdNode)myExp1).genAddr();
            //Codegen.genPush(Codegen.T1, myExp2.sizeOfVar());
        }    
        else{
            ((IdNode)myExp1).assignVar();
            //Codegen.genPush(Codegen.T1, myExp2.sizeOfVar());
        }    
    }
    
    public void codeGen(int localOffset){
        myExp2.codeGen(localOffset);                                   // put value on top of stack
        Codegen.genPop(Codegen.T1, myExp2.sizeOfVar());
        //Codegen.genPush(Codegen.T1, myExp2.sizeOfVar());    // value in T1 and on TOS
        if (((IdNode)myExp1).sym().getGlobal()){
            ((IdNode)myExp1).genAddr();
            //Codegen.genPush(Codegen.T1, myExp2.sizeOfVar());
        }    
        else{
            ((IdNode)myExp1).assignVar(localOffset);
            //Codegen.genPush(Codegen.T1, myExp2.sizeOfVar());
        }    
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp1.genJumpCode(trueLab, doneLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp1.genJumpCode(trueLab, doneLab, localOffset);
    }
}

abstract class ArithmeticBinExpNode extends BinaryExpNode {
    public ArithmeticBinExpNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    /** typeCheck **/
    public String typeCheck() {
	String T1 = myExp1.typeCheck();
	String T2 = myExp2.typeCheck();
	if (! isNumericType(T1) && ! isErrType(T1)) {
	    Errors.fatal(myExp1.linenum(), myExp1.charnum(),
			 "Illegal use of non-numeric operand");
	}
	if (! isNumericType(T2) && ! isErrType(T2)) {
	    Errors.fatal(myExp2.linenum(), myExp2.charnum(),
			 "Illegal use of non-numeric operand");
	    return ERR_TYPE;
	}
	if (isErrType(T1) || isErrType(T2)) return ERR_TYPE;
	else {
	    if (isDblType(T1) || isDblType(T2)) return DBL_TYPE;
	    else return INT_TYPE;
	}
    }
    
    public void codeGen(){
        
    }
    
    public void codeGen(int localOffset){
        
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        
    }
}

abstract class EqualityBinExpNode extends BinaryExpNode {
    public EqualityBinExpNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    /** typeCheck **/
    public String typeCheck() {
	String T1 = myExp1.typeCheck();
	String T2 = myExp2.typeCheck();
	if (! isNumericType(T1) && ! isErrType(T1)) {
	    Errors.fatal(myExp1.linenum(), myExp1.charnum(),
			 "Illegal use of non-numeric operand");
	    T1 = ERR_TYPE;
	}
	if (! isNumericType(T2) && ! isErrType(T2)) {
	    Errors.fatal(myExp2.linenum(), myExp2.charnum(),
			 "Illegal use of non-numeric operand");
	    return ERR_TYPE;
	}
	if (isErrType(T1) || isErrType(T2)) return ERR_TYPE;
	else return INT_TYPE;
    }
    
    public void codeGen(){
        
    }
    
    public void codeGen(int localOffset){
        
    }
    
    public void genJumpCode(String trueLab, String doneLab){
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
    }
}

abstract class LogicalBinExpNode extends BinaryExpNode {
    public LogicalBinExpNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    /** typeCheck **/
    public String typeCheck() {
	String T1 = myExp1.typeCheck();
	String T2 = myExp2.typeCheck();
	String retType = INT_TYPE;
	if (! isIntType(T1) && ! isErrType(T1)) {
	    Errors.fatal(myExp1.linenum(), myExp1.charnum(),
			 "Logical operator applied to non-int operand");
	    retType = ERR_TYPE;
	}
	if (! isIntType(T2) && ! isErrType(T2)) {
	    Errors.fatal(myExp2.linenum(), myExp2.charnum(),
			 "Logical operator applied to non-int operand");
	    retType = ERR_TYPE;
	}
	if (isErrType(T1) || isErrType(T2)) return ERR_TYPE;
	else return retType;
    }
    
    public void codeGen(){
        
    }
    
    public void genJumpCode(String trueLab, String doneLab){

    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        
    }
}

// **********************************************************************
// Subclasses of ArithmeticBinExpNode
// **********************************************************************

class PlusNode extends ArithmeticBinExpNode {
    public PlusNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("+");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){        
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("add", "perform addition", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){       
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("add", "perform addition", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
}

class MinusNode extends ArithmeticBinExpNode {
    public MinusNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("-");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("sub", "generate subtraction", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("sub", "generate subtraction", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
}

class TimesNode extends ArithmeticBinExpNode {
    public TimesNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("*");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("mult", "perform multiplication", Codegen.T1, Codegen.T2);
        Codegen.generate("mflo", Codegen.T1);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("mult", "perform multiplication", Codegen.T1, Codegen.T2);
        Codegen.generate("mflo", Codegen.T1);
        Codegen.genPush(Codegen.T1, 4);
    }
}

class DivideNode extends ArithmeticBinExpNode {
    public DivideNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("/");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("div", "perform division", Codegen.T1, Codegen.T2);
        Codegen.generate("mflo", Codegen.T1);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset );
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("div", "perform division", Codegen.T1, Codegen.T2);
        Codegen.generate("mflo", Codegen.T1);
        Codegen.genPush(Codegen.T1, 4);
    }
}

class EqualsNode extends EqualityBinExpNode {
    public EqualsNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("==");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("seq", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("seq", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("bne", "check if equal", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("bne", "check if equal", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
}

class NotEqualsNode extends EqualityBinExpNode {
    public NotEqualsNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("!=");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("sne", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("sne", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("beq", "check if not equal", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("beq", "check if not equal", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
}

class LessNode extends EqualityBinExpNode {
    public LessNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("<");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("slt", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("slt", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("bge", "check if less than", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("bge", "check if less than", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
}

class GreaterNode extends EqualityBinExpNode {
    public GreaterNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print(">");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("sgt", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("sgt", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("ble", "check if greater than", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("ble", "check if greater than", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
}

class LessEqNode extends EqualityBinExpNode {
    public LessEqNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("<=");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("sle", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("sle", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("bgt", "check if less than or equal", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("bgt", "check if less than or equal", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
}

class GreaterEqNode extends EqualityBinExpNode {
    public GreaterEqNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print(">=");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("sge", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("sge", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("blt", "check if greater than or equal to", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generateWithComment("blt", "check if greater than or equal to", Codegen.T1, Codegen.T2, doneLab);
        Codegen.genPush(Codegen.T1, 4);
        Codegen.generate("b", trueLab);
    }
}


// **********************************************************************
// Subclasses of LogicalBinExpNode
// **********************************************************************

class AndNode extends LogicalBinExpNode {
    public AndNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("&&");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("and", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("and", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        String newLab = Codegen.nextLabel();
        myExp1.genJumpCode(newLab, doneLab);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        Codegen.genLabel(newLab);
        myExp2.genJumpCode(trueLab, doneLab);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        String newLab = Codegen.nextLabel();
        myExp1.genJumpCode(newLab, doneLab, localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        Codegen.genLabel(newLab);
        myExp2.genJumpCode(trueLab, doneLab, localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
    }
    
}

class OrNode extends LogicalBinExpNode {
    public OrNode(ExpNode exp1, ExpNode exp2) {
	super(exp1, exp2);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
	p.print("(");
	myExp1.unparse(p, 0);
	p.print("||");
	myExp2.unparse(p, 0);
	p.print(")");
    }
    
    public void codeGen(){
        myExp1.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen();
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("or", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void codeGen(int localOffset){
        myExp1.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        myExp2.codeGen(localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
        Codegen.generate("or", Codegen.T1, Codegen.T1, Codegen.T2);
        Codegen.genPush(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab){
        String newLab = Codegen.nextLabel();
        myExp1.genJumpCode(trueLab, newLab);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        Codegen.genLabel(newLab);
        myExp2.genJumpCode(trueLab, doneLab);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
    }
    
    public void genJumpCode(String trueLab, String doneLab, int localOffset){
        String newLab = Codegen.nextLabel();
        myExp1.genJumpCode(trueLab, newLab, localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPush(Codegen.T2, 4);
        Codegen.genLabel(newLab);
        myExp2.genJumpCode(trueLab, doneLab, localOffset);
        Codegen.genPop(Codegen.T2, 4);
        Codegen.genPop(Codegen.T1, 4);
    }
}





