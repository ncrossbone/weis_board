package egovframework.common.utilDo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.cms.attach.model.AttachVo;
import egovframework.common.jfile.JProperties;

public final class FileHandler {

	public static int WIDTH = 520;
	
	/**
	 * 
	 * @MethodName          : uploadFiles
	 * @Date                : 2014. 09. 01.
	 * @author              : psj
	 * @Description         : 파일 업로드
	 * @History             : 2014. 09. 01. 최초 작성
	 * @param request       : multipartRequest를 위한 리퀘스트 객체
	 * @param filefieldName : 파일 태그 필드명
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static List<AttachVo> uploadFiles(HttpServletRequest request, String filefieldName) throws Exception{
    	
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		
		List<MultipartFile> files = mRequest.getFiles(filefieldName);
    	
		List<AttachVo> fileList = new ArrayList<AttachVo>();
		
//		int i = 1;
		

		for(MultipartFile file : files) {
			boolean thumbYn = false;
			if(file.isEmpty() || file.getSize() == 0) continue;
			
			AttachVo fileInfo = fileUpload(file);
			/***
			 * 썸네일 제작에 필요한 구문
			String[] arrFormat = {"gif", "jpg", "png", "jpeg"};
			for(int i=0; i<arrFormat.length; i++){
				if(arrFormat[i].equals(fileInfo.getFormat().toLowerCase())){
					thumbYn = true;
				}
			}

			if(thumbYn){
				String orgPath = fileInfo.getPath();
				String thumFileName = fileInfo.getName();
				
				try {
					thumbnailMake(orgPath, thumFileName, WIDTH);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			} 
			
			*/
			
			if(fileInfo != null) fileList.add(fileInfo);
		}
		
		return fileList;
		
	}
	
	
	/**
	 * 
	 * @MethodName   : fileUpload
	 * @Date         : 2014. 09. 01.
	 * @author       : psj
	 * @Description  : 물리 파일 생성 및 파일모델 생성
	 * @History      : 2014. 09. 01. 최초 작성
	 * @param file   : MultipartFile 객체
	 * @return
	 * @throws Exception 
	 */
	public static AttachVo fileUpload(MultipartFile file) throws Exception {
		
    	// 경로 및 파일명
//		String fileRealname = file.getOriginalFilename();
		String fileRealname = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
		String ext = fileRealname.substring(fileRealname.lastIndexOf(".")+1, fileRealname.length());
		
		//저장될 폴더 경로
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String folder = sdf.format(date);
		String fileDir = JProperties.getString("jfile.Uploadpath") + File.separator + folder;
		
		//시스템 파일명
		String timeM = String.valueOf(System.currentTimeMillis());
		
		sdf = new SimpleDateFormat();
		String fileName = fileRealname.substring(0,fileRealname.lastIndexOf(".")) +"_"+ timeM +"."+ ext;
		
		//저장될 경로에 폴더 존재여부 없으면 디렉토리 생성
		File dir = new File(fileDir);
		if(!dir.exists()) dir.mkdirs();
		
		
		//파일 객체 생성
		File newFile = new File(fileDir + "/" + fileName);
		
		// 불가능 확장자 체크
		if(!ext.equalsIgnoreCase("EXE") && !ext.equalsIgnoreCase("JS")) {
			
			try{
				
				//물리파일 생성 시작
				byte[] bytes = file.getBytes();
				
				FileOutputStream fos = new FileOutputStream(newFile);
				fos.write(bytes);
				fos.close();
				//물리파일 생성 끝
			
			} catch (IOException ie) {
				
				ie.printStackTrace();
				new Exception("File writing error!");
			}
			
			//파일 모델 생성
			AttachVo attachModel = new AttachVo();
			//
			attachModel.setAtchfl_save_path(fileDir);
			attachModel.setAtchfl_save_logic_nm(fileName);
			attachModel.setAtchfl_physc_nm(fileRealname);
			attachModel.setAtchfl_extnm(ext);
			attachModel.setAtchfl_size(file.getSize());
			
			return attachModel;
			
		} else {
			
			throw new IllegalArgumentException("File type error!");
			//new Exception("File type error!");
			
		}
		
	}
	
	
	/**
     * 
     * @MethodName         : fileDownload
     * @Date               : 2014. 9. 1.
     * @author             : psj
     * @Description        : 파일 다운로드
     * @History            : 2014. 9. 1. 최초 작성
     * @param fileinfo     : fileInfo Model
     * @param res          : response 객체
     * @throws IOException : 
     */
    public static void fileDownload(Map<String,Object> fileMap, HttpServletResponse res) throws IOException{
    	
		String path = fileMap.get("FILE_COURS") + File.separator + fileMap.get("FILE_VIRTL_NM") +  "." + fileMap.get("FILE_FRMAT").toString().toLowerCase();
		
		File file = new File(path);
		String fileName = fileMap.get("FILE_REAL_NM").toString();
		
		res.reset();
		res.setContentType("application/x-msdownload;");
		res.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20") + "\";");
		
		OutputStream out = res.getOutputStream();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
			out.flush();
		}
    }

    public static void thumbfileDownload(Map<String,Object> fileMap, HttpServletResponse res) throws IOException{
		//String path = fileMap.get("FILE_COURS") + File.separator + "THUMB_" + fileMap.get("FILE_VIRTL_NM") + "." + fileMap.get("FILE_FRMAT").toString().toLowerCase() ;
    	String path = fileMap.get("FILE_COURS") + File.separator + "THUMB_" + fileMap.get("FILE_VIRTL_NM") + ".gif" ;
		
		File file = new File(path);
		String fileName = fileMap.get("FILE_REAL_NM").toString();
		
		res.setContentType("application/x-msdownload;");
		res.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20") + "\";");
		
		OutputStream out = res.getOutputStream();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
			out.flush();
		}
    }

    public static void zipfileDownload(List<Map<String,Object>> fileList, HttpServletRequest req, HttpServletResponse res) throws IOException{
        //파일이 디렉토리가 아니면 첫번째 배열에 파일이름을 넣는다.
    	String[] files = new String[fileList.size()];

        for(int i=0; i<fileList.size(); i++){
        	Map<String,Object> fileMap = fileList.get(i);
        	files[i] = fileMap.get("FILE_COURS") + File.separator + "THUMB_" + fileMap.get("FILE_VIRTL_NM") + ".gif";
//        	files[i] = fileMap.get("FILE_COURS") + File.separator + fileMap.get("FILE_VIRTL_NM") + "." + fileMap.get("FILE_FRMAT");
        }
        
        // 파일을 읽기위한 버퍼
        byte[] buf = new byte[1024];
        
        try {
            // 압축파일명
            File f = new File("c:\\waterImgFile");

            //디렉토리 유무 체크 생성.
            if(!f.exists()){         
                 f.mkdir();
            }
            String zipName = "C:\\waterImgFile\\"+fileList.get(0).get("BRRER_NM").toString()+".zip";
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipName));
        
            // 파일 압축
            for (int i=0; i<files.length; i++) {
                FileInputStream in = new FileInputStream(files[i]);
        
                // 압축 항목추가
                out.putNextEntry(new ZipEntry(files[i]));
        
                // 바이트 전송
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
        
                out.closeEntry();
                in.close();
            }

    		String fileName = fileList.get(0).get("BRRER_NM").toString() + ".zip";
    		
    		res.setContentType("application/x-msdownload;");
    		res.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20") + "\";");
    		
    		OutputStream out2 = res.getOutputStream();
    		FileInputStream fis = null;
    		try{
    			fis = new FileInputStream(zipName);
    			FileCopyUtils.copy(fis, out2);
    		}finally{
    			if(fis != null){
    				try{
    					fis.close();
    				}catch(IOException ex){
    					ex.printStackTrace();
    				}
    			}
    			out2.flush();
    		}
    		
            // 압축파일 작성
            out.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
//		int bufferSize = 1024 * 2;
//		String ouputName = fileList.get(0).get("BRRER_NM").toString();
//		            
//		ZipOutputStream zos = null;
//		            
//		try {
//		                
//		    if (req.getHeader("User-Agent").indexOf("MSIE 5.5") > -1) {
//		        res.setHeader("Content-Disposition", "filename=" + ouputName + ".zip" + ";");
//		    } else {
//		        res.setHeader("Content-Disposition", "attachment; filename=" + ouputName + ".zip" + ";");
//		    }
//		    res.setHeader("Content-Transfer-Encoding", "binary");
//		    
//		    OutputStream os = res.getOutputStream();
//		    zos = new ZipOutputStream(os); // ZipOutputStream
//		    zos.setLevel(8); // 압축 레벨 - 최대 압축률은 9, 디폴트 8
//		    BufferedInputStream bis = null;
//
//		    String []filePaths = new String[fileList.size()];
//		    String []fileNames = new String[fileList.size()]; 
//		    String []fileFormat = new String[fileList.size()]; 
//
//	        for(int i=0; i<fileList.size(); i++){
//	        	Map<String,Object> fileItem = fileList.get(i);
//	        	filePaths[i] = fileItem.get("FILE_COURS").toString();
//	        	fileNames[i] = fileItem.get("FILE_VIRTL_NM").toString();
//	        	fileFormat[i] = fileItem.get("FILE_FRMAT").toString().toLowerCase();
//	        }
//	        
//		    int    i = 0;
//		    for(String filePath : filePaths){
//		        //File sourceFile = new File(filePath + File.separator + fileNames[i] + "." + fileFormat[i]);
//		    	File sourceFile = new File(filePath + File.separator + "THUMB_" + fileNames[i] + ".gif");              
//		        bis = new BufferedInputStream(new FileInputStream(sourceFile));
//		        ZipEntry zentry = new ZipEntry(fileNames[i]);
//		        zentry.setTime(sourceFile.lastModified());
//		        zos.putNextEntry(zentry);
//		        
//		        byte[] buffer = new byte[bufferSize];
//		        int cnt = 0;
//		        while ((cnt = bis.read(buffer, 0, bufferSize)) != -1) {
//		            zos.write(buffer, 0, cnt);
//		        }
//		        zos.closeEntry();
//		 
//		        i++;
//		    }
//		               
//		    zos.close();
//		    bis.close();
//		                
//		                
//		} catch(Exception e){
//		    e.printStackTrace();
//		}
    }
    
	/**
	 * 
	 * @MethodName        : deleteFile
	 * @Date              : 2014. 09. 01.
	 * @author            : psj
	 * @Description       : 물리 파일 삭제
	 * @History           : 2014. 09. 01. 최초 작성
	 * @param strFileName : file Full Path
	 * @return
	 */
	public static boolean deleteFile(String strFileName) {
		
		boolean rCode = true;
		
		File fileName = new File(strFileName);
		
		try {
			
			if (!fileName.delete()) {
				
				System.out.println ("[" + System.currentTimeMillis() + "] [FileHandler] [makeFile] [ERROR] [파일 삭제 실패] file : " + strFileName);	
				
				rCode = false;
			
			}
			
		} catch(Exception ex) {
			
			System.out.println("[" + System.currentTimeMillis() + "] [FileHandler] [deleteFile] [Exception] file : " + strFileName);	
			
			ex.printStackTrace();
			
			rCode = false;
			
			System.gc();
		
		} finally {
			
			fileName = null;
		}
		
		return rCode;
	}
	
	public static boolean thumbDeleteFile(String strFilePath, String strFileName) {
		
		boolean rCode = true;
		String thumbFullPath = strFilePath + File.separator + strFileName;
		
		File thumbFileName = new File(thumbFullPath);
		
		try {
			System.out.println("thumbFile");
			if(thumbFileName.isFile()){
				if (!thumbFileName.delete()) {
					
					System.out.println ("[" + System.currentTimeMillis() + "] [FileHandler] [thumbFile] [ERROR] [파일 삭제 실패] file : " + thumbFullPath);	
					
					rCode = false;
				
				}	
			}
			
		} catch(Exception ex) {
			
			System.out.println("[" + System.currentTimeMillis() + "] [FileHandler] [thumbFile] [Exception] file : " + thumbFullPath);	
			
			ex.printStackTrace();
			
			rCode = false;
			
			System.gc();
		
		} finally {
			
			thumbFileName = null;
		}
		
		return rCode;
	}
	
//	/**
//	 * 
//	 * @MethodName        : thumbnailMake
//	 * @Date              : 2014. 09. 01.
//	 * @author            : psj
//	 * @Description       : 썸네일 생성
//	 * @History           : 2014. 09. 01. 최초 작성
//	 * @param fileUrl     : 파일 Path
//	 * @param fileName    : 원본 파일 명
//	 * @param imageWidth  : 썸네일이미지 폭
//	 * @param imageHeight : 썸네일이미지 높이
//	 * @return
//	 */
//	public static boolean thumbnailMake(String fileUrl, String fileName, int imageWidth, int imageHeight)  {
//		boolean check = true;
//		
//		try {
//				ParameterBlock pb = new ParameterBlock();
//				pb.add(fileUrl + File.separator + fileName);
//				
//				// 이미지 객체를 생성
//				RenderedOp rOp = JAI.create("fileload", pb);
//				
//				// 이미지 객체의 가로길이보다 설정한 가로길이가 큰지 체크
//				if(rOp.getWidth() < imageWidth) {
//				 imageWidth = rOp.getWidth();
//				}
//				
//				// 이미지 객체의 세로길이보다 설정한 세로길이가 큰지 체크
//				if(rOp.getHeight() < imageHeight) {
//				 imageHeight = rOp.getHeight();
//				}
//				
//				BufferedImage bi = rOp.getAsBufferedImage();
//				BufferedImage thumb = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
//				Graphics2D g = thumb.createGraphics();
//				g.drawImage(bi, 0, 0, imageWidth, imageHeight, null);
//				
//				// 썸네일 이미지 생성
//				File file = new File(fileUrl + File.separator + "thum_" + fileName);
//				ImageIO.write(thumb, "jpg", file);
//				
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//			check = false;
//			
//		}
//		
//		return check;
//	
//	}
//	
//	/**
//	 * 
//	 * @MethodName        : thumbnailMake
//	 * @Date              : 2014. 09. 01.
//	 * @author            : psj
//	 * @Description       : 썸네일 생성
//	 * @History           : 2014. 09. 01. 최초 작성
//	 * @param fileUrl     : 파일 Path
//	 * @param fileName    : 원본 파일 명
//	 * @param imageWidth  : 썸네일이미지 폭
//	 * @param imageHeight : 썸네일이미지 높이
//	 * @return
//	 * @throws IOException 
//	 */
//	public static boolean thumbnailMake(String fileUrl, String fileName, int imageWidth) throws IOException  {
//		boolean check = true;
//
//		SeekableStream stream = null;
//		
//		try {
//			File loadFile = new File(fileUrl + File.separator + fileName);
//			stream = new FileSeekableStream(loadFile);
//			ParameterBlock param = new ParameterBlock();
//			param.add(stream);
//			PlanarImage rOp = JAI.create("stream", param);
//			
//			int basicWidth = rOp.getWidth();
//			int basicHeight = rOp.getHeight();
//
//			if(basicWidth <= imageWidth){
//				imageWidth = basicWidth;
//			}
//			
//			BufferedImage bi;
//			BufferedImage thumb;
//			
//			int ThumbHeight = (basicHeight * imageWidth) / basicWidth;
//			
//			bi = rOp.getAsBufferedImage();
//			thumb = new BufferedImage(imageWidth, ThumbHeight, BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = thumb.createGraphics();
//			g.drawImage(bi, 0, 0, imageWidth, ThumbHeight, null);
//
////			String thumbFileDir = fileUrl + File.separator + "thumbFile";
////			File thumbDir = new File(thumbFileDir);
////			if(!thumbDir.exists()) thumbDir.mkdirs();
//
//			// 썸네일 이미지 생성
//			File file = new File(fileUrl + File.separator + "thum_" + fileName);
//			ImageIO.write(thumb, "jpg", file);
//				
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//			check = false;
//			
//		} finally{
//			stream.close();
//		}
//		
//		return check;
//	
//	}
}
