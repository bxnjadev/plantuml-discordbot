package net.ibxnjadev.plantuml.diagram;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.preproc.Defines;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface create the plantuml diagram using the plantuml library
 * this interface is based in the way for create diagram for the project
 * diagram-server : <a href="https://github.com/plantuml/plantuml-server/blob/master/src/main/java/net/sourceforge/plantuml/servlet/DiagramResponse.java">...</a>
 */

public interface PlantumlDiagramCreator {

    /**
     * Write the diagram in the destin
     * @param uml the uml diagram content
     * @param outputStream the outputStream destiny
     */

    void output(String uml,
                OutputStream outputStream) throws IOException;

    SourceStringReader getSourceStringReader(String uml);

    SourceStringReader getSourceStringReaderWithConfig(String uml);

    /**
     * Get PlantUML preprocessor defines.
     *
     * @return preprocessor defines
     */

    Defines getPreProcDefines();

}
