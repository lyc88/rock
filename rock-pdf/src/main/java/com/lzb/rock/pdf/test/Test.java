package com.lzb.rock.pdf.test;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.lzb.rock.pdf.util.UtilPdf;

/**
 * https://blog.csdn.net/sanqima/article/details/50374151
 * 
 * @author lzb
 *
 *         2018年11月26日 下午6:41:10
 */
public class Test {
	static String FILE_DIR = "/f:/";

	/**
	 * 创建PDF
	 * 
	 * @throws Exception
	 */
	public static void createPdf() throws Exception {

		FileOutputStream out = new FileOutputStream(FILE_DIR + "createSamplePDF.pdf");

		// 页面大小
		// Rectangle rect = new Rectangle(PageSize.HALFLETTER.rotate());
		//设置大小
		Rectangle rect = new Rectangle(0, 0, 200, 400);

		// 页面背景色,设置水印的时候不得设置背景色
		// rect.setBackgroundColor(BaseColor.WHITE);

		Document doc = new Document(rect);
		// PDF版本(默认1.4)
		PdfWriter writer = PdfWriter.getInstance(doc, out);
		writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);

		doc.addTitle("zhh@ouyi.com"); // 标题
		doc.addAuthor("zhh@ouyi.com"); // 作者
		doc.addSubject("zhh@ouyi.com"); // 主题
		doc.addKeywords("zhh@ouyi.com"); // 关键字
		doc.addCreator("zhh@ouyi.com");
		// 页边空白
		doc.setMargins(10, 20, 30, 40);

		PdfContentByte waterMar = new PdfContentByte(writer);
		doc.open();

		Paragraph page = new Paragraph();

		page.add(new Chunk("昵称： 阿狸先森", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("手机号： 18589095668", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("会员卡号： 1000000402", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("车架号： ", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("车牌号： 粤B4582", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("本次消费金额：50.25元", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("本次积分抵扣金额：1.5元", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("获得积分：2", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("获得成长值：5", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("消费时间：2018-11-27 10:49:50 ", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("订单号：489", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("卡片余额：486元", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("卡片积分余额：15", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("卡片成长值：25", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);
		page.add(Chunk.NEWLINE);
		page.add(Chunk.NEWLINE);

		page.add(new Chunk("签名：", UtilPdf.getChineseFont()));
		page.add(Chunk.NEWLINE);

//		doc.add(new Chunk("中文输出： ", UtilPdf.getChineseFont()));
//		Chunk tChunk2 = new Chunk("输出的内容", UtilPdf.getChineseFont());
//		tChunk2.setBackground(BaseColor.CYAN, 1f, 0.5f, 1f, 1.5f); // 设置背景色
//		tChunk2.setTextRise(6); // 上浮
//		tChunk2.setUnderline(0.2f, -2f); // 下划线
//		doc.add(tChunk2);
		doc.add(page); // 新建一行
		// document.add(new Phrase("Phrase page :")); //会上浮，不知道原因？？</span>
		doc.close();
		out.close();
		// addPdfTextMark(FILE_DIR + "createSamplePDF.pdf", FILE_DIR +
		// "createSamplePDF2.pdf", "4564asdasdads", 0,0);
		UtilPdf.addPdfTextMark(FILE_DIR + "createSamplePDF.pdf", FILE_DIR + "createSamplePDF2.pdf", "尊鸿荟", 200, 400, 80,
				105);
		System.out.println("====>");
	}

	/**
	 * 
	 * <br>
	 * <p>
	 * Description: 给pdf文件添加水印<br>
	 * 
	 * @param InPdfFile  要加水印的原pdf文件路径
	 * @param outPdfFile 加了水印后要输出的路径
	 * @param textMark   水印文字
	 * @param textWidth  文字横坐标
	 * @param textHeight 文字纵坐标
	 * @throws Exception
	 * @see void
	 */
	public static void addPdfTextMark(String InPdfFile, String outPdfFile, String textMark, int textWidth,
			int textHeight) throws Exception {
		PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));

		PdfContentByte under;

		BaseFont font = BaseFont.createFont();// 使用系统字体

		int pageSize = reader.getNumberOfPages();// 原pdf文件的总页数
		for (int i = 1; i <= pageSize; i++) {
			// under = stamp.getUnderContent(i);// 水印在之前文本下
			under = stamp.getOverContent(i);// 水印在之前文本上
			under.beginText();
			under.setColorFill(new BaseColor(211, 211, 211));// 文字水印 颜色
			under.setFontAndSize(font, 45);// 文字水印 字体及字号
			under.setTextMatrix(textWidth, textHeight);// 文字水印 起始位置
			under.showTextAligned(Element.ALIGN_CENTER, textMark, textWidth, textHeight, 45);
			under.endText();
		}
		stamp.close();// 关闭
	}

	/**
	 * 设置水印
	 * 
	 * @throws Exception
	 */
	public static void waterMarPdf(PdfContentByte waterMar) throws Exception {

		// 开始设置水印
		waterMar.beginText();
		// 设置水印透明度
		PdfGState gs = new PdfGState();
		// 设置填充字体不透明度为0.4f
		gs.setFillOpacity(0.8f);
		// 设置水印字体参数及大小 (字体参数，字体编码格式，是否将字体信息嵌入到pdf中（一般不需要嵌入），字体大小)
		waterMar.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 60);
		// 设置透明度
		waterMar.setGState(gs);
		// 设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
		waterMar.showTextAligned(Element.ALIGN_RIGHT, "www.tomatocc.com", 500, 400, 45);
		// 设置水印颜色
		waterMar.setColorFill(BaseColor.GRAY);
		// 结束设置
		waterMar.endText();
		waterMar.stroke();

	}

	public static void main(String[] args) throws Exception {
		Test.createPdf();
	}

}
