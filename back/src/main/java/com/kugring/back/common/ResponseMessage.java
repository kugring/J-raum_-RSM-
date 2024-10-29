package com.kugring.back.common;

public interface ResponseMessage {

  String SUCCESS = "Success.";

  String NOT_EXISTED_USER = "This user does not exist.";
  String NOT_EXISTED_MANAGER = "This manager does not exists.";
  String NOT_EXISTED_OPTION = "This option does not exists.";
  String NOT_EXISTED_MENU = "This menu does not exists.";
  String NOT_EXISTED_ORDER = "This order does not exists.";
  String NOT_EXISTED_ORDER_STATUS = "This order_status does not exists.";
  String NOT_EXISTED_ORDER_ITEM = "This order_item does not exists.";
  String NOT_EXISTED_ORDER_ITEM_OPTION = "This order_item_option does not exists.";

  String VALIDATION_FAIL = "Validation failed.";
  String DUPLICATE_ID = "Duplicate Id.";

  String SIGN_IN_FAIL = "Login information mismatch.";
  String CERTIFICATION_FAIL = "Certification failed.";

  String MAIL_FAIL = "Mail send failed.";
  String DATABASE_ERROR = "Database error.";

  String PIN_CHECK_FAIL = "Pin check failed.";

  String CANCEL_POINT_CHARGE_FAIL = "Cancel point charge failed.";
  String DELETE_POINT_CHARGE_FAIL = "Delete point charge failed.";
  String POINT_CHARGE_FAIL = "Point charge failed.";
  String ALREADY_POINT_CHARGE = "Already charge point.";

  String ORDER_FAIL = "Order failed.";
  String INSUFFICIENT_BALANCE = "Insufficient balance. Please recharge your account.";

  String REFUND_FAIL = "Refund failed";

}
