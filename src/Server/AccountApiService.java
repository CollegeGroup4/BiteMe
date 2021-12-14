package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import logic.Account;

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
	 * Create Account
	 *
	 * This can only be done by the logged in Account.
	 *
	 */
	public static void createAccount(Account account, Response response) {
		try {
			PreparedStatement postAccount = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.account (AccountID, UserName, Password, FirstName, LastName, PhoneNumber, Email,"
							+ " Type, status, BranchManagerID, Area)"
							+ " VALUES (?,?,?,?,?,?,?,?,?,?,?);SELECT last_insert_id();");
			postAccount.setInt(1, account.getUserID());
			// Its the first userName that he had so the test is in users table on login
			postAccount.setString(2, account.getUserName());
			postAccount.setString(3, account.getPassword());
			postAccount.setString(4, account.getFirstName());
			postAccount.setString(5, account.getLastName());
			postAccount.setString(6, account.getPhone());
			postAccount.setString(7, account.getEmail());
			postAccount.setString(8, account.getType());
			postAccount.setString(9, account.getStatus());
			postAccount.setInt(10, account.getBranch_manager_ID());
			postAccount.setString(11, account.getArea());
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
		response.setDescription("Success in registering" + account.getUserID());
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
	public static void deleteAccount(String userName, int accountID, Response response) {
		Account account = null;
		try {
			PreparedStatement deleteAccount = EchoServer.con.prepareStatement(
					"DELETE FROM accounts biteme.account WHERE accounts.UserName = ? AND accounts.AccountID = ?;");
			deleteAccount.setString(1, userName);
			// Its the first userName that he had so the test is in users table on login
			deleteAccount.setInt(2, accountID);
			deleteAccount.execute();
			deleteAccount.getResultSet();
		} catch (SQLException e) {
		}
		response.setCode(200);
		response.setDescription("Success in deleting account " + accountID);
		response.setBody(null);
	}

	/**
	 * Get all Accounts
	 *
	 */
	public static void getAllAccounts(int branch_manager_id, Response response) {
		ResultSet rs;
		Account account = null;
		ArrayList<Account> acccounts = new ArrayList<>();
		try {
			PreparedStatement loginAccount = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.account WHERE BranchManagerID = ?;");
			loginAccount.setInt(1, branch_manager_id);
			rs = loginAccount.getResultSet();
			while (rs.next()) {
				account = new Account(rs.getInt(finals.ACCOUNT_USER_ID), rs.getString(finals.ACCOUNT_USER_NAME), rs.getString(finals.ACCOUNT_PASSWORD),
						rs.getString(finals.ACCOUNT_FIRST_NAME), rs.getString(finals.ACCOUNT_LAST_NAME),
						rs.getString(finals.ACCOUNT_EMAIL), rs.getString(finals.ACCOUNT_TYPE),
						rs.getString(finals.ACCOUNT_PHONE), rs.getString(finals.ACCOUNT_STATUS),
						rs.getInt(finals.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(finals.ACCOUNT_AREA),
						rs.getInt(finals.ACCOUNT_DEBT));
				acccounts.add(account);
			}
		} catch (SQLException e1) {
		}

		response.setCode(200);
		response.setDescription("Success in fetching accounts " + account.getUserID());
		response.setBody(account);
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
			if (rs.next())
				account = new Account(rs.getInt(finals.ACCOUNT_USER_ID), rs.getString(finals.ACCOUNT_USER_NAME), rs.getString(finals.ACCOUNT_PASSWORD),
						rs.getString(finals.ACCOUNT_FIRST_NAME), rs.getString(finals.ACCOUNT_LAST_NAME),
						rs.getString(finals.ACCOUNT_EMAIL), rs.getString(finals.ACCOUNT_TYPE),
						rs.getString(finals.ACCOUNT_PHONE), rs.getString(finals.ACCOUNT_STATUS),
						rs.getInt(finals.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(finals.ACCOUNT_AREA),
						rs.getInt(finals.ACCOUNT_DEBT));
		} catch (SQLException e) {
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
			PreparedStatement loginAccount = EchoServer.con.prepareStatement(
					"SELECT * FROM biteme.account WHERE UserName = ? AND Password = ?;"
							+ "UPDATE biteme.account SET isLoggedIn = 1 WHERE UserName = ? and Password = ?;");
			loginAccount.setString(1, userName);
			// Its the first userName that he had so the test is in users table on login
			loginAccount.setString(2, password);
			loginAccount.execute();
			rs = loginAccount.getResultSet();
			if (rs.getBoolean(finals.ACCOUNT_IS_LOGGED_IN)) {
				throw new SQLException("User is already logged in", "400", 400);
			} else if (rs.getFetchSize() == 0) {
				throw new SQLException("User isn't exist");
			}

			account = new Account(rs.getInt(finals.ACCOUNT_USER_ID), rs.getString(finals.ACCOUNT_USER_NAME), rs.getString(finals.ACCOUNT_PASSWORD),
					rs.getString(finals.ACCOUNT_FIRST_NAME), rs.getString(finals.ACCOUNT_LAST_NAME),
					rs.getString(finals.ACCOUNT_EMAIL), rs.getString(finals.ACCOUNT_TYPE),
					rs.getString(finals.ACCOUNT_PHONE), rs.getString(finals.ACCOUNT_STATUS),
					rs.getInt(finals.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(finals.ACCOUNT_AREA),
					rs.getInt(finals.ACCOUNT_DEBT));
			if (account.getStatus().equals("blocked")) {
				throw new SQLException("Account" + account.getUserID() + "is blocked", "400", 400);
			
		}} catch (SQLException e) {
			if (e.getErrorCode() == 400) {
				response.setCode(400);
				response.setDescription(e.getMessage());
			} else if (e.getErrorCode() == 401) {
				response.setCode(401);
				response.setDescription(e.getMessage());
				response.setBody(null);}
				else{
				response.setCode(404);
				response.setDescription(e.getMessage());
			}

			response.setCode(200);
			response.setDescription("Success in login " + account.getUserID());
			response.setBody(account);
		}
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
							+ "WHERE AccountID = ? AND UserName = ?");
			postAccount.setInt(1, account.getUserID());
			// Its the first userName that he had so the test is in users table on login
			postAccount.setString(2, account.getUserName());
			postAccount.setString(3, account.getPassword());
			postAccount.setString(4, account.getFirstName());
			postAccount.setString(5, account.getLastName());
			postAccount.setString(6, account.getPhone());
			postAccount.setString(7, account.getEmail());
			postAccount.setString(8, account.getType());
			postAccount.setString(9, account.getStatus());
			postAccount.setInt(10, account.getBranch_manager_ID());
			postAccount.setString(11, account.getArea());
			postAccount.execute();

		} catch (SQLException e) {
		} finally {
			response.setCode(200);
			response.setDescription("Success in updating account" + account.getUserID());
			response.setBody(null);
		}
	}
}
