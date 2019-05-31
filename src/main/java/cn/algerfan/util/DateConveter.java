package cn.algerfan.util;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:   
 *  转换日期类型的数据
 *  S : 页面传递过来的类型
 *  T ： 转换后的类型
 */
public class DateConveter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		try {
			if(null != source){
				DateFormat df = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				return df.parse(source);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static String convert(Date rtn) {
		try {
			if(null != rtn){
				DateFormat df = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				return df.format(rtn);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}

