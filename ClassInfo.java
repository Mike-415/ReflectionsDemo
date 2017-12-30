
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class ClassInfo 
{
	private Class<?> argClass; //The class argument from the command line
	static final String FINAL = "final";
	
	
	ClassInfo(Class<?> argClass)
	{
		this.argClass = argClass;
	}

	/**
	 * Prints objects onto the console
	 * @param objects 
	 */
	void print(Object ... objects)
	{
	    if(objects.length == 0)
	    {
	        System.out.print("");
	    }
	    else if(objects.length == 1)
	    {
	        System.out.print("" + objects[0]);
	    }
	    else for(Object obj : objects)
	    {
	        System.out.print("\t"+obj+"\n");
	    }
	    System.out.println("");
	 }//p()
	
	/**
	 * Prints objects onto the console in a particular format 
	 * @param classData
	 * @param dataObjects
	 */
	void printf(String classData, Object...dataObjects)
	{
		print(classData+" list:");
		for(Object dataObject: dataObjects)
		{
			switch(classData)
			{
			case "Constructor": 
				print("\t-"+(Constructor<?>)dataObject);
				break;
			case "Constant":
			case "Variable":
				print("\t-"+(Field)dataObject);
				break;
			case "Method":
				print("\t-"+(Method)dataObject);
				break;
			case "Interface":
				print("\t-"+(Class<?>)dataObject);
				break;
			default:
			//case "Super Class":
				print("\t-"+dataObject);
				break;
			}
		}
		print();
	}
	
	/**
	 * Displays the help option
	 */
	void help()
	{
		print("Command-line format:");
		print("   $ java ClassInfoImplementer <option> <fully qualified class name>\n");
		print("Available options: ");
    	print("   - c : constructors");
    	print("   - C : constants");
    	print("   - i : interfaces");
    	print("   - m : methods");
    	print("   - s : superclass");
    	print("   - v : variables");
	}
	
	/**
	 * Determines if the signature of a field contains the 'final' keyword
	 * @param keyWords
	 * @return true if the final keyword is found
	 */
	boolean hasFinalKeyWord(String[] keyWords)
	{
		for(int i = 0; i< keyWords.length; i++)
		{
			if(keyWords[i].equals(FINAL))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Filters an array of fields for variables or constants
	 * @param fieldArray the array of fields 
	 * @param isConst true if the client needs constants, variables otherwise
	 * @return an array of Objects that the client specified
	 */
	Object[] varConstFilter(Object[] fieldArray, boolean isConst)
	{
		List<Object> filteredArray = new ArrayList<>();
		int indexCount = 0;
		for(Object fieldSignature: fieldArray)
		{
			String[] keyWords = fieldSignature.toString().split(" ");
			boolean containsFinal = hasFinalKeyWord(keyWords);	
			if(isConst && containsFinal)
			{
				filteredArray.add(fieldArray[indexCount]);	
			}
			else if(!isConst && !containsFinal)
			{
				filteredArray.add(fieldArray[indexCount]);
			}
		}	
		return filteredArray.toArray();	
	}
		
	/**
	 * Display the reflective data about a class
	 * @param option the option that that user chose in the CLI
	 */

	void displayClassData(int option)
	{
		switch(option)
	    {	    
		    case 'a': 
		    		//All arguments
		    	    int allOptions[]  = { 'c','C','i','m','s','v' };
		    	    for(int opt: allOptions)
		    	    {
		    	    	displayClassData(opt);
		    	    }
		    		break;
		    case 'c': 
		    		//Constructors
		    		Object constructors[] = argClass.getDeclaredConstructors();
		    		printf("Constructor", constructors);
		    		break;
		    case 'i': 
		    		//Interface
		    		Object interfaces[] = argClass.getInterfaces(); 
		    		printf("Interface", interfaces);          
		    		break;
		    case 'm': 
	    			//Methods
		    		Object methods[] = argClass.getDeclaredMethods();		               
		    		printf("Method" , methods );
	    			break;
		    case 's': 
	    			//Super class
		    		printf("Super Class", argClass.getSuperclass());
	    			break;
		    case 'v': 
					//Variables
    				Object varFields[] = argClass.getDeclaredFields();
    				Object varArray[] = varConstFilter(varFields, false);
    				printf("Variable" , varArray);
    				break;
		    case 'C':
	    			//Constants
	    			Field constFields[] = argClass.getDeclaredFields();   
	    			Object constArray[] = varConstFilter(constFields, true);     
	    			printf("Constant", constArray);
	    			break;
		    default: 
		    		help();
		    		break;
	    }//switch
	}
}
