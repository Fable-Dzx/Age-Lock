package com.fabledzx.agelock;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

/**
 * 年龄输入界面：每次进入游戏强制弹出，不可通过 ESC 关闭。
 */
public class AgeInputScreen extends Screen {

    private TextFieldWidget ageField;
    private String error = null;

    public AgeInputScreen() {
        super(Text.literal("年龄验证"));
    }

    @Override
    protected void init() {
        super.init();
        int cx = this.width / 2;
        int cy = this.height / 2;

        this.ageField = new TextFieldWidget(this.textRenderer, cx - 100, cy - 20, 200, 20, Text.literal("年龄"));
        this.ageField.setMaxLength(12);
        // 只允许输入数字
        this.ageField.setTextPredicate(s -> s.matches("[0-9]*"));
        this.ageField.setTextFieldFocused(true);
        this.addDrawableChild(this.ageField);
        this.setFocused(this.ageField);

        this.addDrawableChild(new ButtonWidget(cx - 60, cy + 20, 120, 20, Text.literal("验证"), b -> this.tryVerify()));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        int cx = this.width / 2;
        int cy = this.height / 2;
        drawCentered(matrices, Text.literal("请输入您的年龄"), cy - 70, 0xFFFFFF);
        drawCentered(matrices, Text.literal("未满 18 岁只能玩 5 分钟哦~"), cy - 50, 0xAAAAAA);
        this.ageField.render(matrices, mouseX, mouseY, delta);
        if (this.error != null) {
            drawCentered(matrices, Text.literal(this.error), cy + 50, 0xFF5555);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            this.tryVerify();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void tryVerify() {
        String text = this.ageField.getText().trim();
        if (text.isEmpty() || !text.matches("[0-9]{1,12}")) {
            this.error = "请输入有效的年龄（正整数）";
            return;
        }
        long age;
        try {
            age = Long.parseLong(text);
        } catch (NumberFormatException e) {
            this.error = "这个数字太大啦，请重新输入";
            return;
        }
        if (age <= 0) {
            this.error = "年龄要大于 0 哦";
            return;
        }
        AgeManager.getInstance().verify(age);
        VerifyResult result = AgeVerifier.check(age);
        this.client.setScreen(new AgeResultScreen(result.message(), result.locked()));
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
            this.client.setScreen(new AgeInputScreen());
        } else {
            super.close();
        }
    }
}
