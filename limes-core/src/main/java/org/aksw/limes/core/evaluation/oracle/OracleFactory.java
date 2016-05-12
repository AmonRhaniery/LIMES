package org.aksw.limes.core.evaluation.oracle;

import org.aksw.limes.core.io.mapping.Mapping;
import org.aksw.limes.core.io.mapping.reader.CSVMappingReader;
import org.aksw.limes.core.io.mapping.reader.IMappingReader;

//import de.uni_leipzig.simba.learning.oracle.mappingreader.XMLMappingReader;


/**
 * Factory class that gives different types of oracles based on the file type
 * @author ngonga
 * @author mofeed
 * @version 1.0
 */
public class OracleFactory {

    /** Creates an oracle based on the input type (i.e., the type of file within which the
     * oracle data is contained) and the type of oracle needed.
     * @param filePath Path to the file containing the data encapsulated by the oracle
     * @param inputType Type of the file
     * @param oracleType Type of oracle required
     * @return An oracle that contains the data found at filePath
     */
    public static IOracle getOracle(String filePath, String inputType, String oracleType) {
    	IMappingReader reader=null;
        IOracle oracle;
        System.out.println("Getting reader of type " + inputType);
        if (inputType.equalsIgnoreCase("csv")) //scan input types here
        {
            reader = new CSVMappingReader();
        } else if (inputType.equalsIgnoreCase("xml")) //scan input types here
        {
        	//commented by mofeed to check if it exists or not and the possibilities of adding it
            //reader = new XMLMappingReader();
        } else if (inputType.equalsIgnoreCase("tab")) //scan input types here
        {
            reader = new CSVMappingReader();
            ((CSVMappingReader) reader).setDelimiter("\t");
        } else //default
        {
            reader = new CSVMappingReader();
        }
         //now readData
        Mapping m = reader.read(filePath);

        //finally return the right type of oracle
        if (inputType.equals("simple")) //scan input types here
        {
            oracle = new SimpleOracle(m);
        } else //default
        {
            oracle = new SimpleOracle(m);
        }
//        oracle.loadData(m);
        return oracle;
    }
}