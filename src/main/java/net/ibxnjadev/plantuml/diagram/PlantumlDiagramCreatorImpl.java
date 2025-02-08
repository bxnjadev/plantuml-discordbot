package net.ibxnjadev.plantuml.diagram;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.preproc.Defines;
import net.sourceforge.plantuml.security.SecurityProfile;
import net.sourceforge.plantuml.security.SecurityUtils;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PlantumlDiagramCreatorImpl implements PlantumlDiagramCreator {

    /**
     * PLANTUML_CONFIG_FILE content.
     */
    private static final List<String> CONFIG = new ArrayList<>();


    @Override
    public void output(String uml, OutputStream outputStream) throws IOException {
        SourceStringReader sourceStringReader = getSourceStringReader(uml);
        sourceStringReader.outputImage(outputStream);
    }

    @Override
    public SourceStringReader getSourceStringReader(String uml) {
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

    @Override
    public SourceStringReader getSourceStringReaderWithConfig(String uml) {
        final Defines defines = getPreProcDefines();
        SourceStringReader reader = new SourceStringReader(defines, uml, CONFIG);
        if (!CONFIG.isEmpty() && reader.getBlocks().get(0).getDiagram().getWarningOrError() != null) {
            reader = new SourceStringReader(defines, uml);
        }
        return reader;
    }

    @Override
    public Defines getPreProcDefines() {
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
