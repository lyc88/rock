package com.lzb.rock.pdf.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

/**
 * 
 * @author lzb 2018年7月4日 下午3:32:35 参考
 *         https://blog.csdn.net/zmx729618/article/details/52150070
 *         http://rensanning.iteye.com/blog/1538689
 */
public class UtilPdf {

	public static Font getChineseFont() {
		
		return getChineseFont(12);
	}
	
	/**
	 * 设置支持中文编码
	 * 
	 * @return
	 */
	public static Font getChineseFont(float size) {
		BaseFont bfChinese;
		Font fontChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			// fontChinese = new Font(bfChinese, 12, Font.NORMAL);
			fontChinese = new Font(bfChinese, size, Font.NORMAL, BaseColor.BLACK);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fontChinese;
	}

	/**
	 * 
	 * @param InPdfFile          文件输入路径
	 * @param outPdfFile         文件输出路径
	 * @param textMark           水印文字
	 * @param maxWidth
	 * @param maxHeight
	 * @param textWidthInterval
	 * @param textHeightInterval
	 * @throws Exception
	 */
	public static void addPdfTextMark(String InPdfFile, String outPdfFile, String textMark, int maxWidth, int maxHeight,
			int textWidthInterval, int textHeightInterval) throws Exception {
		PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
		PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(new File(outPdfFile)));

		PdfContentByte under;
		//设置中文字体
		BaseFont font = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

		int pageSize = reader.getNumberOfPages();// 原pdf文件的总页数
		for (int i = 1; i <= pageSize; i++) {
			 under = stamp.getUnderContent(i);// 水印在之前文本下
			//under = stamp.getOverContent(i);// 水印在之前文本上
			under.beginText();
			under.setColorFill(new BaseColor(130, 130, 130));// 文字水印 颜色
			under.setFontAndSize(font, 25);
			// 循环生成水印
			for (int j = 0; j < maxHeight; j = j + textHeightInterval) {
				for (int j2 = 0; j2 < maxWidth; j2 = j2 + textWidthInterval) {
					under.setTextMatrix(j2, j);// 文字水印 起始位置
					under.showTextAlignedKerned(Element.ALIGN_CENTER, textMark, j2, j, 45);
					// under.showTextAlignedKerned(alignment, text, x, y, rotation);
				}

			}

			under.endText();
		}
		stamp.close();// 关闭
	}

	static String FILE_DIR = "f://";

	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		FileOutputStream out = new FileOutputStream(FILE_DIR + "testPDF.pdf");

		Rectangle rect = new Rectangle(PageSize.B5.rotate());
		// 设置页面背景
		rect.setBackgroundColor(BaseColor.WHITE);
		// 创建
		Document doc = new Document(rect);
		// 设置输出路径
		PdfWriter writer = PdfWriter.getInstance(doc, out);
		// PDF版本(默认1.4)
		writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);

		// 文档属性
		// 添加标题
		doc.addTitle("标题");
		// 作者
		doc.addAuthor("作者");
		// 主题
		doc.addSubject("主题");
		// 关键字
		doc.addKeywords("关键字");

		// 页边空白
		doc.setMargins(10, 10, 10, 10);

		writer.setPageEmpty(true);// 不会显示空内容的页

		// 设置密码 两个密码
		// writer.setEncryption("111".getBytes(), "222".getBytes(),
		// PdfWriter.ALLOW_SCREENREADERS,
		// PdfWriter.STANDARD_ENCRYPTION_128);

		// 打开文档
		doc.open();

		// 创建页面
		Paragraph page = new Paragraph();
		page.add("Hello World");
		doc.add(page);

		// Chunk对象: a String, a Font, and some attributes
		doc.add(new Chunk("China"));
		doc.add(new Chunk(" "));
		Font font = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
		Chunk id = new Chunk("chinese", font);
		id.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
		id.setTextRise(6);
		doc.add(id);
		doc.add(Chunk.NEWLINE);

		doc.add(new Chunk("Japan"));
		doc.add(new Chunk(" "));
		Font font2 = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
		Chunk id2 = new Chunk("japanese", font2);
		id2.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
		id2.setTextRise(6);
		id2.setUnderline(0.2f, -2f);
		doc.add(id2);
		doc.add(Chunk.NEWLINE);
		// Phrase对象: a List of Chunks with leading
		doc.newPage();
		doc.add(new Phrase("Phrase page"));

		Phrase director = new Phrase();
		Chunk name = new Chunk("China");
		name.setUnderline(0.2f, -2f);
		director.add(name);
		director.add(new Chunk(","));
		director.add(new Chunk(" "));
		director.add(new Chunk("chinese"));
		director.setLeading(24);
		doc.add(director);

		Phrase director2 = new Phrase();
		Chunk name2 = new Chunk("Japan");
		name2.setUnderline(0.2f, -2f);
		director2.add(name2);
		director2.add(new Chunk(","));
		director2.add(new Chunk(" "));
		director2.add(new Chunk("japanese"));
		director2.setLeading(24);
		doc.add(director2);

		// Paragraph对象: a Phrase with extra properties and a newline
		doc.newPage();
		doc.add(new Paragraph("Paragraph page"));

		Paragraph info = new Paragraph();
		info.add(new Chunk("China "));
		info.add(new Chunk("chinese"));
		info.add(Chunk.NEWLINE);
		info.add(new Phrase("Japan "));
		info.add(new Phrase("japanese"));
		doc.add(info);

		// List对象: a sequence of Paragraphs called ListItem
		doc.newPage();
		List list = new List(List.ORDERED);
		// for (int i = 0; i < 10; i++) {
		// ListItem item = new ListItem(String.format("%s: %d movies",
		// "country" + (i + 1), (i + 1) * 100), new Font(
		// Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE));
		// List movielist = new List(List.ORDERED, List.ALPHABETICAL);
		// movielist.setLowercase(List.LOWERCASE);
		// for (int j = 0; j < 5; j++) {
		// ListItem movieitem = new ListItem("Title" + (j + 1));
		// List directorlist = new List(List.UNORDERED);
		// for (int k = 0; k < 3; k++) {
		// directorlist.add(String.format("%s, %s", "Name1" + (k + 1),
		// "Name2" + (k + 1)));
		// }
		// movieitem.add(directorlist);
		// movielist.add(movieitem);
		// }
		// item.add(movielist);
		// list.add(item);
		// }
		// doc.add(list);

		// 添加Page 画图
		doc.newPage();
		// 左右箭头
		doc.add(new VerticalPositionMark() {

			public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
				canvas.beginText();
				BaseFont bf = null;
				try {
					bf = BaseFont.createFont(BaseFont.ZAPFDINGBATS, "", BaseFont.EMBEDDED);
				} catch (Exception e) {
					e.printStackTrace();
				}
				canvas.setFontAndSize(bf, 12);

				// LEFT
				canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf((char) 220), llx - 10, y, 0);
				// RIGHT
				canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf((char) 220), urx + 10, y + 8, 180);

				canvas.endText();
			}
		});

		// 直线
		Paragraph p1 = new Paragraph("LEFT");
		p1.add(new Chunk(new LineSeparator()));
		p1.add("R");
		doc.add(p1);
		// 点线
		Paragraph p2 = new Paragraph("LEFT");
		p2.add(new Chunk(new DottedLineSeparator()));
		p2.add("R");
		doc.add(p2);
		// 下滑线
		LineSeparator UNDERLINE = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);
		Paragraph p3 = new Paragraph("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
		p3.add(UNDERLINE);
		doc.add(p3);
		// 设置段落
		doc.newPage();
		Paragraph p = new Paragraph(
				"In the previous example, you added a header and footer with the showTextAligned() method. This example demonstrates that it’s sometimes more interesting to use PdfPTable and writeSelectedRows(). You can define a bottom border for each cell so that the header is underlined. This is the most elegant way to add headers and footers, because the table mechanism allows you to position and align lines, images, and text.");

		// 默认
		p.setAlignment(Element.ALIGN_JUSTIFIED);
		doc.add(p);

		doc.newPage();
		p.setAlignment(Element.ALIGN_JUSTIFIED);
		p.setIndentationLeft(1 * 15f);
		p.setIndentationRight((5 - 1) * 15f);
		doc.add(p);

		// 居右
		doc.newPage();
		p.setAlignment(Element.ALIGN_RIGHT);
		p.setSpacingAfter(15f);
		doc.add(p);

		// 居左
		doc.newPage();
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(15f);
		doc.add(p);

		// 居中
		doc.newPage();
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingAfter(15f);
		p.setSpacingBefore(15f);
		doc.add(p);

		// 关闭pdf文档
		doc.close();
	}
}
