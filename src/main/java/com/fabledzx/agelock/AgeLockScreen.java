package com.fabledzx.agelock;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

/**
 * 锁定界面：未满 18 岁游玩 5 分钟后弹出，只能点击「我长大了」重新验证年龄。
 */
public class AgeLockScreen extends Screen {

    public AgeLockScreen() {
        super(Text.literal("游戏已锁定"));
    }

    @Override
    protected void init() {
        super.init();
        int cx = this.width / 2;
        this.addDrawableChild(new ButtonWidget(cx - 60, this.height / 2 + 40, 120, 20, Text.literal("我长大了"), b -> {
            AgeManager.getInstance().onGrowUp();
            this.client.setScreen(new AgeInputScreen());
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        int cy = this.height / 2;
        drawCentered(matrices, Text.literal("游玩时间已到！"), cy - 70, 0xFF5555);
        drawCentered(matrices, Text.literal("未满 18 岁只能玩 5 分钟哦~"), cy - 40, 0xFFFFFF);
        drawCentered(matrices, Text.literal("想继续玩？点击下方按钮重新验证年龄"), cy - 15, 0xAAAAAA);
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
            this.client.setScreen(new AgeLockScreen());
        } else {
            super.close();
        }
    }
}
