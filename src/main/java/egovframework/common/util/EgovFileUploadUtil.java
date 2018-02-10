package egovframework.common.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;


public class EgovFileUploadUtil extends EgovFormBasedFileUtil {
  
  private Logger log = LoggerFactory.getLogger(this.getClass());
  
  private String rootPath; // 절대경로(마지막에 / 들어감) : SI
  private int maxSize; // 업로드 최대 용량
  
  public String getRootPath() {
    return rootPath;
  }
  
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }
  
  public int getMaxSize() {
    return maxSize;
  }
  
  public void setMaxSize(int maxSize) {
    this.maxSize = maxSize;
  }
  
  /**
   * 파일을 Upload 처리한다.
   * 
   * @param request
   * @param where
   * @param maxFileSize
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public List<EgovFormBasedFileVo> uploadFiles(HttpServletRequest request, String addPath, int maxSize, String sThumbnlYN) throws Exception {
    List<EgovFormBasedFileVo> list = new ArrayList<EgovFormBasedFileVo>();
    
    MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
    Iterator fileIter = mptRequest.getFileNames();
    
    while (fileIter.hasNext()) {
      
      String fieldName = (String) fileIter.next();
      MultipartFile mFile = mptRequest.getFile(fieldName);
      
      EgovFormBasedFileVo vo = new EgovFormBasedFileVo();
      
      String tmp = mFile.getOriginalFilename();
      
      // 파일 확장자 체크
      String fileExt = tmp.substring(tmp.lastIndexOf(".") + 1, tmp.length());
      if (fileExt.matches("asp|aspx|ascx|bat|cfc|cfm|cgi|cmd|com|csh|dll|exe|inf|jsp|jsp|ksh|php|php3|php5|phtml|ph|reg|sh")) {
        log.info("[upload-failed] FAILED!! ." + fileExt + " is illegal");
        return null;
      }
      // 파일 최대사이즈 체크
      if (maxSize > 0) {
        long fileSize = mFile.getSize();
        if (fileSize > (1048576 * maxSize)) {
          return null;
        }
      }
      
      if (tmp.lastIndexOf("\\") >= 0) {
        tmp = tmp.substring(tmp.lastIndexOf("\\") + 1);
      }
      
      vo.setFileName(tmp);
      vo.setContentType(mFile.getContentType());
      vo.setServerSubPath(addPath);
      if (addPath.equals("depoly/")) {
        vo.setPhysicalName(tmp);
      } else {
        vo.setPhysicalName(getPhysicalFileName());
      }
      
      vo.setPhysicalThumName(vo.getPhysicalName() + "_thumb");
      vo.setSize(mFile.getSize());
      vo.setFieldName(fieldName);
      
      if (tmp.lastIndexOf(".") >= 0) {
        vo.setPhysicalName(vo.getPhysicalName()); // 2012.11 KISA 보안조치
      }
      
      if (mFile.getSize() > 0) {
        InputStream is = null;
        
        try {
          is = mFile.getInputStream();
          System.out.println(rootPath + EgovWebUtil.filePathReplaceAll(SEPERATOR + vo.getServerSubPath()) + SEPERATOR + EgovWebUtil.filePathReplaceAll(vo.getPhysicalName()));
          saveFile(mFile.getInputStream(), new File(EgovWebUtil.filePathBlackList(rootPath + EgovWebUtil.filePathReplaceAll(SEPERATOR + vo.getServerSubPath()) + SEPERATOR + EgovWebUtil.filePathReplaceAll(vo.getPhysicalName()))));
          
          if (StringUtil.noNull(sThumbnlYN).equals("1") && isImage(fileExt)) {
            is = mFile.getInputStream();
            // 썸네일 이미지 생성
            BufferedImage bi = ImageIO.read(is);
            // 일단 고정으로 쓰고 필요에 따라 파라미터로 빼던지 하자
            int width = 320;
            int height = 200;
            // 이미지 사이즈 조정
            if (bi != null) {// 웹취약점 조치
              if (bi != null && ((float) width / bi.getWidth() > (float) height / bi.getHeight())) {
                if (bi != null) width = (int) (bi.getWidth() * ((float) height / bi.getHeight()));
              } else {
                if (bi != null) height = (int) (bi.getHeight() * ((float) width / bi.getWidth()));
              }
              
              // 웹취약점 조치
              String thumName = EgovWebUtil.filePathReplaceAll(vo.getPhysicalThumName());
              String subPath = EgovWebUtil.filePathReplaceAll(vo.getServerSubPath());
              scale(bi, rootPath + SEPERATOR + subPath + SEPERATOR + thumName, "jpg", width, height);
            }
          }
          
        } catch (IOException e) {
          log.debug("에러발생{}", e);
        } finally {
          if (is != null) {
            is.close();
          }
        }
        list.add(vo);
        log.info("[upload] file-name : " + tmp + " / file-type : " + mFile.getContentType() + " / file-size : " + mFile.getSize());
      }
    }
    
    return list;
  }
  
  /**
   * 참조 http://code.google.com/p/java-image-scaling/ http://code.google.com/p/hudson-assembler/downloads/detail?name=Filters.jar&can=2&q= java-image-scaling-0.8.6.jar, Filters.jar 사용
   */
  public void scale(BufferedImage srcImage, String destPath, String imaesFormat, int destWidth, int dsetHight) {
    try {
      ResampleOp resampleOp = new ResampleOp(destWidth, dsetHight);
      resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
      BufferedImage rescaledImage = resampleOp.filter(srcImage, null);
      File destFile = new File(destPath);
      ImageIO.write(rescaledImage, imaesFormat, destFile);
    } catch (IOException e) {
      log.debug("에러발생{}", e);
    }
    
  }
  
  public List<EgovFormBasedFileVo> uploadFiles(HttpServletRequest request, String addPath) throws Exception {
    return uploadFiles(request, addPath, 0, "2");
  }
  
  public List<EgovFormBasedFileVo> uploadFiles(HttpServletRequest request, String addPath, String sThumbnlYN) throws Exception {
    return uploadFiles(request, addPath, 0, sThumbnlYN);
  }
  
  /**
   * 파일을 Download 처리한다.
   * 
   * @param response
   * @param where
   * @param serverSubPath
   * @param physicalName
   * @param original
   * @throws Exception
   */
  public void downloadFile(HttpServletResponse response, HttpServletRequest request, String serverSubPath, String physicalName, String original, String mimeTypeParam) throws Exception {
    
    String downFileName = rootPath + SEPERATOR + serverSubPath + SEPERATOR + physicalName;
    
    File file = new File(EgovWebUtil.filePathBlackList(downFileName));
    
    log.info("downLoad filePath : {}", file.toString());
    
    if (!file.exists()) {
      throw new FileNotFoundException(downFileName);
    }
    
    if (!file.isFile()) {
      throw new FileNotFoundException(downFileName);
    }
    
    int fSize = (int) file.length();
    
    if (fSize > 0) {
      
      String mimetype = "application/x-msdownload";
      if (!StringUtil.isBlank(mimeTypeParam)) {
        mimetype = mimeTypeParam;
      }
      
      response.setContentType(mimetype);
      EgovWebUtil.setDisposition(original, request, response);
      response.setContentLength(fSize);
      
      BufferedInputStream in = null;
      BufferedOutputStream out = null;
      
      try {
        in = new BufferedInputStream(new FileInputStream(file));
        out = new BufferedOutputStream(response.getOutputStream());
        
        FileCopyUtils.copy(in, out);
        out.flush();
        if (in != null) in.close();
        if (out != null) out.close();
        
      } catch (IOException ex) {
        log.info("IO Exception", ex);
      } finally {
        if (in != null) in.close();
        if (out != null) out.close();
        // EgovResourceCloseHelper.close(in, out);
      }
      
    } else {
      response.setContentType("application/x-msdownload");
      
      PrintWriter printwriter = response.getWriter();
      printwriter.println("<html>");
      printwriter.println("<br><br><br><h2>Could not get file name:<br>" + original + "</h2>");
      printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
      printwriter.println("<br><br><br>&copy; webAccess");
      printwriter.println("</html>");
      printwriter.flush();
      printwriter.close();
    }
  }
  
  public File downloadFiles(HttpServletResponse response, HttpServletRequest request, String serverSubPath, String physicalName, String original, String mimeTypeParam) throws Exception {
    String downFileName = rootPath + SEPERATOR + serverSubPath + SEPERATOR + physicalName;
    
    File file = new File(EgovWebUtil.filePathBlackList(downFileName));
    return file;
  }
  
  /**
   * 이미지에 대한 미리보기 기능을 제공한다.
   * 
   * mimeType의 경우는 JSP 상에서 다음과 같이 얻을 수 있다. getServletConfig().getServletContext().getMimeType(name);
   * 
   * @param response
   * @param where
   * @param serverSubPath
   * @param physicalName
   * @param mimeType
   * @throws Exception
   */
  public void viewFile(HttpServletResponse response, String serverSubPath, String physicalName, String mimeTypeParam) throws Exception {
    String mimeType = mimeTypeParam;
    String downFileName = rootPath + SEPERATOR + serverSubPath + SEPERATOR + physicalName;
    
    File file = new File(EgovWebUtil.filePathBlackList(downFileName));
    
    if (!file.exists()) {
      file = new File(EgovWebUtil.filePathBlackList(getNoImagePath()));
      // throw new FileNotFoundException(downFileName);
    }
    
    if (!file.isFile()) {
      file = new File(EgovWebUtil.filePathBlackList(getNoImagePath()));
      // throw new FileNotFoundException(downFileName);
    }
    
    byte[] b = new byte[BUFFER_SIZE];
    
    if (mimeType == null) {
      mimeType = "application/octet-stream;";
    }
    
    response.setContentType(EgovWebUtil.removeCRLF(mimeType));
    response.setHeader("Content-Disposition", "filename=image;");
    
    BufferedInputStream fin = null;
    BufferedOutputStream outs = null;
    
    try {
      fin = new BufferedInputStream(new FileInputStream(file));
      outs = new BufferedOutputStream(response.getOutputStream());
      
      int read = 0;
      
      while ((read = fin.read(b)) != -1) {
        outs.write(b, 0, read);
      }
      
      if (outs != null) outs.close();
      if (fin != null) fin.close();
      // 2011.10.10 보안점검 후속조치
    } catch (IOException ex) {
      log.info("IO Exception", ex);
    } finally {
      if (outs != null) outs.close();
      if (fin != null) fin.close();
      // EgovResourceCloseHelper.close(outs, fin);
    }
  }
  
  /**
   * 파일 삭제
   * 
   * @param addPath
   *          ,fileName
   * @throws Exception
   */
  public synchronized boolean deleteFile(String addPath, String fileName) throws Exception {
    boolean returnVal = false;
    // 선언
    /*
     * File file = new File( rootPath + SEPERATOR + addPath + SEPERATOR +fileName ); //존재여부 검사. if( !file.exists() || !file.isFile() ){ returnVal = false; }else{ returnVal = file.delete(); }
     */
    return returnVal;
  }
  
  /**
   * 이미지 경로를 읽어온다.
   * 
   * @return
   */
  public String getNoImagePath() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    // return request.getSession().getServletContext().getRealPath("/") + JProperties.getString(GlovalVariables.DEFAULT_NO_IMAGE_APP_PATH_KEY);
    return request.getSession().getServletContext().getRealPath("/") + "";
  }
  
  /**
   * 다중 파일 삭제시 파일명 배열로 반환
   * 
   * @param request
   * @throws Exception
   */
  public String[] getDelNames(HttpServletRequest request) throws Exception {
    Enumeration<String> enumer = request.getParameterNames();
    List<String> tmp = new ArrayList<String>();
    while (enumer.hasMoreElements()) {
      String chkName = enumer.nextElement();
      if (chkName.contains("_DEL_YN")) {
        chkName = chkName.substring(0, chkName.indexOf("_DEL_YN"));
        tmp.add(chkName);
      }
    }
    String[] arr = new String[tmp.size()];
    
    for (int i = 0; i < arr.length; i++) {
      arr[i] = tmp.get(i);
    }
    
    return arr;
  }
  
  /**
   * 파일 이미지여부 확인
   * 
   * @param fileExt
   * @return
   */
  public boolean isImage(String fileExt) {
    fileExt = fileExt.toLowerCase();
    return ("bmp".equals(fileExt) || "gif".equals(fileExt) || "jpg".equals(fileExt) || "jpeg".equals(fileExt) || "png".equals(fileExt));
  }
}
