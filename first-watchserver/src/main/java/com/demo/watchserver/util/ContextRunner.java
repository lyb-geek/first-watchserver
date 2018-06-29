package com.demo.watchserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ContextRunner implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(ContextRunner.class);

	@Override
	public void run(String... args) throws Exception {
		WatcherUtil.getInstance().registerWatcher(PropsUtil.CONFIG_NAME);

		WatcherUtil.getInstance().executeWatcher();

		WatcherUtil.getInstance().shutDownWatcher();

		PropsUtil.getInstance().testValueIfChange();
	}

}
