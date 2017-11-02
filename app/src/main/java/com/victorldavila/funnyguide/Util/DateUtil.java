package com.victorldavila.funnyguide.Util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
  public static String getFormatDate(String release_date) {
    if (release_date != null) {
      SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
      SimpleDateFormat outputFormat = new SimpleDateFormat("dd/mm/yyyy");

      try {
        Date date = inputFormat.parse(release_date);
        return outputFormat.format(date);
      } catch (ParseException e) {
        return release_date;
      }
    } else {
      return "";
    }
  }
}
