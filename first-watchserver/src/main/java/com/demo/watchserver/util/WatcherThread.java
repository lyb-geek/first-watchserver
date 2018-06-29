package com.demo.watchserver.util;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WatcherThread implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(WatcherThread.class);

	private WatchService watchService;

	public WatcherThread(WatchService watchService) {
		super();
		this.watchService = watchService;
	}

	@Override
	public void run() {
		pollingMonitor();

	}

	private void pollingMonitor() {
		while (true) {
			try {
				// 尝试获取监控池的变化，如果没有则一直等待
				WatchKey watchKey = watchService.take();
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					if (PropsUtil.CONFIG_NAME.equals(event.context().toString())) {
						logger.info("监控到{}发生{}操作，将重新加载", event.context(), event.kind());
						PropsUtil.getInstance().loadProperties(event.context().toString());
						break;
					}
				}
				// 完成一次监控就需要重置监控器一次
				watchKey.reset();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
