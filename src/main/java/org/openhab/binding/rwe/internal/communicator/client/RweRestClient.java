package org.openhab.binding.rwe.internal.communicator.client;

import java.io.StringReader;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.codec.binary.Base64;
import org.openhab.binding.rwe.internal.RweContext;
import org.openhab.binding.rwe.internal.binding.config.RweBindingConfig;
import org.openhab.binding.rwe.internal.communicator.ValueItemIteratorCallback;
import org.openhab.binding.rwe.notification.NotificationList;
import org.openhab.binding.rwe.request.ActionType;
import org.openhab.binding.rwe.request.BaseRequest;
import org.openhab.binding.rwe.request.GetAllLogicalDeviceStatesRequest;
import org.openhab.binding.rwe.request.GetEntitiesRequest;
import org.openhab.binding.rwe.request.LoginRequest;
import org.openhab.binding.rwe.request.LogoutRequest;
import org.openhab.binding.rwe.request.NotificationRequest;
import org.openhab.binding.rwe.request.NotificationType;
import org.openhab.binding.rwe.request.Request;
import org.openhab.binding.rwe.request.SetActuatorStatesRequest;
import org.openhab.binding.rwe.response.AcknowledgeResponse;
import org.openhab.binding.rwe.response.BaseResponse;
import org.openhab.binding.rwe.response.ControlResultResponse;
import org.openhab.binding.rwe.response.GetAllLogicalDeviceStatesResponse;
import org.openhab.binding.rwe.response.GetEntitiesResponse;
import org.openhab.binding.rwe.response.LoginResponse;
import org.openhab.binding.rwe.response.Response;
import org.openhab.binding.rwe.states.LogicalDeviceState;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class RweRestClient implements RestClient{

	private String clientId = UUID.randomUUID().toString();

	private RweContext context = RweContext.getInstance();

	private static final String COMMAND_PATH = "cmd";

	private static final String UPDATE_PATH = "upd";

	private Integer currentConfigurationVersion = null;

	private String sessionId = null;

	public LoginResponse login()
			throws JAXBException, XMLStreamException, LoginFailedException,
			NoSuchAlgorithmException {
		LoginRequest login = new LoginRequest();
		login.setUserName(context.getConfig().getUsername());
		login.setPassword(hashPassword(context.getConfig().getPassword()));
		BaseResponse response = executeRequest(login);

		if (response instanceof LoginResponse) {
			this.sessionId = response.getSessionId();
			this.currentConfigurationVersion = response
					.getCurrentConfigurationVersion();
			return (LoginResponse) response;
		} else {
			throw new LoginFailedException(
					"LoginFailed: Authentication with user:" + context.getConfig().getUsername()
							+ " was not possible. Session ID is empty.");
		}
	}

	public GetEntitiesResponse getEntitiesResponse() throws JAXBException,
			XMLStreamException {
		GetEntitiesRequest request = new GetEntitiesRequest();
		BaseResponse response = executeRequest(request);
		if (response instanceof GetEntitiesResponse) {
			return (GetEntitiesResponse) response;
		} else
			// TODO fix it, error
			return null;
	}

	public void subscribe(NotificationType notificationType)
			throws JAXBException, XMLStreamException {
		notificationHandler(ActionType.SUBSCRIBE, notificationType);
	}

	public void unsubscribe(NotificationType notificationType)
			throws JAXBException, XMLStreamException {
		notificationHandler(ActionType.UNSUBSCRIBE, notificationType);
	}

	private void notificationHandler(ActionType action,
			NotificationType notificationType) throws JAXBException,
			XMLStreamException {
		NotificationRequest request = new NotificationRequest();
		request.setAction(action);
		request.setNotificationType(notificationType);
		BaseResponse response = executeRequest(request);
		if (response instanceof AcknowledgeResponse) {
			return;
		} else {
			// TODO error
		}
	}

	public GetAllLogicalDeviceStatesResponse getAllLogicalDeviceStatesRequest()
			throws JAXBException, XMLStreamException {
		GetAllLogicalDeviceStatesRequest request = new GetAllLogicalDeviceStatesRequest();
		if (currentConfigurationVersion != null) {
			request.setBasedOnConfigVersion(currentConfigurationVersion);
		}
		BaseResponse response = executeRequest(request);
		if (response instanceof GetAllLogicalDeviceStatesResponse) {
			return (GetAllLogicalDeviceStatesResponse) response;
		} else
			// TODO fix it
			return null;
	}

	public NotificationList statusUpdates()  {		
		Client c = Client.create();
		ClientResponse response  = c.resource("https://" + context.getConfig().getHost() + "/"
				+ UPDATE_PATH).accept(MediaType.TEXT_XML)
		.header("ClientId", clientId).post(ClientResponse.class,UPDATE_PATH);		
		String r_data = response.getEntity(String.class);		
		XMLInputFactory xif = XMLInputFactory.newFactory();
		XMLStreamReader xsr;
		JAXBElement<NotificationList> userElement = null;
		try {
			xsr = xif
					.createXMLStreamReader(new StringReader(r_data));
		
		JAXBContext jc = JAXBContext
				.newInstance("org.openhab.binding.rwe.devices:org.openhab.binding.rwe.location:org.openhab.binding.rwe.request:org.openhab.binding.rwe.states:org.openhab.binding.rwe.response:org.openhab.binding.rwe.notification");
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		userElement = jaxbUnmarshaller.unmarshal(
				xsr, NotificationList.class);
		
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userElement.getValue();
	}

	public ControlResultResponse setActuatorStatesRequest(
			LogicalDeviceState newState) throws JAXBException,
			XMLStreamException {
		SetActuatorStatesRequest request = new SetActuatorStatesRequest();
		if (currentConfigurationVersion != null) {
			request.setBasedOnConfigVersion(currentConfigurationVersion);
		}
		request.getActuatorStates().getLogicalDeviceStates().add(newState);
		BaseResponse response = executeRequest(request);
		if (response instanceof GetAllLogicalDeviceStatesResponse) {
			return (ControlResultResponse) response;
		} else
			// TODO fix it
			return null;
	}

	public void logout(String sessionId) throws JAXBException,
			XMLStreamException, LogoutException {
		LogoutRequest logout = new LogoutRequest();
		logout.setSessionId(sessionId);
		BaseResponse response = executeRequest(logout);
		if (response instanceof AcknowledgeResponse)
			throw new LogoutException("Logout Failed.");
	}

	private BaseResponse executeRequest(BaseRequest request)
			throws JAXBException, XMLStreamException {
		Request base = new Request();
		request.setVersion(context.getConfig().getVersion());
		request.setRequestId(generateRequestId());
		if (sessionId != null) {
			request.setSessionId(sessionId);
		}

		base.setBaseRequest(request);
		StringWriter w = new StringWriter();

		// File file = new File(url.toURI());
		JAXBContext jc = JAXBContext
				.newInstance("org.openhab.binding.rwe.devices:org.openhab.binding.rwe.location:org.openhab.binding.rwe.request:org.openhab.binding.rwe.states:org.openhab.binding.rwe.response:org.openhab.binding.rwe.notification");

		Marshaller jaxbMarshaller = jc.createMarshaller();
		jaxbMarshaller.marshal(base, w);

		String data = w.toString().replace("<Request>", "")
				.replace("</Request>", "");

		Client c = Client.create();
		ClientResponse response  = c.resource("https://" + context.getConfig().getHost() + "/"
				+ COMMAND_PATH).accept(MediaType.TEXT_XML)
		.header("ClientId", clientId)
				.entity(data, MediaType.TEXT_XML).post(ClientResponse.class,data);
		String r_data = response.getEntity(String.class);

		StringBuilder builder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response>");
		builder.append(r_data);
		builder.append("</Response>");
		XMLInputFactory xif = XMLInputFactory.newFactory();
		XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(
				builder.toString()));
		Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
		JAXBElement<Response> userElement = jaxbUnmarshaller.unmarshal(xsr,
				Response.class);
		return userElement.getValue().getBaseResponse();
	}

	private String generateRequestId() {
		return UUID.randomUUID().toString();
	}

	private String hashPassword(String plainPassword)
			throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(plainPassword.getBytes());
		byte byteData[] = md.digest();
		return new String(Base64.encodeBase64(byteData));

	}


	@Override
	public void registerCallback() {
		try {
			subscribe(NotificationType.DEVICE_STATE_CHANGES);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void releaseCallback() {
		try {
			unsubscribe(NotificationType.DEVICE_STATE_CHANGES);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void iterateAllStates(
			ValueItemIteratorCallback callback) {
		try {
			GetAllLogicalDeviceStatesResponse response = getAllLogicalDeviceStatesRequest();
			List<LogicalDeviceState> states = response.getStates().getLogicalDeviceStates();
			for (LogicalDeviceState state : states){
				RweBindingConfig bindingConfig = new RweBindingConfig(state.getLID());
				callback.iterate(bindingConfig, state);
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//TODO
	@Override
	public void start()  {
		if(sessionId == null){
			try {
				login();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LoginFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
	}

}
