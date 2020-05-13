package com.mysrc;

public interface IConstants {

	String loginUserName = "your user name for the org";
	String loginUserPassword = "your password for the org+security token";

	String query = "Select id,Username,country,isactive,email from User where Id in "
			+ "('','')";
	String newUserPassword = "set new password here";
}
