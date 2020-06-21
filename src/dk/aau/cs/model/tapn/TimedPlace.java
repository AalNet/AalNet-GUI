package dk.aau.cs.model.tapn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import dk.aau.cs.model.tapn.event.TimedPlaceListener;
import dk.aau.cs.util.Tuple;

public abstract class TimedPlace {
    protected static final Pattern namePattern = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");
    protected final List<TimedPlaceListener> listeners = new ArrayList<TimedPlaceListener>();
    protected Tuple<PlaceType, Integer> extrapolation = new Tuple<PlaceType, Integer>(PlaceType.Dead, -2);
    protected String name;
    protected TimeInvariant invariant;
    protected TimedMarking currentMarking;

    public enum PlaceType{
		Standard, Invariant, Dead
	}
	
	public abstract void addTimedPlaceListener(TimedPlaceListener listener);
	public abstract void removeTimedPlaceListener(TimedPlaceListener listener);

	public abstract boolean isShared();

	public abstract String name();
	public abstract void setName(String newName);

	public abstract TimeInvariant invariant();
	public abstract void setInvariant(TimeInvariant invariant);

	public abstract List<TimedToken> tokens();
	public abstract int numberOfTokens();

	public abstract void setCurrentMarking(TimedMarking marking);
	
	public abstract void addToken(TimedToken timedToken);
	public abstract void addTokens(Iterable<TimedToken> tokens);

    public void addTokens(int numberOfTokensToAdd) {
        for (int i = 0; i < numberOfTokensToAdd; i++) {
            addToken(new TimedToken(this, BigDecimal.ZERO));
        }
    }

    public void removeTokens(int numberOfTokensToRemove) {
        for (int i = 0; i < numberOfTokensToRemove; i++) {
            removeToken();
        }
    }

    protected abstract void removeToken();
	
	public abstract Tuple<PlaceType, Integer> extrapolate();
	
	public abstract TimedPlace copy();
	
	/**
	 * Returns the tokens in the place, sorted decreasing
	 */
	public List<TimedToken> sortedTokens(){
		List<TimedToken> copy = new ArrayList<TimedToken>(tokens());
		copy.sort((o1, o2) -> {
			//Order reverse
			return o1.age().compareTo(o2.age()) * -1;
		});
		
		return copy;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)	return true;
		if(!(obj instanceof TimedPlace))	return false;
		TimedPlace other = (TimedPlace) obj;
		return name() == other.name();
	}
	
	
//	public abstract void addInhibitorArc(TimedInhibitorArc arc);
//	public abstract void addToPreset(TransportArc arc);
//	public abstract void addToPreset(TimedOutputArc arc);
//	public abstract void addToPostset(TransportArc arc);
//	public abstract void addToPostset(TimedInputArc arc);
//
//	public abstract void removeFromPostset(TimedInputArc arc);
//	public abstract void removeFromPostset(TransportArc arc);
//	public abstract void removeFromPreset(TransportArc arc);
//	public abstract void removeFromPreset(TimedOutputArc arc);
//	public abstract void removeInhibitorArc(TimedInhibitorArc arc);

}