package dk.aau.cs.approximation;

import dk.aau.cs.Messenger;
import dk.aau.cs.io.batchProcessing.LoadedBatchProcessingModel;
import dk.aau.cs.model.tapn.TAPNQuery;
import dk.aau.cs.model.tapn.TimedArcPetriNet;
import dk.aau.cs.model.tapn.TimedArcPetriNetNetwork;
import dk.aau.cs.model.tapn.simulation.TAPNNetworkTrace;
import dk.aau.cs.model.tapn.simulation.TimedArcPetriNetTrace;
import dk.aau.cs.util.Tuple;
import dk.aau.cs.verification.*;
import dk.aau.cs.verification.batchProcessing.BatchProcessingWorker;
import pipe.gui.RunVerificationBase;

public class ApproximationWorker {
	public VerificationResult<TAPNNetworkTrace> normalWorker(
			VerificationOptions options,
			ModelChecker modelChecker,
			Tuple<TimedArcPetriNet, NameMapping> transformedModel,
			ITAPNComposer composer,
			TAPNQuery clonedQuery,
			RunVerificationBase verificationBase,
			TimedArcPetriNetNetwork model
	) throws Exception {

		VerificationResult<TAPNNetworkTrace> toReturn = null;
		VerificationResult<TimedArcPetriNetTrace> result = modelChecker.verify(options, transformedModel, clonedQuery);

		if (result.error()) {
			return new VerificationResult<TAPNNetworkTrace>(result.errorMessage(), result.verificationTime());
		} else {
			toReturn =  new VerificationResult<TAPNNetworkTrace>(
					result.getQueryResult(),
					decomposeTrace(result.getTrace(), transformedModel.value2(), model),
					decomposeTrace(result.getSecondaryTrace(), transformedModel.value2(), model),
					result.verificationTime(),
					result.stats(),
					result.isSolvedUsingStateEquation());
			toReturn.setNameMapping(transformedModel.value2());
		}
		
		return toReturn;
	}

	public VerificationResult<TimedArcPetriNetTrace> batchWorker(
			Tuple<TimedArcPetriNet, NameMapping> composedModel,
			VerificationOptions options,
			pipe.dataLayer.TAPNQuery query,
			LoadedBatchProcessingModel model,
			ModelChecker modelChecker,
			TAPNQuery queryToVerify,
			TAPNQuery clonedQuery,
			BatchProcessingWorker verificationBase
	) throws Exception {
		
		VerificationResult<TimedArcPetriNetTrace> verificationResult = modelChecker.verify(options, composedModel, queryToVerify);
        VerificationResult<TimedArcPetriNetTrace> value;

		if (verificationResult.error()) {
            return new VerificationResult<TimedArcPetriNetTrace>(verificationResult.errorMessage(), verificationResult.verificationTime());
        } else {
	        value = new VerificationResult<TimedArcPetriNetTrace>(
	                verificationResult.getQueryResult(),
	                verificationResult.getTrace(),
	                verificationResult.getSecondaryTrace(),
	                verificationResult.verificationTime(),
	                verificationResult.stats(),
					verificationResult.isSolvedUsingStateEquation()
            );
	        value.setNameMapping(composedModel.value2());
	    }

		return value;
	}
	
	private TAPNNetworkTrace decomposeTrace(TimedArcPetriNetTrace trace, NameMapping mapping, TimedArcPetriNetNetwork model) {
		if (trace == null)
			return null;

		TAPNTraceDecomposer decomposer = new TAPNTraceDecomposer(trace, model, mapping);
		return decomposer.decompose();
	}
	
	private void renameTraceTransitions(TimedArcPetriNetTrace trace) {
		if (trace != null){
			trace.reduceTraceForOriginalNet("_traceNet_", "PTRACE");
			trace.removeTokens("PBLOCK");
		}
	}
	
	private Tuple<TimedArcPetriNet, NameMapping> composeModel(LoadedBatchProcessingModel model) {
		ITAPNComposer composer = new TAPNComposer(new Messenger(){
			public void displayInfoMessage(String message) { }
			public void displayInfoMessage(String message, String title) {}
			public void displayErrorMessage(String message) {}
			public void displayErrorMessage(String message, String title) {}
			public void displayWrappedErrorMessage(String message, String title) {}
			
		}, false);
		Tuple<TimedArcPetriNet, NameMapping> composedModel = composer.transformModel(model.network());
		return composedModel;
	}
}
