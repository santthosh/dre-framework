//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.07.30 at 08:29:01 PM EDT 
//


package edu.ncsu.dre.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Segregator" type="{}Component"/>
 *         &lt;element name="ResearchScheduler" type="{}Component"/>
 *         &lt;element name="Aggregator" type="{}Component"/>
 *         &lt;element name="ServiceProvider" type="{}Component" maxOccurs="unbounded"/>
 *         &lt;element name="Parameter" type="{}Arguments" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "segregator",
    "researchScheduler",
    "aggregator",
    "serviceProvider",
    "parameter"
})
@XmlRootElement(name = "EngineConfiguration")
public class EngineConfiguration {

    @XmlElement(name = "Segregator", required = true)
    protected Component segregator;
    @XmlElement(name = "ResearchScheduler", required = true)
    protected Component researchScheduler;
    @XmlElement(name = "Aggregator", required = true)
    protected Component aggregator;
    @XmlElement(name = "ServiceProvider", required = true)
    protected List<Component> serviceProvider;
    @XmlElement(name = "Parameter")
    protected List<Arguments> parameter;

    /**
     * Gets the value of the segregator property.
     * 
     * @return
     *     possible object is
     *     {@link Component }
     *     
     */
    public Component getSegregator() {
        return segregator;
    }

    /**
     * Sets the value of the segregator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Component }
     *     
     */
    public void setSegregator(Component value) {
        this.segregator = value;
    }

    /**
     * Gets the value of the researchScheduler property.
     * 
     * @return
     *     possible object is
     *     {@link Component }
     *     
     */
    public Component getResearchScheduler() {
        return researchScheduler;
    }

    /**
     * Sets the value of the researchScheduler property.
     * 
     * @param value
     *     allowed object is
     *     {@link Component }
     *     
     */
    public void setResearchScheduler(Component value) {
        this.researchScheduler = value;
    }

    /**
     * Gets the value of the aggregator property.
     * 
     * @return
     *     possible object is
     *     {@link Component }
     *     
     */
    public Component getAggregator() {
        return aggregator;
    }

    /**
     * Sets the value of the aggregator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Component }
     *     
     */
    public void setAggregator(Component value) {
        this.aggregator = value;
    }

    /**
     * Gets the value of the serviceProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Component }
     * 
     * 
     */
    public List<Component> getServiceProvider() {
        if (serviceProvider == null) {
            serviceProvider = new ArrayList<Component>();
        }
        return this.serviceProvider;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Arguments }
     * 
     * 
     */
    public List<Arguments> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<Arguments>();
        }
        return this.parameter;
    }

}
