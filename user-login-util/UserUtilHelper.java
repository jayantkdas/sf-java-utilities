package com.mysrc;

import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.SoapBindingStub;
import com.sforce.soap.enterprise.sobject.User;

public class UserUtilHelper {

	
	/**
	 * @param userList
	 * @param stub
	 */
	public static void setIsActive(User [] userList, SoapBindingStub stub) {

		System.out.println("Total Users to be updated : " + userList.length);

		User [] updateUsers = new User[userList.length];
		int count = 0;

		/*
		 * Iterate through the query result to:
		 * - update user specific fields
		 */
		for(User user : userList) {
			user.setIsActive(true);
			updateUsers[count++] = user;
		}
		
		
		/* Update now */
		count = 0;
		try {
			SaveResult [] sr = stub.update(updateUsers);
			for(SaveResult s : sr) {
				if(s.isSuccess()) {
					System.out.println("User [" + updateUsers[count].getUsername() + "] ACTIVATED !!");
				} else {
					System.out.println("User [" + updateUsers[count] + "] NOT ACTIVATED !! ERROR : " + s.getErrors()[0].getMessage());
				}
				count++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * @param updateUsers
	 * @param newPassword
	 * @param stub
	 */
	public static void setUserPassword(User [] updateUsers, String newPassword, SoapBindingStub stub) {
		/*
		 * Set the user password now
		 */
		for(User user : updateUsers) {
			try { 
				stub.setPassword(user.getId(), newPassword);
				System.out.println("Password set successfully for - " + user.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
