//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.10.17 at 03:33:43 AM EDT 
//


package edu.ncsu.dre.impl.engine.alexa;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.ncsu.dre.impl.engine.alexa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.ncsu.dre.impl.engine.alexa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchResponse }
     * 
     */
    public SearchResponse createSearchResponse() {
        return new SearchResponse();
    }

    /**
     * Create an instance of {@link ResponseMetadataComponent }
     * 
     */
    public ResponseMetadataComponent createResponseMetadataComponent() {
        return new ResponseMetadataComponent();
    }

    /**
     * Create an instance of {@link ResultComponent }
     * 
     */
    public ResultComponent createResultComponent() {
        return new ResultComponent();
    }

    /**
     * Create an instance of {@link SearchResultComponent }
     * 
     */
    public SearchResultComponent createSearchResultComponent() {
        return new SearchResultComponent();
    }

}
