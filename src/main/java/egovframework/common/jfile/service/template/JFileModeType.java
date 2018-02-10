package egovframework.common.jfile.service.template;

public enum JFileModeType {
	
	DBMODE(DBUploadModeTemplate.class),
	
	FILEMODE(FileUploadModeTemplate.class);
	
	JFileUploadModeTemplate handler;
	
	private JFileModeType(Class<? extends JFileUploadModeTemplate> clazz) {
		try {
			handler = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	public JFileUploadModeTemplate getHandler() {
		return this.handler;
	}
}