package com.mysrc;

import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SoapBindingStub;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.User;

public class UserUtil {

	public static void main(String[] args) throws Exception {
		
		/* DO NOT CHANGE THE BELOW LINES */
		/* *** START *** */
		LoginResult loginResult = LoginUtil.loginUser(IConstants.loginUserName, IConstants.loginUserPassword);
		//LoginUtil.printUserCredentials(loginResult);
		SoapBindingStub stub = LoginUtil.getStub(loginResult);
		
		QueryResult qResult = stub.query(IConstants.query);
		SObject [] sobj = qResult.getRecords();
		User [] userList = new User[qResult.getSize()];
		int count = 0;
		
		for(SObject s : sobj) {
			User user = (User) s;
			userList[count++] = user;
		}
		/* *** END *** */


		/*** START : ACTIVATE USERS ***/
		UserUtilHelper.setIsActive(userList, stub);
		/*** END : ACTIVATE USERS ***/
		

		/**** START : SET USER PASSWORD ***/
		UserUtilHelper.setUserPassword(userList, IConstants.newUserPassword, stub);
		/**** END : SET USER PASSWORD ***/
		
		/* DO NOT CHANGE THE BELOW LINES */
		/* *** START *** */
		LoginUtil.logout();
		/* *** END *** */
	}
}
