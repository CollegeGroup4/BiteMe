package biteme.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.JsonElement;
import logic.Account;
import logic.BusinessAccount;
import logic.PrivateAccount;
import logic.Restaurant;

/**
 * BiteMe
 *
 * <p>
 * No description provided (generated by Swagger Codegen
 * https://github.com/swagger-api/swagger-codegen)
 *
 */
public class AccountApiService {
	/**
	 * Create Private Account
	 *
	 * This can only be done by the logged in Account.
	 *
	 */
	public static void createPrivateAccount(PrivateAccount account, Response response) {
		try {
			PreparedStatement postAccount = EchoServer.con.prepareStatement("UPDATE biteme.account SET Role = client AND Status = AND " +
					"BranchManagerID = ? AND Area = ? AND W4C ? WHERE UserName = ?;");

			postAccount.setInt(1, account.getBranch_manager_ID());
			postAccount.setString(2, account.getArea());
			postAccount.setString(3, getRandomHexString());
			postAccount.setString(4, account.getUserName());
			postAccount.execute();

			postAccount = EchoServer.con.prepareStatement("INSERT INTO biteme.private_account (UserName, CreditCardNumber, CreditCardCVV, CreditCardExp) " +
					"VALUES(?,?,?,?)");
			postAccount.setString(1, account.getUserName());
			postAccount.setString(2, account.getCreditCardNumber());
			postAccount.setString(3, account.getCreditCardCVV());
			postAccount.setString(4, account.getCreditCardExpDate());
			postAccount.execute();

		} catch (SQLException e) {
			response.setBody(null);
			response.setDescription(e.getMessage());
			response.setCode(400);
			return;
		}

		response.setCode(200);
		response.setDescription("Success in registering private account:" + account.getUserID());
		response.setBody(null);
	}

	/**
	 * Create Business Account
	 *
	 * This can only be done by the logged in Account.
	 *
	 */
	public static void createBusinessAccount(BusinessAccount account, Response response) {
		try {

			PreparedStatement postAccount = EchoServer.con.prepareStatement("UPDATE biteme.account SET Role = client AND Status = AND " +
					"BranchManagerID = ? AND Area = ? AND W4C ? WHERE UserName = ?;");

			postAccount.setInt(1, account.getBranch_manager_ID());
			postAccount.setString(2, account.getArea());
			postAccount.setString(3, getRandomHexString());
			postAccount.setString(4, account.getUserName());
			postAccount.execute();

			postAccount = EchoServer.con.prepareStatement("INSERT INTO biteme.business_account (UserName, MonthlyBillingCeling, isApproved, BusinessName, CurrentSpent) " +
					"VALUES(?,?,?,?,?)");

			postAccount.setInt(1, account.getUserID());
			postAccount.setFloat(2, account.getMonthlyBillingCeiling());
			postAccount.setBoolean(13, account.getIsApproved());
			postAccount.setString(14, account.getBusinessName());
			postAccount.setFloat(15, account.getCurrentSpent());
			postAccount.execute();

		} catch (SQLException e) {
			response.setBody(null);
			response.setDescription(e.getMessage());
			response.setCode(400);
			return;
		}

		response.setCode(200);
		response.setDescription("Success in registering business account:" + account.getUserID());
		response.setBody(null);
	}

	/**
	 * Generate a random hex string at size of 50
	 *
	 * @return
	 */
	public static String getRandomHexString() {
		int numchars = 50;
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		while (sb.length() < numchars) {
			sb.append(Integer.toHexString(r.nextInt()));
		}
		return sb.toString().substring(0, numchars);
	}

	/**
	 * Delete Account
	 *
	 * This can only be done by the logged in Account.
	 *??????????????????????????????????????????????????????????????????????????????????????????????????????
	 */
	public static void deleteAccount(String userName, int UserID, Response response) {
		Account account = null;
		try {
			PreparedStatement deleteAccount = EchoServer.con.prepareStatement(
					"DELETE FROM accounts biteme.account WHERE accounts.UserName = ? AND accounts.UserID = ?;");
			deleteAccount.setString(1, userName);
			// Its the first userName that he had so the test is in users table on login
			deleteAccount.setInt(2, UserID);
			deleteAccount.execute();
			deleteAccount.getResultSet();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Fields are missing");
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in deleting account " + UserID);
		response.setBody(null);
	}

	/**
	 * Get all Accounts
	 *
	 */
	public static void getAllAccounts(int branch_manager_id, Response response) {
		ResultSet rs;
		Account account = null;
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			PreparedStatement getAllAccounts = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.account WHERE BranchManagerID = ?;");
			getAllAccounts.setInt(1, branch_manager_id);
			getAllAccounts.execute();
			rs = getAllAccounts.getResultSet();
			while (rs.next()) {
				account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID),
						rs.getString(QueryConsts.ACCOUNT_USER_NAME), rs.getString(QueryConsts.ACCOUNT_PASSWORD),
						rs.getString(QueryConsts.ACCOUNT_FIRST_NAME), rs.getString(QueryConsts.ACCOUNT_LAST_NAME),
						rs.getString(QueryConsts.ACCOUNT_EMAIL), rs.getString(QueryConsts.ACCOUNT_ROLE),
						rs.getString(QueryConsts.ACCOUNT_PHONE), rs.getString(QueryConsts.ACCOUNT_STATUS),
						rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS),
						rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.ACCOUNT_AREA),
						rs.getInt(QueryConsts.ACCOUNT_DEBT), rs.getString(QueryConsts.ACCOUNT_W4C));
				accounts.add(account);
			}

		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}

		response.setCode(200);
		response.setDescription("Success in fetching accounts " + account.getUserID());
		response.setBody(accounts.toArray());
	}

	/**
	 * Get Full Account (With private or business fields) by Account
	 *
	 */
	public static void getAccount(Account account, Response response) {
		ResultSet rs;

		try {
			if (account.getRole().equals("client")) {
				if (account.isBusiness()) {
					PreparedStatement getAccount = EchoServer.con
							.prepareStatement("SELECT * FROM biteme.business_account WHERE UserName = ?;");
					getAccount.setString(1, account.getUserName());
					getAccount.execute();
					rs = getAccount.getResultSet();
					if (rs.next()) {

						response.setBody(new BusinessAccount(account.getUserID(), account.getUserName(),
								account.getPassword(), account.getFirstName(), account.getLastName(),
								account.getEmail(), account.getRole(), account.getPhone(), account.getStatus(),
								account.isBusiness(), account.getBranch_manager_ID(), account.getArea(),
								account.getDebt(), account.getW4c_card(), rs.getInt(2), rs.getBoolean(3),
								rs.getString(4), rs.getFloat(5)));
						response.setCode(200);
						response.setDescription("Success fetching business account");
					} else {
						throw new SQLException("Account" + account.getUserID() + " is not found in business_account",
								"400", 400);
					}
				} else {
					PreparedStatement getAccount = EchoServer.con
							.prepareStatement("SELECT * FROM biteme.private_account WHERE UserID = ?;");
					getAccount.setInt(1, account.getUserID());
					getAccount.execute();
					rs = getAccount.getResultSet();
					if (rs.next()) {

						response.setBody(new PrivateAccount(account.getUserID(), account.getUserName(),
								account.getPassword(), account.getFirstName(), account.getLastName(),
								account.getEmail(), account.getRole(), account.getPhone(), account.getStatus(),
								account.isBusiness(), account.getBranch_manager_ID(), account.getArea(),
								account.getDebt(), account.getW4c_card(), rs.getString(2), rs.getString(3),
								rs.getString(4)));
						response.setCode(200);
						response.setDescription("Success fetching private account");
					} else {
						throw new SQLException("Account" + account.getUserID() + " is not found in private_account",
								"400", 401);
					}
				}
			}

		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
		}
	}

	/**
	 * Logs Account into the system
	 *
	 */
	public static void loginAccount(String userName, String password, Response response) {
		ResultSet rs;
		JsonElement body = EchoServer.gson.toJsonTree(new Object());
		Account account = null;
		try {
			PreparedStatement loginAccount = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.account WHERE UserName = ? AND Password = ?;");

			loginAccount.setString(1, userName);
			loginAccount.setString(2, password);
			loginAccount.execute();
			rs = loginAccount.getResultSet();

			if(rs.getFetchSize() == 0) {
				throw new SQLException("Account " + userName + " not found", "402", 402);
			}

			if(rs.getString(QueryConsts.ACCOUNT_ROLE).equals("Not Assigned")){
				throw new SQLException("Account didn't activated yet.", "400", 400);
			}

			loginAccount = EchoServer.con.prepareStatement("UPDATE biteme.account SET isLoggedIn = 1 WHERE UserName = ?;");
			loginAccount.setString(1, userName);
			if (rs.getBoolean(QueryConsts.ACCOUNT_IS_LOGGED_IN)) {
				throw new SQLException("User is already logged in", "401", 401);
			}

			account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID), rs.getString(QueryConsts.ACCOUNT_USER_NAME),
					rs.getString(QueryConsts.ACCOUNT_PASSWORD), rs.getString(QueryConsts.ACCOUNT_FIRST_NAME),
					rs.getString(QueryConsts.ACCOUNT_LAST_NAME), rs.getString(QueryConsts.ACCOUNT_EMAIL),
					rs.getString(QueryConsts.ACCOUNT_ROLE), rs.getString(QueryConsts.ACCOUNT_PHONE),
					rs.getString(QueryConsts.ACCOUNT_STATUS), rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS),rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID),
					rs.getString(QueryConsts.ACCOUNT_AREA), rs.getInt(QueryConsts.ACCOUNT_DEBT), rs.getString(QueryConsts.ACCOUNT_W4C));

			JsonElement accountField = EchoServer.gson.toJsonTree(account);
			body.getAsJsonObject().add("account", accountField);
			if (account.getStatus().equals("blocked")) {
				throw new SQLException("Account" + account.getUserID() + "is blocked", "403", 403);
			}



			if(account.getRole().equals("Supplier") || account.getRole().equals("Moderator")){
				Restaurant res;
				PreparedStatement getRestaurant = EchoServer.con.prepareStatement("SELECT * FROM biteme.restaurants WHERE UserName = ?");
				getRestaurant.setString(1, account.getUserName());
				rs = getRestaurant.getResultSet();
				if(!rs.getBoolean(QueryConsts.RESTAURANT_IS_APPROVED)){
					throw new SQLException("Restaurant didn't approved yet","404",404);
				}
				res = new Restaurant(rs.getInt(QueryConsts.RESTAURANT_ID), rs.getBoolean(QueryConsts.RESTAURANT_IS_APPROVED), rs.getInt(QueryConsts.RESTAURANT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.RESTAURANT_NAME),
						rs.getString(QueryConsts.RESTAURANT_AREA), rs.getString(QueryConsts.RESTAURANT_TYPE), account.getUserName(), rs.getString(QueryConsts.RESTAURANT_PHOTO), rs.getString(QueryConsts.RESTAURANT_ADDRESS),
				rs.getString(QueryConsts.RESTAURANT_DESCRIPTION));
				JsonElement restaurantField = EchoServer.gson.toJsonTree(res);
				body.getAsJsonObject().add("moderator", restaurantField);

			}

		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in login " + account.getUserID());
		response.setBody(EchoServer.gson.toJson(body));
	}

	/**
	 * Logs out current logged in Account session
	 *
	 */
	public static void logoutAccount(String userName, Response response) {


		ResultSet rs;
		try {
			PreparedStatement logOutAccount = EchoServer.con
					.prepareStatement("UPDATE biteme.account AS account SET isLoggedIn = 0 WHERE account.UserName = ?;");
			logOutAccount.setString(1, userName);
			logOutAccount.execute();
			rs = logOutAccount.getResultSet();
			if (rs.rowUpdated() == false) {
				throw new SQLException("couldn't log out");
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in logging out");
		response.setBody(null);
	}

	/**
	 * login Account with W4C code
	 *
	 * 
	 *
	 */
	public static void loginAccountW4C(String w4cCode, Response response) {

		ResultSet rs;
		JsonElement body = EchoServer.gson.toJsonTree(new Object());
		Account account = null;
		try {
			PreparedStatement loginAccount = EchoServer.con.prepareStatement("SELECT * FROM biteme.account WHERE W4C = ?;");
		loginAccount.setString(1, w4cCode);
		rs = loginAccount.getResultSet();
			if(rs.getFetchSize() == 0) {
				throw new SQLException("Account " + w4cCode + " not found", "402", 402);
			}

			if(rs.getString(QueryConsts.ACCOUNT_ROLE).equals("Not Assigned")){
				throw new SQLException("Account didn't activated yet.", "400", 400);
			}

			loginAccount = EchoServer.con.prepareStatement("UPDATE biteme.account SET isLoggedIn = 1 WHERE W4C = ?;");
			loginAccount.setString(1, w4cCode);
			if (rs.getBoolean(QueryConsts.ACCOUNT_IS_LOGGED_IN)) {
				throw new SQLException("User is already logged in", "401", 401);
			}

			account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID), rs.getString(QueryConsts.ACCOUNT_USER_NAME),
					rs.getString(QueryConsts.ACCOUNT_PASSWORD), rs.getString(QueryConsts.ACCOUNT_FIRST_NAME),
					rs.getString(QueryConsts.ACCOUNT_LAST_NAME), rs.getString(QueryConsts.ACCOUNT_EMAIL),
					rs.getString(QueryConsts.ACCOUNT_ROLE), rs.getString(QueryConsts.ACCOUNT_PHONE),
					rs.getString(QueryConsts.ACCOUNT_STATUS), rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS),rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID),
					rs.getString(QueryConsts.ACCOUNT_AREA), rs.getInt(QueryConsts.ACCOUNT_DEBT), rs.getString(QueryConsts.ACCOUNT_W4C));

			JsonElement accountField = EchoServer.gson.toJsonTree(account);
			body.getAsJsonObject().add("account", accountField);
			if (account.getStatus().equals("blocked")) {
				throw new SQLException("Account" + account.getUserID() + "is blocked", "403", 403);
			}



			if(account.getRole().equals("Supplier") || account.getRole().equals("Moderator")){
				Restaurant res;
				PreparedStatement getRestaurant = EchoServer.con.prepareStatement("SELECT * FROM biteme.restaurants WHERE W4C = ?");
				getRestaurant.setString(1, w4cCode);
				rs = getRestaurant.getResultSet();
				if(!rs.getBoolean(QueryConsts.RESTAURANT_IS_APPROVED)){
					throw new SQLException("Restaurant didn't approved yet","404",404);
				}
				res = new Restaurant(rs.getInt(QueryConsts.RESTAURANT_ID), rs.getBoolean(QueryConsts.RESTAURANT_IS_APPROVED), rs.getInt(QueryConsts.RESTAURANT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.RESTAURANT_NAME),
						rs.getString(QueryConsts.RESTAURANT_AREA), rs.getString(QueryConsts.RESTAURANT_TYPE), account.getUserName(), rs.getString(QueryConsts.RESTAURANT_PHOTO), rs.getString(QueryConsts.RESTAURANT_ADDRESS),
						rs.getString(QueryConsts.RESTAURANT_DESCRIPTION));
				JsonElement restaurantField = EchoServer.gson.toJsonTree(res);
				body.getAsJsonObject().add("moderator", restaurantField);

			}

		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in login " + account.getUserID());
		response.setBody(EchoServer.gson.toJson(body));


	}

	/**
	 * Updated Account
	 *
	 * This can only be done by the master / branch manager / CEO
	 *
	 */
	public static void updateAccount(Account account, Response response) {
		try {
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"UPDATE biteme.account AS account SET (UserID = ?, UserName = ?, FirstName = ?, Password = ?, LastName = ?, PhoneNumber = ?, Email = ?,"
							+ " Role = ?, Status = ?, BranchManagerID = ?, Area = ?)"
							+ "WHERE UserName = ?;");
			postAccount.setInt(1, account.getUserID());
			// Its the first userName that he had so the test is in users table on login
			postAccount.setString(2, account.getUserName());
			postAccount.setString(3, account.getFirstName());
			postAccount.setString(4, account.getPassword());
			postAccount.setString(5, account.getLastName());
			postAccount.setString(6, account.getPhone());
			postAccount.setString(7, account.getEmail());
			postAccount.setString(8, account.getRole());
			postAccount.setString(9, account.getStatus());
			postAccount.setInt(10, account.getBranch_manager_ID());
			postAccount.setString(11, account.getArea());
			postAccount.setString(12, account.getUserName());
			postAccount.execute();

		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		} finally {
			response.setCode(200);
			response.setDescription("Success in updating account" + account.getUserID());
			response.setBody(null);
		}
	}
	public static void updatePrivateAccount(PrivateAccount account, Response response) {
		Account account1 = account;
		updateAccount(account1, response);
		try {
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"UPDATE biteme.private_account SET (UserName = ?, CreditCardNumber = ?, CreditCardCVV = ?, CreditCardExp = ?)"
							+ "WHERE UserName = ?;");
			// Its the first userName that he had so the test is in users table on login
			postAccount.setString(1, account.getUserName());
			postAccount.setString(2, account.getCreditCardNumber());
			postAccount.setString(3, account.getCreditCardCVV());
			postAccount.setString(4, account.getCreditCardExpDate());

			postAccount.execute();

		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}
	}
	
	public static void updateBusinessAccount(BusinessAccount account, Response response) {
		Account account1 = account;
		updateAccount(account1, response);
		try {
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"UPDATE biteme.business_account SET (UserName = ?, MonthlyBillingCeling = ?, isApproved = ?, BusinessName = ?, CurrentSpent = ?)"
							+ "WHERE UserName = ?;");
			postAccount.setString(1, account.getUserName());
			postAccount.setFloat(2, account.getMonthlyBillingCeiling());
			postAccount.setBoolean(3, account.getIsApproved());
			postAccount.setString(4, account.getBusinessName());
			postAccount.setFloat(5, account.getCurrentSpent());

			postAccount.execute();

		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}
	}
}
