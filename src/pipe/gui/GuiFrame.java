package pipe.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import com.sun.jna.Platform;
import dk.aau.cs.gui.*;
import net.tapaal.AalNet;
import net.tapaal.Preferences;
import net.tapaal.helpers.Reference.MutableReference;
import net.tapaal.helpers.Reference.Reference;
import net.tapaal.swinghelpers.ExtendedJTabbedPane;
import net.tapaal.swinghelpers.ToggleButtonWithoutText;
import org.jetbrains.annotations.NotNull;
import pipe.gui.Pipe.ElementType;
import pipe.gui.action.GuiAction;
import dk.aau.cs.debug.Logger;
import dk.aau.cs.gui.smartDraw.SmartDrawDialog;
import net.tapaal.resourcemanager.ResourceManager;


public class GuiFrame extends JFrame implements GuiFrameActions, SafeGuiFrameActions {

    /*
      DEBUG
      Set a break point at line actionPerformed, to allow to break execution from the gui. Eg. for inspecting elements
     */
    private final GuiAction breakExecutionAction = new GuiAction("Break Execution", "This action can be used to set a break while debugging, the action has no function.", KeyStroke.getKeyStroke('B',InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK)) {
        public void actionPerformed(ActionEvent e) {}
    };

    // for zoom combobox and dropdown
    private final int[] zoomLevels = {40, 60, 80, 100, 120, 140, 160, 180, 200, 300};

    private final String frameTitle;

    private final MutableReference<GuiFrameControllerActions> guiFrameController = new MutableReference<>();

    private final ExtendedJTabbedPane<TabContent> appTab = new ExtendedJTabbedPane<>() {
        @Override
        public Component generator() {
            return new TabComponent(this) {
                @Override
                protected void closeTab(int tab) {
                    GuiFrame.this.guiFrameController.ifPresent(o -> o.closeTab(tab));
                }
            };
        }
    };

    // Status bar...
    private final StatusBar statusBar = new StatusBar();
    private final JMenuBar menuBar = new JMenuBar();

    private final JMenu fileMenu = new JMenu("File");
    private final JMenu exampleMenu = new JMenu("Example nets");

    private final JMenu drawMenu = new JMenu("Draw");
    private final JMenu animateMenu = new JMenu("Simulator");
    private final JMenu viewMenu = new JMenu("View");

    // Start drawingToolBar
    private final JToolBar drawingToolBar = new JToolBar();

    private final JComboBox<String> timeFeatureOptions = new JComboBox<>(new String[]{"No", "Yes"});
    private final JComboBox<String> gameFeatureOptions = new JComboBox<>(new String[]{"No", "Yes"});

    private JComboBox<String> zoomComboBox;

    private static final int shortcutkey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    private final GuiAction createAction = new GuiAction("New", "Create a new Petri net", KeyStroke.getKeyStroke('N', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::showNewPNDialog);
        }
    };
    private final GuiAction createNewNTAAction = new GuiAction("New NTA", "Create a new NTA") {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::newNTA);
        }
    };
    private final GuiAction openAction = new GuiAction("Open", "Open", KeyStroke.getKeyStroke('O', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::openTAPNFile);
        }
    };
    private final GuiAction closeAction = new GuiAction("Close", "Close the current tab", KeyStroke.getKeyStroke('W', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            int index = appTab.getSelectedIndex();
            guiFrameController.ifPresent(o -> o.closeTab(index));
        }
    };
    private final GuiAction saveAction = new GuiAction("Save", "Save", KeyStroke.getKeyStroke('S', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::save);
        }
    };
    private final GuiAction saveAsAction = new GuiAction("Save as", "Save as...", KeyStroke.getKeyStroke('S', (shortcutkey + InputEvent.SHIFT_MASK))) {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::saveAs);
        }
    };
    private final GuiAction exitAction = new GuiAction("Exit", "Close the program", KeyStroke.getKeyStroke('Q', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::exit);
        }
    };
    private final GuiAction printAction = new GuiAction("Print", "Print", KeyStroke.getKeyStroke('P', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            Export.exportGuiView(getCurrentTab().drawingSurface(), Export.PRINTER, null, getCurrentTabName(), getCurrentTab().getFile());
        }
    };
    /*private final GuiAction importPNMLAction = new GuiAction("PNML untimed net", "Import an untimed net in the PNML format", KeyStroke.getKeyStroke('X', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::importPNMLFile);
        }
    };*/
    private final GuiAction importSUMOAction = new GuiAction("SUMO queries (.txt)", "Import SUMO queries in a plain text format") {
        public void actionPerformed(ActionEvent arg0) {
            currentTab.ifPresent(TabContentActions::importSUMOQueries);
        }
    };
    private final GuiAction importXMLAction = new GuiAction("XML queries (.xml)", "Import MCC queries in XML format", KeyStroke.getKeyStroke('R', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            currentTab.ifPresent(TabContentActions::importXMLQueries);
        }
    };
    private final GuiAction exportPNGAction = new GuiAction("PNG", "Export the net to PNG format", KeyStroke.getKeyStroke('G', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
                Export.exportGuiView(getCurrentTab().drawingSurface(), Export.PNG, null, getCurrentTabName(), getCurrentTab().getFile());
        }
    };
    private final GuiAction exportPSAction = new GuiAction("PostScript", "Export the net to PostScript format", KeyStroke.getKeyStroke('T', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
                Export.exportGuiView(getCurrentTab().drawingSurface(), Export.POSTSCRIPT, null, getCurrentTabName(), getCurrentTab().getFile());
        }
    };
    private final GuiAction exportToTikZAction = new GuiAction("TikZ", "Export the net to LaTex (TikZ) format", KeyStroke.getKeyStroke('L', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
                Export.exportGuiView(getCurrentTab().drawingSurface(), Export.TIKZ, getCurrentTab().getModel(), getCurrentTabName(), getCurrentTab().getFile());
        }
    };
    private final GuiAction exportToXMLAction = new GuiAction("XML Queries", "Export the queries to XML format", KeyStroke.getKeyStroke('H', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            Export.exportGuiView(getCurrentTab().drawingSurface(), Export.QUERY, null, getCurrentTabName(), getCurrentTab().getFile());
        }
    };
    private final GuiAction exportTraceAction = new GuiAction("Export trace", "Export the current trace", "") {
        public void actionPerformed(ActionEvent arg0) {
            currentTab.ifPresent(TabContentActions::exportTrace);
        }
    };
    private final GuiAction importTraceAction = new GuiAction("Import trace", "Import trace to simulator", "") {
        public void actionPerformed(ActionEvent arg0) {
            currentTab.ifPresent(TabContentActions::importTrace);
        }
    };

    /*private GuiAction  copyAction, cutAction, pasteAction, */
    private final GuiAction undoAction = new GuiAction("Undo", "Undo", KeyStroke.getKeyStroke('Z', shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::undo);
        }
    };
    private final GuiAction redoAction = new GuiAction("Redo", "Redo", KeyStroke.getKeyStroke('Y', shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::redo);
        }
    };
    private final GuiAction toggleGrid = new GuiAction("Cycle grid", "Change the grid size", "G") {
        public void actionPerformed(ActionEvent arg0) {
            Grid.increment();
            repaint();
        }
    };
    private final GuiAction alignToGrid = new GuiAction("Align To Grid", "Align Petri net objects to current grid", KeyStroke.getKeyStroke("shift G")) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::alignPNObjectsToGrid);
        }
    };
    private final GuiAction netStatisticsAction = new GuiAction("Net statistics", "Shows information about the number of transitions, places, arcs, etc.", KeyStroke.getKeyStroke(KeyEvent.VK_I, shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::showStatistics);
        }
    };
    private final GuiAction batchProcessingAction = new GuiAction("Batch processing", "Batch verification of multiple nets and queries", KeyStroke.getKeyStroke(KeyEvent.VK_B, shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::showBatchProcessingDialog);
        }
    };
    private final GuiAction engineSelectionAction = new GuiAction("Engine selection", "View and modify the location of verification engines", KeyStroke.getKeyStroke('E', shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::showEngineDialog);
        }
    };
    private final GuiAction clearPreferencesAction = new GuiAction("Clear all preferences", "Clear all custom preferences to default") {
        public void actionPerformed(ActionEvent actionEvent) {
            guiFrameController.ifPresent(GuiFrameControllerActions::clearPreferences);
        }
    };

    private final GuiAction verifyAction = new GuiAction("Verify query", "Verifies the currently selected query", KeyStroke.getKeyStroke(KeyEvent.VK_M, shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            currentTab.ifPresent(TabContentActions::verifySelectedQuery);
        }
    };
    private final GuiAction workflowDialogAction = new GuiAction("Workflow analysis", "Analyse net as a TAWFN", KeyStroke.getKeyStroke(KeyEvent.VK_F, shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::workflowAnalyse);
        }
    };
    private final GuiAction smartDrawAction = new GuiAction("Automatic Net Layout", "Rearrange the Petri net objects", KeyStroke.getKeyStroke('D', KeyEvent.SHIFT_DOWN_MASK)) {
        public void actionPerformed(ActionEvent e) {
            SmartDrawDialog.showSmartDrawDialog();
        }
    };
    private final GuiAction mergeComponentsDialogAction = new GuiAction("Merge net components", "Export an xml file of composed net and approximated net if enabled", KeyStroke.getKeyStroke(KeyEvent.VK_C, (shortcutkey + InputEvent.SHIFT_MASK))) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::mergeNetComponents);
        }
    };
    private final GuiAction zoomOutAction = new GuiAction("Zoom out", "Zoom out by 10% ", KeyStroke.getKeyStroke('K', shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::zoomOut);
        }
    };
    private final GuiAction zoomInAction = new GuiAction("Zoom in", "Zoom in by 10% ", KeyStroke.getKeyStroke('J', shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::zoomIn);
        }
    };
    private final GuiAction zoomToAction = new GuiAction("Zoom", "Select zoom percentage ", "") {
        public void actionPerformed(ActionEvent e) {
            String selectedZoomLevel = (String) zoomComboBox.getSelectedItem();
            //parse selected zoom level, and strip of %.
            int newZoomLevel = Integer.parseInt(selectedZoomLevel.replace("%", ""));

            currentTab.ifPresent(o -> o.zoomTo(newZoomLevel));
        }
    };

    private final GuiAction incSpacingAction = new GuiAction("Increase node spacing", "Increase spacing by 20% ", KeyStroke.getKeyStroke('U', shortcutkey)) {
        public void actionPerformed(ActionEvent arg0) {
            currentTab.ifPresent(TabContentActions::increaseSpacing);
        }
    };
    private final GuiAction decSpacingAction = new GuiAction("Decrease node spacing", "Decrease spacing by 20% ", KeyStroke.getKeyStroke("shift U")) {
        public void actionPerformed(ActionEvent arg0) {
            currentTab.ifPresent(TabContentActions::decreaseSpacing);
        }
    };
    public final GuiAction deleteAction = new GuiAction("Delete", "Delete selection", "DELETE") {
        public void actionPerformed(ActionEvent arg0) {
            currentTab.ifPresent(TabContentActions::deleteSelection);
        }

    };

    private final GuiAction annotationAction = new GuiAction("Annotation", "Add an annotation (N)", "N", true) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(o -> o.setMode(ElementType.ANNOTATION));
        }
    };

    private final GuiAction showComponentsAction = new GuiAction("Display components", "Show/hide the list of components.", KeyStroke.getKeyStroke('1', shortcutkey), true) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::toggleComponents);
        }
    };
    private final GuiAction showSharedPTAction = new GuiAction("Display shared places/transitions", "Show/hide the list of shared places/transitions.", KeyStroke.getKeyStroke('2', shortcutkey), true) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::toggleSharedPT);
        }
    };
    private final GuiAction showQueriesAction = new GuiAction("Display queries", "Show/hide verification queries.", KeyStroke.getKeyStroke('3', shortcutkey), true) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::toggleQueries);
        }
    };
    private final GuiAction showConstantsAction = new GuiAction("Display constants", "Show/hide global constants.", KeyStroke.getKeyStroke('4', shortcutkey), true) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::toggleConstants);
        }
    };
    private final GuiAction showEnabledTransitionsAction = new GuiAction("Display enabled transitions", "Show/hide the list of enabled transitions", KeyStroke.getKeyStroke('5', shortcutkey), true) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::toggleEnabledTransitionsList);
        }
    };
    private final GuiAction showDelayEnabledTransitionsAction = new GuiAction("Display future-enabled transitions", "Highlight transitions which can be enabled after a delay", KeyStroke.getKeyStroke('6', shortcutkey), true) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::toggleDelayEnabledTransitions);
        }
    };
    private final GuiAction showToolTipsAction = new GuiAction("Display tool tips", "Show/hide tool tips when mouse is over an element", KeyStroke.getKeyStroke('8', shortcutkey), true) {
        public void actionPerformed(ActionEvent e) {
            guiFrameController.ifPresent(GuiFrameControllerActions::toggleDisplayToolTips);
        }
    };
    private final GuiAction showAboutAction = new GuiAction("About", "Show the About menu") {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::showAbout);
        }
    };
    private final GuiAction showHomepage = new GuiAction("Visit AalNet home", "Visit the AalNet homepage") {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(o -> o.openURL("https://github.com/AalNet/AalNet-GUI"));
        }
    };

    private final GuiAction showReportBugAction = new GuiAction("Report bug", "Report a bug in AalNet") {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(o -> o.openURL("https://github.com/AalNet/AalNet-GUI/issues"));
        }
    };
    private final GuiAction checkUpdate = new GuiAction("Check for updates", "Check if there is a new version of TAPAAL") {
        public void actionPerformed(ActionEvent arg0) {
            guiFrameController.ifPresent(GuiFrameControllerActions::checkForUpdate);
        }
    };

    private final GuiAction selectAllAction = new GuiAction("Select all", "Select all components", KeyStroke.getKeyStroke('A', shortcutkey)) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::selectAll);
        }
    };

    private final GuiAction startAction = new GuiAction("Simulation mode", "Toggle simulation mode (M)", "M", true) {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::toggleAnimationMode);
        }
    };
    public final GuiAction stepforwardAction = new GuiAction("Step forward", "Step forward", "pressed RIGHT") {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::stepForward);
        }
    };
    public final GuiAction stepbackwardAction = new GuiAction("Step backward", "Step backward", "pressed LEFT") {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::stepBackwards);
        }
    };

    private final GuiAction prevcomponentAction = new GuiAction("Previous component", "Previous component", "pressed UP") {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::previousComponent);
        }
    };
    private final GuiAction nextcomponentAction = new GuiAction("Next component", "Next component", "pressed DOWN") {
        public void actionPerformed(ActionEvent e) {
            currentTab.ifPresent(TabContentActions::nextComponent);
        }
    };

    private final GuiAction changeTimeFeatureAction = new GuiAction("Time", "Change time semantics") {
        public void actionPerformed(ActionEvent e) {
            boolean isTime = timeFeatureOptions.getSelectedIndex() != 0;
            currentTab.ifPresent(o -> o.changeTimeFeature(isTime));
        }
    };

    private final GuiAction changeGameFeatureAction = new GuiAction("Game", "Change game semantics") {
        public void actionPerformed(ActionEvent e) {
            boolean isGame = gameFeatureOptions.getSelectedIndex() != 0;
            currentTab.ifPresent(o -> o.changeGameFeature(isGame));
        }
    };

    public enum GUIMode {
        draw, animation, noNet
    }

    private JCheckBoxMenuItem showDelayEnabledTransitionsCheckbox;

    private final JMenu zoomMenu = new JMenu("Zoom");

    public GuiFrame(String title) {
        // HAK-arrange for frameTitle to be initialized and the default file
        // name to be appended to basic window title

        frameTitle = title;
        setTitle(null);
        setOSIntegrationAndHotkeys();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width * 80 / 100, screenSize.height * 80 / 100);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(825, 480));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getContentPane().add(appTab);
        setChangeListenerOnTab(); // sets Tab properties

        Grid.enableGrid();

        buildMenus();

        // Net Type
        JPanel featurePanel = new JPanel();
        featurePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        featurePanel.add(new JLabel("Timed: "));
        featurePanel.add(timeFeatureOptions);
        featurePanel.add(new JLabel("   Game: "));
        featurePanel.add(gameFeatureOptions);
        timeFeatureOptions.addActionListener(changeTimeFeatureAction);
        gameFeatureOptions.addActionListener(changeGameFeatureAction);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(statusBar);
        bottomPanel.add(featurePanel);
        getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

        // Build menus
        buildToolbar();

        addWindowListener(new WindowAdapter() {
            // Handler for window closing event
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });

        this.setForeground(java.awt.Color.BLACK);

        // Set GUI mode
        setGUIMode(GUIMode.noNet);

    }

    private void setOSIntegrationAndHotkeys() {
        try {
            // Set enter to select focus button rather than default (makes ENTER selection key on all LAFs)
            UIManager.put("Button.focusInputMap", new UIDefaults.LazyInputMap(new Object[]{
                    "SPACE", "pressed",
                    "released SPACE", "released",
                    "ENTER", "pressed",
                    "released ENTER", "released"
                })
            );
            UIManager.put("OptionPane.informationIcon", ResourceManager.infoIcon());
            UIManager.put("Slider.paintValue", false);

        } catch (Exception exc) {
            Logger.log("Error loading L&F: " + exc);
        }


        if (Platform.isMac()) {

            //Set specific settings
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", AalNet.TOOLNAME);

            // Use native file chooser
            System.setProperty("apple.awt.fileDialogForDirectories", "false");

            // Grow size of boxes to add room for the resizer
            System.setProperty("apple.awt.showGrowBox", "true");

        }

        this.setIconImage(ResourceManager.getIcon("icon.png").getImage());
    }


    @Override
    public void setWindowSize(Dimension dimension) {
        if (dimension == null) return;

        this.setSize(dimension);
    }

    /**
     * Build the menues and actions
     **/
    private void buildMenus() {
        menuBar.add(buildMenuFiles());
        menuBar.add(buildMenuEdit());
        menuBar.add(buildMenuView());
        menuBar.add(buildMenuDraw());

        menuBar.add(buildMenuAnimation());
        menuBar.add(buildMenuTools());
        menuBar.add(buildMenuHelp());

        if (AalNet.debugEnabled()){
            menuBar.add(buildMenuDebug());
        }

        setJMenuBar(menuBar);

    }

    private JMenu buildMenuDebug() {
        JMenu debugMenu = new JMenu("DEBUG");

        debugMenu.add(breakExecutionAction);

        return debugMenu;
    }

    private JMenu buildMenuEdit() {

        /* Edit Menu */
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        editMenu.add(undoAction);


        editMenu.add(redoAction);
        editMenu.addSeparator();

        editMenu.add(deleteAction);

        // Bind delete to backspace also
        editMenu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("BACK_SPACE"), "Delete");
        editMenu.getActionMap().put("Delete", deleteAction);

        editMenu.addSeparator();


        editMenu.add(selectAllAction);
        editMenu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('A', shortcutkey), "SelectAll");
        editMenu.getActionMap().put("SelectAll", selectAllAction);

        return editMenu;
    }

    private JMenu buildMenuDraw() {
        drawMenu.setMnemonic('D');
        return drawMenu;
    }

    private JMenu buildMenuView() {
        viewMenu.setMnemonic('V');

        zoomMenu.setIcon(ResourceManager.getIcon("Zoom.png"));

        addZoomMenuItems(zoomMenu);

        viewMenu.add(zoomInAction);

        viewMenu.add(zoomOutAction);
        viewMenu.add(zoomMenu);

        viewMenu.addSeparator();

        viewMenu.add(incSpacingAction);

        viewMenu.add(decSpacingAction);


        viewMenu.addSeparator();

        viewMenu.add(toggleGrid);

        viewMenu.add(alignToGrid);


        viewMenu.addSeparator();

        addCheckboxMenuItem(viewMenu, showComponentsAction);

        addCheckboxMenuItem(viewMenu, showSharedPTAction);

        addCheckboxMenuItem(viewMenu, showQueriesAction);

        addCheckboxMenuItem(viewMenu, showConstantsAction);

        addCheckboxMenuItem(viewMenu, showEnabledTransitionsAction);

        showDelayEnabledTransitionsCheckbox = addCheckboxMenuItem(viewMenu, showDelayEnabledTransitionsAction);

        addCheckboxMenuItem(viewMenu, showToolTipsAction);

        return viewMenu;
    }


    private JMenu buildMenuAnimation() {
        animateMenu.setMnemonic('A');
        animateMenu.add(startAction);


        animateMenu.add(stepbackwardAction);
        animateMenu.add(stepforwardAction);

        animateMenu.add(prevcomponentAction);

        animateMenu.add(nextcomponentAction);

        animateMenu.addSeparator();

        animateMenu.add(exportTraceAction);
        animateMenu.add(importTraceAction);


        return animateMenu;
    }

    private JMenu buildMenuHelp() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        helpMenu.add(showHomepage);
        helpMenu.add(showReportBugAction);

        helpMenu.addSeparator();

        helpMenu.add(checkUpdate);

        helpMenu.addSeparator();

        helpMenu.add(showAboutAction);
        return helpMenu;
    }


    private JMenu buildMenuTools() {
        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setMnemonic('t');

        JMenuItem batchProcessing = new JMenuItem(batchProcessingAction);
        batchProcessing.setMnemonic('b');
        toolsMenu.add(batchProcessing);

        toolsMenu.addSeparator();

        toolsMenu.add(verifyAction).setMnemonic('m');

        toolsMenu.add(netStatisticsAction).setMnemonic('i');

        JMenuItem workflowDialog = new JMenuItem(workflowDialogAction);
        workflowDialog.setMnemonic('f');
        toolsMenu.add(workflowDialog);

        JMenuItem smartDrawDialog = new JMenuItem(smartDrawAction);
        smartDrawDialog.setMnemonic('D');
        toolsMenu.add(smartDrawDialog);

        JMenuItem mergeComponentsDialog = new JMenuItem(mergeComponentsDialogAction);
        mergeComponentsDialog.setMnemonic('c');
        toolsMenu.add(mergeComponentsDialog);

        toolsMenu.addSeparator();

        JMenuItem engineSelection = new JMenuItem(engineSelectionAction);
        toolsMenu.add(engineSelection);

        JMenuItem clearPreferences = new JMenuItem(clearPreferencesAction);
        toolsMenu.add(clearPreferences);

        return toolsMenu;
    }

    private void buildToolbar() {

        //XXX .setRequestFocusEnabled(false), removed "border" around tollbar buttons when selcted/focus
        // https://stackoverflow.com/questions/9361658/disable-jbutton-focus-border and
        //https://stackoverflow.com/questions/20169436/how-to-prevent-toolbar-button-focus-in-java-swing

        // Create the toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);// Inhibit toolbar floating
        toolBar.setRequestFocusEnabled(false);

        // Basis file operations
        toolBar.add(createAction).setRequestFocusEnabled(false);
        toolBar.add(createNewNTAAction).setRequestFocusEnabled(false);
        toolBar.add(openAction).setRequestFocusEnabled(false);
        toolBar.add(saveAction).setRequestFocusEnabled(false);
        toolBar.add(saveAsAction).setRequestFocusEnabled(false);

        // Print
        toolBar.addSeparator();
        toolBar.add(printAction).setRequestFocusEnabled(false);

        // Copy/past
        /*
         * Removed copy/past button toolBar.addSeparator();
         * toolBar.add(cutAction); toolBar.add(copyAction);
         * toolBar.add(pasteAction);
         */

        // Undo/redo
        toolBar.addSeparator();
        toolBar.add(deleteAction).setRequestFocusEnabled(false);
        toolBar.add(undoAction).setRequestFocusEnabled(false);
        toolBar.add(redoAction).setRequestFocusEnabled(false);

        // Zoom
        toolBar.addSeparator();
        toolBar.add(zoomOutAction).setRequestFocusEnabled(false);
        addZoomComboBox(toolBar, zoomToAction);
        toolBar.add(zoomInAction).setRequestFocusEnabled(false);

        // Modes

        toolBar.addSeparator();
        toolBar.add(toggleGrid).setRequestFocusEnabled(false);

        toolBar.add(new ToggleButtonWithoutText(startAction));

        drawingToolBar.setFloatable(false);
        drawingToolBar.addSeparator();
        drawingToolBar.setRequestFocusEnabled(false);

        // Create panel to put toolbars in
        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // Add toolbars to pane
        toolBarPanel.add(toolBar);
        toolBarPanel.add(drawingToolBar);

        // Create a toolBarPaneltmp usign broderlayout and a spacer to get
        // toolbar to fill the screen
        JPanel toolBarPaneltmp = new JPanel();
        toolBarPaneltmp.setLayout(new BorderLayout());
        toolBarPaneltmp.add(toolBarPanel, BorderLayout.WEST);
        JToolBar spacer = new JToolBar();
        spacer.addSeparator();
        spacer.setFloatable(false);
        toolBarPaneltmp.add(spacer, BorderLayout.CENTER);

        // Add to GUI
        getContentPane().add(toolBarPaneltmp, BorderLayout.PAGE_START);
    }

    /**
     * @param zoomMenu - the menu to add the submenu to
     * @author Ben Kirby Takes the method of setting up the Zoom menu out of the
     * main buildMenus method.
     */
    private void addZoomMenuItems(JMenu zoomMenu) {
        for (int i = 0; i <= zoomLevels.length - 1; i++) {

            final int zoomper = zoomLevels[i];
            GuiAction newZoomAction = new GuiAction(zoomLevels[i] + "%", "Select zoom percentage", "") {
                public void actionPerformed(ActionEvent e) {
                    currentTab.ifPresent(o -> o.zoomTo(zoomper));
                }
            };

            zoomMenu.add(newZoomAction);
        }


    }

    /**
     * @param toolBar the JToolBar to add the button to
     * @param action  the action that the ZoomComboBox performs
     * @author Ben Kirby Just takes the long-winded method of setting up the
     * ComboBox out of the main buildToolbar method. Could be adapted
     * for generic addition of comboboxes
     */
    private void addZoomComboBox(JToolBar toolBar, Action action) {
        Dimension zoomComboBoxDimension = new Dimension(75, 28);

        String[] zoomExamplesStrings = new String[zoomLevels.length];
        int i;
        for (i = 0; i < zoomLevels.length; i++) {
            zoomExamplesStrings[i] = zoomLevels[i] + "%";
        }

        zoomComboBox = new JComboBox<String>(zoomExamplesStrings);
        zoomComboBox.setEditable(true);
        zoomComboBox.setSelectedItem("100%");
        zoomComboBox.setMaximumRowCount(zoomLevels.length);
        zoomComboBox.setMaximumSize(zoomComboBoxDimension);
        zoomComboBox.setMinimumSize(zoomComboBoxDimension);
        zoomComboBox.setPreferredSize(zoomComboBoxDimension);
        zoomComboBox.setAction(action);
        zoomComboBox.setFocusable(false);
        toolBar.add(zoomComboBox);
    }

    private JCheckBoxMenuItem addCheckboxMenuItem(JMenu menu, Action action) {
        return addCheckboxMenuItem(menu, true, action);
    }

    private JCheckBoxMenuItem addCheckboxMenuItem(JMenu menu, boolean selected, Action action) {

        JCheckBoxMenuItem checkBoxItem = new JCheckBoxMenuItem();

        checkBoxItem.setAction(action);
        checkBoxItem.setSelected(selected);
        JMenuItem item = menu.add(checkBoxItem);
        KeyStroke keystroke = (KeyStroke) action.getValue(Action.ACCELERATOR_KEY);

        if (keystroke != null) {
            item.setAccelerator(keystroke);
        }
        return checkBoxItem;
    }

    /**
     * Sets all buttons to enabled or disabled according to the current GUImode.
     * <p>
     * Reimplementation of old enableGUIActions(bool status)
     *
     * @author Kenneth Yrke Joergensen (kyrke)
     */
    private void enableGUIActions(GUIMode mode) {
        switch (mode) {
            case draw:
                enableAllActions(true);
                exportTraceAction.setEnabled(false);
                importTraceAction.setEnabled(false);

                annotationAction.setEnabled(true);
                deleteAction.setEnabled(true);
                selectAllAction.setEnabled(true);

                stepbackwardAction.setEnabled(false);
                stepforwardAction.setEnabled(false);
                prevcomponentAction.setEnabled(false);
                nextcomponentAction.setEnabled(false);

                deleteAction.setEnabled(true);
                showEnabledTransitionsAction.setEnabled(false);
                showDelayEnabledTransitionsAction.setEnabled(false);

                verifyAction.setEnabled(getCurrentTab().isQueryPossible());

                smartDrawAction.setEnabled(true);
                mergeComponentsDialogAction.setEnabled(true);
                workflowDialogAction.setEnabled(true);

                timeFeatureOptions.setEnabled(true);
                gameFeatureOptions.setEnabled(true);

                fixBug812694GrayMenuAfterSimulationOnMac();
                break;

            case animation:
                enableAllActions(true);

                annotationAction.setEnabled(false);
                deleteAction.setEnabled(false);
                selectAllAction.setEnabled(false);

                alignToGrid.setEnabled(false);

                showSharedPTAction.setEnabled(false);
                showConstantsAction.setEnabled(false);
                showQueriesAction.setEnabled(false);

                stepbackwardAction.setEnabled(true);
                stepforwardAction.setEnabled(true);
                prevcomponentAction.setEnabled(true);
                nextcomponentAction.setEnabled(true);

                deleteAction.setEnabled(false);
                undoAction.setEnabled(false);
                redoAction.setEnabled(false);
                verifyAction.setEnabled(false);

                smartDrawAction.setEnabled(false);
                mergeComponentsDialogAction.setEnabled(false);
                workflowDialogAction.setEnabled(false);

                timeFeatureOptions.setEnabled(false);
                gameFeatureOptions.setEnabled(false);

                // Remove constant highlight
                getCurrentTab().removeConstantHighlights();

                getCurrentTab().getAnimationController().requestFocusInWindow();

                break;
            case noNet:
                exportTraceAction.setEnabled(false);
                importTraceAction.setEnabled(false);
                verifyAction.setEnabled(false);

                annotationAction.setEnabled(false);
                selectAllAction.setEnabled(false);

                stepbackwardAction.setEnabled(false);
                stepforwardAction.setEnabled(false);

                deleteAction.setEnabled(false);
                undoAction.setEnabled(false);
                redoAction.setEnabled(false);
                prevcomponentAction.setEnabled(false);
                nextcomponentAction.setEnabled(false);

                smartDrawAction.setEnabled(false);
                mergeComponentsDialogAction.setEnabled(false);
                workflowDialogAction.setEnabled(false);

                timeFeatureOptions.setEnabled(false);
                gameFeatureOptions.setEnabled(false);

                enableAllActions(false);

                // Disable All Actions
                statusBar.changeText("Open a net to start editing");
                setFocusTraversalPolicy(null);

                break;
        }

    }

    /**
     * Helperfunction for disabeling/enabeling all actions when we are in noNet GUImode
     */
    private void enableAllActions(boolean enable) {

        // File
        closeAction.setEnabled(enable);

        saveAction.setEnabled(enable);
        saveAsAction.setEnabled(enable);

        exportPNGAction.setEnabled(enable);
        exportPSAction.setEnabled(enable);
        exportToTikZAction.setEnabled(enable);
        exportToXMLAction.setEnabled(enable);

        exportTraceAction.setEnabled(enable);
        importTraceAction.setEnabled(enable);

        printAction.setEnabled(enable);

        // View
        zoomInAction.setEnabled(enable);
        zoomOutAction.setEnabled(enable);
        zoomComboBox.setEnabled(enable);
        zoomMenu.setEnabled(enable);

        decSpacingAction.setEnabled(enable);
        incSpacingAction.setEnabled(enable);

        toggleGrid.setEnabled(enable);
        alignToGrid.setEnabled(enable);

        showComponentsAction.setEnabled(enable);
        showSharedPTAction.setEnabled(enable);
        showConstantsAction.setEnabled(enable);
        showQueriesAction.setEnabled(enable);
        showEnabledTransitionsAction.setEnabled(enable);
        showDelayEnabledTransitionsAction.setEnabled(enable);
        showToolTipsAction.setEnabled(enable);

        // Simulator
        startAction.setEnabled(enable);

        // Tools
        netStatisticsAction.setEnabled(enable);

    }

    // set tabbed pane properties and add change listener that updates tab with
    // linked model and view
    private void setChangeListenerOnTab() {
        appTab.addChangeListener(e -> {
                //This event will only fire if the tab index is changed, so it won't trigger if once
                // also if code calls setSelectedIndex(index), thereby avoiding a loop.
                int selectedIndex = appTab.getSelectedIndex();

                if (selectedIndex > 0) {
                    guiFrameController.ifPresent(o -> o.changeToTab(selectedIndex));
                }
            }
        );
    }

    @Override
    public void updatedTabName(TabContent tab) {
        int index = appTab.indexOfComponent(tab);

        appTab.setTitleAt(index, tab.getTabTitle());

        // resize "header" of current tab immediately to fit the length of the model name (if it shorter)
        appTab.getTabComponentAt(index).doLayout();

        if (index >= 0 && index == appTab.getSelectedIndex()) {
            setTitle(tab.getTabTitle()); // Change the window title
        }
    }

    @Override
    public void attachTabToGuiFrame(Tab tab) {
        appTab.addTab(tab.getTabTitle(), tab.getTabComponent());
    }

    @Override
    public void detachTabFromGuiFrame(Tab tab) {
        appTab.remove(tab.getTabComponent());

        if (appTab.getTabCount() == 0) {
            setGUIMode(GUIMode.noNet);
        }
    }

    /**
     * Set the current mode of the GUI, and changes possible actions
     *
     * @param mode change GUI to this mode
     * @author Kenneth Yrke Joergensen (kyrke)
     */
    //TODO
    @Override
    public void setGUIMode(GUIMode mode) {
        switch (mode) {
            case draw:
                // Enable all draw actions
                startAction.setSelected(false);

                break;
            case animation:
                startAction.setSelected(true);

                break;
            case noNet:
                setFeatureInfoText(null);
                registerDrawingActions(List.of());
                registerAnimationActions(List.of());
                //registerViewActions(List.of());
                break;

            default:
                break;
        }

        // Enable actions based on GUI mode
        enableGUIActions(mode);
        if (currentTab != null) {
            currentTab.ifPresent(o -> o.updateEnabledActions(mode));
        }
    }

    @Override
    public void registerDrawingActions(@NotNull List<GuiAction> drawActions) {

        drawingToolBar.removeAll();
        drawMenu.removeAll();

        if (drawActions.size() > 0) {
            drawMenu.setEnabled(true);
            drawingToolBar.addSeparator();

            for (GuiAction action : drawActions) {
                drawingToolBar.add(new ToggleButtonWithoutText(action));
                drawMenu.add(action);
            }
        } else {
            drawMenu.setEnabled(false);
        }

    }

    @Override
    public void registerAnimationActions(@NotNull List<GuiAction> animationActions) {

        animateMenu.removeAll();

        if (animationActions.size() > 0) {

            animateMenu.setEnabled(true);
            animateMenu.add(startAction);

            animateMenu.add(stepbackwardAction);
            animateMenu.add(stepforwardAction);

            for (GuiAction action : animationActions) {
                animateMenu.add(action);
            }

            animateMenu.add(prevcomponentAction);
            animateMenu.add(nextcomponentAction);

            animateMenu.addSeparator();
            animateMenu.add(exportTraceAction);
            animateMenu.add(importTraceAction);
        } else {
            animateMenu.setEnabled(false);
        }
    }

    @Override
    public void registerViewActions(@NotNull List<GuiAction> viewActions) {
        //TODO: This is a temporary implementation until view actions can be moved to tab content

        if (!getCurrentTab().getLens().isTimed()) {
            showDelayEnabledTransitionsCheckbox.setVisible(false);
        } else {
            showDelayEnabledTransitionsCheckbox.setVisible(true);
        }
    }

    private void fixBug812694GrayMenuAfterSimulationOnMac() {
        // XXX
        // This is a fix for bug #812694 where on mac some menues are gray after
        // changing from simulation mode, when displaying a trace. Showing and
        // hiding a menu seems to fix this problem
        JDialog a = new JDialog(this, false);
        a.setUndecorated(true);
        a.setVisible(true);
        a.dispose();
    }

    @Override
    public void setStatusBarText(String s) {
        statusBar.changeText(Objects.requireNonNullElse(s, ""));
    }


    Reference<TabContentActions> currentTab = null;

    @Override
    public void registerController(GuiFrameControllerActions guiFrameController, Reference<TabContentActions> currentTab) {
        this.guiFrameController.setReference(guiFrameController);
        this.currentTab = currentTab;
    }

    @Override
    public void changeToTab(Tab tab) {
        if (tab != null) {
            //Change tab event will only fire if index != currentIndex, to changing it via setSelectIndex will not
            // create a tabChanged event loop.
            // Throw exception if tab is not found
            appTab.setSelectedComponent(tab.getTabComponent());
        }
    }

    @Override
    public void setShowComponentsSelected(boolean b) {
        showComponentsAction.setSelected(b);
    }

    @Override
    public void setShowSharedPTSelected(boolean b) {
        showSharedPTAction.setSelected(b);
    }

    @Override
    public void setShowConstantsSelected(boolean b) {
        showConstantsAction.setSelected(b);
    }

    @Override
    public void setShowQueriesSelected(boolean b) {
        showQueriesAction.setSelected(b);
    }

    @Override
    public void setShowEnabledTransitionsSelected(boolean b) {
        showEnabledTransitionsAction.setSelected(b);
    }

    @Override
    public void setShowDelayEnabledTransitionsSelected(boolean b) {
        showDelayEnabledTransitionsAction.setSelected(b);
    }

    @Override
    public void setShowToolTipsSelected(boolean b) {
        showToolTipsAction.setSelected(b);
    }

    public void setTitle(String title) {
        super.setTitle((title == null) ? frameTitle : frameTitle + ": " + title);
    }

    public boolean isEditionAllowed() {
        return !getCurrentTab().isInAnimationMode();
    }

    @Override
    public void setUndoActionEnabled(boolean flag) {
        undoAction.setEnabled(flag);
    }

    @Override
    public void setRedoActionEnabled(boolean flag) {
        redoAction.setEnabled(flag);
    }

    /**
     * @author Ben Kirby Remove the listener from the zoomComboBox, so that when
     * the box's selected item is updated to keep track of ZoomActions
     * called from other sources, a duplicate ZoomAction is not called
     */
    @Override
    public void updateZoomCombo() {
        ActionListener zoomComboListener = (zoomComboBox.getActionListeners())[0];
        zoomComboBox.removeActionListener(zoomComboListener);
        zoomComboBox.setSelectedItem(getCurrentTab().drawingSurface().getZoomController().getPercent() + "%");
        zoomComboBox.addActionListener(zoomComboListener);
    }

    private JMenu buildMenuFiles() {
        fileMenu.setMnemonic('F');

        fileMenu.add(createAction);
        fileMenu.add(createNewNTAAction);

        fileMenu.add(openAction);

        fileMenu.add(closeAction);

        fileMenu.addSeparator();

        fileMenu.add(saveAction);


        fileMenu.add(saveAsAction);


        // Import menu
        JMenu importMenu = new JMenu("Import");
        importMenu.setIcon(ResourceManager.getIcon("Export.png"));

        //importMenu.add(importPNMLAction);


        importMenu.add(importSUMOAction);

        importMenu.add(importXMLAction);
        fileMenu.add(importMenu);

        // Export menu
        JMenu exportMenu = new JMenu("Export");
        exportMenu.setIcon(ResourceManager.getIcon("Export.png"));

        exportMenu.add(exportPNGAction);
        exportMenu.add(exportPSAction);
        exportMenu.add(exportToTikZAction);
        exportMenu.add(exportToXMLAction);

        fileMenu.add(exportMenu);

        fileMenu.addSeparator();
        fileMenu.add(printAction);

        fileMenu.addSeparator();

        exampleMenu.setEnabled(false);
        exampleMenu.setIcon(ResourceManager.getIcon("Example.png"));
        fileMenu.add(exampleMenu);
        fileMenu.addSeparator();


        fileMenu.add(exitAction);

        return fileMenu;
    }

    @Override
    public void registerExampleNets(List<String> nets) {
        if (nets != null && nets.size() > 0) {
            exampleMenu.setEnabled(true);

            for (String filename : nets) {
                if (filename.toLowerCase().endsWith(".tapn")) {

                    final String netname = filename.replace(".tapn", "");
                    final String filenameFinal = filename;
                    GuiAction tmp = new GuiAction(netname, "Open example file \"" + netname + "\"") {
                        public void actionPerformed(ActionEvent arg0) {
                            InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/Example nets/" + filenameFinal);
                            guiFrameController.ifPresent(o -> {
                                TabContent tab = o.createNewTabFromInputStream(file, netname);
                                o.openTab(tab);
                            });
                        }
                    };
                    tmp.putValue(Action.SMALL_ICON, ResourceManager.getIcon("Net.png"));
                    exampleMenu.add(tmp);
                }
            }
        } else {
            exampleMenu.setEnabled(false);
        }
    }

    public String getCurrentTabName() {
        return appTab.getTitleAt(appTab.getSelectedIndex());
    }

    //XXX: Needs further cleanup
    @Deprecated
    public boolean isShowingDelayEnabledTransitions() {
        return showDelayEnabledTransitionsAction.isSelected();
    }

    public int getSelectedTabIndex() {
        return appTab.getSelectedIndex();
    }

    private TabContent getCurrentTab() {
        return CreateGui.getCurrentTab();
    }

    @Override
    public void setFeatureInfoText(boolean[] features) {
        if (features != null) {
            timeFeatureOptions.setSelectedIndex(features[0] ? 1 : 0);
            gameFeatureOptions.setSelectedIndex(features[1] ? 1 : 0);
        }
    }



}
