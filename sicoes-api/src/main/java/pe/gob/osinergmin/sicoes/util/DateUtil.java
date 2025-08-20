package pe.gob.osinergmin.sicoes.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;

public class DateUtil {
	
	private static Logger logger = LogManager.getLogger(DateUtil.class);


	public static Long restarFecha(Date fechaInicio,Date fechaFin) {
		String sFechaInicio=getDate(fechaInicio, "dd/MM/yyyy");
		String sFechaFin=getDate(fechaFin, "dd/MM/yyyy");		
		fechaInicio=getFormat(sFechaInicio, "dd/MM/yyyy");
		fechaFin=getFormat(sFechaFin, "dd/MM/yyyy");
		if(fechaInicio==null||fechaFin==null) {return 0L;}
		
		long dias=((fechaFin.getTime()-fechaInicio.getTime()));
		TimeUnit time = TimeUnit.DAYS; 
		return time.convert(dias, TimeUnit.MILLISECONDS);
	}
	
	public static String getDate(String fecha,String format) {
		try {
			SimpleDateFormat sdf1=new SimpleDateFormat(format);
			Date date=sdf1.parse(fecha);
			SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			return sdf2.format(date);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static Boolean esMayorIgual(Date fechaInicio,Date fechaFin) {
		if(fechaInicio.compareTo(fechaFin)<0) {
			return false;
		}else if(fechaInicio.compareTo(fechaFin)==0) {
			return true;
		}else {
			return true;
		}
	}
	
	public static Boolean esMayor(Date fechaInicio,Date fechaFin) {
		if(fechaInicio.compareTo(fechaFin)<0) {
			return false;
		}else if(fechaInicio.compareTo(fechaFin)==0) {
			return false;
		}else {
			return true;
		}
	}
	
	public static Boolean esMenorIgual(Date fechaInicio,Date fechaFin) {
		if(fechaInicio.compareTo(fechaFin)<0) {
			return true;
		}else if(fechaInicio.compareTo(fechaFin)==0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static Date getFormat(String date,String format) {
		try {
			if(StringUtils.isBlank(date)) return null;
			SimpleDateFormat sdf2=new SimpleDateFormat(format);
			return sdf2.parse(date);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);;
		}
		return null;
	}
	public static String getDate(Date date,String format) {
		try {
			if(date==null) return "";
			SimpleDateFormat sdf2=new SimpleDateFormat(format);
			return sdf2.format(date);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);;
		}
		return null;
	}
	
static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	
	public static Date getInitDay(String fecha) {
		try {
			return getInitDay(sdf.parse(fecha));
		}catch (Exception e) {
			return null;
		}
	}
	public static Date getEndDay(String fecha) {
		try {
			return getEndDay(sdf.parse(fecha));
		}catch (Exception e) {
			return null;
		}
	}
	
	public static Date getInitDay(Date fecha) {
		Calendar calendar =Calendar.getInstance(Locale.forLanguageTag("es_PE"));
		calendar.setTime(fecha);
		calendar.set(Calendar.HOUR,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTime();
	}
	
	public static Date getEndDay(Date fecha) {
		Calendar calendar =Calendar.getInstance(Locale.forLanguageTag("es_PE"));
		calendar.setTime(fecha);
		calendar.set(Calendar.HOUR,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return calendar.getTime();
	}
	public static Date sumarDia(Date fecha,Long dia) {
		Calendar calendar =Calendar.getInstance(Locale.forLanguageTag("es_PE"));
		calendar.setTime(fecha);
		calendar.set(Calendar.HOUR,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.add(Calendar.DATE, dia.intValue());
		return calendar.getTime();
	}
}
