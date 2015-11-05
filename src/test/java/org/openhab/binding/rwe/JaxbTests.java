package org.openhab.binding.rwe;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.openhab.binding.rwe.devices.LDs;
import org.openhab.binding.rwe.devices.PushButtonSensor;
import org.openhab.binding.rwe.notification.NotificationList;
import org.openhab.binding.rwe.request.LoginRequest;
import org.openhab.binding.rwe.request.Request;
import org.openhab.binding.rwe.response.AcknowledgeResponse;
import org.openhab.binding.rwe.response.ControlResultResponse;
import org.openhab.binding.rwe.response.GetAllLogicalDeviceStatesResponse;
import org.openhab.binding.rwe.response.GetEntitiesResponse;
import org.openhab.binding.rwe.response.LoginResponse;
import org.openhab.binding.rwe.response.Response;
import org.openhab.binding.rwe.states.LogicalDeviceState;


public class JaxbTests {
	
	private JAXBContext jc = null;
	
	@Before
	public void setUp() throws Exception {
		jc = JAXBContext.newInstance("org.openhab.binding.rwe.devices:org.openhab.binding.rwe.location:org.openhab.binding.rwe.request:org.openhab.binding.rwe.states:org.openhab.binding.rwe.response:org.openhab.binding.rwe.notification");
		
	}

	@Test
	public void testControlResultResponse() throws JAXBException, URISyntaxException, XMLStreamException {
		URL url = this.getClass().getResource("/ControlResultResponse.xml");
		 XMLInputFactory xif = XMLInputFactory.newFactory();
	        StreamSource xml = new StreamSource(url.getFile());
	        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
	
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		JAXBElement<Response> userElement = jaxbUnmarshaller.unmarshal(xsr,Response.class);
		assertTrue(userElement.getValue().getBaseResponse() instanceof ControlResultResponse);
	}
	
	@Test
	public void testAcknowledgeResponse() throws JAXBException, URISyntaxException, XMLStreamException {
		URL url = this.getClass().getResource("/AcknowledgeResponse.xml");
		 XMLInputFactory xif = XMLInputFactory.newFactory();
	        StreamSource xml = new StreamSource(url.getFile());
	        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
		
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		JAXBElement<Response> userElement = jaxbUnmarshaller.unmarshal(xsr,Response.class);
		assertTrue(userElement.getValue().getBaseResponse() instanceof AcknowledgeResponse);
	}
	
	@Test
	public void testNotificationListResponse() throws JAXBException, URISyntaxException, XMLStreamException {
		URL url = this.getClass().getResource("/NotificationList.xml");
		 XMLInputFactory xif = XMLInputFactory.newFactory();
	        StreamSource xml = new StreamSource(url.getFile());
	        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
		
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		JAXBElement<NotificationList> userElement = jaxbUnmarshaller.unmarshal(xsr,NotificationList.class);
		assertTrue(userElement.getValue() instanceof NotificationList);
	}
	
	@Test
	public void testLoginResponse() throws JAXBException, URISyntaxException, XMLStreamException {
		URL url = this.getClass().getResource("/LoginResponse.xml");
		 XMLInputFactory xif = XMLInputFactory.newFactory();
	        StreamSource xml = new StreamSource(url.getFile());
	        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
		
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		JAXBElement<Response> userElement = jaxbUnmarshaller.unmarshal(xsr,Response.class);
		assertTrue(userElement.getValue().getBaseResponse() instanceof LoginResponse);
	}
	
	@Test
	public void testLoginRequest() throws JAXBException, URISyntaxException, XMLStreamException {
		Request base = new Request();
		LoginRequest login = new LoginRequest();
		login.setUserName("test");
		login.setPassword("test");
		login.setSessionId("test");
		login.setVersion(1.7f);	
		base.setBaseRequest(login);
		 final StringWriter w = new StringWriter();
		
		Marshaller jaxbMarshaller = jc.createMarshaller();
		jaxbMarshaller.marshal( base, w);
		
		String data = w.toString().replace("<Request>", "").replace("</Request>", "");
		
		assertTrue(data!=null);
	}
	
	@Test
	public void testGetEntitiesResponse() throws JAXBException, URISyntaxException, XMLStreamException {
		URL url = this.getClass().getResource("/GetEntitiesResponse.xml");
		 XMLInputFactory xif = XMLInputFactory.newFactory();
	        StreamSource xml = new StreamSource(url.getFile());
	        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
	       
		
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		JAXBElement<Response> userElement = jaxbUnmarshaller.unmarshal(xsr,Response.class);
		
		
		assertTrue(userElement.getValue().getBaseResponse() instanceof GetEntitiesResponse);
	}
	
	@Test
	public void testLogicalDevices() throws JAXBException, URISyntaxException, XMLStreamException {
		JAXBContext jc = JAXBContext.newInstance("de.itarchitecture.smarthome.api.devices");
		
		URL url = this.getClass().getResource("/LogicalDevices.xml");
		 XMLInputFactory xif = XMLInputFactory.newFactory();
	        StreamSource xml = new StreamSource(url.getFile());
	        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
	       
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		JAXBElement<LDs> userElement = jaxbUnmarshaller.unmarshal(xsr,LDs.class);
		
		
		assertTrue(userElement.getValue() instanceof LDs);
	}
	
	@Test
	public void testGetAllLogicalDeviceStatesResponse() throws JAXBException, URISyntaxException, XMLStreamException {
		@SuppressWarnings("unused")
		List<LogicalDeviceState> states = null;
		URL url = this.getClass().getResource("/GetAllLogicalDeviceStatesResponse.xml");
		 XMLInputFactory xif = XMLInputFactory.newFactory();
	        StreamSource xml = new StreamSource(url.getFile());
	        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
	       
		
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		JAXBElement<Response> userElement = jaxbUnmarshaller.unmarshal(xsr,Response.class);
		
		if(userElement.getValue().getBaseResponse() instanceof GetAllLogicalDeviceStatesResponse){
			states = ((GetAllLogicalDeviceStatesResponse)userElement.getValue().getBaseResponse()).getStates().getLogicalDeviceStates();
		}
		assertTrue(userElement.getValue().getBaseResponse() instanceof GetAllLogicalDeviceStatesResponse);
	}
	
	/*
	 * test just prints to system.out
	 */	
	@Test
	public void testControlResultResponsefromObject() throws JAXBException, URISyntaxException {
		Response base = new Response();
		ControlResultResponse control = new ControlResultResponse();
		control.setResult("Ok");
		control.setCorrespondingRequestId("123");				
		control.setVersion(new BigDecimal(1.70));
		base.setBaseResponse(control);
				
		Marshaller jaxbMarshaller = jc.createMarshaller();
		 
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
		
		jaxbMarshaller.marshal(base, System.out);
	}
	
	/*
	 * test just prints to system.out
	 */	
	@Test
	public void testDevicesfromObject() throws JAXBException, URISyntaxException {
		LDs lds = new LDs();
		PushButtonSensor button = new PushButtonSensor();
		lds.getLDS().add(button);
		
		
		
		Marshaller jaxbMarshaller = jc.createMarshaller();		
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
		
		jaxbMarshaller.marshal(lds, System.out);
	}
	
}
