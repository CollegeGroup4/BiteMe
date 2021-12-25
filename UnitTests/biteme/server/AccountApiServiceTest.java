package biteme.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import Server.AccountApiService;
import Server.EchoServer;
import Server.Response;
import common.DBController;
import logic.Account;
import logic.BusinessAccount;
import logic.PrivateAccount;

public class AccountApiServiceTest {
	private Response response;

	@Before
	public void setUp() throws Exception {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
				"MoshPe2969999");
		response = new Response();
	}

	@Test
	public void testCreatePrivateAccount() {
		int userID = 2;
		String userName = "b";
		AccountApiService.getAccountByUserNameAndID(userName, userID, response);
		Account account = EchoServer.gson.fromJson((String)response.getBody(), Account.class);
		PrivateAccount acc = new PrivateAccount(account.getUserID(), account.getUserName(), account.getPassword(), account.getFirstName(), account.getLastName(),
				account.getEmail(), account.getRole(), account.getPhone(), account.getStatus(), account.isBusiness(), account.getBranch_manager_ID(), account.getArea(),
				account.getDebt(), "1234567891234567", "123", "12-2026", null);
		AccountApiService.createPrivateAccount(acc, response);
		assertEquals("Success in registering private account: 2", response.getDescription());
	}

	@Test
	public void testCreateBusinessAccount() {
		int userID = 2;
		String userName = "b";
		AccountApiService.getAccountByUserNameAndID(userName, userID, response);
		Account account = EchoServer.gson.fromJson((String)response.getBody(), Account.class);
		BusinessAccount acc = new BusinessAccount(account.getUserID(), account.getUserName(), account.getPassword(), account.getFirstName(), account.getLastName(),
				account.getEmail(), account.getRole(), account.getPhone(), account.getStatus(), true, account.getBranch_manager_ID(), account.getArea(),
				account.getDebt(), 1000, false, "intel",0 , null);
		AccountApiService.createBusinessAccount(acc, response);
		assertEquals("Success in registering business account -> UserID: 2", response.getDescription());
	}

//	@Test
//	public void testDeleteAccount() {
//		String userName = "b";
//		AccountApiService.deleteAccount(userName, response);
//		assertEquals("Success in deleting account -> userName: b", response.getDescription());
//	}
//
//	@Test
//	public void testGetAllAccounts() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAccount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAccountByUserNameAndID() {
//		int userID = 2;
//		String userName = "b";
//		AccountApiService.getAccountByUserNameAndID(userName, userID, response);
//		Account account = EchoServer.gson.fromJson((String) response.getBody(), Account.class);
//		System.out.println(account.toString());
//		assertEquals("Success in fetching UserID: -> 2", response.getDescription());
//	}
//
//	@Test
//	public void testLoginAccount() {
//		String userName = "a";
//		String password = "a";
//		AccountApiService.loginAccount(userName, password, response);
//		try {
//			JsonElement j = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
//			System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("account"), Account.class));
//		} catch (Exception e) {
//			fail(response.getDescription());
//		}
//		assertEquals("Success in login 1", response.getDescription());		
//	}
//
//	@Test
//	public void testLoginAccountW4C() {
//		String w4c = "ed4ba0058f262edc608dec868f0d2bd9bf3d653f7dce6a1118";
//		AccountApiService.loginAccountW4C(w4c, response);
//		try {
//			JsonElement j = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
//			System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("account"), Account.class));
//		} catch (Exception e) {
//			fail(response.getDescription());
//		}
//		assertEquals("Success in login: userID -> 2", response.getDescription());
//	}
//
//	@Test
//	public void testLogoutAccount() {
//		String userName = "a";
//		AccountApiService.logoutAccount(userName, response);
//		assertEquals("Success in logging out", response.getDescription());
//	}

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
