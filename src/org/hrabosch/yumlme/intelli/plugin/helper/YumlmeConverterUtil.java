package org.hrabosch.yumlme.intelli.plugin.helper;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Helper util class for converting.
 *
 * @author hrabosch
 */
public class YumlmeConverterUtil {

	/**
	 * Encoding source for generating diagram to valid format for URL.
	 *
	 * @param source
	 * @return String which can be part of URL.
	 * @throws UnsupportedEncodingException
	 */
	public static String convertToValidUrlPart(String source) throws UnsupportedEncodingException {
		return URLEncoder.encode(source, "UTF-8").replaceAll("\\+", "%20");
	}

	/**
	 * Calling remote endpoint for generating diagram image.
	 *
	 * @param source code of diagram
	 * @return BufferedImage of generated image.
	 */
	public static BufferedImage generateDiagram(String source) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet get = new HttpGet(
					YumlmeConstants.YUMLME_URL + YumlmeConverterUtil
							.convertToValidUrlPart(source));

			CloseableHttpResponse response = client.execute(get);

			HttpEntity entity = response.getEntity();
			BufferedImage generatedImage = ImageIO.read(entity.getContent());

			return generatedImage;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
