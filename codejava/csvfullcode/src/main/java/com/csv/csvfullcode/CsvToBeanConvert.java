package com.csv.csvfullcode;

	import java.io.*;
	import java.util.*;

	import com.opencsv.CSVReader;
	import com.opencsv.bean.CsvToBean;
	import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

	public class CsvToBeanConvert {
		public static void main(String[] args)
		{

			// Hashmap to map CSV data to 
			// Bean attributes.
			Map<String, String> mapping = new
						HashMap<String, String>();
			mapping.put("name", "Name");
			mapping.put("rollno", "RollNo");
			mapping.put("department", "Department");
			mapping.put("result", "Result");
			mapping.put("cgpa", "Pointer");

			// HeaderColumnNameTranslateMappingStrategy
			// for Student class
			HeaderColumnNameTranslateMappingStrategy<Student> strategy =
				new HeaderColumnNameTranslateMappingStrategy<Student>();
			strategy.setType(Student.class);
			strategy.setColumnMapping(mapping);

			// Create csvtobean and csvreader object
			CSVReader csvReader = null;
			try {
				csvReader = new CSVReader(new FileReader
				("c:\\temp\\StudentData.csv"));
			}
			catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			CsvToBean csvToBean = new CsvToBean();

			// call the parse method of CsvToBean
			// pass strategy, csvReader to parse method
			List<Student> list = csvToBean.parse(strategy, csvReader);

			// print details of Bean object
			for (Student e : list) {
				System.out.println(e);
			}
		}
	}
