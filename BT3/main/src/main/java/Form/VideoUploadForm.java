package Form;

public class VideoUploadForm {
	private final String fileName;
	private final String name;

	public VideoUploadForm(String fileName, String name) {
		this.fileName = fileName == null ? "" : fileName;
		this.name = name == null ? "" : name.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public String getName() {
		return name;
	}
}
