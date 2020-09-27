package pipe.gui;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dk.aau.cs.debug.Logger;
import net.tapaal.AalNet;
import net.tapaal.Preferences;
import net.tapaal.resourcemanager.ResourceManager;
import pipe.dataLayer.DataLayer;
import pipe.gui.canvas.DrawingSurfaceImpl;
import dk.aau.cs.gui.TabContent;
import pipe.gui.undo.UndoManager;

import javax.swing.*;

public class CreateGui {

	private final static GuiFrame appGui = new GuiFrame(AalNet.getProgramName());
    private final static GuiFrameController appGuiController = new GuiFrameController(appGui);


	public static void init() {

	    try {
            Desktop.getDesktop().setAboutHandler(e -> appGuiController.showAbout());
        } catch (SecurityException | UnsupportedOperationException ignored) {
            Logger.log("Failed to set native about handler");
        }

	    try {
	        Desktop.getDesktop().setQuitHandler(
	            (e, response) -> {
	                appGuiController.exit();
	                response.cancelQuit(); //If we get here the request was canceled.
	            }
	        );

        } catch (SecurityException | UnsupportedOperationException ignored) {
	        Logger.log("Failed to set native quit handler");
        }

        try {
            Image appImage = ResourceManager.getIcon("icon.png").getImage();
            Taskbar.getTaskbar().setIconImage(appImage);

        } catch (SecurityException | UnsupportedOperationException ignored) {
            Logger.log("Failed to set DockIcon");
        }

		appGui.setVisible(true);
		appGuiController.checkForUpdate(false);
	}

	@Deprecated
	public static DrawingSurfaceImpl getDrawingSurface() {
		return getCurrentTab().drawingSurface();
	}

	@Deprecated
	public static TabContent getCurrentTab() {
		return appGuiController.getTab(appGui.getSelectedTabIndex());
	}

	@Deprecated
    public static void repaintAll() {
	    getCurrentTab().repaintAll();
    }

	/**
	 * @deprecated Use method getAnimator in GuiFrame
	 */
	@Deprecated
	public static Animator getAnimator() {
		if (getCurrentTab() == null) {
			return null;
		}
		return getCurrentTab().getAnimator();
	}

	//Use this only when accessing the Root frame to open a dialog/popup
    public static JFrame getRootFrame() {return getApp(); }

	@Deprecated
	public static GuiFrame getApp() { // returns a reference to the application
		return appGui;
	}

    //XXX The following function should properly not be used and is only used while refactoring, but is better
	// that the chained access via guiFrame, App or drawingsurface now marked with deprecation.
	public static TabContent openNewTabFromStream(InputStream file, String name) {
		TabContent tab = appGuiController.createNewTabFromInputStream(file, name);
		return tab;
	}

    public static boolean useExtendedBounds = false;

	@Deprecated
    public static UndoManager getUndoManager() {
        return getCurrentTab().getUndoManager();
    }

    public static boolean showTokenAge() {
        return Preferences.getInstance().getShowTokenAge();
    }
}
