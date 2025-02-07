package net.ibxnjadev.plantuml;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.preproc.Defines;
import net.sourceforge.plantuml.security.SecurityProfile;
import net.sourceforge.plantuml.security.SecurityUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String PLANTUML_DEFAULT = "@startuml\n" +
            "skin rose\n" +
            "\n" +
            "entity User {\n" +
            "  * name\n" +
            "  * secondName \n" +
            "}\n" +
            "\n" +
            "entity Pet {\n" +
            "  * name\n" +
            "}\n" +
            "\n" +
            "User \"1\" -- \"1\" Pet : \"> has\"\n" +
            "\n" +
            "@enduml";

    /**
     * PLANTUML_CONFIG_FILE content.
     */
    private static final List<String> CONFIG = new ArrayList<>();


    public static void main(String[] args) {
        final SourceStringReader reader = getSourceStringReader(PLANTUML_DEFAULT);

        try (OutputStream outputStream = new FileOutputStream("diagram.png")) {
            assert reader != null;
            reader.outputImage(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static SourceStringReader getSourceStringReader(String uml) {
        SourceStringReader reader = getSourceStringReaderWithConfig(uml);
        if (reader.getBlocks().isEmpty()) {
            uml = "@startuml\n" + uml + "\n@enduml";
            reader = getSourceStringReaderWithConfig(uml);
            if (reader.getBlocks().isEmpty()) {
                return null;
            }
        }
        return reader;
    }

    private static SourceStringReader getSourceStringReaderWithConfig(String uml) {
        final Defines defines = getPreProcDefines();
        SourceStringReader reader = new SourceStringReader(defines, uml, CONFIG);
        if (!CONFIG.isEmpty() && reader.getBlocks().get(0).getDiagram().getWarningOrError() != null) {
            reader = new SourceStringReader(defines, uml);
        }
        return reader;
    }

    /**
     * Get PlantUML preprocessor defines.
     *
     * @return preprocessor defines
     */
    private static Defines getPreProcDefines() {
        final Defines defines;
        if (SecurityUtils.getSecurityProfile() == SecurityProfile.UNSECURE) {
            // set dirpath to current dir but keep filename and filenameNoExtension undefined
            defines = Defines.createWithFileName(new java.io.File("dummy.puml"));
            defines.overrideFilename("");
        } else {
            defines = Defines.createEmpty();
        }
        return defines;
    }


}