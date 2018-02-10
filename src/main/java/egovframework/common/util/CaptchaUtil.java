package egovframework.common.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 자동등록방지 유틸
 * @author Administrator
 *
 */
public class CaptchaUtil {
	
	private static Logger log = LoggerFactory.getLogger(CaptchaUtil.class);

	public static void writeImage(HttpServletResponse response, BufferedImage bi) {

		response.setHeader("Cache-Control", "private,no-cache,no-store");
		response.setContentType("image/png");

		try {
			writeImage(response.getOutputStream(), bi);
		} catch (IOException e) {
			log.info("IOException : ", e);;
		}
	}

	public static void writeImage(OutputStream os, BufferedImage bi) throws IOException {
		try {
			ImageIO.write(bi, "png", os);
			if(os != null) os.close();
		} catch (IOException e) {
			log.info("IOException : ", e);;
		}finally{
			if(os != null)
				os.close();
		}
	}
}