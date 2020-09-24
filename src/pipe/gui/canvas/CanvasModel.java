package pipe.gui.canvas;

import pipe.gui.graphicElements.GraphicalElement;
import pipe.gui.graphicElements.PetriNetObject;

import java.util.ArrayList;

public abstract class CanvasModel {

    //XXX: Temp solution while refactoring, should be changed to interface to now allow to many actions
    //Long term should use callback to not have tight coupling
    private Canvas view;

    public final void addedToView(Canvas view){this.view = view;}
    public final void removedFromView() {this.view = null;}

    //XXX temp solution while refactorting, component removes children them self
    //migth not be best solution long term.
    protected final void removeFromViewIfConnected(GraphicalElement g) {
        if (view != null) {
            view.removePetriNetObject(g);
        }
    }

    protected final void addToViewIfConnected(GraphicalElement g) {
        if (view != null) {
            view.addNewPetriNetObject(g);
        }
    }

    //TODO: cleanup function names and types, moved naively from DataLayer
    public abstract Iterable<PetriNetObject> getPetriNetObjectsWithArcPathPoint();
    public abstract ArrayList<PetriNetObject> getPlaceTransitionObjects();
    public abstract ArrayList<PetriNetObject> getPNObjects();

}
