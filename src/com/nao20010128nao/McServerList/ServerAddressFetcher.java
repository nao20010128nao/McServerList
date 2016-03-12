package com.nao20010128nao.McServerList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nao20010128nao.McServerList.sites.MinecraftServersList_Org;
import com.nao20010128nao.McServerList.sites.Minecraft_Jp;
import com.nao20010128nao.McServerList.sites.Minecraftpeservers_Org;
import com.nao20010128nao.McServerList.sites.Minecraftservers_Org;
import com.nao20010128nao.McServerList.sites.Pe_Minecraft_Jp;
import com.nao20010128nao.McServerList.sites.ServerListSite;

/**
 * Finds Minecraft multiplayer IP & port from website. Cannot be instantinated.
 * */
public class ServerAddressFetcher {
	static List<ServerListSite> services = new ArrayList<>();
	static {
		services.add(new Minecraft_Jp());
		services.add(new Minecraftpeservers_Org());
		services.add(new Minecraftservers_Org());
		services.add(new MinecraftServersList_Org());
		services.add(new Pe_Minecraft_Jp());
		// services.add(new Pmmp_Jp_Net());//Unavaliable
	}

	private ServerAddressFetcher() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * Finds Minecraft multiplayer IP & port from website.
	 *
	 * @param url
	 *            An URL that is for server's detail or servers list
	 * @return A list that was found servers contained. Immutable.
	 * */
	public static List<Server> findServersInWebpage(URL url) throws IOException {
		ServerListSite service = null;
		for (ServerListSite serv : services) {
			if (serv.matches(url)) {
				service = serv;
				break;
			}
		}
		if (service == null) {
			throw new IllegalArgumentException(
					"This website is not supported: " + url);
		}
		List<Server> servers = service.getServers(url);
		if (servers == null) {
			throw new IllegalArgumentException("Unsupported webpage: " + url);
		}
		return Collections.unmodifiableList(new ArrayList<>(servers));
	}
}
