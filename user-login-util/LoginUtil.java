package com.mysrc;

import java.rmi.RemoteException;

import org.apache.axis.AxisFault;

import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.soap.enterprise.Login;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.SessionHeader;
import com.sforce.soap.enterprise.Soap;
import com.sforce.soap.enterprise.SoapBindingStub;
import com.sforce.soap.enterprise.SoapProxy;
import com.sforce.soap.enterprise.fault.InvalidIdFault;
import com.sforce.soap.enterprise.fault.LoginFault;
import com.sforce.soap.enterprise.fault.UnexpectedErrorFault;


/**
 * A util class to support the WS Client calls
 * 
 */
final public class LoginUtil {

	/**
	 * The <code>soap</code> instance.
	 */
	private final static Soap soap = new SoapProxy();
	private static SoapBindingStub stub;

	
	private LoginUtil() {
		// do nothing, cannot be invoked
	}
	
	
	/**
	 * The method expects the security token to be appended in the password for API login
	 * 
	 * @param userName					the username for login
	 * @param password					the password appended with the security token for API login
	 * @return the <code>LoginResult</code> object
	 * @throws InvalidIdFault
	 * @throws UnexpectedErrorFault
	 * @throws LoginFault
	 * @throws RemoteException
	 */
	public static LoginResult loginUser(String userName, String password) throws InvalidIdFault, UnexpectedErrorFault, LoginFault, RemoteException {
		LoginResult loginResult = soap.login(userName, password);
		return loginResult;
	}

	
	/**
	 * The method expects the security token to be appended in the password for API login
	 * 
	 * @param loginObject				the login object containing the username and password details
	 * @return the <code>LoginResult</code> object
	 * @throws InvalidIdFault
	 * @throws UnexpectedErrorFault
	 * @throws LoginFault
	 * @throws RemoteException
	 */
	public static LoginResult loginUser(Login loginObject) throws InvalidIdFault, UnexpectedErrorFault, LoginFault, RemoteException {
		LoginResult loginResult = soap.login(loginObject.getUsername(), loginObject.getPassword());
		return loginResult;
	}
	

	public static void logout() throws UnexpectedErrorFault, RemoteException {
		stub.logout();
	}

	
	/**
	 * Returns the soap binding stub for operations based on the login result.
	 * 
	 * @param loginResult
	 * @return
	 * @throws AxisFault
	 */
	public static SoapBindingStub getStub(LoginResult loginResult) throws AxisFault {
		stub = new SoapBindingStub();
		stub._setProperty(SoapBindingStub.ENDPOINT_ADDRESS_PROPERTY, loginResult.getServerUrl());
		stub.setHeader("urn:enterprise.soap.sforce.com", "SessionHeader", new SessionHeader(loginResult.getSessionId()));

		return stub;
	}
	
	
	/**
	 * @param loginResult
	 * @throws InvalidIdFault
	 * @throws UnexpectedErrorFault
	 * @throws LoginFault
	 * @throws RemoteException
	 */
	public static void printUserCredentials(LoginResult loginResult) throws InvalidIdFault, UnexpectedErrorFault, LoginFault, RemoteException {
		
		GetUserInfoResult userInfo = loginResult.getUserInfo();

		// print the logged in user credentials
		System.out.println("User Credentials");
		System.out.println("**************************************************");
		System.out.println("Welcome -- " + userInfo.getUserFullName());
		System.out.println("Organization Name -- " + userInfo.getOrganizationName());
		System.out.println("Email -- " + userInfo.getUserEmail());
		System.out.println("Session Id -- " + loginResult.getSessionId());
		System.out.println("Server URL -- " + loginResult.getServerUrl());
		System.out.println("**************************************************");
	}
}
