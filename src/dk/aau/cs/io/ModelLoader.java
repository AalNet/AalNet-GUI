package dk.aau.cs.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import dk.aau.cs.TCTL.Parsing.ParseException;

public class ModelLoader {

    public ModelLoader() {
    }

    public LoadedModel load(File file) throws Exception {
        TapnXmlLoader newFormatLoader = new TapnXmlLoader();

        return newFormatLoader.load(file);
    }


    public LoadedModel load(InputStream file) throws Exception {
        TapnXmlLoader newFormatLoader = new TapnXmlLoader();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = file.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return newFormatLoader.load(new ByteArrayInputStream(baos.toByteArray()));

    }

}
