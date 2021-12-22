package biteme.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonElement;
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
	public static void getRestaurants(String area, Response response, String type) {
		PreparedStatement stmt;
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		Restaurant restaurant = null;
		try {
			stmt = EchoServer.con.prepareStatement(
					"SELECT * FROM restaurant biteme.restaurant WHERE restaurant.IsApproved = 1" + area + type + ";");
			ResultSet rs = stmt.executeQuery();
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
		response.setDescription(
				"Success in fetching restaurants -> Area:" + area + ", type: " + (type.equals("") ? "All" : type));
		response.setBody(restaurants);
	}

	public static void getRestaurantsByArea(String area, Response response) {
		getRestaurants("and restaurant.Area = " + area, response, "");
	}

	/**
	 * Get restaurants for the specific location
	 *
	 */
	public static void getRestaurantsByTypeAndArea(String area, String type, Response response) {
		getRestaurants("and restaurant.Area = " + area, response, "and restaurant.Type = " + type);
	}

	/**
	 * Get all categories
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void getAllCategories(int restaurantID, Response response) {
		PreparedStatement stmt;
		ArrayList<Category> categories = new ArrayList<>();
		HashMap<String, ArrayList<String>> cat = new HashMap<>();
		Category category = null;
		try {
			stmt = EchoServer.con
					.prepareStatement("SELECT DISTINCT (items.category, items.subCategory) FROM items biteme.item"
							+ " WHERE items.restaurantNID = ?;");
			stmt.setInt(1, restaurantID);
			ResultSet rs = stmt.executeQuery();
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
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM items biteme.item, itemInMenu biteme.item_in_menu"
					+ " WHERE items.RestaurantID = ? and itemInMenu.ItemID = items.ItemID and"
					+ "itemInMenu.RestaurantID = ?;");
			stmt.setInt(1, restaurantID);
			stmt.setInt(2, restaurantID);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				item = new Item(rs.getString(QueryConsts.ITEM_CATEGORY), rs.getString(QueryConsts.ITEM_SUB_CATEGORY),
						rs.getInt(QueryConsts.ITEM_ID), rs.getInt(QueryConsts.ITEM_RES_ID),
						rs.getString(QueryConsts.ITEM_NAME), rs.getFloat(QueryConsts.ITEM_PRICE),
						rs.getString(QueryConsts.ITEM_DESCRIPTION), rs.getString(QueryConsts.ITEM_INGREDIENTS), null,
						rs.getString(QueryConsts.ITEM_IMAGE), 0);
				stmt = EchoServer.con
						.prepareStatement("SELECT * FROM options biteme.optional_category WHERE options.itemID = ?;");
				stmt.setInt(1, item.getItemID());
				stmt.executeQuery();
				ResultSet rs2 = stmt.getResultSet();
				while (rs2.next()) {
					options.add(new Options(rs2.getString(QueryConsts.OPTIONAL_TYPE),
							rs2.getString(QueryConsts.OPTIONAL_SPECIFY), rs2.getInt(QueryConsts.OPTIONAL_PRICE),rs2.getInt(QueryConsts.OPTIONAL_ITEM_ID)));
				}
				item.setOptions((Options[]) options.toArray());
				options.clear();
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setCode(200);
		response.setDescription("Success in fetching all restaurant itesm" + Integer.toString(restaurantID));
		response.setBody(items.toArray());
	}
	
	/**
	 * Getting list of all related items
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void updateItem(Item item, Response response) {
		PreparedStatement stmt;
		try {
			stmt = EchoServer.con.prepareStatement("UPDATE biteme.item AS items SET Category = ?, SubCategory = ?, Name = ?, Price = ?,"
					+ "Ingredients = ?, Image = ?, Description = ?  WHERE items.ItemID = ?"
					+ "AND items.RestaurantID = ?;");
			stmt.setString(1, item.getCategory());
			stmt.setString(2, item.getSubcategory());
			stmt.setString(3, item.getName());
			stmt.setFloat(4, item.getPrice());
			stmt.setString(5, item.getIngrediants());
			stmt.setString(6, item.getPhoto());
			stmt.setString(7, item.getDescription());
			stmt.setInt(8, item.getItemID());
			stmt.setInt(9, item.getRestaurantID());			
			stmt.executeQuery();
			stmt = EchoServer.con.prepareStatement("DELETE FROM options biteme.optional_category WHERE options.itemID = ?;");
			stmt.setInt(1, item.getItemID());
			stmt.executeQuery();
			
			PreparedStatement updateOptions = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.optional_category (OptionalType, Specify, ItemID, price)" + " VALUES (?,?,?,?);");
			updateOptions.setInt(3, item.getItemID());
			for (Options temp : item.getOptions()) {
				try { // just in case
					updateOptions.setString(1, temp.getOption_category());
					updateOptions.setString(2, temp.getSpecify_option());
					updateOptions.setDouble(4, temp.getPrice());
					updateOptions.execute();
				} catch (SQLException e) {
				}
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Fail in update item -> itemID: " + Integer.toString(item.getItemID()));
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
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<item_in_menu> item_in_menu = new ArrayList<>();
		ArrayList<Options> options = new ArrayList<>();
		Menu mtemp;
		Item itemp;
		item_in_menu iimtemp;
		Options otemp;
		try {
			PreparedStatement getItems = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.item WHERE RestaurantID = ?");
			getItems.setInt(1, restaurantID);
			rs1 = getItems.getResultSet();
			while (rs1.next()) {
				itemp = new Item(rs1.getString(QueryConsts.ITEM_CATEGORY), rs1.getString(QueryConsts.ITEM_SUB_CATEGORY),
						rs1.getInt(QueryConsts.ITEM_ID), rs1.getInt(QueryConsts.ITEM_RES_ID),
						rs1.getString(QueryConsts.ITEM_NAME), rs1.getFloat(QueryConsts.ITEM_PRICE),
						rs1.getString(QueryConsts.ITEM_DESCRIPTION), rs1.getString(QueryConsts.ITEM_INGREDIENTS), null,
						rs1.getString(QueryConsts.ITEM_IMAGE), 0);
				items.add(itemp);

				PreparedStatement getOptions = EchoServer.con
						.prepareStatement("SELECT * FROM biteme.optional_category WHERE itemID = ?");
				getOptions.setInt(1, itemp.getItemID());
				getOptions.execute();
				rs2 = getOptions.getResultSet();
				while (rs2.next()) {
					otemp = new Options(rs2.getString(QueryConsts.OPTIONAL_TYPE),
							rs2.getString(QueryConsts.OPTIONAL_SPECIFY), rs2.getDouble(QueryConsts.OPTIONAL_PRICE),itemp.getItemID());

					options.add(otemp);
				}
				itemp.setOptions(options.toArray(new Options[0]));
				options.clear();
				rs2.close();
			}
			rs1.close();

			PreparedStatement getMenus = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.menu WHERE RestaurantID = ?");
			getMenus.setInt(1, restaurantID);
			getMenus.execute();
			rs1 = getMenus.getResultSet();

			while (rs1.next()) {
				// mtemp = new Menu(rs1.getString(2), restaurantID);
				PreparedStatement getItemInMenu = EchoServer.con.prepareStatement(
						"SELECT * FROM biteme.item_in_menu WHERE " + "IIM.RestaurantID = ? AND IIM.MenuName = ?;");
				getItemInMenu.setInt(1, restaurantID);
				getItemInMenu.setString(2, rs1.getString(2));
				getItemInMenu.execute();
				rs2 = getItemInMenu.getResultSet();
				while (rs2.next()) {
					iimtemp = new item_in_menu(rs2.getInt(QueryConsts.ITEM_IN_MENU_ITEM_ID),
							rs2.getInt(QueryConsts.ITEM_IN_MENU_RES_ID),
							rs2.getString(QueryConsts.ITEM_IN_MENU_MENU_NAME),
							rs2.getString(QueryConsts.ITEM_IN_MENU_COURSE));
					item_in_menu.add(iimtemp);
				}
				mtemp = new Menu(rs1.getString(2), rs1.getInt(1),(item_in_menu[])item_in_menu.toArray());
				menus.add(mtemp);
			}
		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Invalid input");
			return;
		}
		JsonElement itemim = EchoServer.gson.toJsonTree(new Object());
		JsonElement itemi = EchoServer.gson.toJsonTree(items.toArray());
		JsonElement menui = EchoServer.gson.toJsonTree(menus.toArray());
		itemim.getAsJsonObject().add("items", itemi);
		itemim.getAsJsonObject().add("menus", menui);
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
					.prepareStatement("UPDATE biteme.order AS orders SET isApproved = 1 WHERE order.OrderNum = ?;");
			approveOrder.setInt(1, orderId);
			approveOrder.execute();
			rs = approveOrder.getResultSet();
			if (rs.rowUpdated() == false) {
				throw new SQLException("couldn't approve order " + Integer.toString(orderId));
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			PreparedStatement updateApprovalTime = EchoServer.con
					.prepareStatement("UPDATE biteme.order AS orders SET approve_time = ? WHERE order.OrderNum = ?;");
			updateApprovalTime.setString(1, dtf.format(now));
			updateApprovalTime.setInt(2, orderId);
			updateApprovalTime.execute();
			rs = updateApprovalTime.getResultSet();
			if (rs.rowUpdated() == false) {
				throw new SQLException("couldn't update the time in menu" + Integer.toString(orderId));
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(e.getMessage());
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in approve order " + Integer.toString(orderId));
		response.setBody(null);

	}

	/**
	 * Create item
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void createItem(Item item, Response response) {
		ResultSet rs = null;
		int itemID;
		try {

			PreparedStatement postItem = EchoServer.con
					.prepareStatement("INSERT INTO items biteme.item (Category, SubCategory, Name, Price, Ingredients,"
							+ "RestaurantID, Image, Description)"
							+ " VALUES (?,?,?,?,?,?,?,?);SELECT last_insert_id();");
			postItem.setString(1, item.getCategory());
			postItem.setString(2, item.getSubcategory());
			postItem.setString(3, item.getName());
			postItem.setFloat(4, item.getPrice());
			postItem.setString(5, item.getIngrediants());
			postItem.setInt(6, item.getRestaurantID());
			postItem.setString(7, item.getPhoto());
			postItem.setString(8, item.getDescription());
			postItem.execute();
			rs = postItem.getResultSet();
			itemID = rs.getInt(1);

			PreparedStatement postOptions = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.optional_category (OptionalType, Specify, ItemID, price)" + " VALUES (?,?,?,?);");
			postOptions.setInt(3, itemID);

			for (Options temp : item.getOptions()) {
				try { // just in case
					postOptions.setString(1, temp.getOption_category());
					postOptions.setString(2, temp.getSpecify_option());
					postOptions.setDouble(4, temp.getPrice());
					postOptions.execute();
				} catch (SQLException e) {
				}
			}

		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				response.setCode(405);
				response.setDescription("Item already exist in the restaurant");
			}
			response.setCode(401);
			response.setDescription("Invalid input");
			return;
		}
		response.setCode(200);
		response.setDescription("Success in creating a new item -> itemID:" + Integer.toString(itemID));
	}

	/**
	 * Create menu
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void createMenu(Menu menu, Response response) {
		try {
			PreparedStatement postMenu = EchoServer.con
					.prepareStatement("INSERT INTO biteme.menu SET (RestaurantID, " + "MenuName) VALUES(?,?);");
			postMenu.setInt(1, menu.getRestaurantID());
			postMenu.setString(2, menu.getName());
			postMenu.execute();
			PreparedStatement postIIM = EchoServer.con.prepareStatement("INSERT INTO biteme.item_in_menu SET (ItemID, "
					+ "RestaurantID, MenuName, Course) VALUES(?,?,?,?);");
			postIIM.setInt(2, menu.getRestaurantID());
			postIIM.setString(3, menu.getName());
			for (item_in_menu temp : menu.getItems()) {
				try {
					postIIM.setInt(1, temp.getItemID());
					postIIM.setString(4, temp.getCourse());
					postIIM.execute();
				} catch (SQLException e) {}
			}
		} catch (SQLException e) {
			if(e.getErrorCode() == 1062) {
				response.setBody(null);
				response.setCode(400);
				response.setDescription("Menu name already exist for restaurant "+ menu.getName());
			}else {
				response.setBody(null);
				response.setCode(e.getErrorCode());
				response.setDescription(e.getMessage());
			}
			return;
		}
		response.setBody(null);
		response.setCode(200);
		response.setDescription("Success in creating menu " + menu.getName());
	}


	/**
	 * Edit menu
	 *
	 * This can only be done by the logged in supplier/moderator
	 *
	 */
	public static void editMenu(Menu oldMenu, Menu newMenu, Response response) {
		try {
			PreparedStatement deleteOldMenu = EchoServer.con.prepareStatement("DELETE FROM biteme.item_in_menu WHERE RestaurantID = ? AND MenuName = ?;");
			deleteOldMenu.setInt(1, oldMenu.getRestaurantID());
			deleteOldMenu.setString(2, oldMenu.getName());
			deleteOldMenu.execute();
		}catch(SQLException e) {}
		try {
			PreparedStatement deleteOldMenu = EchoServer.con.prepareStatement("DELETE FROM biteme.menu WHERE RestaurantID = ? AND MenuName = ?;");
			deleteOldMenu.setInt(1, oldMenu.getRestaurantID());
			deleteOldMenu.setString(2, oldMenu.getName());
			deleteOldMenu.execute();
		}catch(SQLException e) {
			response.setBody(null);
			response.setCode(400);
			response.setDescription("Old menu dosn't exist!");
			return;
		}
		if(newMenu != null)
			createMenu(newMenu, response);
	}


	/**
	 * Delete item
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void itemsDelete(int itemID, Response response) {
		try {
			PreparedStatement deleteItem = EchoServer.con
					.prepareStatement("DELETE FROM items biteme.item WHERE items.ItemID = ?;");
			deleteItem.setInt(1, itemID);
			// Its the first userName that he had so the test is in users table on login
			deleteItem.execute();
			deleteItem.getResultSet();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Fields are missing");
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in deleting an item -> itemID: " + itemID);
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
					"SELECT credit.AmountInCredit FROM credit biteme.credit WHERE credit.UserName = ? AND "
							+ "credit.RestaurantID = ?;");
			getCredit.setString(1, userName);
			getCredit.setInt(2, restaurantID);
			getCredit.execute();
			rs = getCredit.getResultSet();
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
		return;
	}
}