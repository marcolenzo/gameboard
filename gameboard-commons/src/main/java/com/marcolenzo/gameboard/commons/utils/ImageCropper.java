package com.marcolenzo.gameboard.commons.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCropper {

	private ImageCropper() {

	}

	public static byte[] cropImage(byte[] bytes) {
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
			int w = img.getWidth();
			int h = img.getHeight();
			int diff = 0;
			if(w > h) {
				diff = w - h;
				int diffFloor = (int) Math.floor(diff / 2);
				img = img.getSubimage(diffFloor, 0, (int) w - diff, h);
			}
			else if (h > w) {
				diff = h - w;
				int diffFloor = (int) Math.floor(diff / 2);
				img = img.getSubimage(0, diffFloor, w, (int) h - diff);
			}
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			
			return imageInByte;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
