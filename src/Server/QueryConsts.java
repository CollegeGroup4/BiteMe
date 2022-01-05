package Server;

public class QueryConsts {
    private QueryConsts(){}
	//photos path
	public static final String FILE_PATH = "projectImages";
	public static final String FILE_PATH_ITEMS = "projectImages\\Items\\";
	public static final String FILE_PATH_RESTAURANTS = "projectImages\\Restaurants\\";
	public static final String FILE_PATH_REPORTS = "projectImages\\REPORTS\\";
	
	//for orders
    public static final int ORDER_ORDER_NUM = 1;
    public static final int ORDER_RESTAURANT_ID = 2;
    public static final int ORDER_RESTAURANT_NAME = 3;
    public static final int ORDER_USER_NAME = 4;
    public static final int ORDER_ORDER_TIME = 5;
    public static final int ORDER_PHONE_NUM = 6;
    public static final int ORDER_TYPE_OF_ORDER = 7;
    public static final int ORDER_DISCOUNT_FOR_EARLY_ORDER = 8;
    public static final int ORDER_CHECK_OUT_PRICE = 9;
    public static final int ORDER_IS_APPROVED = 10;
    public static final int ORDER_REQUIRED_TIME = 11;
    public static final int ORDER_APPROVED_TIME = 12;
    public static final int ORDER_HAS_ARRIVED = 13;
    
    //for items
    public static final int ITEM_ID = 1;
    public static final int ITEM_CATEGORY = 2;
    public static final int	ITEM_SUB_CATEGORY = 3;
    public static final int ITEM_NAME = 4;
    public static final int ITEM_PRICE = 5;
    public static final int ITEM_INGREDIENTS = 6;
    public static final int ITEM_RES_ID = 7;
    public static final int ITEM_IMAGE = 8;
    public static final int ITEM_DESCRIPTION = 9;
    		
    //for options
    public static final int OPTIONAL_TYPE = 1;
    public static final int OPTIONAL_SPECIFY = 2;
    public static final int OPTIONAL_ITEM_ID = 3;
    public static final int OPTIONAL_PRICE = 4;
    public static final int OPTIONAL_IS_DUPLICATABLE = 5;

    //for item_in_menu_in_order
    public static final int ITEM_IN_ORDER_ORDER_NUM = 1;
    public static final int ITEM_IN_ORDER_ITEM_ID = 2;
    public static final int ITEM_IN_ORDER_OPTIONAL_TYPE = 3;
    public static final int ITEM_IN_ORDER_OPTIONAL_SPECIFY = 4;
    public static final int ITEM_IN_ORDER_AMOUNT = 5;

    //for item_in_menu
    public static final int ITEM_IN_MENU_ITEM_ID = 1;
    public static final int ITEM_IN_MENU_RES_ID = 2;
    public static final int ITEM_IN_MENU_MENU_NAME = 3;
    public static final int ITEM_IN_MENU_COURSE = 4;

    //for private_account
    public static final int PRIVATE_ACCOUNT_USER_NAME = 1;
    public static final int PRIVATE_ACCOUNT_CREDIT_CARD_NUMBER = 2;
    public static final int PRIVATE_ACCOUNT_CREDIT_CARD_CVV = 3;
    public static final int PRIVATE_ACCOUNT_CREDIT_CARD_EXP = 4;
    public static final int PRIVATE_ACCOUNT_W4C = 5;

    //for business_account
    public static final int BUSINESS_ACCOUNT_USER_NAME = 1;
    public static final int BUSINESS_ACCOUNT_MONTHLY_BILLING_CEILING = 2;
    public static final int BUSINESS_ACCOUNT_IS_APPROVED = 3;
    public static final int BUSINESS_ACCOUNT_BUSINESS_NAME = 4;
    public static final int BUSINESS_ACCOUNT_CURRENT_SPENT = 5;
    public static final int BUSINESS_ACCOUNT_W4C = 6;

    //for accounts
    public static final int ACCOUNT_USER_ID = 1;
    public static final int ACCOUNT_USER_NAME = 2;
    public static final int ACCOUNT_PASSWORD = 3;
    public static final int ACCOUNT_FIRST_NAME = 4;
    public static final int ACCOUNT_LAST_NAME = 5;
    public static final int ACCOUNT_PHONE = 6;
    public static final int ACCOUNT_EMAIL = 7;
    public static final int ACCOUNT_ROLE = 8;
    public static final int ACCOUNT_STATUS = 9;
    public static final int ACCOUNT_BRANCH_MANAGER_ID = 10;
    public static final int ACCOUNT_AREA = 11;
    public static final int ACCOUNT_IS_LOGGED_IN = 12;
    public static final int ACCOUNT_DEBT = 13;
    public static final int ACCOUNT_IS_BUSINESS = 14;

    //for restaurant
    public static final int RESTAURANT_ID = 1;
    public static final int RESTAURANT_NAME = 2;
    public static final int RESTAURANT_IS_APPROVED = 3;
    public static final int RESTAURANT_BRANCH_MANAGER_ID = 4;
    public static final int RESTAURANT_AREA = 5;
    public static final int RESTAURANT_PHOTO = 6;
    public static final int RESTAURANT_USER_NAME = 7;
    public static final int RESTAURANT_TYPE = 8;
    public static final int RESTAURANT_ADDRESS = 9;
    public static final int RESTAURANT_DESCRIPTION = 10;

    //for Categories
    public static final int CATEGORY_CATEGORY = 1;
    public static final int CATEGORY_SUB_CATEGORY = 2;

    //for shipment
    public static final int SHIPMENT_ID = 1;
    public static final int SHIPMENT_WORK_PLACE = 2;
    public static final int SHIPMENT_ADDRESS = 3;
    public static final int SHIPMENT_RECIEVER_NAME = 4;
    public static final int SHIPMENT_RECIEVER_PHONE_NUMBER = 5;
    public static final int SHIPMENT_RECIEVER_DELIVERY_TYPE = 6;
    
    //for employer
    public static final int EMPLOYER_BUSINESS_NAME = 1;
    public static final int EMPLOYER_IS_APPROVED= 2;
    public static final int EMPLOYER_HR_NAME = 3;
    public static final int EMPLOYER_HR_USER_NAME = 4;
    public static final int EMPLOYER_BRANCH_MANAGER_ID = 5;
    
    public static String firstName;
    public static String lastName;
    public static String email;
    public static String role;
    public static String phone;
    public static String orderDate;
    public static String orderID;
    public static String checkOutPrice;
    
    public static String items;
    public static String itemCol;
    public static String optionCol;
    
    public static String item = "<td class=\"m_1113311250331273147amount-field m_1113311250331273147last-column\">"+QueryConsts.itemCol+"</td>\r\n";
    public static String option = "<td></td><td></td><td></td> <td class=\"m_1113311250331273147wrapword\">"+QueryConsts.optionCol+"</td>\r\n";
    
    
    public static String INVOICE_HEADER= "<div>\r\n"
			+ "<div class=\"m_1113311250331273147receipt-ctn-wrapper\">\r\n"
			+ "    <div style=\"text-align:center;line-height:24px\">\r\n"
			+ "    		<div style=\"align:center;line-height:24px\">\r\n"
			+ "                <span style=\"font-size:35px;line-height:40px\"><img src=\"https://i.ibb.co/z7sTJhT/BiteMe.png\" style=\"background-color:orange\"></span><br>\r\n"
			+ "                <span style=\"font-size:35px;line-height:40px\"><strong>Thank You.<br><br><br></strong></span><br>\r\n"
			+ "\r\n"
			+ "        <div class=\"m_1113311250331273147receipt-body\">\r\n"
			+ "            <div style=\"text-align:center;line-height:14px\">&nbsp;</div>\r\n"
			+ "            <div style=\"text-align:center;line-height:24px\">\r\n"
			+ "                <span style=\"font-size:18px;font-weight:bold\">\r\n"
			+ "                    Hi "+QueryConsts.firstName+"!\r\n"
			+ "                </span>\r\n"
			+ "                <br>\r\n"
			+ "                Thanks for your purchase from <strong>BiteMe</strong><br><br>\r\n"
			+ "                <span style=\"font-size:35px;line-height:40px\"><strong>INVOICE ID: <br>"+QueryConsts.orderID+"</strong></span><br>\r\n"
			+ "\r\n"
			+ "                <span style=\"font-size:14px;color:#b2b2b2;line-height:40px\">( Please keep a copy of this receipt for your records. )</span><br><br><br>\r\n"
			+ "            </div>\r\n"
			+ "            <div style=\"font-family:arial,helvetica,sans-serif;font-size:14px;color:#b2b2b2;text-align:left\">\r\n"
			+ "                <strong>YOUR ORDER INFORMATION:</strong>\r\n"
			+ "            </div>\r\n"
			+ "            <table class=\"m_1113311250331273147order-info\">\r\n"
			+ "                <tbody><tr>\r\n"
			+ "                    <th style=\"height:1px;min-width:12px\"></th>\r\n"
			+ "                    <th style=\"height:1px;min-width:12px\"></th>\r\n"
			+ "                </tr>\r\n"
			+ "                <tr>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>First Name:</strong></td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>Last Name:</strong></td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top\"><strong>Bill To:</strong></td>\r\n"
			+ "                </tr>\r\n"
			+ "                <tr>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword m_1113311250331273147order-info-value\">"+firstName+"</td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword m_1113311250331273147order-info-value\">"+lastName+"</td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword m_1113311250331273147order-info-value m_1113311250331273147email\"><a href=\"mailto:fghghf98@gmail.com\" target=\"_blank\">"+email+"</a></td>\r\n"
			+ "                </tr>\r\n"
			+ "                <tr>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>Role:</strong></td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>Phone:</strong></td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top\"><strong>Order Date:</strong></td>\r\n"
			+ "                </tr>\r\n"
			+ "                <tr>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword m_1113311250331273147order-info-value\">"+role+"</td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword m_1113311250331273147order-info-value\">"+phone+"</td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword m_1113311250331273147order-info-value\">"+orderDate+"</td>\r\n"
			+ "                </tr>\r\n"
			+ "            </tbody></table>\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "            <div style=\"font-family:arial,helvetica,sans-serif;font-size:14px;color:#b2b2b2;text-align:left;margin-top:10px\">\r\n"
			+ "                <strong>HERE'S WHAT YOU ORDERED:</strong>\r\n"
			+ "            </div>\r\n"
			+ "            <table class=\"m_1113311250331273147order-item\">\r\n"
			+ "                    <th style=\"height:1px;min-width:12px\"></th>\r\n"
			+ "                    <th style=\"height:1px;min-width:12px\"></th>\r\n"
			+ "                <tbody><tr>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>Item Name:</strong></td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>Item Category:</strong></td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>Restaurant Name:</strong></td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>Options</strong></td>\r\n"
			+ "                    <td class=\"m_1113311250331273147wrapword\" style=\"vertical-align:top;min-width:120px\"><strong>Quantity:</strong></td>\r\n"
			+ "					<tr>\r\n"
			+ "						"+QueryConsts.items+""	
			+ "                </tr>\r\n"
			+ "            </tbody></table>\r\n"
			+ "            <table class=\"m_1113311250331273147payment-info\">\r\n"
			+ "                <tbody><tr>\r\n"
			+ "                    <th></th>\r\n"
			+ "                    <th style=\"width:1%\"></th>\r\n"
			+ "                </tr>\r\n"
			+ "                <tr>\r\n"
			+ "                    <td style=\"font-family:Ariel,Helvetica,sans-serif;font-weight:bold;text-transform:uppercase;font-size:14px;color:#b2b2b2;text-align:left;line-height:26px\">TOTAL: <span style=\"color:#313131\" class=\"m_1113311250331273147email\"> [USD]: $ "+checkOutPrice+"</span></td>\r\n"
			+ "                <tr>\r\n"
			+ "                    <td style=\"font-family:Ariel,Helvetica,sans-serif;font-size:14px;color:#313131;text-align:center;line-height:26px\" colspan=\"2\">\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "                    </td>\r\n"
			+ "                </tr>\r\n"
			+ "            </tbody></table>\r\n"
			+ "\r\n"
			+ "            <div style=\"font-family:arial,helvetica,sans-serif;font-size:14px;color:#b2b2b2;text-align:left\">\r\n"
			+ " <tr>\r\n"
			+ "                    <th style=\"height:1px;width:50%\"></th>\r\n"
			+ "                    <th style=\"height:1px;width:50%\"></th>\r\n"
			+ "                </tr>\r\n"
			+ "                <strong>PAYMENT DETAILS:</strong>\r\n"
			+ "            </div>\r\n"
			+ "            <table class=\"m_1113311250331273147order-info\">\r\n"
			+ "                <tbody><tr>\r\n"
			+ "                    <th style=\"height:2px;width:60%\"></th>\r\n"
			+ "                    <th style=\"height:2px;width:60%\"></th>\r\n"
			+ "                </tr>\r\n"
			+ "                <tr>\r\n"
			+ "                </tr>\r\n"
			+ "            </tbody></table>\r\n"
			+ "                <tbody><tr>\r\n"
			+ "                    <th></th>\r\n"
			+ "                </tr>\r\n"
			+ "                <tr>\r\n";

}
