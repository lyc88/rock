package com.lzb.rock.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * excl 导出类
 * 
 * @author lzb 2018年7月4日 上午9:16:29
 */
public class UtilExcl {

	/**
	 * 获取表格样式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getStyle(HSSFWorkbook wb) {
		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		// 设置背景颜色
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		// 设置无边框
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置字体
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10); // 字体高度
		font.setFontName(" 黑体 "); // 字体
		font.setColor(HSSFColor.BLACK.index);
		// 设置对齐方式
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.DISTRIBUTED);

		style.setWrapText(true);
		style.setFont(font);
		return style;
	}

	public static HSSFCellStyle getStyleHead(HSSFWorkbook wb) {
		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		// 设置背景颜色
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		// 设置无边框
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置字体
		HSSFFont font = wb.createFont();

		font.setFontHeightInPoints((short) 10); // 字体高度
		font.setFontName(" 黑体 "); // 字体
		font.setColor(HSSFColor.BLACK.index);
		// 设置加粗
		font.setBold(true);
		// 设置对齐方式
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.DISTRIBUTED);
		style.setWrapText(true);
		style.setFont(font);
		return style;
	}

	/**
	 * 设置表格内容
	 * 
	 * @param style
	 * @param text
	 * @param cell
	 */
	public static void cell(HSSFCellStyle style, String text, HSSFCell cell) {
		// 设置cell样式
		cell.setCellStyle(style);

		// 设置cell文本内容
		if (text == null) {
			text = "";
		}
		cell.setCellValue(new HSSFRichTextString(text));
	}

	/**
	 * 新增一行
	 * 
	 * @param texts     数据集合
	 * @param sheet
	 * @param wb
	 * @param count     行号
	 * @param patriarch
	 * @param style     表格样式
	 */
	public static void addRows(List<String> texts, HSSFSheet sheet, HSSFWorkbook wb, Integer count,
			HSSFPatriarch patriarch, HSSFCellStyle style) {
		HSSFRow row1 = sheet.createRow(count);
		row1.setHeight((short) 450);// 设定行的高度
		for (int i = 0; i < texts.size(); i++) {
			HSSFCell cell = row1.createCell(i);
			cell(style, texts.get(i), cell);
		}
	}

	/**
	 * 生成excl表格
	 * 
	 * @param sheetName sheet 名称
	 * @param list      数据集合
	 * @return
	 * @throws Exception
	 */

	public static HSSFWorkbook getWorkbook(String sheetName, List<List<String>> list) {
		HSSFWorkbook wb = new HSSFWorkbook();// 创建Excel工作簿对象
		HSSFSheet sheet = wb.createSheet(sheetName);// 创建Excel工作表对象
		// 设置整列格式
		CellStyle textStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		textStyle.setDataFormat(format.getFormat("@"));
		for (int i = 0; i < list.get(0).size(); i++) {
			sheet.setDefaultColumnStyle(i, textStyle);
		}
		// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 获取样式
		HSSFCellStyle style = getStyle(wb);
		for (int i = 0; i < list.size(); i++) {
			List<String> texts = list.get(i);
			if (i == 0) {
				addRows(texts, sheet, wb, i, patriarch, getStyleHead(wb));
			} else {
				addRows(texts, sheet, wb, i, patriarch, style);
			}

		}
		return wb;
	}

	/**
	 * 合并单元格
	 * 
	 * @param region
	 * @return
	 */
	public static void addMergedRegion(HSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {

		CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		// 合并到
		sheet.addMergedRegion(region);
		// 得到区域
		// sheet.getNumMergedRegions();
	}

	/**
	 * 发送流到前端
	 * 
	 * @param request
	 * @param response
	 * @param workBook
	 * @param filename
	 * @throws Exception
	 */
	public static void out(HttpServletRequest request, HttpServletResponse response, HSSFWorkbook workBook,
			String filename) throws Exception {
		response.setContentType("application/force-download");// 设置强制下载不打开
		// String name = URLEncoder.encode(filename, "utf-8");
		String name = new String(filename.getBytes(), "iso-8859-1");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + name);
		OutputStream ouputStream = response.getOutputStream();
		workBook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * 
	 * 解析XML
	 * 
	 * @param maxColumn     最大列数
	 * @param workbookIndex 第几workbook 0 开始
	 * @param inputStream   输入流
	 * @param heard         头部跟字段对应 key excl头部 value 字段名
	 * @param clazz         list 泛型
	 * @return
	 */
	public static <T> List<T> importExcel(Integer maxColumn, Integer workbookIndex, InputStream inputStream,
			Map<String, String> heard, Class<T> clazz) {

		List<Map<String, String>> rows = importExcel(maxColumn, workbookIndex, inputStream, heard);
		List<T> list = UtilJson.getJavaList(UtilJson.getStr(rows), clazz);

		return list;

	}

	/**
	 * 解析XML
	 * 
	 * @param maxColumn     最大列数
	 * @param workbookIndex 第几workbook 0 开始
	 * @param inputStream   输入流
	 * @param heard         头部跟字段对应 key excl头部 value 字段名
	 * @return
	 */
	public static List<Map<String, String>> importExcel(Integer maxColumn, Integer workbookIndex,
			InputStream inputStream, Map<String, String> heard) {
		List<Map<String, String>> rows = importExcel(maxColumn, workbookIndex, inputStream);
		List<Map<String, String>> rowsNew = new ArrayList<>();
		for (Map<String, String> map : rows) {
			Map<String, String> mapNew = new HashMap<>();
			boolean flag = false;
			for (Entry<String, String> entry : heard.entrySet()) {
				String value = map.get(entry.getKey());
				if (UtilString.isNotBlank(value)) {
					mapNew.put(entry.getValue(), value);
					flag = true;
				}
			}
			if (flag) {
				rowsNew.add(mapNew);
			}
		}
		return rowsNew;
	}

	/**
	 * 解析EXCL
	 * 
	 * @param maxColumn     最大列
	 * @param workbookIndex 第几workbook 0 开始
	 * @param inputStream   文件流
	 * @return List<Map<String,String>>
	 * @throws Exception
	 */
	public static List<Map<String, String>> importExcel(Integer maxColumn, Integer workbookIndex,
			InputStream inputStream) {
		List<Map<String, String>> rows = new ArrayList<>();
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return rows;
		}
		HSSFSheet hssfSheet = workbook.getSheetAt(workbookIndex);// 获取第一个工作表

		Map<Integer, String> heard = new HashMap<>();
		for (Row row : hssfSheet) {
			int rowNum = row.getRowNum();
			if (rowNum == 0) {// 跳出第一行 一般第一行都是表头没有数据意义
				for (int i = 0; i < maxColumn; i++) {
					if (row.getCell(i) != null) {
						heard.put(i, row.getCell(i).getStringCellValue());
					}
				}
			} else {
				Map<String, String> rowMap = new HashMap<>();
				for (int i = 0; i < maxColumn; i++) {
					if (row.getCell(i) != null) {
						row.getCell(i).setCellType(CellType.STRING);
						rowMap.put(heard.get(i), row.getCell(i).getStringCellValue());
					}
				}
				rows.add(rowMap);
			}
		}
		return rows;
	}

//	public static void main(String[] args) throws IOException {
//		// 生成测试数据
//		List<List<String>> list = new ArrayList<>();
//		for (int i = 0; i < 655; i++) {
//			List<String> texts = new ArrayList<>();
//			for (int j = 0; j < 10; j++) {
//				texts.add("text" + i + "," + j);
//			}
//			list.add(texts);
//		}
//		UtilExcl excl = new UtilExcl();
//		HSSFWorkbook workBook = excl.getWorkbook("测试", list);
//		File file = new File("G:\\text2.xls");
//		workBook.write(file);
//	}
}
