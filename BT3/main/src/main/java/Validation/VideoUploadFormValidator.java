package Validation;

import Form.VideoUploadForm;

public class VideoUploadFormValidator implements FormValidator<VideoUploadForm> {
	public String validate(VideoUploadForm form) {
		if (InputChecks.isBlank(form.getFileName())) {
			return "Please select a video to upload.";
		}
		if (!InputChecks.hasVideoExtension(form.getFileName())) {
			return "Please upload a valid video file.";
		}
		return null;
	}
}
