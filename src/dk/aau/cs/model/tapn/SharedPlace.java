package dk.aau.cs.model.tapn;

import java.util.ArrayList;

import pipe.dataLayer.Template;
import pipe.gui.CreateGui;
import dk.aau.cs.util.Tuple;

public class SharedPlace extends TimedPlace{

    private TimedArcPetriNetNetwork network;

    public SharedPlace(String name){
		this(name, TimeInvariant.LESS_THAN_INFINITY);
	}
	
	public SharedPlace(String name, TimeInvariant invariant){
		setName(name);
		setInvariant(invariant);
	}

    public void setNetwork(TimedArcPetriNetNetwork network) {
		this.network = network;		
	}
	
	public TimedArcPetriNetNetwork network(){
		return network;
	}
	


	public TimedPlace copy() {
		return new SharedPlace(this.name(), this.invariant().copy());
	}

	public boolean isShared() {
		return true;
	}

	
	public ArrayList<String> getComponentsUsingThisPlace(){
		ArrayList<String> components = new ArrayList<String>();
		for(Template t : CreateGui.getCurrentTab().allTemplates()){
			TimedPlace tp = t.model().getPlaceByName(SharedPlace.this.name);
			if(tp != null){
				components.add(t.model().name());
			}
		}
		return components;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SharedPlace))
			return false;
		SharedPlace other = (SharedPlace) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
