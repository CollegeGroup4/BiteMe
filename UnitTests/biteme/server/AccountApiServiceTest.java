package biteme.server;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import common.DBController;
import logic.Account;
import logic.PrivateAccount;

public class AccountApiServiceTest {
	private Response response;

	@Before
	public void setUp() throws Exception {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST","root", "MoshPe2969999");
		 response = new Response();
	}

	@Test
	public void testCreatePrivateAccount() {
		int userID = 2;
		String userName = "b";
		AccountApiService.getAccountByUserNameAndID(userName, userID, response);
		Account account = EchoServer.gson.fromJson((String)response.getBody(), Account.class);
		
		fail("Not yet implemented");
	}
 
	@Test
	public void testCreateBusinessAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllAccounts() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAccount() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetAccountByUserNameAndID() {
		int userID = 2;
		String userName = "b";
		AccountApiService.getAccountByUserNameAndID(userName, userID, response);
		Account account = EchoServer.gson.fromJson((String)response.getBody(), Account.class);
		System.out.println(account.toString());
		assertEquals("Success in fetching UserID: -> 2", response.getDescription());	
	}

	@Test
	public void testLoginAccount() {
		String userName = "a";
		String password = "a";
		AccountApiService.loginAccount(userName, password, response);
		JsonElement j = EchoServer.gson.fromJson((String)response.getBody(), JsonElement.class);
		System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("account"), Account.class));
		assertEquals("Success in login 1", response.getDescription());		
	}

	@Test
	public void testLogoutAccount() {
		String userName = "a";
		AccountApiService.logoutAccount(userName, response);
		assertEquals("Success in logging out", response.getDescription());	
	}

	@Test
	public void testLoginAccountW4C() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePrivateAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateBusinessAccount() {
		fail("Not yet implemented");
	}

}
