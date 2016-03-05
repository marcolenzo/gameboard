package com.marcolenzo.gameboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.marcolenzo.gameboard.exceptions.FileUploadException;
import com.marcolenzo.gameboard.model.User;
import com.marcolenzo.gameboard.model.UserAvatar;
import com.marcolenzo.gameboard.repositories.UserAvatarRepository;
import com.marcolenzo.gameboard.utils.ImageCropper;

/**
 * Services for the management of user avatars.
 * @author Marco Lenzo
 *
 */
@Component
public class AvatarServices {

	@Autowired
	private UserAvatarRepository repository;

	/**
	 * 
	 * @param userId
	 */
	public UserAvatar getAvatarByUserId(String userId) {
		return repository.findOne(userId);
	}

	/**
	 * Update user avatar with uploaded file. The image is automatically cropped.
	 * @param user
	 * @param file
	 * @throws FileUploadException
	 */
	public void updateUserAvatar(User user, MultipartFile file) throws FileUploadException {
		if (!file.isEmpty()) {
			try {
				// Crop Image
				byte[] croppedImage = ImageCropper.cropImage(file.getBytes());

				UserAvatar avatar = new UserAvatar();
				avatar.setContentType(file.getContentType());
				avatar.setFilename(file.getName());
				avatar.setAvatar(croppedImage);
				avatar.setContentLength(croppedImage.length);
				avatar.setUserId(user.getId());

				avatar = repository.save(avatar);

			}
			catch (Exception e) {
				throw new FileUploadException("Avatar not accepted.");
			}
		}
		else {
			throw new FileUploadException("Avatar not accepted.");
		}
	}

}
