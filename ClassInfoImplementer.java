import java.lang.reflect.Field;

public class ClassInfoImplementer 
{
	/**
	 * Attempt to find a class that the user specified 
	 * @param className the argument the user chose
	 * @return the Class object
	 */
	private static ClassInfo findClass(String className)
	{
		try 
	    {
			Class<?> argClass = Class.forName(className);
			return new ClassInfo(argClass);
		} 
	    catch (ClassNotFoundException e) 
	    {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	
	//*****************( main( ) )*****************************//
	public static void main(String...args) 
	{
		GetOpt cmdLineArgsAndOpts = new GetOpt(args, "acCimsv");
		cmdLineArgsAndOpts.opterr(false);
		int optionSelected;
		String argArray[] = cmdLineArgsAndOpts.getarg();
		ClassInfo classInfo = findClass(argArray[0]);
	    optionSelected = cmdLineArgsAndOpts.getopt();
	    System.out.println("Class Reflection Information:\n");
		while( optionSelected != -1)
		{
			classInfo.displayClassData(optionSelected);    
		}
	}

}
