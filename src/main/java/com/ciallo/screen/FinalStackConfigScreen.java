package com.ciallo.screen;

import com.ciallo.config.ConfigManager;
import com.ciallo.config.FinalStackConfig;
import com.ciallo.keybind.KeybindManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class FinalStackConfigScreen extends Screen {
	private static final int FIELD_WIDTH = 80;

	private final Screen parent;
	private TextFieldWidget normalField;
	private TextFieldWidget sixteenField;
	private TextFieldWidget unstackableField;

	public FinalStackConfigScreen(Screen parent) {
		super(Text.literal("FinalStack Configuration"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();
		FinalStackConfig config = ConfigManager.getConfig();
		int centerX = this.width / 2;
		int y = 50;

		normalField = new TextFieldWidget(this.textRenderer, centerX - FIELD_WIDTH / 2, y, FIELD_WIDTH, 20, Text.empty());
		normalField.setText(String.valueOf(config.normalItemMaxStack));
		normalField.setChangedListener(this::onNormalChanged);
		this.addSelectableChild(normalField);
		addAdjustButtons(centerX, y, () -> adjustNormal(1), () -> adjustNormal(10), () -> adjustNormal(100), this::setNormalMax);

		y += 30;
		sixteenField = new TextFieldWidget(this.textRenderer, centerX - FIELD_WIDTH / 2, y, FIELD_WIDTH, 20, Text.empty());
		sixteenField.setText(String.valueOf(config.sixteenItemMaxStack));
		sixteenField.setChangedListener(this::onSixteenChanged);
		this.addSelectableChild(sixteenField);
		addAdjustButtons(centerX, y, () -> adjustSixteen(1), () -> adjustSixteen(10), () -> adjustSixteen(100), this::setSixteenMax);

		y += 30;
		unstackableField = new TextFieldWidget(this.textRenderer, centerX - FIELD_WIDTH / 2, y, FIELD_WIDTH, 20, Text.empty());
		unstackableField.setText(String.valueOf(config.unstackableItemMaxStack));
		unstackableField.setChangedListener(this::onUnstackableChanged);
		this.addSelectableChild(unstackableField);
		addAdjustButtons(centerX, y, () -> adjustUnstackable(1), () -> adjustUnstackable(10), () -> adjustUnstackable(100), this::setUnstackableMax);

		y += 40;
		boolean abbr = ConfigManager.getConfig().enableAbbreviation;
		this.addDrawableChild(ButtonWidget.builder(
			Text.literal("Count Abbreviation: " + (abbr ? "ON" : "OFF")),
			btn -> {
				ConfigManager.setEnableAbbreviation(!ConfigManager.getConfig().enableAbbreviation);
				boolean newVal = ConfigManager.getConfig().enableAbbreviation;
				btn.setMessage(Text.literal("Count Abbreviation: " + (newVal ? "ON" : "OFF")));
			}
		).dimensions(centerX - 75, y, 150, 20).build());

		y += 30;
		String keyName = KeybindManager.getKeybindTranslation();
		this.addDrawableChild(ButtonWidget.builder(
			Text.literal("Keybind: " + keyName),
			btn -> {}
		).dimensions(centerX - 75, y, 150, 20).build());

		y += 35;
		this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, btn -> this.close()).dimensions(centerX - 50, y, 100, 20).build());
	}

	private void addAdjustButtons(int centerX, int y, Runnable add1, Runnable add10, Runnable add100, Runnable setMax) {
		this.addDrawableChild(ButtonWidget.builder(Text.literal("+1"), btn -> add1.run()).dimensions(centerX + 45, y, 25, 20).build());
		this.addDrawableChild(ButtonWidget.builder(Text.literal("+10"), btn -> add10.run()).dimensions(centerX + 72, y, 28, 20).build());
		this.addDrawableChild(ButtonWidget.builder(Text.literal("+100"), btn -> add100.run()).dimensions(centerX + 102, y, 28, 20).build());
		this.addDrawableChild(ButtonWidget.builder(Text.literal("\u221E"), btn -> setMax.run()).dimensions(centerX + 132, y, 28, 20).build());
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
		normalField.setText(String.valueOf(newVal));
	}

	private void adjustSixteen(int amount) {
		FinalStackConfig cfg = ConfigManager.getConfig();
		int newVal = cap(cfg.sixteenItemMaxStack + amount);
		ConfigManager.setSixteenItemMaxStack(newVal);
		sixteenField.setText(String.valueOf(newVal));
	}

	private void adjustUnstackable(int amount) {
		FinalStackConfig cfg = ConfigManager.getConfig();
		int newVal = cap(cfg.unstackableItemMaxStack + amount);
		ConfigManager.setUnstackableItemMaxStack(newVal);
		unstackableField.setText(String.valueOf(newVal));
	}

	private void setNormalMax() {
		ConfigManager.setNormalItemMaxStack(Integer.MAX_VALUE);
		normalField.setText("\u221E");
	}

	private void setSixteenMax() {
		ConfigManager.setSixteenItemMaxStack(Integer.MAX_VALUE);
		sixteenField.setText("\u221E");
	}

	private void setUnstackableMax() {
		ConfigManager.setUnstackableItemMaxStack(Integer.MAX_VALUE);
		unstackableField.setText("\u221E");
	}

	private static int cap(int value) {
		return value < 0 ? Integer.MAX_VALUE : Math.min(value, Integer.MAX_VALUE);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		int centerX = this.width / 2;
		int y = 38;
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, centerX, y, 0xFFFFFF);

		y = 54;
		context.drawTextWithShadow(this.textRenderer, "Normal Items (default 64):", centerX - 155, y, 0xA0A0A0);
		y += 30;
		context.drawTextWithShadow(this.textRenderer, "16-Stack Items (default 16):", centerX - 155, y, 0xA0A0A0);
		y += 30;
		context.drawTextWithShadow(this.textRenderer, "Unstackable Items (default 1):", centerX - 155, y, 0xA0A0A0);

		normalField.render(context, mouseX, mouseY, delta);
		sixteenField.render(context, mouseX, mouseY, delta);
		unstackableField.render(context, mouseX, mouseY, delta);
	}

	@Override
	public void close() {
		if (this.client != null) {
			this.client.setScreen(parent);
		}
	}

	@Override
	public boolean shouldPause() {
		return false;
	}

	public static void open(Screen parent) {
		MinecraftClient.getInstance().setScreen(new FinalStackConfigScreen(parent));
	}
}
