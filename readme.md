# Sales Data Processing in Scala with Apache Spark

## Description

This project is a data processing pipeline developed in Scala with Apache Spark. It allows cleaning and analyzing a sales transactions dataset to extract useful insights for the business.

The program performs the following tasks:

1. Reads a CSV file `sale_data.csv`  
2. Cleans the data (removes null or missing values)  
3. Calculates total revenue by product category  
4. Identifies the top 5 products generating the highest revenue  
5. Calculates average revenue per month  
6. Saves the results into separate CSV files  

## Technologies Used

The project uses the following technologies and tools:

- **Scala**: Main language for development  
- **Apache Spark**: Distributed data processing framework  
- **SBT (Scala Build Tool)**: Project management and build tool  
- **WSL (Ubuntu)**: Linux runtime environment via Windows  
- **Visual Studio Code**: Code editor used for development  

## Project Structure

Here is the organization of the project files and folders:

```
│
├── data/ # Input CSV files (e.g., sale_data.csv)
├── output/ # Processing results (output CSV files)
│ ├── avg_sales_month/ # Monthly average revenue
│ ├── clean_data/ # Cleaned data
│ ├── Missing_values_col/ # Columns with missing values
│ ├── sales_by_category/ # Revenue by category
│ └── top_5_OrderLines/ # Top 5 products by revenue
│
├── project/ # SBT configuration files
├── src/
│ ├── main/scala/
│ │ ├── app/
│ │ │ └── main.scala # Program entry point
│ │ ├── models/
│ │ │ ├── DataProcessing.scala # Data processing logic
│ │ │ └── SaleData.scala # Model definitions (case classes)
│ │ └── resources/
│ │ └── log4j.properties # Spark logging configuration
│ └── test/scala/app/
│ └── TestProcessor.scala # Unit tests
│
├── build.sbt # SBT build configuration file
└── readme.md # Project documentation

```

## Usage

### Run the Program

To execute the data processing pipeline:

```bash
sbt run
```

By default, the application will:

- **Read** the input file from:  
  `data/sale_data.csv`
- **Perform the following operations:**
  - Remove rows with null or missing values
  - Calculate total revenue by product category
  - Identify the top 5 products generating the highest revenue
  - Compute average revenue per month
- **Save** the results into the `output/` directory:
  - `output/clean_data/` — cleaned dataset
  - `output/sales_by_category/` — revenue by category
  - `output/top_5_OrderLines/` — top 5 products
  - `output/avg_sales_month/` — average monthly revenue
  - `output/Missing_values_col/` — columns with missing values

---

### Run Unit Tests

Unit tests are located in:  
`src/test/scala/app/TestProcessor.scala`

To execute the tests:

```bash
sbt test
```

These tests validate the core data transformation functions defined in:  
`src/main/scala/models/DataProcessing.scala`







