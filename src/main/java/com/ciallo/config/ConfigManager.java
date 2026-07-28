package com.ciallo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
	private static final Logger LOGGER = LoggerFactory.getLogger("finalstack-config");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("finalstack.json");
	private static FinalStackConfig config = new FinalStackConfig();

	public static FinalStackConfig getConfig() {
		return config;
	}

	public static void load() {
		if (Files.exists(CONFIG_PATH)) {
			try {
				String json = Files.readString(CONFIG_PATH);
				config = GSON.fromJson(json, FinalStackConfig.class);
				if (config == null) config = new FinalStackConfig();
				validate();
				LOGGER.info("Config loaded from {}", CONFIG_PATH);
			} catch (IOException | JsonSyntaxException e) {
				LOGGER.error("Failed to load config, using defaults", e);
				config = new FinalStackConfig();
				save();
			}
		} else {
			save();
			LOGGER.info("Created default config at {}", CONFIG_PATH);
		}
	}

	public static void save() {
		validate();
		try {
			Files.createDirectories(CONFIG_PATH.getParent());
			Files.writeString(CONFIG_PATH, GSON.toJson(config));
		} catch (IOException e) {
			LOGGER.error("Failed to save config", e);
		}
	}

	private static void validate() {
		config.normalItemMaxStack = clamp(config.normalItemMaxStack, 1, Integer.MAX_VALUE);
		config.sixteenItemMaxStack = clamp(config.sixteenItemMaxStack, 1, Integer.MAX_VALUE);
		config.unstackableItemMaxStack = clamp(config.unstackableItemMaxStack, 1, Integer.MAX_VALUE);
	}

	private static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}

	public static void setNormalItemMaxStack(int value) {
		config.normalItemMaxStack = value;
		save();
	}

	public static void setSixteenItemMaxStack(int value) {
		config.sixteenItemMaxStack = value;
		save();
	}

	public static void setUnstackableItemMaxStack(int value) {
		config.unstackableItemMaxStack = value;
		save();
	}

	public static void setEnableAbbreviation(boolean value) {
		config.enableAbbreviation = value;
		save();
	}
}
