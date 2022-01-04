package Server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;

import common.DBController;
import common.Response;
import logic.Restaurant;

public class AccountApiServiceTest {
	private Response response;

	@Before
	public void setUp() throws Exception {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
				"MoshPe2969999");
		response = new Response();
	}

//	@Test
//	public void testCreatePrivateAccount() {
//		String userName = "c";
//		Request r = new Request();
//		AccountApiService.getAccountByUserName(userName, response);
//		Account account = EchoServer.gson.fromJson((String)response.getBody(), Account.class);
//		PrivateAccount acc = new PrivateAccount(account.getUserID(), account.getUserName(), account.getPassword(), account.getFirstName(), account.getLastName(),
//				account.getEmail(), account.getRole(), account.getPhone(), account.getStatus(), account.isBusiness(), account.getBranch_manager_ID(), account.getArea(),
//				 account.getDebt(), null,"1234567891234567", "123", "12-2026");
//		AccountApiService.createPrivateAccount(acc, response);
//	r.setPath("/accounts/privateAccount");
//	r.setMethod("POST");
//	r.setBody(EchoServer.gson.toJson(account));
//	EchoServer i = new EchoServer(5555);
//	i.handleMessageFromClient(EchoServer.gson.toJson(r), null);
//		assertEquals("Success in registering private account: 2", response.getDescription());
//	}
//
//	@Test
//	public void testCreateBusinessAccount() {
//		String userName = "c";
//		Request r = new Request();
//		AccountApiService.getAccountByUserName(userName, response);
//		Account account = EchoServer.gson.fromJson((String)response.getBody(), Account.class);
//		BusinessAccount acc = new BusinessAccount(account.getUserID(), account.getUserName(), account.getPassword(), account.getFirstName(), account.getLastName(),
//				account.getEmail(), account.getRole(), account.getPhone(), account.getStatus(), true, account.getBranch_manager_ID(), account.getArea(),
//				account.getDebt(), null,1000, false, "intel",0);
//		r.setPath("/accounts/businessAccount");
//		r.setMethod("POST");
//		r.setBody(EchoServer.gson.toJson(account));
//		EchoServer i = new EchoServer(5555);
//		i.handleMessageFromClient(EchoServer.gson.toJson(r), null);
//		AccountApiService.createBusinessAccount(acc, response);
//		assertEquals("Success in registering business account -> UserID: 2", response.getDescription());
//	}
//
//	@Test
//	public void testDeleteAccount() {
//		String userName = "b";
//		AccountApiService.deleteAccount(userName, response);
//		assertEquals("Success in deleting account -> userName: b", response.getDescription());
//	}
//
//	@Test
//	public void testGetAllAccounts() {
//		int branchManager = 1;
//		AccountApiService.getAllAccounts(branchManager, response);
//		Account[] acc = EchoServer.gson.fromJson((String)response.getBody(), Account[].class);
//		System.out.println(Arrays.toString(acc));
//	}
//
//	@Test
//	public void testGetAccount() {
//
//		AccountApiService.getAccountByUserName("b", response);
//		Account account = EchoServer.gson.fromJson((String)response.getBody(), Account.class);
//		Request r = new Request();
//		r.setPath("/accounts/getAccount");
//		r.setMethod("GET");
//		r.setBody(EchoServer.gson.toJson(account));
//		EchoServer i = new EchoServer(5555);
//		i.handleMessageFromClient(EchoServer.gson.toJson(r), null);
//		AccountApiService.getAccount(account, response);
//		JsonElement j = EchoServer.gson.fromJson((String)response.getBody(), JsonElement.class);
//		System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("account"), Account.class));
//		System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("businessAccount"), BusinessAccount.class));
//		System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("privateAccount"), PrivateAccount.class));
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
	@Test
	public void testLoginAccount() {
		String userName = "d";
		String password = "d";
		AccountApiService.loginAccount(userName, password, response);
		try {
			JsonElement j = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
			Restaurant a = EchoServer.gson.fromJson(j.getAsJsonObject().get("restaurant"), Restaurant.class);
			System.out.println(a.toString());
		} catch (Exception e) {
			fail(response.getDescription());
		}
		assertEquals("Success in login 1", response.getDescription());		
	}
//
//	@Test
//	public void testLoginAccountW4C() {
//		String w4c = "d4443cc65d4335a323753495938867731af47c7d73bbab3d76";
//		AccountApiService.loginAccountW4C(w4c, response);
//		try {
//			JsonElement j = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
//			System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("account"), Account.class));
//			System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("privateAccount"), PrivateAccount.class));
//			System.out.println(EchoServer.gson.fromJson(j.getAsJsonObject().get("businessAccount"), BusinessAccount.class));
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