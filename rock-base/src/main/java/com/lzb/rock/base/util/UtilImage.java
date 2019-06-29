package com.lzb.rock.base.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.util.Base64Utils;

import com.lzb.rock.base.exception.UtilExceptionStackTrace;

import lombok.extern.slf4j.Slf4j;

/**
 * 图片生成类
 * 
 * @author lzb
 *
 */
@Slf4j
public class UtilImage {

	/**
	 * 生成图片 返回base64 格式图片
	 * 
	 * @param width  图片宽度 像素点
	 * @param height 图片高度 像素点
	 * @param colors 图片二维数组
	 * @return
	 */
	public static String getImageBase64(Integer width, Integer height, List<List<Color>> colors) {
		/**
		 * 得到图片缓冲区,INT精确度达到一定,RGB三原色，高度70,宽度150
		 */
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		/**
		 * 得到它的绘制环境(这张图片的笔)
		 */
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		// 画图
		Integer x = 0;
		Integer y = 0;
		for (List<Color> list : colors) {
			y = 0;
			for (Color color : list) {
				g2.setColor(color);
				g2.fillRect(x, y, 1, 1);// 填充整张图片(其实就是设置背景颜色)
				y++;
			}
			x++;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "JPEG", outputStream);
			
		
		} catch (IOException e) {
			log.error("图片生成失败:{}", UtilExceptionStackTrace.getStackTrace(e));
			try {
				outputStream.close();
			} catch (IOException e1) {
				log.warn("outputStream 关闭异常:{}", UtilExceptionStackTrace.getStackTrace(e1));
			}
			return "";
		} // 保存图片 JPEG表示保存格式
		String base64Img = Base64Utils.encodeToString(outputStream.toByteArray());
		try {
			outputStream.close();
		} catch (IOException e) {
			log.warn("outputStream 关闭异常:{}", UtilExceptionStackTrace.getStackTrace(e));
		}
		String res = "data:image/jpg;base64," + base64Img;
		return res;
	}

	public static void main(String[] args) {

		Integer maxX = 700;
		Integer maxY = 700;
		List<List<Color>> colors = new ArrayList<>(maxX);

		for (int i = 0; i < maxX; i++) {
			List<Color> rows = new ArrayList<>(maxY);
			for (int j = 0; j < maxY; j++) {
				Integer b = Math.abs(i - 350) * Math.abs(i - 350) + Math.abs(j - 350) * Math.abs(j - 350);
				Color color = null;
				if (b < 5000 && b > 4800) {
					//color = new Color(0, 0, 0);
					color = new Color(0xFF6A6A);
				} else {
					color = new Color(0xFFFFFF);
				}
				rows.add(color);
			}
			colors.add(rows);
		}
		String res = UtilImage.getImageBase64(700, 700, colors);
		UtilFile.out("/F:/", "cc.text", res);
	}
}
