package guiNew;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import Server.EchoServer;
import logic.Account;

public class LoginControllerTest {
	LoginController login = new LoginController();
	IResponse response;
	Account account;
	int code;
	String description;
	String body;
	Gson gson;
	
	
	@Before
	public void setUp() throws Exception {
		gson = new Gson();
		account = new Account(2, "mosh", "mosh", null, null, null, null, null, null, false, 1, "North", 0);
		response = new stubResponse();
	}
	
	class stubResponse implements IResponse{
		
		@Override
		public Object getBody() {
			return body;
		}

		@Override
		public Integer getCode() {
			// TODO Auto-generated method stub
			return code;
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return description;
		}
	}
	/**
	 * Test1:
	 * test for response with login failed and return null
	 */
	@Test
	public void testResponseFailLogin() {
		Account result;
		code = 400;
		description = "Account doesn't exist";
		login.setResponse(response);
		result = login.response();
		assertNull(result);
	}
	/**
	 * Test2:
	 * test for response, with given account
	 * should extract and return the account
	 */
	@Test
	public void testResponseSuccessAccountLogin() {
		Account result;
		Account expected = account;
		code = 200;
		description = "Success in fetching account id 2";
		JsonElement j = gson.toJsonTree(new Object());
		JsonElement temp = EchoServer.gson.toJsonTree(account);
		j.getAsJsonObject().add("account", temp);
		body = gson.toJson(j);
		login.setResponse(response);
		result = login.response();
		assertEquals(expected, result);
	}
	
	/**
	 * Test that the function returns null when the response doesn't
	 * contain account
	 */
	
	@Test
	public void testResponseFailNullLogin() {
		Account result;
		code = 200;
		description = "Success in fetching account id 2";
		JsonElement j = gson.toJsonTree(new Object());
		body = gson.toJson(j);
		login.setResponse(response);
		result = login.response();
		assertNull(result);
	}
	
}
