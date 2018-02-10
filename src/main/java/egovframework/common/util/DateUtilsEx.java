package egovframework.common.util;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
public class DateUtilsEx {

  public static Date getDate(int year, int month, boolean truncate) {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.YEAR, year);
      cal.set(Calendar.MONTH, month);
      
      if( truncate ) {
        cal = DateUtils.truncate(cal, Calendar.MONTH);
      }
      
      return cal.getTime();
    }

    public static Date getDate(int year, int month) {
      return getDate(year, month, false); 
    }
    
    /**
   * 1년 후의 날을 구한다.
   */
  public static Date getNextYear(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.add(Calendar.YEAR, 1);
      
      return cal.getTime();
    }

  /**
   * 1년 전의 날을 구한다.
   */
    public static Date getPreviousYear(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.add(Calendar.YEAR, -1);
      
      return cal.getTime();
    }
    
    /**
   * 한달 후의 날을 구한다.
   */
    public static Date getNextMonth(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.add(Calendar.MONTH, 1);
      
      return cal.getTime();
    }
    
    /**
   * 한달 전의 날을 구한다.
   */
  public static Date getPreviousMonth(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.add(Calendar.MONTH, -1);
      
      return cal.getTime();
    }
  
  /**
   * 7일 후의 날을 구한다.
   */
  public static Date getNextWeek(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.add(Calendar.DATE, 7);
      
      return cal.getTime();
    }
    
  /**
   * 7일전의 날을 구한다.
   */
  public static Date getPreviousWeek(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.add(Calendar.DATE, -7);
      
      return cal.getTime();
    }
  
  /**
   * 다음 날을 구한다.
   */
  public static Date getNextDate(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.add(Calendar.DATE, 1);
      
      return cal.getTime();
    }
    
  /**
   * 하루 전 날을 구한다.
   */
  public static Date getPreviousDate(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.add(Calendar.DATE, -1);
      
      return cal.getTime();
    }
  
  /**
   * 해당 주의 첫번째 날을 구한다.
   */
  public static Date getFirstDateOfWeek(Date date) {
    Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
      cal.add(Calendar.DATE, (dayOfWeek-1)*-1);
      
      return cal.getTime();
  }
  
  /**
   * 해당 주의 마지막 날을 구한다.
   */
  public static Date getLastDateOfWeek(Date date) {
    Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
      cal.add(Calendar.DATE, 7-dayOfWeek);
      
      return cal.getTime();
  }
  
  /**
   * 해당 연도 달의 첫번째 날을 구한다.
   */
  public static Date getFirstDateOfMonth(Date date) {
    Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    return cal.getTime();
  }
  
  /**
   * 해당 연도 달의 첫번째 날을 구한다.
   */
  public static Date getFirstDateOfMonth(int year, int month) {
    Calendar cal = Calendar.getInstance();
      
    cal.set(Calendar.YEAR, year);
      cal.set(Calendar.MONTH, month-1);
      cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    
    return minimized(cal.getTime());
  }
  
  /**
   * 해당 연도 달의 마지막 날을 구한다.
   */
  public static Date getLastDateOfMonth(Date date) {
    Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    return cal.getTime();
  }
  
  /**
   * 해당 연도 달의 마지막 날을 구한다.
   */
  public static Date getLastDateOfMonth(int year, int month) {
    Calendar cal = Calendar.getInstance();
      
    cal.set(Calendar.YEAR, year);
      cal.set(Calendar.MONTH, month-1);
      cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    
    return maximize(cal.getTime());
  }
  
  /**
   * 시,분,초를 모두 최소치로 초기화한다.
   */
  public static Date minimized(Date date) {
    return DateUtils.truncate(date, Calendar.DATE);
  }
  
  /**
   * 시,분,초를 모두 최대치로 초기화한다.
   */
  public static Date maximize(Date date) {
    Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      
      cal.set(Calendar.HOUR_OF_DAY, 23);
      cal.set(Calendar.MINUTE, 59);
      cal.set(Calendar.SECOND, 59);
    
    return cal.getTime();
  }
  
  /*public static void main(String[] args) {
    System.out.println(DateUtilsEx.getLastDateOfMonth(2016, 8));
    
  }*/
  
}
