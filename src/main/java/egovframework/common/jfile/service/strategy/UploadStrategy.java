package egovframework.common.jfile.service.strategy;

import java.io.InputStream;
import java.io.OutputStream;

public interface UploadStrategy {
	
	public void handle(InputStream in, OutputStream os);
}