package com.vt.vt_assignment.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CSVReaderUtil {

  public static Map<String, String> readCsv(InputStream inputStream)
      throws IOException, CsvValidationException {
    Map<String, String> urlMap = new HashMap<>();

    try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
      String[] values;
      while ((values = csvReader.readNext()) != null) {
        if (values.length == 2) {
          urlMap.put(values[0], values[1]);
        }
      }
    }
    return urlMap;
  }
}
