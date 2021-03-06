package pipe.gui.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.*;

import net.tapaal.AalNet;
import pipe.gui.CreateGui;

import pipe.gui.graphicElements.PetriNetObject;

/**
 * Class used to implement methods corresponding to mouse events on all
 * PetriNetObjects.
 * 
 * @author unknown
 */
public class PetriNetObjectHandler extends javax.swing.event.MouseInputAdapter implements java.awt.event.MouseWheelListener {

	protected final PetriNetObject myObject;

	// constructor passing in all required objects
	public PetriNetObjectHandler(PetriNetObject obj) {
		myObject = obj;
	}

	/**
	 * Creates the popup menu that the user will see when they right click on a
	 * component
	 */
	public JPopupMenu getPopup(MouseEvent e) {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem(CreateGui.getApp().deleteAction);
		menuItem.setText("Delete");
		popup.add(menuItem);

        if (AalNet.debugEnabled()){
            JTextArea pane = new JTextArea();
            pane.setEditable(false);

            pane.setText(
                "(Debug) \n" +
                "  org X:" + myObject.getOriginalX() + " Y:" + myObject.getOriginalY() +"\n" +
                "  pos X:" + myObject.getPositionX() + " Y:" + myObject.getPositionY() +""
            );

		    popup.insert(pane, 1);
        }

		return popup;
	}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
	public void mouseWheelMoved(MouseWheelEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	//Changes dispatches an event to the parent component, with the mouse location updated to the parent
	//MouseLocation is relative to the component
	public void dispatchToParentWithMouseLocationUpdated(MouseEvent e) {
		e.translatePoint(myObject.getX(), myObject.getY());
		myObject.getParent().dispatchEvent(e);
	}

}
