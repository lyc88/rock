package com.lzb.rock.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件读取，删除，写入
 * @author lzb
 * 2018年2月28日 上午11:44:59
 */
public class UtilFile {
	/**
	 * 删除文件
	 * 
	 * @param root_patch
	 * @param name
	 */
	public static void delete(String rootPath, String fileName) {
		String path = "";
		if (rootPath.endsWith("/")) {
			path = rootPath + fileName;
		} else {
			path = rootPath + "/" + fileName;
		}
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 *
	 * @param str
	 *            写入字符串
	 * @param url
	 *            保存文件名字
	 */
	public static void out(String rootPath, String fileName, String str) {
		List<String> list = new ArrayList<String>();
		list.add(str);
		out(rootPath, fileName, list);
	}
	/**
	 *
	 * @param list
	 *            写入list
	 * @param url
	 *            保存文件名字
	 * @throws FileNotFoundException 
	 */
	
	public static void out(String rootPath, String fileName,byte[] b) throws Exception {
		
		String path = "";
		if (rootPath.endsWith("/")) {
			path = rootPath + fileName;
		} else {
			path = rootPath + "/" + fileName;
		}
		File file = new File(path);
		OutputStream outputstream=new FileOutputStream(file,true);
		outputstream.write(b);
		outputstream.close();
		
	}
	public static void out(String rootPath, String fileName, List<String> list) {
		String path = "";
		if (rootPath.endsWith("/")) {
			path = rootPath + fileName;
		} else {
			path = rootPath + "/" + fileName;
		}
		File file = new File(path);
		BufferedWriter writer = null;
		OutputStreamWriter out = null;
		try {
			if (!file.exists()) {
				new File(rootPath).mkdirs();// 创建目录
				file.createNewFile();// 创建文件
			} //

			// true 表示追加
			out = new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8");
			writer = new BufferedWriter(out);
			for (String str : list) {
				writer.write(str);
				writer.newLine();
			}
			writer.flush();
			// System.out.println("str:" + str + ",保存文本成功");

		} catch (IOException e) {
			System.out.println("文件不存在");
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return List<String>
	 */
	public static List<String> input(String rootPath, String fileName) {
		String path = "";
		if (rootPath.endsWith("/")) {
			path = rootPath + fileName;
		} else {
			path = rootPath + "/" + fileName;
		}

		String str = new String();
		List<String> list = new ArrayList<String>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			System.out.println("文件不存在");
			e1.printStackTrace();
		}

		if (fis != null) {
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
				while ((str = br.readLine()) != null) {
					list.add(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (isr != null) {
						isr.close();
					}
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	public static void main(String[] args) {
		UtilFile.delete("G:/", "rinetd.conf-20180228105538325");
	}
}
