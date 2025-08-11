package models

case class SaleData(
  ORDERNUMBER: Option[Int],
  QUANTITYORDERED: Option[Int],
  PRICEEACH: Option[Double],
  ORDERLINENUMBER: Option[Int],
  SALES: Option[Double],
  ORDERDATE: Option[String],
  STATUS: Option[String],
  QTR_ID: Option[Int],
  MONTH_ID: Option[Int],
  YEAR_ID: Option[Int],
  PRODUCTLINE: Option[String],
  MSRP: Option[Int],
  PRODUCTCODE: Option[String],
  CUSTOMERNAME: Option[String],
  PHONE: Option[String],
  ADDRESSLINE1: Option[String],
  ADDRESSLINE2: Option[String],
  CITY: Option[String],
  STATE: Option[String],
  POSTALCODE: Option[String],
  COUNTRY: Option[String],
  TERRITORY: Option[String],
  CONTACTLASTNAME: Option[String],
  CONTACTFIRSTNAME: Option[String],
  DEALSIZE: Option[String]
)
