package net.tapaal;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import pipe.gui.CreateGui;
import dk.aau.cs.debug.Logger;

public class TAPAAL {
	
	/**
	 * Main class for lunching TAPAAL
	 * 
	 * @author Kenneth Yrke Joergensen (kenneth@yrke.dk)
	 */
	
	public static final String TOOLNAME = "TAPAAL";
	public static final String VERSION = "DEV"; 

	public static String getProgramName(){
		return "" + TAPAAL.TOOLNAME + " " + TAPAAL.VERSION;
	}

	
	public static void main(String[] args) {
		// Create a CommandLineParser using Posix Style
		CommandLineParser parser = new PosixParser();

		// Create possible commandline options
		Options options = new Options();
		options.addOption("d", "debug", false, "enable debug output .");

		CommandLine commandline = null;

		// Parse command line arguments
		try {
			commandline = parser.parse(options, args);
		} catch (ParseException exp) {
			System.err.println("There where an error parsing the specifyed arguments");
			System.err.println("Unexpected exception:" + exp.getMessage());
		}

		// Create the TAPAAL GUI
		CreateGui.init();

		// Enable debug
		if (commandline.hasOption("debug")) {
			Logger.enableLogging(true);
		}

		// Open files
		String[] files = commandline.getArgs();
		for (String f : files) {
			File file = new File(f);

			if (file.exists()) { // Open the file
				if (file.canRead()) {
					CreateGui.appGui.createNewTabFromFile(file);
				} else if (file.exists()) {
					System.err.println("Can not read file " + file.toString());
				}
			} else {
				// XXX: Can we create the file? what would the default file type
				// be?
				// XXX: Can we check if we can write to the directory?
				System.err.println("Can not find file " + file.toString());

			}
		}

	}
	
	public static File getInstallDir() {
		
		String str = ClassLoader.getSystemResource("TAPAAL.class").getPath();
		
		int placeOfJarSeperator = str.lastIndexOf('!');
		
		if (placeOfJarSeperator != -1){
			// Its a jar file, lets strip the jar ending.
			str = str.substring(0, placeOfJarSeperator);
			System.out.println(str);
			//Remove the name of the jar file
			str = str.substring(0, str.lastIndexOf("/")); //Keep the last /
			
		} else {
			// Its a class file, stip the name
			str = str.replace("TAPAAL.class", "");
		}
		
		try {
			
			// Stip to base dir (exit bin dir)
			 
			
			//Some magic to remove file:// and get the right seperators
			str = (new File(new URL(str).toURI()).getAbsolutePath());
			
			File installdir = new File(str);
			installdir = installdir.getParentFile();
			
			return installdir;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;

	}
	

}
