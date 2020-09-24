package net.tapaal;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import dk.aau.cs.gui.TabContent;
import dk.aau.cs.gui.components.BatchProcessingResultsTableModel;
import dk.aau.cs.io.batchProcessing.BatchProcessingResultsExporter;
import dk.aau.cs.model.tapn.TimedArcPetriNetNetwork;
import dk.aau.cs.model.tapn.simulation.TAPNNetworkTrace;
import dk.aau.cs.translations.ReductionOption;
import dk.aau.cs.util.MemoryMonitor;
import dk.aau.cs.util.VerificationCallback;
import dk.aau.cs.verification.*;
import dk.aau.cs.verification.batchProcessing.BatchProcessingVerificationResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import pipe.dataLayer.TAPNQuery;
import pipe.gui.CreateGui;
import dk.aau.cs.debug.Logger;
import pipe.gui.Verifier;

import javax.swing.*;

/**
 * Main class for lunching TAPAAL
 *
 * @author Kenneth Yrke Joergensen (kenneth@yrke.dk)
 */
public class AalNet {

	public static final String TOOLNAME = "AalNet";
	public static final String VERSION = "DEV";

    public static final int MINIMUM_SUPPORTED_JAVAVERSION = 11;

	public static String getProgramName(){
		return "" + AalNet.TOOLNAME + " " + AalNet.VERSION;
	}

	//XXX: This values should never be change while running the program
	private static boolean debug = false;
	public static boolean debugEnabled() {
	    return debug;
    }
	
	public static void main(String[] args) throws Exception {
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
			System.err.println("There where an error parsing the specified arguments");
			System.err.println("Unexpected exception:" + exp.getMessage());
		}

		// Enable debug
		if (commandline.hasOption("debug")) {
			Logger.enableLogging(true);
			debug = true;
		}

		if (AalNet.VERSION.equals("DEV")){
			Logger.enableLogging(true);
			Logger.log("Debug logging is enabled by default in DEV branch");
            debug = true;
		}


        try {
            // Set the Look and Feel native for the system.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException ignored) {
            //Default l&f will be used
        }

        // Create the TAPAAL GUI
		CreateGui.init();


	}

    public static File getInstallDir() {
		
		String str = ClassLoader.getSystemResource("AalNet.class").getPath();
		
		int placeOfJarSeperator = str.lastIndexOf('!');
		
		if (placeOfJarSeperator != -1){
			// Its a jar file, lets strip the jar ending.
			str = str.substring(0, placeOfJarSeperator);
			
			//Remove the name of the jar file
			str = str.substring(0, str.lastIndexOf("/")); //Keep the last /
			
		} else {
			// Its a class file, stip the name
			str = str.replace("AalNet.class", "");
		}

		try {
			
			//Fix as ubuntu (at least) does not set file:// from ClassLoader
			if (!str.contains("file:/")) {
				str = "file://" + str;
			}
			
			
			//Some magic to remove file:// and get the right seperators
			URL url = new URL(str);
			URI uri = url.toURI();
			
			// Workaround for the following bug: 
		    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5086147
		    // Remove extra slashes after the scheme part.
			
		    if ( uri.getAuthority() != null ){
		        try {
		            uri = new URI( uri.toString().replace("file://", "file:////" ) );
		        } catch ( URISyntaxException e ) {
		            throw new IllegalArgumentException( "The specified " +
		                "URI contains an authority, but could not be " +
		                "normalized.", e );
		        }
		    }
			
		    File f = new File(uri);
			str = f.getAbsolutePath();
			
			// Stip to base dir (exit bin dir)
			File installdir = new File(str);
			installdir = installdir.getParentFile();
			
			return installdir;
			
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}
		return null;

	}
	

}
