package com.ciallo.screen;

import com.ciallo.config.ConfigManager;
import com.ciallo.config.FinalStackConfig;
import com.ciallo.keybind.KeybindManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class FinalStackConfigScreen extends Screen {
	private static final int FIELD_WIDTH = 80;

	private final Screen parent;
	private EditBox normalField;
	private EditBox sixteenField;
	private EditBox unstackableField;
	private boolean widgetsInitialized;

	public FinalStackConfigScreen(Screen parent) {
		super(Component.literal("FinalStack Configuration"));
		this.parent = parent;
		this.widgetsInitialized = false;
	}

	@Override
	public void added() {
		super.added();
		initWidgets();
	}

	private void initWidgets() {
		if (widgetsInitialized) return;
		widgetsInitialized = true;

		FinalStackConfig config = ConfigManager.getConfig();
		int centerX = this.width / 2;
		int y = 50;

		normalField = new EditBox(this.font, centerX - FIELD_WIDTH / 2, y, FIELD_WIDTH, 20, Component.empty());
		normalField.setValue(String.valueOf(config.normalItemMaxStack));
		normalField.setResponder(this::onNormalChanged);
		this.addWidget(normalField);
		addAdjustButtons(centerX, y, () -> adjustNormal(1), () -> adjustNormal(10), () -> adjustNormal(100), this::setNormalMax);

		y += 30;
		sixteenField = new EditBox(this.font, centerX - FIELD_WIDTH / 2, y, FIELD_WIDTH, 20, Component.empty());
		sixteenField.setValue(String.valueOf(config.sixteenItemMaxStack));
		sixteenField.setResponder(this::onSixteenChanged);
		this.addWidget(sixteenField);
		addAdjustButtons(centerX, y, () -> adjustSixteen(1), () -> adjustSixteen(10), () -> adjustSixteen(100), this::setSixteenMax);

		y += 30;
		unstackableField = new EditBox(this.font, centerX - FIELD_WIDTH / 2, y, FIELD_WIDTH, 20, Component.empty());
		unstackableField.setValue(String.valueOf(config.unstackableItemMaxStack));
		unstackableField.setResponder(this::onUnstackableChanged);
		this.addWidget(unstackableField);
		addAdjustButtons(centerX, y, () -> adjustUnstackable(1), () -> adjustUnstackable(10), () -> adjustUnstackable(100), this::setUnstackableMax);

		y += 40;
		boolean abbr = ConfigManager.getConfig().enableAbbreviation;
		this.addRenderableWidget(Button.builder(
			Component.literal("Count Abbreviation: " + (abbr ? "ON" : "OFF")),
			btn -> {
				ConfigManager.setEnableAbbreviation(!ConfigManager.getConfig().enableAbbreviation);
				boolean newVal = ConfigManager.getConfig().enableAbbreviation;
				btn.setMessage(Component.literal("Count Abbreviation: " + (newVal ? "ON" : "OFF")));
			}
		).bounds(centerX - 75, y, 150, 20).build());

		y += 30;
		String keyName = KeybindManager.getKeybindTranslation();
		this.addRenderableWidget(Button.builder(
			Component.literal("Keybind: " + keyName),
			btn -> {}
		).bounds(centerX - 75, y, 150, 20).build());

		y += 35;
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, btn -> this.onClose()).bounds(centerX - 50, y, 100, 20).build());
	}

	private void addAdjustButtons(int centerX, int y, Runnable add1, Runnable add10, Runnable add100, Runnable setMax) {
		this.addRenderableWidget(Button.builder(Component.literal("+1"), btn -> add1.run()).bounds(centerX + 45, y, 25, 20).build());
		this.addRenderableWidget(Button.builder(Component.literal("+10"), btn -> add10.run()).bounds(centerX + 72, y, 28, 20).build());
		this.addRenderableWidget(Button.builder(Component.literal("+100"), btn -> add100.run()).bounds(centerX + 102, y, 28, 20).build());
		this.addRenderableWidget(Button.builder(Component.literal("\u221E"), btn -> setMax.run()).bounds(centerX + 132, y, 28, 20).build());
	}

	private void onNormalChanged(String text) {
		try { int val = Integer.parseInt(text); if (val >= 1) ConfigManager.setNormalItemMaxStack(val); } catch (NumberFormatException ignored) {}
	}

	private void onSixteenChanged(String text) {
		try { int val = Integer.parseInt(text); if (val >= 1) ConfigManager.setSixteenItemMaxStack(val); } catch (NumberFormatException ignored) {}
	}

	private void onUnstackableChanged(String text) {
		try { int val = Integer.parseInt(text); if (val >= 1) ConfigManager.setUnstackableItemMaxStack(val); } catch (NumberFormatException ignored) {}
	}

	private void adjustNormal(int amount) {
		FinalStackConfig cfg = ConfigManager.getConfig();
		int newVal = cap(cfg.normalItemMaxStack + amount);
		ConfigManager.setNormalItemMaxStack(newVal);
		normalField.setValue(String.valueOf(newVal));
	}

	private void adjustSixteen(int amount) {
		FinalStackConfig cfg = ConfigManager.getConfig();
		int newVal = cap(cfg.sixteenItemMaxStack + amount);
		ConfigManager.setSixteenItemMaxStack(newVal);
		sixteenField.setValue(String.valueOf(newVal));
	}

	private void adjustUnstackable(int amount) {
		FinalStackConfig cfg = ConfigManager.getConfig();
		int newVal = cap(cfg.unstackableItemMaxStack + amount);
		ConfigManager.setUnstackableItemMaxStack(newVal);
		unstackableField.setValue(String.valueOf(newVal));
	}

	private void setNormalMax() {
		ConfigManager.setNormalItemMaxStack(Integer.MAX_VALUE);
		normalField.setValue("\u221E");
	}

	private void setSixteenMax() {
		ConfigManager.setSixteenItemMaxStack(Integer.MAX_VALUE);
		sixteenField.setValue("\u221E");
	}

	private void setUnstackableMax() {
		ConfigManager.setUnstackableItemMaxStack(Integer.MAX_VALUE);
		unstackableField.setValue("\u221E");
	}

	private static int cap(int value) {
		return value < 0 ? Integer.MAX_VALUE : Math.min(value, Integer.MAX_VALUE);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
		super.extractRenderState(context, mouseX, mouseY, delta);

		int centerX = this.width / 2;
		int y = 38;
		context.centeredText(this.font, this.title, centerX, y, 0xFFFFFF);

		y = 54;
		context.text(this.font, "Normal Items (default 64):", centerX - 155, y, 0xA0A0A0);
		y += 30;
		context.text(this.font, "16-Stack Items (default 16):", centerX - 155, y, 0xA0A0A0);
		y += 30;
		context.text(this.font, "Unstackable Items (default 1):", centerX - 155, y, 0xA0A0A0);
	}

	@Override
	public void onClose() {
		if (this.minecraft != null) {
			this.minecraft.gui.setScreen(parent);
		}
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}

	public static void open(Screen parent) {
		Minecraft.getInstance().gui.setScreen(new FinalStackConfigScreen(parent));
	}
}
