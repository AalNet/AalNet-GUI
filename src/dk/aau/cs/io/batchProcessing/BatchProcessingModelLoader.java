package dk.aau.cs.io.batchProcessing;

import dk.aau.cs.io.TapnXmlLoader;
import dk.aau.cs.util.FormatException;

import java.io.File;

public class BatchProcessingModelLoader {
    public BatchProcessingModelLoader() {
    }

    public LoadedBatchProcessingModel load(File file) throws FormatException {
        var newFormatLoader = new TapnXmlLoader();

        return newFormatLoader.load(file);
    }
}
