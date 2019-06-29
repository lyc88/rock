package com.lzb.rock.pdf.doc;
//package com.jiahong.pdf.util;
//
//
//import java.io.File;
//import java.io.FileOutputStream;
// 
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfGState;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.itextpdf.text.pdf.PdfWriter;
// 
///**
// * <br>
// * <p>
// * Description: 审核意见生成pdf工具类 <br>
// * 注意：此工具类生成pdf支持中文，故需要系统含有宋体（SIMSUM.ttc）字体 <br>
// * <p>
// * <br/>
// * <p>
// * 
// * @author Mr_yao
// *
// */
//public class ItextPdfUtil {
// 
//	private static final String WINDOWS_FILEPATH = "D:\\";
//	private static final String LINUX_FILEPATH = "/usr/weaver/ecology/loyo/biddingContract/opinionPdf/";
//	// 宋体（对应css中的 属性 font-family: SimSun; /*宋体*/）
//	private static final String WINDOWS_FONTS = "C:\\Windows\\Fonts\\SIMSUN.TTC";
//	private static final String LINUX_FONTS = "/usr/share/fonts/SIMSUN.TTC";
//	private static final BaseColor BORDER_COLOR = new BaseColor(240, 255, 255);
//	private static final BaseColor HCELL_BACKGROUNDCOLOR = new BaseColor(111, 168, 220);// 175, 238, 238
//	private static final BaseColor BCELL_NAME_COLOR = new BaseColor(65, 105, 225);// 72, 209, 204
//	private static final BaseColor BCELL_OPINION_BACKGROUNDCOLOR = new BaseColor(220, 220, 220);
//	private static final float TABLE_BORDER_WIDTH = 0.5f;
// 
//	public static void createPDF() {
//		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Document document = new Document(PageSize.A4, 0, 0, 50, 0);
// 
//		try {
//			BaseFont bf = BaseFont.createFont(getChineseFont() + ",1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 注意这里有一个,1
//			Font titleFont = new Font(bf, 15, Font.BOLD);
//			Font subTitleFont = new Font(bf, 10, Font.NORMAL);
//			Font tableFont = new Font(bf, 13, Font.NORMAL);
// 
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(getFilePath() + "first.pdf"));
//			// file.getParentFile().mkdirs();
//			document.addTitle("***审批意见");
//			document.addCreationDate();
//			document.open();
//			Paragraph title = new Paragraph("***审批意见", titleFont);
//			title.setAlignment(Element.ALIGN_CENTER);
//			Paragraph subTitle = new Paragraph("组建工作组\n\n\n", subTitleFont);
//			subTitle.setAlignment(Element.ALIGN_CENTER);
//			document.add(title);
//			document.add(subTitle);
//			document.add(createTable(tableFont));
//			document.close();
//			writer.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
// 
//	public static PdfPTable createTable(Font font) throws Exception {
//		PdfPTable table = new PdfPTable(6);// 创建一个有6列的表格
//		createTHead(table, font);
//		createTBody(table, font);
//		return table;
//	}
// 
//	public static void createTHead(PdfPTable table, Font font) throws Exception {
//		table.addCell(getHCell("姓名", font));
//		table.addCell(getHCell("部门", font));
//		table.addCell(getHCell("意见", font));
//		table.addCell(getHCell("接收人", font));
//		table.addCell(getHCell("审批时间", font));
//		table.addCell(getHCell("审批节点", font));
//	}
// 
//	public static PdfPCell getHCell(String name, Font font) {
//		PdfPCell cell = new PdfPCell(new Phrase((name), font));
//		cell.setBorderColor(BORDER_COLOR);
//		cell.setBackgroundColor(HCELL_BACKGROUNDCOLOR);
//		cell.setBorderWidthTop(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthBottom(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthLeft(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthRight(TABLE_BORDER_WIDTH);
//		return cell;
//	}
// 
//	public static void createTBody(PdfPTable table, Font font) {
//		Font nameFont = new Font(font);
//		nameFont.setColor(BCELL_NAME_COLOR);
//		for (int i = 0; i < 5; i++) {
//			PdfPCell cell = new PdfPCell(new Phrase("张三", nameFont));
//			cell.setBorderWidth(TABLE_BORDER_WIDTH);
//			cell.setBorderColor(BORDER_COLOR);
//			table.addCell(cell);
//			table.addCell(getBCommonCell("管理部", font));
//			table.addCell(getBOpinionCell("同意", font));
//			table.addCell(getBCommonCell("张三", font));
//			table.addCell(getBCommonCell("2018-09-17 12:00:00", font));
//			table.addCell(getBCommonCell("A", font));
//		}
//	}
// 
//	public static PdfPCell getBCommonCell(String value, Font font) {
//		PdfPCell cell = new PdfPCell(new Phrase(value, font));
//		cell.setBorderColor(BORDER_COLOR);
//		cell.setBorderWidthTop(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthBottom(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthLeft(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthRight(TABLE_BORDER_WIDTH);
//		return cell;
//	}
// 
//	public static PdfPCell getBOpinionCell(String value, Font font) {
//		PdfPCell cell = new PdfPCell(new Phrase(value, font));
//		cell.setBackgroundColor(BCELL_OPINION_BACKGROUNDCOLOR);
//		cell.setBorderColor(BORDER_COLOR);
//		cell.setBorderWidthTop(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthBottom(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthLeft(TABLE_BORDER_WIDTH);
//		cell.setBorderWidthRight(TABLE_BORDER_WIDTH);
//		return cell;
//	}
// 
//	/**
//	 * 
//	 * <br>
//	 * <p>
//	 * Description: 获取中文字体位置 <br>
//	 * <p>
//	 * <br/>
//	 * <p>
//	 * 
//	 * @return String 系统字体位置
//	 */
//	private static String getChineseFont() {
//		String font1 = WINDOWS_FONTS;
//		// 判断系统类型，加载字体文件
//		String osName = getOsName();
//		if (osName.indexOf("linux") > -1) {
//			font1 = LINUX_FONTS;
//		}
//		if (!new File(font1).exists()) {
//			throw new RuntimeException("字体文件不存在,影响导出pdf中文显示！" + font1);
//		}
//		return font1;
//	}
// 
//	/**
//	 * 
//	 * <br>
//	 * <p>
//	 * Description: 根据系统类型自动选择文件路径 <br>
//	 * <p>
//	 * <br/>
//	 * <p>
//	 * 
//	 * @return
//	 */
//	public static String getFilePath() {
//		String osName = getOsName();
//		if (osName.indexOf("linux") > -1) {
//			return LINUX_FILEPATH;
//		} else {
//			return WINDOWS_FILEPATH;
//		}
//	}
// 
//	/**
//	 * 
//	 * <br>
//	 * <p>
//	 * Description: 获取系统类型名 <br>
//	 * <p>
//	 * <br/>
//	 * <p>
//	 * 
//	 * @return
//	 */
//	public static String getOsName() {
//		java.util.Properties prop = System.getProperties();
//		return prop.getProperty("os.name").toLowerCase();
//	}
// 
//	/**
//	 * 
//	 * <br>
//	 * <p>
//	 * Description: 给pdf文件添加水印 <br>
//	 * <p>
//	 * <br/>
//	 * <p>
//	 * 
//	 * @param InPdfFile
//	 *            要加水印的原pdf文件路径
//	 * @param outPdfFile
//	 *            加了水印后要输出的路径
//	 * @param markImagePath
//	 *            水印图片路径
//	 * @param imgWidth
//	 *            图片横坐标
//	 * @param imgHeight
//	 *            图片纵坐标
//	 * @throws Exception
//	 * @see void
//	 */
//	public static void addPdfImgMark(String InPdfFile, String outPdfFile, String markImagePath, int imgWidth,
//			int imgHeight) throws Exception {
//		PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
//		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));
// 
//		PdfContentByte under;
// 
//		PdfGState gs1 = new PdfGState();
//		gs1.setFillOpacity(0.3f);// 透明度设置
// 
//		Image img = Image.getInstance(markImagePath);// 插入图片水印
// 
//		img.setAbsolutePosition(imgWidth, imgHeight); // 坐标
//		img.setRotation(-20);// 旋转 弧度
//		img.setRotationDegrees(45);// 旋转 角度
//		img.scaleAbsolute(700, 80);// 自定义大小
//		// img.scalePercent(50);//依照比例缩放
// 
//		int pageSize = reader.getNumberOfPages();// 原pdf文件的总页数
//		for (int i = 1; i <= pageSize; i++) {
//			under = stamp.getUnderContent(i);// 水印在之前文本下
//			// under = stamp.getOverContent(i);//水印在之前文本上
//			under.setGState(gs1);// 图片水印 透明度
//			under.addImage(img);// 图片水印
//		}
// 
//		stamp.close();// 关闭
//	}
// 
//	/**
//	 * 
//	 * <br>
//	 * <p>
//	 * Description: 给pdf文件添加水印<br>
//	 * 
//	 * @param InPdfFile
//	 *            要加水印的原pdf文件路径
//	 * @param outPdfFile
//	 *            加了水印后要输出的路径
//	 * @param textMark
//	 *            水印文字
//	 * @param textWidth
//	 *            文字横坐标
//	 * @param textHeight
//	 *            文字纵坐标
//	 * @throws Exception
//	 * @see void
//	 */
//	public static void addPdfTextMark(String InPdfFile, String outPdfFile, String textMark, int textWidth,
//			int textHeight) throws Exception {
//		PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
//		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));
// 
//		PdfContentByte under;
// 
//		BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", "Identity-H", true);// 使用系统字体
// 
//		int pageSize = reader.getNumberOfPages();// 原pdf文件的总页数
//		for (int i = 1; i <= pageSize; i++) {
//			under = stamp.getUnderContent(i);// 水印在之前文本下
//			// under = stamp.getOverContent(i);//水印在之前文本上
//			under.beginText();
//			under.setColorFill(new BaseColor(211,211,211));// 文字水印 颜色
//			under.setFontAndSize(font, 38);// 文字水印 字体及字号
//			under.setTextMatrix(textWidth, textHeight);// 文字水印 起始位置
//			under.showTextAligned(Element.ALIGN_CENTER, textMark, textWidth, textHeight, 45);
//			under.endText();
//		}
//		stamp.close();// 关闭
//	}
// 
//	public static void main(String[] args) {
//		// createPDF();
//		try {
//			//addPdfImgMark("d:\\first.pdf", "d:\\first-photo.pdf", "d:\\first.jpg", 0, 100);
//			addPdfTextMark("d:\\first.pdf","d:\\first-text.pdf","***审批意见",300,400);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
// 
//}
