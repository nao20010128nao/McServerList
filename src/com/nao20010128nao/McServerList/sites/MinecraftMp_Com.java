package com.nao20010128nao.McServerList.sites;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nao20010128nao.McServerList.Server;

/**
 * Parser class for "minecraft-mp.com"
 */
public class MinecraftMp_Com implements ServerListSite {

	public MinecraftMp_Com() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public boolean matches(URL url) {
		// TODO 自動生成されたメソッド・スタブ
		return url.getHost().equalsIgnoreCase("minecraft-mp.com");
	}

	@Override
	public boolean hasMultipleServers(URL url) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		if (isPathStartsFromServers(url) & isSingleServer(url.getPath())) {
			return false;
		}
		if (isPathStartsFromServers(url) | url.getPath().replace("/", "").equals("") | !isSingleServer(url.getPath())) {
			return true;
		}
		return false;
	}

	@Override
	public List<Server> getServers(URL url) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		if (isSingleServer(url.getPath())) {
			// Single server page
			Document page = Jsoup.connect(url.toString()).userAgent("Mozilla").get();
			Elements elems = page.select("html > body > div > div > div > div > table > tbody > tr > td > strong");
			return Arrays.asList(Server.makeServerFromString(elems.get(1).html(), false));
		}
		if (isPathStartsFromServers(url) | url.getPath().replace("/", "").equals("") | !isSingleServer(url.getPath())) {
			List<Server> list = new ArrayList<>();
			Document page = Jsoup.connect(url.toString()).userAgent("Mozilla").get();
			Elements elems = page.select("html > body > div > div > table > tbody > tr > td > strong");
			for (Element e : elems) {
				String ip = e.html();
				if (ip.startsWith("#")) {
					continue;
				}
				list.add(Server.makeServerFromString(ip, false));
			}
			return list;
		}
		return null;
	}

	private boolean isPathStartsFromServers(URL url) {
		return url.getPath().replace("/", "").toLowerCase().startsWith("servers");
	}

	private boolean isSingleServer(String path) {
		String[] s = path.toLowerCase().split("\\/");
		if (s.length <= 1) {
			return false;
		}
		// System.err.println(s[1]);
		String act = s[1];
		if (act.startsWith("server-s")) {
			return true;
		}
		return false;
	}
}
