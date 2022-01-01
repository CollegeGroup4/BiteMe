package logic;


public enum typeOfOrder {
		ORDER_IN("order-in"), TAKE_OUT("take-out"), DELIVERY("delivery");

		private String value;

		private typeOfOrder(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
}
