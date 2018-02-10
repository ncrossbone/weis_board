package egovframework.common.jfile.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import egovframework.common.jfile.GlovalVariables;
import egovframework.common.jfile.security.service.CipherService;
import egovframework.common.jfile.service.JFile;
import egovframework.common.jfile.utils.SpringUtils;
import egovframework.common.jfile.utils.ZipUtils;
import egovframework.common.util.EgovWebUtil;

/**
 *  클래스
 * @author 정호열
 * @since 2010.10.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2010.10.17   정호열       최초 생성
 *
 * </pre>
 */
public class JfileDownloadView extends AbstractView {
	static Logger logger = Logger.getLogger(JfileDownloadView.class);

	/** DownloadView 명. */
	public static final String NAME = "jfileDownload";
	
	/** DownloadView 모델 명. */
	public static final String MODELNAME = "downloadFile";

	/**
	 * 생성자.
	 */
	public JfileDownloadView() {
		setContentType("application/octet-stream");
	}

	/**
	 * 파일을 다운로드 한다.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Object obj = model.get(MODELNAME);
		response.setContentType(getContentType());
		if (obj != null && obj.getClass().isArray())
		{
			JFile[] files = (JFile[]) obj;
			EgovWebUtil.setDisposition("all.zip", request, response);
			response.setContentLength(-1);
			ZipUtils.createZipJFile(files, response.getOutputStream());
		}
		else
		{
			File file = (File) obj;
			EgovWebUtil.setDisposition((file instanceof JFile) ? ((JFile) file).getOriginalFileName() : file.getName(), request, response);
			response.setContentLength((int) file.length());
			OutputStream out = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			if (file instanceof JFile && ("true".equalsIgnoreCase(((JFile) file).getUseSecurity())))
			{
				CipherService service = (CipherService) SpringUtils.getBean(GlovalVariables.CIPHER_SERVICE_BEAN_NAME);
				service.decrypt(fis, out);
			}
			else
			{
				FileInputStream fin = null;
				try
				{
					fin = new FileInputStream(file);
					FileCopyUtils.copy(fin, out);
				}
				catch (FileNotFoundException e)
				{
					logger.error("[ERROR] 파일이 존재하지 않습니다.");
					out.close();
				}
				finally
				{
					try
					{
						if (out != null)
						{
							out.close();
						}
						if (fis != null)
						{
							fis.close();
						}
						if (fin != null)
						{
							fin.close();
						}
					}
					catch (IOException e)
					{
						logger.error("IO Exception", e);
					}
//					EgovResourceCloseHelper.close(out, fis, fin);
				}
			}
		}
	}
}