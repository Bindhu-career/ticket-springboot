package com.bigbang.api.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageHelper {

	private static Storage storage = null;

	static {
		StorageOptions storageOptions;
		try {
			storageOptions = StorageOptions.newBuilder().setProjectId(Constants.PROJECT_ID).setCredentials(
					GoogleCredentials.fromStream(new ClassPathResource(Constants.CREDENTIALS).getInputStream()))
					.build();
			storage = storageOptions.getService();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public String uploadFile(Part filePart, final String bucketName) throws IOException {

		DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");

		DateTime dt = DateTime.now(DateTimeZone.UTC);

		String dtString = dt.toString(dtf);

		final String fileName = filePart.getSubmittedFileName();

		// the inputstream is closed by default, so we don't need to close it here

		BlobInfo blobInfo =

				storage.create(

						BlobInfo

								.newBuilder(bucketName, fileName)

								// Modify access list to allow all users with link to read file

								.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))

								.build(),

						filePart.getInputStream());

		// return the public download link

		return blobInfo.getMediaLink();

	}

	/**
	 * 
	 * Extracts the file payload from an HttpServletRequest, checks that the file
	 * extension
	 * 
	 * is supported and uploads the file to Google Cloud Storage.
	 * 
	 */

	public String getImageUrl(HttpServletRequest req, HttpServletResponse res,

			final String bucket) throws IOException, ServletException {

		Part filePart = req.getPart("file");

		final String fileName = filePart.getSubmittedFileName();

		String imageUrl = req.getParameter("imageUrl");

		// Check extension of file

		if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {

			final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

			String[] allowedExt = { "jpg", "jpeg", "png", "gif" };

			for (String s : allowedExt) {

				if (extension.equals(s)) {

					return this.uploadFile(filePart, bucket);

				}

			}

			throw new ServletException("file must be an image");

		}

		return imageUrl;

	}

}