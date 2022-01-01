package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.mysql.cj.xdevapi.Result;

import common.Response;
import common.imageUtils;
import logic.Category;
import logic.Item;
import logic.Menu;
import logic.Options;
import logic.Restaurant;
import logic.item_in_menu;

/**
 * BiteMe
 *
 * <p>
 * No description provided (generated by Swagger Codegen
 * https://github.com/swagger-api/swagger-codegen)
 *
 */
public class RestaurantApiService {

	/**
	 * Get approved restaurants for the specific location
	 *
	 */
	// @SuppressWarnings("resource")
	private static void getRestaurants(String area, Response response, String type, String areaBlank,
			String typeBlank) {
		PreparedStatement stmt;
		ResultSet rs;
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		Restaurant restaurant = null;
		try {
			stmt = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.restaurant WHERE IsApproved = 1" + area + type + ";");
			rs = stmt.executeQuery();
			while (rs.next()) {
				restaurant = new Restaurant(rs.getInt(QueryConsts.RESTAURANT_ID),
						rs.getBoolean(QueryConsts.RESTAURANT_IS_APPROVED),
						rs.getInt(QueryConsts.RESTAURANT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.RESTAURANT_NAME),
						rs.getString(QueryConsts.RESTAURANT_AREA), rs.getString(QueryConsts.RESTAURANT_TYPE),
						rs.getString(QueryConsts.RESTAURANT_USER_NAME), rs.getString(QueryConsts.RESTAURANT_PHOTO),
						rs.getString(QueryConsts.RESTAURANT_ADDRESS), rs.getString(QueryConsts.RESTAURANT_DESCRIPTION));
				restaurants.add(restaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setCode(200);
		response.setDescription("Success in fetching restaurants -> Area: " + areaBlank + ", type: " + typeBlank);
		response.setBody(EchoServer.gson.toJson(restaurants.toArray()));
	}

	public static void getRestaurantsByArea(String area, Response response) {
		if (area.equals("All"))
			getRestaurants("", response, "", area, "All");
		else
			getRestaurants(" AND Area = '" + area + "'", response, "", area, "All");
	}

	/**
	 * Get restaurants for the specific location
	 *
	 */
	public static void getRestaurantsByTypeAndArea(String area, String type, Response response) {
		if (area.equals("All"))
			getRestaurants("", response, " AND Type = '" + type + "'", area, type);
		else
			getRestaurants(" AND Area = '" + area + "'", response, " AND Type = '" + type + "'", area, type);
	}

	/**
	 * Get all categories
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void getAllCategories(int restaurantID, Response response) {
		PreparedStatement stmt;
		ResultSet rs;
		ArrayList<Category> categories = new ArrayList<>();
		HashMap<String, ArrayList<String>> cat = new HashMap<>();
		Category category = null;
		try {
			stmt = EchoServer.con
					.prepareStatement("SELECT DISTINCT Category, SubCategory FROM biteme.item WHERE restaurantID = ?;");
			stmt.setInt(1, restaurantID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (cat.containsKey(rs.getString(1))) {
					cat.get(rs.getString(1)).add(rs.getString(2));
				} else {
					ArrayList<String> newCategory = new ArrayList<>();
					newCategory.add(rs.getString(2));
					cat.put(rs.getString(1), newCategory);
				}
			}
			for (String category2 : cat.keySet()) {
				category = new Category(category2);

				for (String category3 : cat.get(category2)) {
					category.getSubCategory().add(category3);
				}
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setCode(200);
		response.setDescription("Success in fetching categories for restaurantID" + Integer.toString(restaurantID));
		response.setBody(categories.toArray());
	}

	/**
	 * Getting list of all related items
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void allItems(int restaurantID, Response response) {
		PreparedStatement stmt;
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<Options> options = new ArrayList<>();
		Item item = null;
		ResultSet rs, rs2;
		try {
			stmt = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.item AS items" + " WHERE items.RestaurantID = ?;");
			stmt.setInt(1, restaurantID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				item = new Item(rs.getString(QueryConsts.ITEM_CATEGORY), rs.getString(QueryConsts.ITEM_SUB_CATEGORY),
						rs.getInt(QueryConsts.ITEM_ID), rs.getInt(QueryConsts.ITEM_RES_ID),
						rs.getString(QueryConsts.ITEM_NAME), rs.getFloat(QueryConsts.ITEM_PRICE),
						rs.getString(QueryConsts.ITEM_DESCRIPTION), rs.getString(QueryConsts.ITEM_INGREDIENTS), null,
						rs.getString(QueryConsts.ITEM_IMAGE), 0);
				stmt = EchoServer.con.prepareStatement("SELECT * FROM biteme.optional_category WHERE ItemID = ?;");
				stmt.setInt(1, item.getItemID());
				rs2 = stmt.executeQuery();
				while (rs2.next()) {
					options.add(new Options(rs2.getString(QueryConsts.OPTIONAL_TYPE),
							rs2.getString(QueryConsts.OPTIONAL_SPECIFY), rs2.getInt(QueryConsts.OPTIONAL_PRICE),
							rs2.getInt(QueryConsts.OPTIONAL_ITEM_ID),
							rs2.getBoolean(QueryConsts.OPTIONAL_IS_DUPLICATABLE)));
				}
				item.setOptions(options.toArray(new Options[0]));
				options.clear();
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setCode(200);
		response.setDescription(
				"Success in fetching all restaurant items -> restaurantID: " + Integer.toString(restaurantID));
		response.setBody(EchoServer.gson.toJson(items.toArray()));
	}

	/**
	 * Getting list of all related items
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void updateItem(Item item, Response response) {
		PreparedStatement stmt, updateOptions;
		int rowsUpdated;
		try {
			stmt = EchoServer.con
					.prepareStatement("UPDATE biteme.item SET Category = ?, SubCategory = ?, Name = ?, Price = ?,"
							+ "Ingredients = ?, Image = ?, Description = ?  WHERE ItemID = ?"
							+ "AND RestaurantID = ?;");
			stmt.setString(1, item.getCategory());
			stmt.setString(2, item.getSubcategory());
			stmt.setString(3, item.getName());
			stmt.setFloat(4, item.getPrice());
			stmt.setString(5, item.getIngrediants());
			stmt.setString(6, item.getPhoto());
			stmt.setString(7, item.getDescription());
			stmt.setInt(8, item.getItemID());
			stmt.setInt(9, item.getRestaurantID());
			rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 0) {
				throw new SQLException();
			}
			stmt = EchoServer.con.prepareStatement("DELETE FROM biteme.optional_category WHERE ItemID = ?;");
			stmt.setInt(1, item.getItemID());
			rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 0) {
				throw new SQLException();
			}
			if (item.getOptions() != null) {
				for (Options temp : item.getOptions()) {
					try { // just in case
						updateOptions = EchoServer.con.prepareStatement(
								"INSERT INTO biteme.optional_category (OptionalType, Specify, ItemID, price, isDuplicatable)"
										+ " VALUES (?,?,?,?,?);");
						updateOptions.setString(1, temp.getOption_category());
						updateOptions.setString(2, temp.getSpecify_option());
						updateOptions.setInt(3, item.getItemID());
						updateOptions.setDouble(4, temp.getPrice());
						updateOptions.setBoolean(5, temp.isDuplicatable());
						updateOptions.executeUpdate();
					} catch (SQLException e) {
					}
				}
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Fail in update an item -> itemID: " + Integer.toString(item.getItemID()));
		}
		response.setCode(200);
		response.setDescription("Success in updating item -> itemID: " + Integer.toString(item.getItemID()));
	}

	/**
	 * Get all menus
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void allMenues(int restaurantID, Response response) {
		ResultSet rs1, rs2;
		ArrayList<Menu> menus = new ArrayList<>();
		Item[] items = null;
		ArrayList<item_in_menu> item_in_menu = new ArrayList<>();
		Menu mtemp;
		item_in_menu iimtemp;
		allItems(restaurantID, response);
		items = EchoServer.gson.fromJson((String) response.getBody(), Item[].class);
		try {
			PreparedStatement getMenus = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.menu WHERE RestaurantID = ?");
			getMenus.setInt(1, restaurantID);
			rs1 = getMenus.executeQuery();
			while (rs1.next()) {
				try {
					PreparedStatement getItemInMenu = EchoServer.con.prepareStatement(
							"SELECT * FROM biteme.item_in_menu WHERE RestaurantID = ? AND MenuName = ?;");
					getItemInMenu.setInt(1, restaurantID);
					getItemInMenu.setString(2, rs1.getString(2));
					rs2 = getItemInMenu.executeQuery();
					while (rs2.next()) {
						iimtemp = new item_in_menu(rs2.getInt(QueryConsts.ITEM_IN_MENU_ITEM_ID),
								rs2.getInt(QueryConsts.ITEM_IN_MENU_RES_ID),
								rs2.getString(QueryConsts.ITEM_IN_MENU_MENU_NAME),
								rs2.getString(QueryConsts.ITEM_IN_MENU_COURSE));
						item_in_menu.add(iimtemp);
					}
					mtemp = new Menu(rs1.getString(2), rs1.getInt(1), item_in_menu.toArray(new item_in_menu[0]));
					menus.add(mtemp);
				} catch (SQLException e) {
					response.setCode(405);
					response.setDescription("Couldn't get all the menus -> restaurantID: " + Integer.toString(restaurantID));
				}
			}
		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Invalid input");
			return;
		}
		JsonElement itemim = EchoServer.gson.toJsonTree(new Object());
		JsonElement itemi = EchoServer.gson.toJsonTree(items);
		JsonElement menui = EchoServer.gson.toJsonTree(menus.toArray());
		itemim.getAsJsonObject().add("items", itemi);
		itemim.getAsJsonObject().add("menues", menui);
		response.setBody(EchoServer.gson.toJson(itemim));
		response.setCode(200);
		response.setDescription("Success in fetching items and menus for restaurant " + restaurantID);
	}

	/**
	 * Approve order
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void approveOrder(int orderId, Response response) {
		ResultSet rs;
		try {
			PreparedStatement approveOrder = EchoServer.con
					.prepareStatement("UPDATE biteme.order SET isApproved = 1 WHERE OrderNum = ?;");
			approveOrder.setInt(1, orderId);
			if (approveOrder.executeUpdate() == 0) {
				throw new SQLException("couldn't approve order " + Integer.toString(orderId));
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			PreparedStatement updateApprovalTime = EchoServer.con
					.prepareStatement("UPDATE biteme.order SET approve_time = ? WHERE OrderNum = ?;");
			updateApprovalTime.setString(1, dtf.format(now));
			updateApprovalTime.setInt(2, orderId);
			if (updateApprovalTime.executeUpdate() == 0) {
				throw new SQLException("couldn't update the time in menu" + Integer.toString(orderId));
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in approve order " + Integer.toString(orderId));
	}
	
	/**
	 * Approve order
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void setPrepTime(int orderId, Response response) {
		ResultSet rs;
		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			PreparedStatement updateApprovalTime = EchoServer.con
					.prepareStatement("UPDATE biteme.order SET prep_time = ? WHERE OrderNum = ?;");
			updateApprovalTime.setString(1, dtf.format(now));
			updateApprovalTime.setInt(2, orderId);
			if (updateApprovalTime.executeUpdate() == 0) {
				throw new SQLException("couldn't update the time in menu" + Integer.toString(orderId));
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in approve order " + Integer.toString(orderId));
	}

	/**
	 * Create item
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void createItem(Item item, Response response) {
		PreparedStatement postItem, checkDuplicate, postCategory, postOptions;
		ResultSet rs = null;
		int itemID;
		try {
			checkDuplicate = EchoServer.con
					.prepareStatement("SELECT COUNT(Name) FROM biteme.item WHERE Name = ? AND RestaurantID = ?;");
			checkDuplicate.setString(1, item.getName());
			checkDuplicate.setInt(2, item.getRestaurantID());
			rs = checkDuplicate.executeQuery();
			rs.next();
			if (rs.getInt(1) >= 1) {
				throw new SQLException("Duplicate key ", null, 1062);
			}
			postItem = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.item (Category, SubCategory, Name, Price, Ingredients,"
							+ "RestaurantID, Image, Description)" + " VALUES (?,?,?,?,?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			postItem.setString(1, item.getCategory());
			postItem.setString(2, item.getSubcategory());
			postItem.setString(3, item.getName());
			postItem.setFloat(4, item.getPrice());
			postItem.setString(5, item.getIngrediants());
			postItem.setInt(6, item.getRestaurantID());
			postItem.setString(7, item.getPhoto());
			postItem.setString(8, item.getDescription());
			postItem.executeUpdate();
			rs = postItem.getGeneratedKeys();
			rs.next();
			itemID = rs.getInt(1);
			if (item.getOptions() != null) {
				String sufix = item.getItemImage().getFileName().substring(item.getItemImage().getFileName().length()-4);
				String imageFileName = "item_" + itemID + sufix;
				item.getItemImage().setFileName(imageFileName);
				imageUtils.receiver(item.getItemImage(), QueryConsts.FILE_PATH_ITEMS);
				postItem = EchoServer.con.prepareStatement(
						"UPDATE biteme.item SET Image = ? WHERE ItemID = ?;");
				postItem.setString(1, imageFileName);
				postItem.setInt(2, itemID);
				postItem.executeUpdate();
				
				for (Options temp : item.getOptions()) {
					try { // just in case
						postOptions = EchoServer.con.prepareStatement(
								"INSERT INTO biteme.optional_category (OptionalType, Specify, ItemID, price, isDuplicatable)"
										+ " VALUES (?,?,?,?,?);");
						postOptions.setString(1, temp.getOption_category());
						postOptions.setString(2, temp.getSpecify_option());
						postOptions.setInt(3, itemID);
						postOptions.setDouble(4, temp.getPrice());
						postOptions.setBoolean(5, temp.isDuplicatable());
						postOptions.executeUpdate();
					} catch (SQLException e) {
					}
				}
			}
			try {
				postCategory = EchoServer.con
						.prepareStatement("INSERT INTO biteme.item_category (Category, SubCategory) VALUES (?,?);");
				postCategory.setString(1, item.getCategory());
				postCategory.setString(2, item.getSubcategory());
				postCategory.executeUpdate();
			} catch (SQLException e) {
				// TODO: handle exception
			}

		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				response.setCode(405);
				response.setDescription("Item already exist in the restaurant");
			}
			response.setCode(401);
			response.setDescription("Invalid input " + e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in creating a new item -> itemID: " + Integer.toString(itemID));
	}

	/**
	 * Create menu
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void createMenu(Menu menu, Response response) {
		try {
			checkMenu(menu);
		} catch (SQLException e1) {
			response.setCode(e1.getErrorCode());
			response.setDescription(e1.getMessage());
			return;
		}
		PreparedStatement postIIM, postMenu;
		try {
			postMenu = EchoServer.con.prepareStatement("INSERT INTO biteme.menu (RestaurantID, MenuName) VALUES(?,?);");
			postMenu.setInt(1, menu.getRestaurantID());
			postMenu.setString(2, menu.getName());
			postMenu.executeUpdate();
			for (item_in_menu temp : menu.getItems()) {
				try {
					postIIM = EchoServer.con.prepareStatement("INSERT INTO biteme.item_in_menu (ItemID, "
							+ "RestaurantID, MenuName, Course) VALUES(?,?,?,?);");
					postIIM.setInt(1, temp.getItemID());
					postIIM.setInt(2, menu.getRestaurantID());
					postIIM.setString(3, menu.getName());
					postIIM.setString(4, temp.getCourse());
					postIIM.executeUpdate();
				} catch (SQLException e) {
				}
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				response.setBody(null);
				response.setCode(400);
				response.setDescription("Menu name already exist for restaurant " + menu.getName());
			} else {
				response.setBody(null);
				response.setCode(e.getErrorCode());
				response.setDescription(e.getMessage());
			}
			return;
		}
		response.setCode(200);
		response.setDescription("Success in creating menu -> menuName:  " + menu.getName());
	}

	private static void checkMenu(Menu menu) throws SQLException {

		for (item_in_menu items : menu.getItems()) {
			if (!items.getMenu_name().equals(menu.getName()) || items.getRestaurantNum() != menu.getRestaurantID()) {
				throw new SQLException("Menu information doesn't match items information", "400", 400);
			}
		}
	}

	/**
	 * Edit menu
	 *
	 * This can only be done by the logged in supplier/moderator
	 *
	 */
	public static void editMenu(Menu oldMenu, Menu newMenu, Response response) {
		deleteMenu(oldMenu.getName(),oldMenu.getRestaurantID(), response);
		if (newMenu != null)
			createMenu(newMenu, response);
	}
	
	/**
	 * Delete menu
	 *
	 * This can only be done by the logged in supplier/moderator
	 *
	 */
	public static void deleteMenu(String menuName, int restaurantID, Response response) {
		try {
			PreparedStatement deleteOldMenu = EchoServer.con
					.prepareStatement("DELETE FROM biteme.item_in_menu WHERE RestaurantID = ? AND MenuName = ?;");
			deleteOldMenu.setInt(1, restaurantID);
			deleteOldMenu.setString(2, menuName);
			deleteOldMenu.executeUpdate();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Couldn't delete menu");
			return;
		}
		try {
			PreparedStatement deleteOldMenu = EchoServer.con
					.prepareStatement("DELETE FROM biteme.menu WHERE RestaurantID = ? AND MenuName = ?;");
			deleteOldMenu.setInt(1, restaurantID);
			deleteOldMenu.setString(2, menuName);
			deleteOldMenu.executeUpdate();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Couldn't delete menu");
			return;
		}
		response.setCode(200);
		response.setDescription("Success in deleting menu");
	}
	

//	/**
//	 * Edit menu
//	 *
//	 * This can only be done by the logged in supplier/moderator
//	 *
//	 */
//	public static void deleteMenu(Menu menu, Response response) {
//		try {
//			PreparedStatement deleteOldMenu = EchoServer.con
//					.prepareStatement("DELETE FROM biteme.item_in_menu WHERE RestaurantID = ? AND MenuName = ?;");
//			deleteOldMenu.setInt(1, menu.getRestaurantID());
//			deleteOldMenu.setString(2, menu.getName());
//			deleteOldMenu.execute();
//		} catch (SQLException e) {
//		}
//		try {
//			PreparedStatement deleteOldMenu = EchoServer.con
//					.prepareStatement("DELETE FROM biteme.menu WHERE RestaurantID = ? AND MenuName = ?;");
//			deleteOldMenu.setInt(1, menu.getRestaurantID());
//			deleteOldMenu.setString(2, menu.getName());
//			deleteOldMenu.execute();
//		} catch (SQLException e) {
//			response.setBody(null);
//			response.setCode(400);
//			response.setDescription("Old menu dosn't exist!");
//			return;
//		}
//		response.setCode(200);
//		response.setDescription("Success in deletint menu -> menuName: " + menu.getName());
//	}
	/**
	 * Delete item
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void itemsDelete(int itemID, Response response) {
		int rowsDeleted;
		try {
			PreparedStatement deleteItem = EchoServer.con.prepareStatement(
					"DELETE FROM biteme.item WHERE ItemID = ?;DELETE FROM biteme.optional_category WHERE ItemID = ?;");
			deleteItem.setInt(1, itemID);
			// Its the first userName that he had so the test is in users table on login
			rowsDeleted = deleteItem.executeUpdate();
			if (rowsDeleted == 0) {
				throw new SQLException();
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Couldn't delete an item -> itemID: " + Integer.toString(itemID));
			return;
		}
		response.setCode(200);
		response.setDescription("Success in deleting an item -> itemID: " + Integer.toString(itemID));
		response.setBody(null);
	}

	/**
	 * Get credit for the user
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void getCredit(String userName, int restaurantID, Response response) {
		ResultSet rs;
		PreparedStatement getCredit;
		Double sendCredit;
		try {
			getCredit = EchoServer.con.prepareStatement(
					"SELECT AmountInCredit FROM biteme.credit WHERE UserName = ? AND "
							+ "RestaurantID = ?;");
			getCredit.setString(1, userName);
			getCredit.setInt(2, restaurantID);
			rs = getCredit.executeQuery();
			sendCredit = rs.getDouble(1);
		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Couldn't get credit for the user -> UserName: " + userName + ", RestaurantID: "
					+ Integer.toString(restaurantID));
			return;
		}
		response.setCode(200);
		response.setDescription("Success in getting credit for the user -> UserName: " + userName + ", RestaurantID: "
				+ Integer.toString(restaurantID));
		JsonElement setCredit = EchoServer.gson.toJsonTree(new Object());
		setCredit.getAsJsonObject().addProperty("credit", sendCredit);
		response.setBody(EchoServer.gson.toJson(setCredit));
	}
}