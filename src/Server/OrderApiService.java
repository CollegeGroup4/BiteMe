package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.*;
/**
 * BiteMe
 *
 * <p>No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 */
public class OrderApiService{
    /**
     * Add a new order
     *
     */
    public static void addOrder(Order order, Response response) {
        ResultSet rs
        try {
            PreparedStatement postOrder = EchoServer.con.prepareStatement(
                    "INSERT INTO biteme.order (ResturantID, ResturantName,OrderTime, PhoneNumber, TypeOfOrder, Discount_for_early_order," +
                            "Check_out_price, isBusiness, required_time)"
                            + " VALUES (?,?,?,?,?,?,?,?);SELECT last_insert_id();");
            postOrder.setInt(1, order.getRestaurantID());
            postOrder.setString(2, order.getRestaurantName());
            postOrder.setString(3, order.getTime_taken());
            postOrder.setString(4, order.getPhone());
            postOrder.setString(5, order.getType_of_order());
            postOrder.setInt(6, order.getDiscount_for_early_order());
            postOrder.setFloat(7, order.getCheck_out_price());
            postOrder.setBoolean(8, order.isBuisness());
            postOrder.setString(9, order.getRequired_time());
            postOrder.execute();
            rs = postOrder.getResultSet();
        }catch (SQLException e) {
            response.setCode(405);
            response.setDescription("Invalid input");
            return;
        }
            try{
            PreparedStatement postItem = EchoServer.con.prepareStatement(
                    "INSERT INTO biteme.order (OrderNum, ItemID, Item_name, OptionalType, OptionalSpecify" +
                            "amount)"
                            + " VALUES (?,?,?,?,?,?);");
            postItem.setInt(1, rs.getInt(1));
            postItem.setInt(6, 1);
            for6(I1der.getItems()) {
                postItem.setString();
                postItem.setString();
                postItem.setString();
                postItem.setString();
                postItem.setString();
                postItem.setString();
            }
        } catch (SQLException e) {
//            if (e.getErrorCode() == 1062){
//                response.setCode(404);
//                response.setDescription("Order is already exist");
//            }

        }
        response.setCode(200);
        response.setDescription("Order was added");
    }
    
    /**
     * Return all the orders
     *
     */
    public static void allOrders(String restaurantID, Response response) {
        PreparedStatement stmt;
        ArrayList<Order> orders = new ArrayList<>();
        Order order = null;
        try{
            stmt = EchoServer.con.prepareStatement("SELECT * FROM biteme.order WHERE RestaurantID = ?");
            stmt.setString(1, restaurantID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                order = new Order(rs.getInt(finals.ORDER_NUM), rs.getInt(finals.RESTAURANT_ID), rs.getString(finals.ORDER_TIME),
                        rs.getFloat(finals.CHECK_OUT_PRICE), rs.getString(finals.REQUIRED_TIME), rs.getString(finals.TYPE_OF_ORDER),
                        rs.getInt(finals.ACCOUNT_ID), rs.getString(finals.PHONE_NUM), rs.getInt(finals.DISCOUNT_FOR_EARLY_ORDER),
                        rs.getBoolean(finals.IS_BUISNESS), null);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setCode(200);
        response.setDescription("Success in fetching orders");
        response.setBody(orders.toArray());
    }
    
    /**
     * Deletes an order
     *
     */
    public static void deleteOrder(Integer orderId, Response response) {
        try {
            PreparedStatement postOrder = EchoServer.con.prepareStatement("DELETE FROM biteme.order WHERE OrderID = ?;");
            postOrder.setInt(1, orderId);
            postOrder.execute();
        } catch (SQLException e) {
            response.setCode(404);
            response.setDescription("Order not found");
            return;
        }
        response.setCode(200);
        response.setDescription("Success in deleting order: " + orderId.toString());
    }
    
    /**
     * Find order by ID
     *
     * Returns a single order
     *
     */
    public static Order getOrderById(Integer orderId, Response response) {
        PreparedStatement stmt;
        Order order = null;
        try {
            stmt = EchoServer.con.prepareStatement("SELECT * FROM biteme.order WHERE OrderNum = ?");
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                order = new Order(rs.getInt(finals.ORDER_NUM), rs.getInt(finals.RESTAURANT_ID), rs.getString(finals.ORDER_TIME),
                        rs.getFloat(finals.CHECK_OUT_PRICE), rs.getString(finals.REQUIRED_TIME), rs.getString(finals.TYPE_OF_ORDER),
                        rs.getInt(finals.ACCOUNT_ID), rs.getString(finals.PHONE_NUM), rs.getInt(finals.DISCOUNT_FOR_EARLY_ORDER),
                        rs.getBoolean(finals.IS_BUISNESS), null);
            }
        } catch (SQLException e) {
            response.setCode(404);
            response.setDescription("Order not found");
            response.setBody(null);
        }
        response.setCode(200);
        response.setDescription("Success in fetching order" + orderId);
        response.setBody(order);
    }
    
    /**
     * Get payment approval for monthly budget
     *
     */
    public static void getPaymentApproval(Integer accountID, Response response) {
        // TODO: Implement...
        
        
    }
    
    /**
     * Get resturants for the specific
     *
     */
    public static List<Resturant> getResturants(String area, Response response) {
        // TODO: Implement...
        
        return null;
    }
    
    /**
     * Updates a order in the DB with form data
     *
     */
    public static void updateOrderWithForm(Order body, Response response) {
        // TODO: Implement...
        
        
    }
    
    /**
     * Updates a order in the DB with form data
     *
     */
    public static void updateOrderWithForm(Long orderId, String address, String delivery, Response response) {
        // TODO: Implement...
        
        
    }
    
}

