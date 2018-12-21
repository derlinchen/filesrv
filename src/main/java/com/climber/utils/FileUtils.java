package com.climber.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.util.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FileUtils {

	/**
	 * 将文件流转为Base64字符串
	 * 
	 * @param fis
	 *            文件流
	 * @return
	 */
	public static String encodeBase64File(FileInputStream fis) {
		String rtv = "";
		try {
			byte[] buffer = new byte[fis.available()];
			if (buffer.length > 0) {
				fis.read(buffer);
				rtv = new BASE64Encoder().encode(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return rtv;
	}

	/**
	 * 将Base64为字符串写入文件
	 * 
	 * @param filename
	 *            文件名
	 * @param fileinfo
	 *            文件Base64位字符串
	 * @param filepath
	 *            文件路径
	 * @return
	 */
	public static String decoderBase64File(String filename, String fileinfo,
			String filepath) {
		String rtv = "";
		String systempath = "";
		FileOutputStream fos = null;
		try {
			filename = DateUtils.getStamp()
					+ filename.substring(filename.lastIndexOf("."));
			if (isWindows()) {
				systempath = Consts.WINDOWS_FOLDER_PATH;
			} else {
				systempath = Consts.LINUX_FOLDER_PATH;
			}

			if (StringUtils.isEmpty(filepath)) {
				rtv = Consts.DEFAULT_FOLDER + filename;
			} else {
				rtv = filepath + filename;
			}
			
			filepath = systempath + rtv;

			File file = new File(filepath);
			if (!file.exists()) {
				// 判断文件上一级是否有文件夹，没有文件夹，先创建文件夹
				if (!file.getParentFile().exists()) {
					// 按照路径创建所有文件夹
					file.getParentFile().mkdirs();
				}
				// 创建文件
				file.createNewFile();
			}
			byte[] buffer = new BASE64Decoder().decodeBuffer(fileinfo);
			fos = new FileOutputStream(filepath);
			fos.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return rtv;
	}

	/**
	 * 判断操作系统类型
	 * @return
	 */
	public static boolean isWindows() {
		boolean isWindows = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindows = true;
		}
		return isWindows;
	}

	/**
	 * 对文件进行压缩
	 * @param file 文件
	 * @param rate 压缩比率
	 */
	public static void compressPic(File file, Float rate) {
		FileOutputStream fos = null;
		BufferedImage tag = null;
		try {
			int widthdist = 0;
			int heightdist = 0;
			if (!file.exists()) {
				return;
			}

			String filename = "p" + file.getName();
			String filepath = file.getAbsolutePath()
					.replace(file.getName(), "");

			File distfile = new File(filepath + filename);
			if (!distfile.exists()) {
				distfile.createNewFile();
			}

			int[] results = getImgWidthHeight(file);

			if (rate != null && rate > 0) {
				// 获取文件高度和宽度
				if (results == null || results[0] == 0 || results[1] == 0) {
					return;
				} else {
					widthdist = (int) (results[0] * rate);
					heightdist = (int) (results[1] * rate);
				}
			} else {
				widthdist = (int) (results[0]);
				heightdist = (int) (results[1]);
			}

			Image src = ImageIO.read(file);

			tag = new BufferedImage((int) widthdist, (int) heightdist,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					src.getScaledInstance(widthdist, heightdist,
							Image.SCALE_SMOOTH), 0, 0, null);
			fos = new FileOutputStream(distfile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(tag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (tag != null) {
					tag.flush();
				}
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 获取文件的宽度
	 * @param file
	 * @return
	 */
	public static int[] getImgWidthHeight(File file) {
		InputStream is = null;
		BufferedImage src = null;
		int result[] = { 0, 0 };
		try {
			is = new FileInputStream(file);
			src = javax.imageio.ImageIO.read(is);
			result[0] = src.getWidth(null); // 得到源图宽
			result[1] = src.getHeight(null); // 得到源图高
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (src != null) {
					src.flush();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 通过url获取文件
	 * @param imgurl
	 * @return
	 */
	public static byte[] getFileByte(String imgurl) {
		byte[] data = null;
		InputStream inStream = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(imgurl);
			// 打开链接
			conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 通过输入流获取图片数据
			inStream = conn.getInputStream();
			// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
			data = readInputStream(inStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * 将输入流转为二进制数组
	 * @param inStream
	 * @return
	 */
	public static byte[] readInputStream(InputStream inStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = inStream.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				baos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}

				if (inStream != null) {
					inStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	/**
	 * 将二进制数组写入文件
	 * @param path
	 * @param filebyte
	 * @return
	 */
	public static boolean writeFile(String path, byte[] filebyte) {
		boolean bool = false;
		FileOutputStream fos = null;
		// 实例化文件对象
		File file = new File(path);
		try {
			// 文件不存在，则对文件进行创建
			if (!file.exists()) {
				// 判断文件上一级是否有文件夹，没有文件夹，先创建文件夹
				if (!file.getParentFile().exists()) {
					// 按照路径创建所有文件夹
					file.getParentFile().mkdirs();
				}
				// 创建文件
				file.createNewFile();
				fos = new FileOutputStream(file);
				fos.write(filebyte);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bool;
	}

}
