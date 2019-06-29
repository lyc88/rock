package com.lzb.rock.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * java 执行shell命令
 *
 * @author lzb 2018年2月28日 上午11:44:22
 */
public class UtilShell {
	private static Logger log = LoggerFactory.getLogger(UtilShell.class);

	public static boolean runShell(String shellFilePath) throws Exception {
		log.info("run shell:[" + shellFilePath + "]");
		boolean flag = false;

		Process process;
		String[] command = { "/bin/sh", "-c", shellFilePath };

		process = Runtime.getRuntime().exec(command);
		BufferedReader inputReader = null;
		BufferedReader brError = null;
		// 读取标准输出流
		try {
			inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = inputReader.readLine()) != null) {
				log.info("shell InputStream:" + line);
			}
		} catch (IOException e) {
			log.error("read InputStream failed!", e);
		} finally {
			if (inputReader != null) {
				inputReader.close();
			}
		}
		// 读取标准错误流
		try {
			brError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
			String errline = null;
			while ((errline = brError.readLine()) != null) {
				log.info("shell ErrorStream:" + errline);
			}
		} catch (IOException e) {
			log.error("read ErrorStream failed!", e);
		} finally {
			if (brError != null) {
				brError.close();
			}
		}
		int waitFor = process.waitFor();

		if (0 == waitFor) {
			flag = true;
		}
		return flag;
	}
}
