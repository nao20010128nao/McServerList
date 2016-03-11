package com.nao20010128nao.McServerList.sites;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.nao20010128nao.McServerList.Server;

public class MinecraftServersList_Org implements ServerListSite {

	public MinecraftServersList_Org() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public boolean matches(URL url) {
		// TODO 自動生成されたメソッド・スタブ
		return url.getHost().equalsIgnoreCase("www.minecraft-servers-list.org");
	}

	@Override
	public boolean hasMultipleServers(URL url) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public List<Server> getServers(URL url) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		if (url.getPath().replace("/", "").toLowerCase().startsWith("details")) {
			// Single server page
			Document page = Jsoup.connect(url.toString()).userAgent("Mozilla")
					.get();
			String ip = page
					.select("html > body > div > div > section > div > div > div > div > div.content.col-md-8 > div.box > div.center > h5.text-muted > span.color")
					.get(0).html();
			String[] spl = ip.split("\\:");
			if (spl.length == 2) {
				// IP & port
				Server s = new Server();
				s.ip = spl[0];
				s.port = new Integer(spl[1]);
				s.isPE = false;
				return Arrays.asList(s);
			} else {
				// IP only
				Server s = new Server();
				s.ip = spl[0];
				s.port = 19132;
				s.isPE = false;
				return Arrays.asList(s);
			}
		}
		return null;
	}
}
