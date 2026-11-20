package Form;

public class ImageUploadForm {
	private final String fileName;

	public ImageUploadForm(String fileName) {
		this.fileName = fileName == null ? "" : fileName;
	}

	public String getFileName() {
		return fileName;
	}
}
