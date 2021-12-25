package Server;

public class QueryConsts {
    private QueryConsts(){}
	//photos path
	public static final String LOCAL_FILE_PATH = "d:\\projectImages\\";
	
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

}
