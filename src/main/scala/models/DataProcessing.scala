package models

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions._

class DataProcessing(spark: SparkSession) {
  // first i put spark session here once an object is created but this will create a pb cuz each time we have new object we will 
  // create a new session so instead we ill create one session in the object and pass it as param for the class

  def read_csv(path: String): Dataset[SaleData] = {   
    // Read the CSV file into a DataFrame
    val df = spark.read
      .option("header", "true") // "true" cuz the CSV has a header row
      .option("inferSchema", "true") // detect auto the types of values in columns
      .csv(path) 

    // always specify schema when reading files (parquet, json or csv) into a DataFrame, so we'll use saledata model for the validation
    println(s"size of the data): ${df.count()} rows")
    import spark.implicits._  // for .as[T]
    val ds = df.as[SaleData]  
    println(s"size after model validation : ${ds.count()} rows")
    // ds.show() // evaluate as little as possible, avoid calling an action on your Dataset unless it is necessary. 
    // from best spark best practices.
    ds // ds is a dataset so the items are objects of the case class saledata, (scala types)
  }

  def count_na(df: Dataset[SaleData], save_data: Boolean = false): Unit = {
    // Count null values for each column
    val nullCounts = df.columns.map { colName =>
      colName -> df.filter(col(colName).isNull).count()
    }.toMap

    import spark.implicits._  // Needed for .toDF
    val nullCountsDF = nullCounts.toSeq.toDF("column", "null_count")

    if (save_data) {
      save_to_csv(nullCountsDF, "output/Missing_values_col")
    }
  }

  def drop_na(df: Dataset[SaleData], save_data:Boolean = false): Dataset[SaleData] = {
    // Drop rows with any NULL values
    import spark.implicits._  
    val df_clean = df.na.drop().as[SaleData]
    println(s"size after droping rows with missing values : ${df_clean.count()} rows")
    
    if (save_data) {
      save_to_csv(df_clean, "output/clean_data")
    }
    df_clean
  }

  def get_total_sales_category(df: Dataset[SaleData], save_data: Boolean = false): DataFrame =  {
    
    // group values by product type and calculate the sum of the revenues 
    val salesByCategory = df.groupBy("PRODUCTLINE")
      .agg(sum("SALES").alias("total_sales"))
      .orderBy(desc("total_sales"))

    if (save_data) {
      save_to_csv(salesByCategory, "output/sales_by_category")
    }
    salesByCategory
  }

  def get_top5_products(df: Dataset[SaleData], save_data: Boolean = false): DataFrame =  {
    
    // use the previous fct to get data grouped by products
    val salesByCategory = this.get_total_sales_category(df)
    val top5OrderLines = salesByCategory.limit(5)
    // top5OrderLines.show()
    if (save_data) {
      save_to_csv(top5OrderLines, "output/top_5_OrderLines")
    }
    top5OrderLines
  }

  def get_avg_sales_month(df: Dataset[SaleData], save_data: Boolean = false): DataFrame =  {
    // group data by month and sum the revenus of each month
    val AvgSalesMonth = df.groupBy("MONTH_ID")
        .agg(avg("SALES").alias("avg_sales"))
        .orderBy(desc("avg_sales")) 

    if (save_data) {
      save_to_csv(AvgSalesMonth, "output/avg_sales_moth")
    }
    AvgSalesMonth
  }

  def save_to_csv(df: Dataset[_], path: String): Unit = {
    df.coalesce(1) // to have a one csv file with one partition (not practical for big datasets)
      .write
      .mode("overwrite")
      .option("header", "true")
      .csv(path)
  }


}




