package app
import models.DataProcessing
import models.SaleData

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SparkSession
import org.scalatest.funsuite.AnyFunSuite
import com.univocity.parsers.common.DataProcessingException
import org.scalatest.BeforeAndAfterAll


class TestProcessor extends AnyFunSuite with BeforeAndAfterAll {
  // create spark session 
  val spark: SparkSession = SparkSession.builder()
    .appName("UnitTest")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._
  // override def beforeAll(): Unit = {
  //   super.beforeAll()
  // }

  // cerate data with to test the different functions
  val dataSample = Seq(
    SaleData(Some(10107), Some(30), Some(95.7), Some(2), Some(2871.0), Some("2/24/2003 0:00"), Some("Shipped"), Some(1), Some(2), Some(2003), Some("Motorcycles"), Some(95), Some("S10_1678"), Some("Land of Toys Inc."), Some("2125557818"), Some("897 Long Airport Avenue"), None, Some("NYC"), Some("NY"), Some("10022"), Some("USA"), Some("NA"), Some("Yu"), Some("Kwai"), Some("Small")),

    SaleData(Some(10121), Some(34), None, Some(5), Some(2765.9), Some("5/7/2003 0:00"), Some("Shipped"), Some(2), Some(5), Some(2003), Some("Motorcycles"), Some(95), Some("S10_1678"), Some("Reims Collectables"), Some("26.47.1555"), Some("59 rue de l'Abbaye"), None, Some("Reims"), None, Some("51100"), Some("France"), Some("EMEA"), Some("Henriot"), Some("Paul"), Some("Small")),

    SaleData(Some(10134), Some(41), Some(94.74), Some(2), Some(2300.0), Some("7/1/2003 0:00"), Some("Shipped"), Some(3), Some(7), Some(2003), Some("Classic Cars"), Some(95), Some("S10_1678"), Some("Lyon Souveniers"), Some("+33 1 46 62 7555"), Some("27 rue du Colonel Pierre Avia"), None, Some("Paris"), None, Some("75508"), Some("France"), Some("EMEA"), Some("Da Cunha"), Some("Daniel"), Some("Medium")),

    SaleData(Some(10145), Some(45), Some(83.26), Some(6), Some(3746.7), Some("8/25/2003 0:00"), Some("Shipped"), Some(3), Some(8), Some(2003), Some("Classic Cars"), Some(95), Some("S10_1678"), Some("Toys4GrownUps.com"), Some("6265557265"), Some("78934 Hillside Dr."), None, Some("Pasadena"), Some("CA"), Some("90003"), Some("USA"), Some("NA"), Some("Young"), Some("Julie"), Some("Medium")),

    SaleData(Some(10150), Some(50), Some(90.0), Some(1), Some(4500.0), Some("9/5/2003 0:00"), Some("Shipped"), Some(3), Some(9), Some(2003), Some("Motorcycles"), Some(95), Some("S10_1678"), Some("New Customer"), Some("1234567890"), Some("123 Some St"), None, Some("City"), Some("ST"), Some("12345"), Some("USA"), Some("NA"), Some("Smith"), Some("John"), Some("Large"))
  )

  // convert the data to dataset 
  val test_data: Dataset[SaleData] = spark.createDataset(dataSample)
  // create a data processing instance to test the functions
  val data_processor = new DataProcessing(spark)
  val df_clean = data_processor.drop_na(test_data)

  // perform the first test for drop na values
  test("drop_na should remove rows containing nulls in any column") {
  assert(df_clean.count() == 0)
  }

  val df_out = data_processor.get_total_sales_category(test_data)
  val df_expected = Seq(
    ("Motorcycles", 10136.9),
    ("Classic Cars", 6046.7)
  ).toDF("PRODUCTLINE", "total_sales")
  val expectedCollect = df_expected.collect()
  val actualCollect = df_out.collect()
  // peroform the second test for group data by product and sul the revenues for each one
  test("get_total_sales_category should group data by category and calculate the total sales per prod") {
  assert(expectedCollect.sameElements(actualCollect), "DataFrames are not equal!")
  }
  override def afterAll(): Unit = {
    if (spark != null) {
      spark.stop()
    }
    super.afterAll()
  }

}

