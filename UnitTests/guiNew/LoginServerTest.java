package guiNew;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;

import Server.AccountApiService;
import Server.EchoServer;
import common.DBController;
import common.Response;
import logic.Account;

public class LoginServerTest {
	Response response;
	String userName;
	String password;
	Account account;

	@Before
	public void setUp() throws Exception {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
				"MoshPe2969999");
		response = new Response();
		account = new Account(2, "mosh", "mosh", "Moshe", "Peretz", "fghghf98@gmail.com", "Client", "0556668511",
				"active", false, 1, "North", 0);
	}
	
	@After
	public void tearDown() throws Exception {
		PreparedStatement resetAccount = EchoServer.con
				.prepareStatement("UPDATE biteme.account SET isLoggedIn = 0, Status = 'active', Role = 'Client' WHERE UserName = ?;");
		resetAccount.setString(1, userName);
		resetAccount.executeUpdate();
	}

	/**
	 * Test1: test for loginAccount with given userName and should return the account upon
	 * success in login to account
	 */
	@Test
	public void testSuccessLogin() {
		userName = "mosh";
		password = "mosh";
		Account expected = account;
		Account result;
		AccountApiService.loginAccount(userName, password, response);
		JsonElement j = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
		result = EchoServer.gson.fromJson(j.getAsJsonObject().get("account"), Account.class);
		assertEquals(expected,result);
	}
	
	/**
	 * Test1: test for loginAccount with given userName and a Blocked account
	 * return account {id} is blocked
	 */
	@Test
	public void testFailLoginBlocked() {
		userName = "mosh";
		password = "mosh";
		Account expected = account;
		Account result;
		try {
			PreparedStatement blockAccount = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET Status = ? WHERE UserName = ?;");
			blockAccount.setString(1, "Blocked");
			blockAccount.setString(2, userName);
			blockAccount.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AccountApiService.loginAccount(userName, password, response);
		assertEquals("Account2is blocked",response.getDescription());
	}
	
	/**
	 * Test1: test for loginAccount with given userName and an already logged in account
	 * return user already logged in
	 */
	@Test
	public void testFailAlreadyLoggedIn() {
		userName = "mosh";
		password = "mosh";
		Account expected = account;
		Account result;
		try {
			PreparedStatement blockAccount = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET isLoggedIn = 1 WHERE UserName = ?;");
			blockAccount.setString(1, userName);
			blockAccount.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AccountApiService.loginAccount(userName, password, response);
		assertEquals("User is already logged in",response.getDescription());
	}
	
	/**
	 * Test1: test for loginAccount with given userName and a none active account
	 * return account didnt activated yet
	 */
	@Test
	public void testFailNotActiveAccount() {
		userName = "mosh";
		password = "mosh";
		Account expected = account;
		Account result;
		try {
			PreparedStatement blockAccount = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET Role = 'Not Assigned' WHERE UserName = ?;");
			blockAccount.setString(1, userName);
			blockAccount.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AccountApiService.loginAccount(userName, password, response);
		assertEquals("Account didn't activated yet.",response.getDescription());
	}
	
	/**
	 * Test1: test for loginAccount with given userName and a none active account
	 * return account didnt activated yet
	 */
	@Test
	public void testFailWithOutPassword() {
		userName = "mosh";
		Account expected = account;
		Account result;
		AccountApiService.loginAccount(userName, "", response);
		assertEquals("Invalid Fields",response.getDescription());
	}
	
	/**
	 * Test1: test for loginAccount with given userName and a none active account
	 * return account didnt activated yet
	 */
	@Test
	public void testFailWithOutUserName() {
		password = "mosh";
		Account expected = account;
		Account result;
		AccountApiService.loginAccount("", password, response);
		assertEquals("Invalid Fields",response.getDescription());
	}
	
	
	/**
	 * Test1: test for loginAccount with given userName and a none active account
	 * return account didnt activated yet
	 */
	@Test
	public void testFailAcountDoesntExist() {
		password = "braude";
		userName = "braude";
		Account expected = account;
		Account result;
		AccountApiService.loginAccount(userName, password, response);
		assertEquals("Account braude not found",response.getDescription());
	}
}
