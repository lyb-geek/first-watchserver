package com.demo.watchserver.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class WatcherUtil {
	private static Logger logger = LoggerFactory.getLogger(WatcherUtil.class);

	private WatcherUtil() {
	}

	public static WatcherUtil getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	private static enum Singleton {
		INSTANCE;

		private WatcherUtil singleton;

		// JVM会保证此方法绝对只调用一次
		private Singleton() {
			singleton = new WatcherUtil();
		}

		public WatcherUtil getInstance() {
			return singleton;
		}
	}

	private WatchService watchService;

	public void registerWatcher(String configName) throws IOException {
		logger.info("{},registerWathcer....", configName);
		// 获取文件系统的WatchService对象
		watchService = FileSystems.getDefault().newWatchService();
		// 获取要监听的文件夹目录
		String filePath = new ClassPathResource(configName).getFile().getParent();
		Path path = Paths.get(filePath);
		// 注册要监监听指定目录的那些事件（创建、修改、删除）
		path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE);

		PropsUtil.getInstance().loadProperties(configName);
	}

	public void executeWatcher() {
		Thread watcherThread = new Thread(new WatcherThread(watchService));
		watcherThread.setName("watcherThread");
		watcherThread.setDaemon(true);
		watcherThread.start();
	}

	public void shutDownWatcher() {
		Thread shutDownThread = new Thread(new WatcherHookThead(watchService));
		shutDownThread.setName("shutDownThread");
		Runtime.getRuntime().addShutdownHook(shutDownThread);
	}

}
