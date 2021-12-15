package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import logic.Account;
import logic.BusinessAccount;
import logic.PrivateAccount;

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
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.account (UserID, UserName, Password, FirstName, LastName, PhoneNumber, Email,"
							+ " Type, status, BranchManagerID, Area, W4C)" + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?);"
							+ "INSERT INTO biteme.private_account (UserID, CreditCardNumber, CreditCardCVV, CreditCardExpDate)"
							+ "VALUES (?,?,?,?)");
			postAccount.setInt(1, account.getUserID());
			// Its the first userName that he had so the test is in users table on login
			postAccount.setString(2, account.getUserName());
			postAccount.setString(3, account.getPassword());
			postAccount.setString(4, account.getFirstName());
			postAccount.setString(5, account.getLastName());
			postAccount.setString(6, account.getPhone());
			postAccount.setString(7, account.getEmail());
			postAccount.setString(8, account.getRole());
			postAccount.setString(9, account.getStatus());
			postAccount.setInt(10, account.getBranch_manager_ID());
			postAccount.setString(11, account.getArea());
			postAccount.setString(12, account.getW4c_card());
			postAccount.setInt(13, account.getUserID());
			postAccount.setString(14, account.getCreditCardNumber());
			postAccount.setString(15, account.getCreditCardCVV());
			postAccount.setString(16, account.getCreditCardExpDate());
			postAccount.execute();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				response.setCode(404);
				response.setDescription("User id already exist");
			} else {
				response.setCode(400);
				response.setDescription("Fields are missing");
			}
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
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.account (UserID, UserName, Password, FirstName, LastName, PhoneNumber, Email,"
							+ " Type, status, BranchManagerID, Area, W4C)" + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?);"
							+ "INSERT INTO biteme.business_account (UserID, MonthBillingCeiling, isApproved, BusinessName, CurrentSpent)"
							+ "VALUES (?,?,?,?,?)");
			postAccount.setInt(1, account.getUserID());
			// Its the first userName that he had so the test is in users table on login
			postAccount.setString(2, account.getUserName());
			postAccount.setString(3, account.getPassword());
			postAccount.setString(4, account.getFirstName());
			postAccount.setString(5, account.getLastName());
			postAccount.setString(6, account.getPhone());
			postAccount.setString(7, account.getEmail());
			postAccount.setString(8, account.getRole());
			postAccount.setString(9, account.getStatus());
			postAccount.setInt(10, account.getBranch_manager_ID());
			postAccount.setString(11, account.getArea());
			postAccount.setString(12, account.getW4c_card());
			postAccount.setInt(13, account.getUserID());
			postAccount.setFloat(14, account.getMonthBillingCeiling());
			postAccount.setBoolean(15, account.getIsApproved());
			postAccount.setString(16, account.getBusinessName());
			postAccount.setFloat(17, account.getCurrentSpent());
			postAccount.execute();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				response.setCode(404);
				response.setDescription("User id already exist");
			} else {
				response.setCode(400);
				response.setDescription("Fields are missing");
			}
			return;
		}

		response.setCode(200);
		response.setDescription("Success in registering business account:" + account.getUserID());
		response.setBody(null);
	}

	/**
	 * Generate a random hex string at size of 50
	 * 
	 * @param numchars
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
					.prepareStatement("SELECT * FROM biteme.account WHERE BranchManagerID = ?" + ";");
			getAllAccounts.setInt(1, branch_manager_id);
			getAllAccounts.execute();
			rs = getAllAccounts.getResultSet();
			while (rs.next()) {
				account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID), rs.getString(QueryConsts.ACCOUNT_USER_NAME),
						rs.getString(QueryConsts.ACCOUNT_PASSWORD), rs.getString(QueryConsts.ACCOUNT_FIRST_NAME),
						rs.getString(QueryConsts.ACCOUNT_LAST_NAME), rs.getString(QueryConsts.ACCOUNT_EMAIL),
						rs.getString(QueryConsts.ACCOUNT_ROLE), rs.getString(QueryConsts.ACCOUNT_PHONE),
						rs.getString(QueryConsts.ACCOUNT_STATUS), rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID),
						rs.getString(QueryConsts.ACCOUNT_AREA), rs.getInt(QueryConsts.ACCOUNT_DEBT),
						rs.getString(QueryConsts.ACCOUNT_W4C), rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS));
				accounts.add(account);
//				PreparedStatement getPrivateAccount = EchoServer.con
//						.prepareStatement("SELECT * FROM biteme.private_account WHERE UserID = ?");
//				getPrivateAccount.setInt(1, account.getUserID());
//				getPrivateAccount.execute();
//				rs1 = getPrivateAccount.getResultSet();
//				if (rs1.next()) {
//					PrivateAccount privateAccount = new PrivateAccount(rs.getInt(QueryConsts.ACCOUNT_USER_ID),
//							rs.getString(QueryConsts.ACCOUNT_USER_NAME), rs.getString(QueryConsts.ACCOUNT_PASSWORD),
//							rs.getString(QueryConsts.ACCOUNT_FIRST_NAME), rs.getString(QueryConsts.ACCOUNT_LAST_NAME),
//							rs.getString(QueryConsts.ACCOUNT_EMAIL), rs.getString(QueryConsts.ACCOUNT_TYPE),
//							rs.getString(QueryConsts.ACCOUNT_PHONE), rs.getString(QueryConsts.ACCOUNT_STATUS),
//							rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.ACCOUNT_AREA),
//							rs.getInt(QueryConsts.ACCOUNT_DEBT), rs.getString(QueryConsts.ACCOUNT_W4C), rs1.getString(2),
//							rs1.getString(3), rs1.getString(4));
//					paccounts.add(privateAccount);
//				}
//				rs1.close();
//
//				PreparedStatement getBusinessAccount = EchoServer.con
//						.prepareStatement("SELECT * FROM biteme.private_account WHERE UserID = ?");
//				getBusinessAccount.setInt(1, account.getUserID());
//				getBusinessAccount.execute();
//				rs1 = getBusinessAccount.getResultSet();
//				if (rs1.next()) {
//					BusinessAccount businessAccount = new BusinessAccount(rs.getInt(QueryConsts.ACCOUNT_USER_ID),
//							rs.getString(QueryConsts.ACCOUNT_USER_NAME), rs.getString(QueryConsts.ACCOUNT_PASSWORD),
//							rs.getString(QueryConsts.ACCOUNT_FIRST_NAME), rs.getString(QueryConsts.ACCOUNT_LAST_NAME),
//							rs.getString(QueryConsts.ACCOUNT_EMAIL), rs.getString(QueryConsts.ACCOUNT_TYPE),
//							rs.getString(QueryConsts.ACCOUNT_PHONE), rs.getString(QueryConsts.ACCOUNT_STATUS),
//							rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.ACCOUNT_AREA),
//							rs.getInt(QueryConsts.ACCOUNT_DEBT), rs.getString(QueryConsts.ACCOUNT_W4C), rs1.getFloat(2),
//							rs1.getBoolean(3), rs1.getString(4), rs1.getFloat(5));
//					baccounts.add(businessAccount);
//				}
			}

		} catch (

		SQLException e) {
			if (e.getErrorCode() == 400) {
				response.setCode(400);
				response.setDescription(e.getMessage());
				response.setBody(null);
			} else if (e.getErrorCode() == 401) {
				response.setCode(401);
				response.setDescription(e.getMessage());
				response.setBody(null);
			} else {
				response.setCode(e.getErrorCode());
				response.setDescription(e.getMessage());
			}
			return;
		}

		response.setCode(200);
		response.setDescription("Success in fetching accounts " + account.getUserID());
		response.setBody(accounts.toArray());
	}

	/**
	 * Get Account by Account name
	 *
	 */
	public static void getAccount(String userName, int userID, int branchManagerID, Response response) {
		ResultSet rs;
		Account account = null;
		try {
			PreparedStatement getAccount = EchoServer.con.prepareStatement(
					"SELECT * FROM accounts biteme.account WHERE accounts.UserName = ? and accounts.UserID = ? and accounts.BranchManagerID = ?;");
			getAccount.setString(1, userName);
			// Its the first userName that he had so the test is in users table on login
			getAccount.setInt(2, userID);
			getAccount.setInt(3, branchManagerID);
			getAccount.execute();
			rs = getAccount.getResultSet();
			if (rs.next()) {
				account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID), rs.getString(QueryConsts.ACCOUNT_USER_NAME),
						rs.getString(QueryConsts.ACCOUNT_PASSWORD), rs.getString(QueryConsts.ACCOUNT_FIRST_NAME),
						rs.getString(QueryConsts.ACCOUNT_LAST_NAME), rs.getString(QueryConsts.ACCOUNT_EMAIL),
						rs.getString(QueryConsts.ACCOUNT_TYPE), rs.getString(QueryConsts.ACCOUNT_PHONE),
						rs.getString(QueryConsts.ACCOUNT_STATUS), rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID),
						rs.getString(QueryConsts.ACCOUNT_AREA), rs.getInt(QueryConsts.ACCOUNT_DEBT), null);
			} else
				throw new SQLException("Account" + account.getUserID() + "is not found in table", "400", 400);
			rs.close();
			if (account.getRole().equals("private")) {
				PreparedStatement getPrivateAccount = EchoServer.con
						.prepareStatement("SELECT * FROM biteme.private_account WHERE UserID = ?");
				getPrivateAccount.setInt(1, account.getUserID());
				getPrivateAccount.execute();
				rs = getPrivateAccount.getResultSet();
				if (rs.next()) {
					PrivateAccount privateAccount = new PrivateAccount(account.getUserID(), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5), userName, userName, userName, userName, branchManagerID, userName, branchManagerID, userName, userName, userName, userName);
				}
				rs.close();
			} else if (account.getRole().equals("business")) {
				PreparedStatement getBusinessAccount = EchoServer.con
						.prepareStatement("SELECT * FROM biteme.business_account WHERE UserID = ?");
				getBusinessAccount.setInt(1, account.getUserID());
				getBusinessAccount.execute();
				rs.close();
				rs = getBusinessAccount.getResultSet();
				if (rs.next()) {
					BusinessAccount businessAcount = new BusinessAccount(account.getUserID(), rs.getFloat(2),
							rs.getBoolean(3), rs.getString(4), rs.getFloat(5), rs.getString(6));
					account.setBusinessAccount(businessAcount);

				}
				rs.close();
			}

		} catch (SQLException e) {
			if (e.getErrorCode() == 400) {
				response.setCode(400);
				response.setDescription(e.getMessage());
				response.setBody(null);
			} else {
				response.setCode(e.getErrorCode());
				response.setDescription(e.getMessage());
				response.setBody(null);
			}
			return;
		}
		response.setCode(200);
		response.setDescription("Success in fetching account " + account.getUserID());
		response.setBody(account);
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
					.prepareStatement("SELECT * FROM biteme.account WHERE UserName = ? AND Password = ?;"
							+ "UPDATE biteme.account SET isLoggedIn = 1 WHERE UserName = ? and Password = ?;");
			loginAccount.setString(1, userName);
			// Its the first userName that he had so the test is in users table on login
			loginAccount.setString(2, password);
			loginAccount.execute();
			rs = loginAccount.getResultSet();
			if (rs.getBoolean(QueryConsts.ACCOUNT_IS_LOGGED_IN)) {
				throw new SQLException("User is already logged in", "400", 400);
			} else if (rs.getFetchSize() == 0) {
				throw new SQLException("User isn't exist");
			}

			account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID), rs.getString(QueryConsts.ACCOUNT_USER_NAME),
					rs.getString(QueryConsts.ACCOUNT_PASSWORD), rs.getString(QueryConsts.ACCOUNT_FIRST_NAME),
					rs.getString(QueryConsts.ACCOUNT_LAST_NAME), rs.getString(QueryConsts.ACCOUNT_EMAIL),
					rs.getString(QueryConsts.ACCOUNT_TYPE), rs.getString(QueryConsts.ACCOUNT_PHONE),
					rs.getString(QueryConsts.ACCOUNT_STATUS), rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID),
					rs.getString(QueryConsts.ACCOUNT_AREA), rs.getInt(QueryConsts.ACCOUNT_DEBT), null);
			if (account.getStatus().equals("blocked")) {
				throw new SQLException("Account" + account.getUserID() + "is blocked", "401", 401);

			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 400) {
				response.setCode(400);
				response.setDescription(e.getMessage());
			} else if (e.getErrorCode() == 401) {
				response.setCode(401);
				response.setDescription(e.getMessage());
				response.setBody(null);
			} else {
				response.setCode(404);
				response.setDescription(e.getMessage());
			}
			return;
		}
		response.setCode(200);
		response.setDescription("Success in login " + account.getUserID());
		response.setBody(account);
	}

	/**
	 * Logs out current logged in Account session
	 *
	 */
	public static void logoutAccount(String userName, Response response) {
		ResultSet rs;
		try {
			PreparedStatement logOutAccount = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET isLoggedIn = 0 WHERE account.UserName = ?;");
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
	 * This can only be done by the logged in Account.
	 *
	 */
	public static void loginAccountW4C(String w4cCode, Response response) {
		ResultSet rs;
		try {
			PreparedStatement logOutAccount = EchoServer.con
					.prepareStatement("SELECT * biteme.account WHERE isLoggedIn = 0 WHERE account.UserName = ?;");
			logOutAccount.setString(1, w4cCode);
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
	 * Updated Account
	 *
	 * This can only be done by the logged in Account.
	 *
	 */
	public static void updateAccount(Account account, Response response) {
		try {
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"UPDATE biteme.account AS account SET (UserID = ?, UserName = ?, Password = ?,FirstName = ?, LastName = ?, PhoneNumber = ?, Email = ?,"
							+ " Type = ?, status = ?, BranchManagerID = ?, Area = ?, isLoggedIn = ?)"
							+ "WHERE UserID = ? AND UserName = ?");
			postAccount.setInt(1, account.getUserID());
			// Its the first userName that he had so the test is in users table on login
			postAccount.setString(2, account.getUserName());
			postAccount.setString(3, account.getPassword());
			postAccount.setString(4, account.getFirstName());
			postAccount.setString(5, account.getLastName());
			postAccount.setString(6, account.getPhone());
			postAccount.setString(7, account.getEmail());
			postAccount.setString(8, account.getRole());
			postAccount.setString(9, account.getStatus());
			postAccount.setInt(10, account.getBranch_manager_ID());
			postAccount.setString(11, account.getArea());
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
}