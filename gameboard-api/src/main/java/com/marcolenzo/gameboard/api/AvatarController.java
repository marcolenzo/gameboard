package com.marcolenzo.gameboard.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.marcolenzo.gameboard.api.exceptions.FileUploadException;
import com.marcolenzo.gameboard.api.exceptions.NotFoundException;
import com.marcolenzo.gameboard.commons.model.User;
import com.marcolenzo.gameboard.commons.model.UserAvatar;
import com.marcolenzo.gameboard.commons.repositories.UserAvatarRepository;
import com.marcolenzo.gameboard.commons.utils.ImageCropper;

@Controller
public class AvatarController {

	@Autowired
	private UserAvatarRepository repo;

	@RequestMapping(value = "/avatar", method = RequestMethod.POST)
	public @ResponseBody String createAvatar(@RequestParam("file") MultipartFile file, Authentication authentication)
			throws FileUploadException {

		User currentUser = (User) authentication.getPrincipal();

		if (!file.isEmpty()) {
			try {
				// Crop Image
				byte[] croppedImage = ImageCropper.cropImage(file.getBytes());

				UserAvatar avatar = new UserAvatar();
				avatar.setContentType(file.getContentType());
				avatar.setContentLength(file.getSize());
				avatar.setFilename(file.getName());
				avatar.setAvatar(croppedImage);
				avatar.setUserId(currentUser.getId());

				avatar = repo.save(avatar);

				return avatar.getUserId();

			}
			catch (Exception e) {
				throw new FileUploadException("Avatar not accepted.");
			}
		}
		else {
			throw new FileUploadException("Avatar not accepted.");
		}
	}

	@RequestMapping(value = "/avatar/{userId}", method = RequestMethod.GET)
	public void getAvatar(@PathVariable String userId, HttpServletResponse response) throws NotFoundException,
			IOException {
		
		UserAvatar avatar = repo.findOne(userId);

		if (avatar == null) {
			throw new NotFoundException("Avatar not found.");
		}
		
		response.setContentType(avatar.getContentType());
		response.setContentLengthLong(avatar.getContentLength());
		response.getOutputStream().write(avatar.getAvatar());

	}


}
