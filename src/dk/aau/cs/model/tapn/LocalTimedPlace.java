package dk.aau.cs.model.tapn;

import dk.aau.cs.util.Tuple;

public class LocalTimedPlace  extends TimedPlace {

	private TimedArcPetriNet model;

    public LocalTimedPlace(String name) {
		this(name, TimeInvariant.LESS_THAN_INFINITY);
	}

	public LocalTimedPlace(String name, TimeInvariant invariant) {
		setName(name);
		setInvariant(invariant);
	}
	
	public TimedArcPetriNet model() {
		return model;
	}

	public void setModel(TimedArcPetriNet model) {
		this.model = model;
	}

	public boolean isShared() {
		return false;
	}

	public LocalTimedPlace copy() {
		LocalTimedPlace p = new LocalTimedPlace(name);

		p.invariant = invariant.copy();

		return p;
	}

	@Override
	public String toString() {
		if (model() != null)
			return model().name() + "." + name;
		else
			return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((model() == null) ? 0 : model().hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LocalTimedPlace))
			return false;
		LocalTimedPlace other = (LocalTimedPlace) obj;
		if (model() == null) {
			if (other.model() != null)
				return false;
		} else if (!model().equals(other.model()))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
