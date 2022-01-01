package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.JsonElement;

import common.Response;
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
	public static void importSuper(Response response) {
		try {
			PreparedStatement postAccount = EchoServer.con
					.prepareStatement("INSERT INTO table2"
							+ "SELECT * FROM biteme.account;");
			postAccount.executeUpdate();
		} catch (SQLException e) {
				response.setCode(400);
				response.setDescription("Fail importing from UsersManagement");
			return;
		}
		response.setCode(200);
		response.setDescription("Success in registering a new private account -> userName: " + account.getUserName());
	}
	
	/**
	 * Create Private Account
	 *
	 * This can only be done by the logged in Account.
	 *
	 */
	public static void createPrivateAccount(PrivateAccount account, Response response) {
		try {
			PreparedStatement postAccount = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET Role = ?, Status = 'active', "
							+ "BranchManagerID = ?, Area = ? WHERE UserName = ?;");
			if (account.getRole().equals("Not Assigned"))
				postAccount.setString(1, account.getRole());
			else
				postAccount.setString(1, "Client");
			postAccount.setInt(2, account.getBranch_manager_ID());
			postAccount.setString(3, account.getArea());
			postAccount.setString(4, account.getUserName());
			postAccount.executeUpdate();
			postAccount = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.private_account (UserName, CreditCardNumber, CreditCardCVV, CreditCardExp, W4C) "
							+ "VALUES(?,?,?,?,?)");
			postAccount.setString(1, account.getUserName());
			postAccount.setString(2, account.getCreditCardNumber());
			postAccount.setString(3, account.getCreditCardCVV());
			postAccount.setString(4, account.getCreditCardExpDate());
			postAccount.setString(5, getRandomHexString());
			postAccount.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				response.setCode(400);
				response.setDescription("Fail! Private account already exist -> userName: " + account.getUserName());
			}
			return;
		}
		response.setCode(200);
		response.setDescription("Success in registering a new private account -> userName: " + account.getUserName());
	}

	/**
	 * Create Business Account
	 *
	 * This can only be done by the logged in Account.
	 *
	 */
	public static void createBusinessAccount(BusinessAccount account, Response response) {
		PreparedStatement postAccount, isBusinessApproved;
		ResultSet rs;
		try {
			isBusinessApproved = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.employer WHERE businessName = ? AND isApproved = 1;");
			isBusinessApproved.setString(1, account.getBusinessName());
			rs = isBusinessApproved.executeQuery();
			if (!rs.next())
				throw new SQLException("Business " + account.getBusinessName() + " is not found in Employers or is not approved", "400",
						400);

		} catch (SQLException e) {
			response.setDescription(e.getMessage());
			response.setCode(400);
			return;
		}
		try {
			postAccount = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET Role = ?, Status = 'active',"
							+ "BranchManagerID = ? , Area = ? , isBusiness = ? WHERE UserName = ?;");
			if (account.getRole().equals("Not Assigned"))
				postAccount.setString(1, account.getRole());
			else
				postAccount.setString(1, "Client");
			postAccount.setInt(2, account.getBranch_manager_ID());
			postAccount.setString(3, account.getArea());
			postAccount.setBoolean(4, true);
			postAccount.setString(5, account.getUserName());
			postAccount.executeUpdate();
		} catch (SQLException e) {
			response.setBody(null);
			response.setDescription(e.getMessage());
			response.setCode(400);
			return;
		}
		try {
			postAccount = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.business_account (UserName, MonthlyBillingCeling, isApproved, BusinessName, CurrentSpent, W4C) "
							+ "VALUES(?,?,?,?,?,?)");
			postAccount.setString(1, account.getUserName());
			postAccount.setFloat(2, account.getMonthlyBillingCeiling());
			postAccount.setBoolean(3, account.getIsApproved());
			postAccount.setString(4, account.getBusinessName());
			postAccount.setFloat(5, account.getCurrentSpent());
			postAccount.setString(6, getRandomHexString());
			postAccount.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				response.setCode(400);
				response.setDescription("Fail! Business account already exist -> userName: " + account.getUserName());
			}
			return;
		}
		response.setCode(200);
		response.setDescription("Success in registering a new business account -> userName: " + account.getUserName());
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
	 * 
	 * @throws SQLException
	 */
	private static void deleteQuery(String from, String userName) throws SQLException {
		PreparedStatement temp = EchoServer.con.prepareStatement("DELETE FROM " + from + " WHERE UserName = ?;");
		temp.setString(1, userName);
		temp.executeUpdate();
	}

	public static void deleteAccount(String userName, Response response) {
		PreparedStatement deleteAccount, deleteItemInMenu, getRestaurantID;
		int restaurantID;
		ResultSet rs;
		try {
			deleteAccount = EchoServer.con.prepareStatement("SELECT * FROM biteme.account WHERE UserName = ?;");
			deleteAccount.setString(1, userName);
			rs = deleteAccount.executeQuery();
			if (rs.next()) {
				if (rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS)) {
					deleteQuery("biteme.business_account", userName);
 
				} else {
					if (rs.getString(QueryConsts.ACCOUNT_ROLE).equals("Supplier")) {
						// get the supplier restaurant ID
						getRestaurantID = EchoServer.con
								.prepareStatement("SELECT RestaurantID FROM biteme.restaurant WHERE UserName = ?;");
						getRestaurantID.setString(1, userName);
						rs = getRestaurantID.executeQuery();
						restaurantID = rs.getInt(QueryConsts.RESTAURANT_ID);
						// delete all the items in menu that connects to the restaurant ID
						deleteItemInMenu = EchoServer.con
								.prepareStatement("DELETE FROM biteme.item_in_menu WHERE RestaurantID = ?;");
						deleteItemInMenu.setInt(1, restaurantID);
						deleteItemInMenu.executeUpdate();
						// delete the supplier account
						deleteQuery("biteme.restaurant", userName);
					} else if (rs.getString(QueryConsts.ACCOUNT_ROLE).equals("Moderator")) {
						deleteQuery("biteme.restaurant", userName);
					} else {
						deleteQuery("biteme.private_account", userName);
					}
				}
				deleteQuery("biteme.account", userName);
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Fields are missing");
			return;
		}
		response.setCode(200);
		response.setDescription("Success in deleting account -> userName: " + userName);
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
			PreparedStatement getAllAccounts = EchoServer.con.prepareStatement(
					"SELECT * FROM biteme.account WHERE BranchManagerID = ? AND Role != 'Branch Manager';");
			getAllAccounts.setInt(1, branch_manager_id);
			rs = getAllAccounts.executeQuery();
			while (rs.next()) {
				account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID),
						rs.getString(QueryConsts.ACCOUNT_USER_NAME), rs.getString(QueryConsts.ACCOUNT_PASSWORD),
						rs.getString(QueryConsts.ACCOUNT_FIRST_NAME), rs.getString(QueryConsts.ACCOUNT_LAST_NAME),
						rs.getString(QueryConsts.ACCOUNT_EMAIL), rs.getString(QueryConsts.ACCOUNT_ROLE),
						rs.getString(QueryConsts.ACCOUNT_PHONE), rs.getString(QueryConsts.ACCOUNT_STATUS),
						rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS),
						rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.ACCOUNT_AREA),
						rs.getInt(QueryConsts.ACCOUNT_DEBT));
				accounts.add(account);
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in fetching accounts -> branchManagerID: " + Integer.toString(branch_manager_id));
		response.setBody(EchoServer.gson.toJson(accounts.toArray()));
	}

	/**
	 * Get Full Account (With private or business fields) by Account
	 *
	 */
	public static void getAccount(Account account, Response response) {
		JsonElement body = EchoServer.gson.toJsonTree(new Object());
		PreparedStatement getAccount;
		ResultSet rs;
		JsonElement temp = EchoServer.gson.toJsonTree(account);
		body.getAsJsonObject().add("account", temp);
		try {
			if (account.isBusiness()) {
				getAccount = EchoServer.con
						.prepareStatement("SELECT * FROM biteme.business_account WHERE UserName = ?;");
				getAccount.setString(1, account.getUserName());
				rs = getAccount.executeQuery();
				if (rs.next()) {
					temp = EchoServer.gson.toJsonTree(new BusinessAccount(account.getUserID(), account.getUserName(),
							account.getPassword(), account.getFirstName(), account.getLastName(), account.getEmail(),
							account.getRole(), account.getPhone(), account.getStatus(), account.isBusiness(),
							account.getBranch_manager_ID(), account.getArea(), account.getDebt(),
							rs.getString(QueryConsts.BUSINESS_ACCOUNT_W4C),
							rs.getInt(QueryConsts.BUSINESS_ACCOUNT_MONTHLY_BILLING_CEILING),
							rs.getBoolean(QueryConsts.BUSINESS_ACCOUNT_IS_APPROVED),
							rs.getString(QueryConsts.BUSINESS_ACCOUNT_BUSINESS_NAME),
							rs.getFloat(QueryConsts.BUSINESS_ACCOUNT_CURRENT_SPENT)));
					body.getAsJsonObject().add("businessAccount", temp);
				}
				getAccount = EchoServer.con
						.prepareStatement("SELECT * FROM biteme.private_account WHERE UserName = ?;");
				getAccount.setString(1, account.getUserName());
				rs = getAccount.executeQuery();
				if (rs.next()) {
					temp = EchoServer.gson.toJsonTree(new PrivateAccount(account.getUserID(), account.getUserName(),
							account.getPassword(), account.getFirstName(), account.getLastName(), account.getEmail(),
							account.getRole(), account.getPhone(), account.getStatus(), account.isBusiness(),
							account.getBranch_manager_ID(), account.getArea(), account.getDebt(),
							rs.getString(QueryConsts.PRIVATE_ACCOUNT_W4C),
							rs.getString(QueryConsts.PRIVATE_ACCOUNT_CREDIT_CARD_NUMBER),
							rs.getString(QueryConsts.PRIVATE_ACCOUNT_CREDIT_CARD_CVV),
							rs.getString(QueryConsts.PRIVATE_ACCOUNT_CREDIT_CARD_EXP)));
					body.getAsJsonObject().add("privateAccount", temp);
				}
				if (account.getRole().equals("Supplier") || account.getRole().equals("Moderator")) {
					getAccount = EchoServer.con.prepareStatement("SELECT * FROM biteme.restaurant WHERE UserName = ?;");
					getAccount.setString(1, account.getUserName());
					rs = getAccount.executeQuery();
					if (rs.next()) {

						temp = EchoServer.gson.toJsonTree(new Restaurant(rs.getInt(QueryConsts.RESTAURANT_ID),
								rs.getBoolean(QueryConsts.RESTAURANT_IS_APPROVED),
								rs.getInt(QueryConsts.RESTAURANT_BRANCH_MANAGER_ID),
								rs.getString(QueryConsts.RESTAURANT_NAME), rs.getString(QueryConsts.RESTAURANT_AREA),
								rs.getString(QueryConsts.RESTAURANT_TYPE), account.getUserName(),
								rs.getString(QueryConsts.RESTAURANT_PHOTO),
								rs.getString(QueryConsts.RESTAURANT_ADDRESS),
								rs.getString(QueryConsts.RESTAURANT_DESCRIPTION)));
						body.getAsJsonObject().add("restaurant", temp);
					}
				}
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
		}
		response.setCode(200);
		response.setDescription("Success in fetching account -> userName: " + account.getUserName());
		response.setBody(EchoServer.gson.toJson(body));

	}

	/**
	 * Logs Account into the system
	 *
	 */
	public static void loginAccount(String userName, String password, Response response) {
		ResultSet rs;
		Account account = null;
		try {
			PreparedStatement loginAccount = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.account WHERE UserName = ? AND Password = ?;");
			loginAccount.setString(1, userName);
			loginAccount.setString(2, password);
			rs = loginAccount.executeQuery();
			if (rs.next()) {
				account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID),
						rs.getString(QueryConsts.ACCOUNT_USER_NAME), rs.getString(QueryConsts.ACCOUNT_PASSWORD),
						rs.getString(QueryConsts.ACCOUNT_FIRST_NAME), rs.getString(QueryConsts.ACCOUNT_LAST_NAME),
						rs.getString(QueryConsts.ACCOUNT_EMAIL), rs.getString(QueryConsts.ACCOUNT_ROLE),
						rs.getString(QueryConsts.ACCOUNT_PHONE), rs.getString(QueryConsts.ACCOUNT_STATUS),
						rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS),
						rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.ACCOUNT_AREA),
						rs.getInt(QueryConsts.ACCOUNT_DEBT));
				if (rs.getBoolean(QueryConsts.ACCOUNT_IS_LOGGED_IN)) {
					throw new SQLException("User is already logged in", "401", 401);
				}
			} else {
				throw new SQLException("Account " + userName + " not found", "402", 402);
			}
			if (account.getStatus().equals("blocked")) {
				throw new SQLException("Account" + account.getUserID() + "is blocked", "403", 403);
			}
			if (account.getRole().equals("Not Assigned")) {
				throw new SQLException("Account didn't activated yet.", "400", 400);
			}

			getAccount(account, response);
			loginAccount = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET isLoggedIn = 1 WHERE UserName = ?;");
			loginAccount.setString(1, userName);
			loginAccount.executeUpdate();
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in login: userID -> " + Integer.toString(account.getUserID()));
	}

	/**
	 * Logs out current logged in Account session
	 *
	 */
	public static void logoutAccount(String userName, Response response) {
		int rowsAffected;
		try {
			PreparedStatement logOutAccount = EchoServer.con.prepareStatement(
					"UPDATE biteme.account AS account SET isLoggedIn = 0 WHERE account.UserName = ?;");
			logOutAccount.setString(1, userName);
			rowsAffected = logOutAccount.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("couldn't log out");
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in logging out");
	}

	/**
	 * login Account with W4C code
	 */
	public static void loginAccountW4C(String w4cCode, Response response) {
		ResultSet rs;
		String userName, password;
		try {
			PreparedStatement loginAccount = EchoServer.con
					.prepareStatement("SELECT UserName, Password FROM biteme.account AS account WHERE EXISTS("
							+ "SELECT * FROM biteme.private_account AS private WHERE W4C = ? AND account.UserName = private.UserName)"
							+ " OR EXISTS(SELECT * FROM biteme.business_account AS business WHERE W4C = ? AND account.UserName = business.UserName);");
			loginAccount.setString(1, w4cCode);
			loginAccount.setString(2, w4cCode);
			rs = loginAccount.executeQuery();
			if (rs.next()) {
				userName = rs.getString(1);
				password = rs.getString(2);
				loginAccount(userName, password, response);
			} else
				throw new SQLException("Account " + w4cCode + " not found", "402", 402);
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			return;
		}
	}

	/**
	 * Updated Account
	 *
	 * This can only be done by the master / branch manager / CEO
	 *
	 */
	private static void updateAccount(Account account, Response response) {
		int updatedRows;
		try {
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"UPDATE biteme.account SET (UserID = ?, UserName = ?, FirstName = ?, Password = ?, LastName = ?, PhoneNumber = ?, Email = ?,"
							+ "Role = ?, Status = ?, BranchManagerID = ?, Area = ?) WHERE UserName = ?;");
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
			updatedRows = postAccount.executeUpdate();
			if (updatedRows == 0) {
				throw new SQLException("Couldn't update account: -> UserName: " + account.getUserName(), "401", 401);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in updating account: accountID -> " + account.getUserID());
	}

	/**
	 * Updated Private Account
	 *
	 * This can only be done by the master / branch manager / CEO
	 *
	 */
	public static void updatePrivateAccount(PrivateAccount account, Response response) {
		Account account1 = account;
		int updatedRows;
		updateAccount(account1, response);
		try {
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"UPDATE biteme.private_account SET (CreditCardNumber = ?, CreditCardCVV = ?, CreditCardExp = ?)"
							+ " WHERE UserName = ?;");
			// Its the first userName that he had so the test is in users table on login
			postAccount.setString(1, account.getUserName());
			postAccount.setString(2, account.getCreditCardNumber());
			postAccount.setString(3, account.getCreditCardCVV());
			postAccount.setString(4, account.getCreditCardExpDate());
			updatedRows = postAccount.executeUpdate();
			if (updatedRows == 0) {
				throw new SQLException("Couldn't update private account: -> UserName: " + account.getUserName(), "401",
						401);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in updating private account: accountID -> " + account.getUserID());
	}

	/**
	 * Updated Business Account
	 *
	 * This can only be done by the master / branch manager / CEO
	 *
	 */
	public static void updateBusinessAccount(BusinessAccount account, Response response) {
		Account account1 = account;
		int updatedRows;
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
			updatedRows = postAccount.executeUpdate();
			if (updatedRows == 0) {
				throw new SQLException("Couldn't update business account: -> UserName: " + account.getUserName(), "401",
						401);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in updating business account: accountID -> " + account.getUserID());
	}

	/**
	 * Get Account By UserName and UserID
	 *
	 * This can only be done by the master / branch manager / CEO
	 *
	 */
	public static void getAccountByUserName(String userName, Response response) {
		ResultSet rs;
		Account account = null;
		try {
			PreparedStatement getAccount = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.account WHERE UserName = ?;");
			getAccount.setString(1, userName);
			rs = getAccount.executeQuery();
			if (rs.next()) {
				account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID),
						rs.getString(QueryConsts.ACCOUNT_USER_NAME), rs.getString(QueryConsts.ACCOUNT_PASSWORD),
						rs.getString(QueryConsts.ACCOUNT_FIRST_NAME), rs.getString(QueryConsts.ACCOUNT_LAST_NAME),
						rs.getString(QueryConsts.ACCOUNT_EMAIL), rs.getString(QueryConsts.ACCOUNT_ROLE),
						rs.getString(QueryConsts.ACCOUNT_PHONE), rs.getString(QueryConsts.ACCOUNT_STATUS),
						rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS),
						rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.ACCOUNT_AREA),
						rs.getInt(QueryConsts.ACCOUNT_DEBT));
			} else {
				throw new SQLException("Account " + userName + " not found", "402", 402);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in fetching UserID: -> " + Integer.toString(account.getUserID()));
		response.setBody(EchoServer.gson.toJson(account));
	}
}
