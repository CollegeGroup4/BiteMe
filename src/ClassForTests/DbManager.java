package ClassForTests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Server.EchoServer;
import common.DBController;

public class DbManager implements iDbManager {

	@Override
	public String getCeoEmail(String email) {
		try {
			PreparedStatement getCEOMail = EchoServer.con
					.prepareStatement("SELECT Email FROM account WHERE Role = 'CEO';");
			ResultSet rs = getCEOMail.executeQuery();
			if (rs.next())
				email = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return email;
	}

	public void setUp() {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
				"Tal4EvEr");
	}

}
