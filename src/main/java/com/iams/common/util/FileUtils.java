package com.iams.common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @Description: 文件管理工具类
 * @author puing
 * @date 2020年7月5日
 *
 */
public class FileUtils {

	/**
	 * 上传文件
	 * @param file 文件
	 * @param basePath 文件的存放目录
	 * @return
	 */
	public static String upload(MultipartFile file,String basePath) {
		try {
			if (file==null || file.isEmpty()) {
				//throw new ParameterException("文件为空");
				return null;
			}
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 设置文件存储路径
			String fName = UUID.randomUUID().toString().replace("-", "");
			//拼接路径
			String path = basePath + fName + suffixName;
			File dest = new File(path);
			if (!dest.getParentFile().exists()) { // 检测是否存在目录
				dest.getParentFile().mkdirs();// 新建文件夹
			}
			file.transferTo(dest);// 文件写入
			System.out.println("文件名称：" + suffixName);
			return fName + suffixName;//返回文件名称
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//throw new ParameterException("上传失败");
		return null;
	}


	public static String downloadFile(HttpServletResponse response, String realPath, String name,String loadName) {
		if (Utils.isEmpty(name)) {
			File file = new File(realPath, name);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition",
						"attachment;fileName=" + loadName);// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					System.out.println("下载文件成功");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
}
