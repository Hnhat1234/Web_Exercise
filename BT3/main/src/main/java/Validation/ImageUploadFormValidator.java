package Validation;

import Form.ImageUploadForm;

public class ImageUploadFormValidator implements FormValidator<ImageUploadForm> {
	public String validate(ImageUploadForm form) {
		if (InputChecks.isBlank(form.getFileName())) {
			return "Please select an image to upload.";
		}
		if (!InputChecks.hasImageExtension(form.getFileName())) {
			return "Please upload a valid image file.";
		}
		return null;
	}
}
