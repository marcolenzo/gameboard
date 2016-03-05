package com.marcolenzo.gameboard.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.marcolenzo.gameboard.exceptions.FileUploadException;
import com.marcolenzo.gameboard.exceptions.NotFoundException;
import com.marcolenzo.gameboard.model.User;
import com.marcolenzo.gameboard.model.UserAvatar;
import com.marcolenzo.gameboard.services.AvatarServicesTests;

@Controller
public class AvatarController {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private AvatarServicesTests avatarServices;

	@RequestMapping(value = "/avatar", method = RequestMethod.POST)
	public @ResponseBody void createAvatar(@RequestParam("file") MultipartFile file, Authentication authentication)
			throws FileUploadException {

		User currentUser = (User) authentication.getPrincipal();
		avatarServices.updateUserAvatar(currentUser, file);

	}

	@RequestMapping(value = "/avatar/{userId}", method = RequestMethod.GET)
	public void getAvatar(@PathVariable String userId, HttpServletResponse response) throws NotFoundException,
			IOException {
		
		UserAvatar avatar = avatarServices.getAvatarByUserId(userId);

		if (avatar == null) {
			Resource defaultAvatar = resourceLoader.getResource("classpath:/static/images/spy.jpeg");
			response.setContentType("image/jpeg");
			byte[] b = IOUtils.toByteArray(defaultAvatar.getInputStream());
			response.setContentLengthLong(b.length);
			response.getOutputStream().write(b);
		}
		else {
			response.setContentType("image/jpeg");
			response.setContentLengthLong(avatar.getContentLength());
			response.getOutputStream().write(avatar.getAvatar());
		}

	}


}
