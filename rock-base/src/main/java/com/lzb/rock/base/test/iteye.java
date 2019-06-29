package com.lzb.rock.base.test;

import java.io.File;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.lzb.rock.base.util.UtilFile;
import com.lzb.rock.base.util.UtilHttp;

public class iteye {

	public static void main(String[] args) {
		// List<String> list = UtilFile.input("C:/Users/lzb/Desktop/", "555555.htm");
		// StringBuffer sb = new StringBuffer();
		// for (String str : list) {
		// sb.append(str).append("\t\n");
		// }
		// String html = sb.toString();
		// ====================================

		String url = "http://rensanning.iteye.com/blog/1538689";
		String html = UtilHttp.sendGet(url);
		/// ============================
		Document doc = Jsoup.parse(html);
		Elements el = doc.select("div[id=blog_content]");
		Elements strongs = el.select("strong");
		Elements texts = doc.select("pre[class=java]");
		Elements br = el.select("br");
		Integer count = 0;
		// for (Element element : texts) {
		// //Element strong = strongs.get(count);
		// String text = element.text();
		// //System.out.println(strong.text());
		// System.out.println(text);
		// count++;
		// }
		List<String> t = el.eachText();
		for (String string : t) {
			System.out.println(string);
		}

	}
}
