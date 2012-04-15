// Error
//
// This class is used to generate warning and fatal error messages.

class Errors {
public static boolean fatalError = false;
    static void fatal(int lineNum, int charNum, String msg) {
        System.err.println(lineNum + ":" + charNum + " **ERROR** " + msg);
    	setStatus();
    }

    static void warn(int lineNum, int charNum, String msg) {
        System.err.println(lineNum + ":" + charNum + " **WARNING** " + msg);
    }
   
    static void setStatus(){
	fatalError = true;
    }

    public static boolean getStatus(){
	return fatalError;
    }
}
