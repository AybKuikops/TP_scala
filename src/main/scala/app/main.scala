import models.DataProcessing

import org.apache.spark.sql.SparkSession


object ProcessData {
  def main(args: Array[String]): Unit = {  // part of args is important for compilation otherwise u will have an error "no mian class detected"
    // create a spark session for the dataprocessor instance
    val spark = SparkSession.builder()
      .appName("Clean CSV")
      .master("local[*]")
      .getOrCreate()

    val path: String = "./data/sales_data.csv"

    val data_processor = new DataProcessing(spark)
    val df = data_processor.read_csv(path)
    // data_processor.count_na(df)   
    // data_processor.drop_na(df, save_data=true)
    data_processor.get_total_sales_category(df, save_data=true)
    data_processor.get_top5_products(df, save_data=true)
    // data_processor.get_avg_sales_month(df, save_data=true)

    spark.stop()
    }
}



