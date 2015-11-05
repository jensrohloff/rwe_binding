package org.openhab.binding.rwe;

import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.openhab.binding.rwe.internal.RweContext;
import org.openhab.binding.rwe.internal.communicator.client.LoginFailedException;
import org.openhab.binding.rwe.internal.communicator.client.RweRestClient;
import org.openhab.binding.rwe.notification.NotificationList;
import org.openhab.binding.rwe.response.GetAllLogicalDeviceStatesResponse;
import org.openhab.binding.rwe.response.GetEntitiesResponse;

public class RestTests {

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void setup(){
		RweContext context = RweContext.getInstance();
		Dictionary properties = new Hashtable();
		properties.put("username", System.getProperty("user"));
		properties.put("password", System.getProperty("pwd"));
		context.getConfig().parse(properties);
	}
	
	@Test
	public void testLogin() throws JAXBException, URISyntaxException, XMLStreamException, NoSuchAlgorithmException, LoginFailedException {
		
		RweRestClient client = new RweRestClient();
		client.start();	
	}
	
	@Test
	public void testGetEntities() throws JAXBException, URISyntaxException, XMLStreamException, NoSuchAlgorithmException, LoginFailedException {
		
		RweRestClient client = new RweRestClient();
		client.start();	
		GetEntitiesResponse data = client.getEntitiesResponse();
		assertTrue(data !=null);
		
	}
	
	@Test
	public void testGetAllLogicalDeviceStates() throws JAXBException, URISyntaxException, XMLStreamException, NoSuchAlgorithmException, LoginFailedException {
		
		RweRestClient client = new RweRestClient();
		client.start();		
		GetAllLogicalDeviceStatesResponse data = client.getAllLogicalDeviceStatesRequest();
		assertTrue(data !=null);
		
	}
	
	@Test
	public void testNotificationList() throws JAXBException, URISyntaxException, XMLStreamException, NoSuchAlgorithmException, LoginFailedException {
		
		RweRestClient client = new RweRestClient();
		client.start();	
		NotificationList data = client.statusUpdates();
		assertTrue(data !=null);
		
	}
}
