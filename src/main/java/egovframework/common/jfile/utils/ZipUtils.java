package egovframework.common.jfile.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/*한글이 깨져서 아래로 대체*/
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import egovframework.common.jfile.GlovalVariables;
import egovframework.common.jfile.security.service.CipherService;
import egovframework.common.jfile.service.JFile;

public class ZipUtils {
	private static final byte[] buf = new byte[1024];

	public static void createZipJFile(JFile[] targetFiles, OutputStream os)	throws Exception {
		ZipArchiveOutputStream zipOs = new ZipArchiveOutputStream(os);

		for (int i = 0; i < targetFiles.length; i++) {
			FileInputStream in = null;

			// 물리적인 파일이 없을경우 압축파일 목록에서 제외 시키기 위해 사용
			try {
				in = new FileInputStream(new File(targetFiles[i].getPath()));
			} catch (FileNotFoundException e) {
				if (in != null) in.close();
				continue;
			}
			try {
			zipOs.putArchiveEntry(new ZipArchiveEntry(targetFiles[i].getOriginalFileName()));
			if ("true".equalsIgnoreCase(targetFiles[i].getUseSecurity())) {
				CipherService service = (CipherService) SpringUtils.getBean(GlovalVariables.CIPHER_SERVICE_BEAN_NAME);
				service.decryptForZipFile(in, zipOs);
			} else {
				int data;
				while ((data = in.read(buf)) > 0) {
					zipOs.write(buf, 0, data);
				}
			}
			zipOs.closeArchiveEntry();
			} catch (IOException e) {
				System.out.println("IOException: " + e);
			} finally {
				//웹취약점 조치
				if (in != null) {
					in.close();
				}
				//웹취약점 조치
				if (zipOs != null) {
					zipOs.close();
				}
				//웹취약점 조치
				if (os != null) {
					os.close();
				}
			}
		}

	}
}