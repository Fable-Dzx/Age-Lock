package com.fabledzx.agelock;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

/**
 * 验证结果弹窗：展示年龄区间的提示语（含彩蛋），点击「好的」后正常游玩。
 * 提示语通过翻译键引用，自动适配游戏语言。
 */
public class AgeResultScreen extends Screen {

    private final String messageKey;
    private final boolean locked;

    public AgeResultScreen(String messageKey, boolean locked) {
        super(Text.translatable("agelock.screen.result.title"));
        this.messageKey = messageKey;
        this.locked = locked;
    }

    @Override
    protected void init() {
        super.init();
        int cx = this.width / 2;
        this.addDrawableChild(new ButtonWidget(cx - 60, this.height / 2 + 40, 120, 20,
                Text.translatable("agelock.screen.result.ok"), b -> {
            AgeManager.getInstance().confirm();
            this.close();
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        int cy = this.height / 2;
        drawCentered(matrices, Text.translatable("agelock.screen.result.passed"), cy - 70, 0x55FF55);
        drawCentered(matrices, Text.translatable(this.messageKey), cy - 30, 0xFFFFFF);
        if (this.locked) {
            drawCentered(matrices, Text.translatable("agelock.screen.result.hint_locked"), cy + 10, 0xFFAA00);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    /** 水平居中绘制带阴影文本 */
    private void drawCentered(MatrixStack matrices, Text text, int y, int color) {
        int x = (this.width - this.textRenderer.getWidth(text)) / 2;
        this.textRenderer.drawWithShadow(matrices, text, x, y, color);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void close() {
        if (this.client != null && this.client.world != null && AgeManager.getInstance().canForceScreen()) {
            this.client.setScreen(new AgeResultScreen(this.messageKey, this.locked));
        } else {
            super.close();
        }
    }
}
