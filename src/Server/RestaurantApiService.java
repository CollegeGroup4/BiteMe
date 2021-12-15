package Server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import logic.Category;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import common.MyPhoto;
import logic.Item;
import logic.Menu;
import logic.MyFile;
import logic.Options;
import logic.Restaurant;

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
    @SuppressWarnings("resource")
	public static void getRestaurants(String area, Response response, String type) {
    		PreparedStatement stmt;
    		ArrayList<Restaurant> restaurants = new ArrayList<>();
    		ArrayList<MyPhoto> photos = new ArrayList<>();
    		Restaurant restaurant = null;
    		try {
    			stmt = EchoServer.con.prepareStatement("SELECT * FROM restaurant biteme.restaurant WHERE restaurant.IsApproved = 1" +area
    					+ type + ";");
    			ResultSet rs = stmt.executeQuery();
    			while (rs.next()) {
    				restaurant = new Restaurant(rs.getInt(finals.RESTAURANT_ID), rs.getBoolean(finals.RESTAURANT_IS_APPROVED),
    						rs.getInt(finals.RESTAURANT_BRANCH_MANAGER_ID), rs.getString(finals.RESTAURANT_NAME),
    						rs.getString(finals.RESTAURANT_AREA), rs.getString(finals.RESTAURANT_TYPE),
    						rs.getInt(finals.RESTAURANT_USER_ID), rs.getString(finals.RESTAURANT_PHOTO));
    				restaurants.add(restaurant);
    				MyPhoto msg= new MyPhoto(Integer.toString(restaurant.getID())); //"diagnosisE.jpg"
    				String path = finals.LocalfilePath + Integer.toString(restaurant.getID());
    				  try{
    					      File newFile = new File (path);		      		      
    					      byte [] mybytearray  = new byte [(int)newFile.length()];
    					      FileInputStream fis = new FileInputStream(newFile);
    					      BufferedInputStream bis = new BufferedInputStream(fis);			    					      
    					      msg.initArray(mybytearray.length);
    					      msg.setSize(mybytearray.length);  					      
    					      bis.read(msg.getMybytearray(),0,mybytearray.length);		      
    					    }
    					catch (Exception e) {
    						System.out.println("Error send (Files)msg) to Server");
    					}
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		response.setCode(200);
    		response.setDescription("Success in fetching orders");
    		Gson gson = new Gson();
    		JsonElement v = gson.toJsonTree(restaurants);
    		JsonElement pics = gson.toJsonTree(photos);
    		JsonElement j = gson.toJsonTree(new Object());
    		j.getAsJsonObject().add("restaurants", v);
    		j.getAsJsonObject().add("image",pics);
    		response.setBody(gson.toJson(j));
    }
    
    public static void getAllRestaurants(String area, Response response, String type) {
    	getRestaurants("and restaurant.Area = " +area, response, "");
    }
    
    /**
     * Get restaurants for the specific location
     *
     */
    public static void getRestaurantsByType(String area, String type, Response response) {
    	getRestaurants("and restaurant.Area = " +area, response, "and restaurant.Type = " +type);
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
		HashMap<String, String> cat = new HashMap<>();
		Category category = null;
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM categories biteme.item_category WHERE categories.restaurantNum = ?;");
			stmt.setInt(1, restaurantID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cat.put(rs.getString(1), rs.getString(2));
			}
			for (String category2 : cat.keySet()) {
				
				for (Category category3 : cat.g) {
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setCode(200);
		response.setDescription("Success in fetching orders");
		
        
    }
	/**
     * Getting list of all related items
     *
     * This can only be done by the logged in supplier.
     *
     */
	public static void allCategoryItems(String menuName, String itemCategory, Long resturantID, Response response) {
        // TODO: Implement...
        
    }
	/**
     * Getting list of all related items
     *
     * This can only be done by the logged in supplier.
     *
     */
    public static void allSubCategoryItems(String menuName, String itemCategory, String itemSubCategory, Long resturantID) {
        // TODO: Implement...
        
    }
	/**
	 * Getting list of all related items
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void allItems(String menuName, Long resturantID, Response response) {
		// TODO: Implement...

	}

	/**
	 * Get all menus
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void allMenues(int restaurantID, Response response) {
		ResultSet rs1, rs2, rs3;
		ArrayList<Menu> menus = new ArrayList<>();
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<Options> options = new ArrayList<>();
		Menu mtemp;
		Item itemp;
		Options otemp;

		try {
			PreparedStatement getMenus = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.menu WHERE RestaurantID = ?");
			getMenus.setInt(1, restaurantID);
			getMenus.execute();
			rs1 = getMenus.getResultSet();

			while (rs1.next()) {
				mtemp = new Menu(rs1.getString(2), restaurantID);

				PreparedStatement getItems = EchoServer.con
						.prepareStatement("SELECT I.* FROM biteme.item_in_menu IIM, biteme.item I WHERE "
								+ "IIM.RestaurantID = ? AND IIM.MenuName = ? AND IIM.ItemID = I.ItemID"
								+ "AND IIM.RestaurantID = I.RestaurantID");
				getItems.setInt(1, restaurantID);
				getItems.setString(2, mtemp.getName());
				getItems.execute();
				rs2 = getItems.getResultSet();

				while (rs2.next()) {
					itemp = new Item(rs2.getInt(finals.ITEM_ID), restaurantID, rs2.getString(finals.ITEM_TYPE),
							rs2.getString(finals.ITEM_NAME), rs2.getFloat(finals.ITEM_PRICE),
							rs2.getString(finals.ITEM_DESCRIPTION), rs2.getString(finals.ITEM_INGRIDIENTS), null,
							rs2.getBytes(finals.ITEM_IMAGE));

					// get optional for each item

					PreparedStatement getOptions = EchoServer.con
							.prepareStatement("SELECT * FROM biteme.optional_category WHERE itemID = ?");
					getOptions.setInt(1, itemp.getItemID());
					getOptions.execute();
					rs3 = getOptions.getResultSet();

					while (rs3.next()) {
						otemp = new Options(rs3.getString(finals.OPTIONAL_TYPE), rs3.getString(finals.OPTIONAL_SPECIFY),
								itemp.getItemID());

						options.add(otemp);
					}

					// from ArrayList<T> to T[]

					itemp.setOptions(options.toArray(new Options[0]));

					items.add(itemp);
					
					options.clear();
				}
				mtemp.setItems(items.toArray(new Item[0]));
				
				menus.add(mtemp);
			
			}

		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Invalid input");
			return;
		}

//		// if we don't have menus
//
//		if (menus.size() == 0)
//			return;
//
//		// get for each menu the items
//
//		for (Menu menu : menus) {
//			try {
//				PreparedStatement getItems = EchoServer.con
//						.prepareStatement("SELECT I.* FROM biteme.item_in_menu IIM, biteme.item I WHERE "
//								+ "IIM.RestaurantID = ? AND IIM.MenuName = ? AND IIM.ItemID = I.ItemID"
//								+ "AND IIM.RestaurantID = I.RestaurantID");
//				getItems.setInt(1, restaurantID);
//				getItems.setString(2, menu.getName());
//				getItems.execute();
//				rs1 = getItems.getResultSet();
//
//				while (rs1.next()) {
//					itemp = new Item(rs1.getInt(finals.ITEM_ID), restaurantID, rs1.getString(finals.ITEM_TYPE),
//							rs1.getString(finals.ITEM_NAME), rs1.getFloat(finals.ITEM_PRICE),
//							rs1.getString(finals.ITEM_DESCRIPTION), rs1.getString(finals.ITEM_INGRIDIENTS), null,
//							rs1.getBytes(finals.ITEM_IMAGE));
//
//					// get optional for each item
//
//					PreparedStatement getOptions = EchoServer.con
//							.prepareStatement("SELECT * FROM biteme.optional_category WHERE itemID = ?");
//					getOptions.setInt(1, itemp.getItemID());
//					getOptions.execute();
//					rs2 = getOptions.getResultSet();
//
//					while (rs2.next()) {
//						otemp = new Options(rs2.getString(finals.OPTIONAL_TYPE), rs2.getString(finals.OPTIONAL_SPECIFY),
//								itemp.getItemID());
//
//						options.add(otemp);
//					}
//
//					// from ArrayList<T> to T[]
//
//					itemp.setOptions(options.toArray(new Options[0]));
//
//					items.add(itemp);
//				}
//
//			} catch (SQLException e) {
////			response.setCode(405);
////			response.setDescription("Invalid input");
//				return;
//			}
//		}

	}

	/**
	 * Approve order
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void approveOrder(Long orderId) {
		// TODO: Implement...

	}

	/**
	 * Create item
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void createItem(Item body) {
		// TODO: Implement...

	}

	/**
	 * Create menu
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void createMenu(NewMenu body) {
		// TODO: Implement...

	}

	/**
	 * Edit menu
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void editMenu(NewMenu body) {
		// TODO: Implement...

	}

	/**
	 * Delete item
	 *
	 * This can only be done by the logged in supplier.
	 *
	 */
	public static void suppliersItemsDelete(String itemName) {
		// TODO: Implement...

	}

}
