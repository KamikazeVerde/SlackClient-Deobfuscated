/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.command.server.CommandBlockLogic;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiCommandBlock extends GuiScreen {
/*  16 */   private static final Logger field_146488_a = LogManager.getLogger();
/*     */   
/*     */   private GuiTextField commandTextField;
/*     */   
/*     */   private GuiTextField previousOutputTextField;
/*     */   
/*     */   private final CommandBlockLogic localCommandBlock;
/*     */   
/*     */   private GuiButton doneBtn;
/*     */   
/*     */   private GuiButton cancelBtn;
/*     */   
/*     */   private GuiButton field_175390_s;
/*     */   
/*     */   private boolean field_175389_t;
/*     */   
/*     */   public GuiCommandBlock(CommandBlockLogic p_i45032_1_) {
/*  33 */     this.localCommandBlock = p_i45032_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  41 */     this.commandTextField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  50 */     Keyboard.enableRepeatEvents(true);
/*  51 */     this.buttonList.clear();
/*  52 */     this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
/*  53 */     this.buttonList.add(this.cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  54 */     this.buttonList.add(this.field_175390_s = new GuiButton(4, this.width / 2 + 150 - 20, 150, 20, 20, "O"));
/*  55 */     this.commandTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
/*  56 */     this.commandTextField.setMaxStringLength(32767);
/*  57 */     this.commandTextField.setFocused(true);
/*  58 */     this.commandTextField.setText(this.localCommandBlock.getCommand());
/*  59 */     this.previousOutputTextField = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 150, 150, 276, 20);
/*  60 */     this.previousOutputTextField.setMaxStringLength(32767);
/*  61 */     this.previousOutputTextField.setEnabled(false);
/*  62 */     this.previousOutputTextField.setText("-");
/*  63 */     this.field_175389_t = this.localCommandBlock.shouldTrackOutput();
/*  64 */     func_175388_a();
/*  65 */     this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  73 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  81 */     if (button.enabled) {
/*     */       PacketBuffer packetbuffer;
/*  83 */       switch (button.id) {
/*     */         case 1:
/*  85 */           this.localCommandBlock.setTrackOutput(this.field_175389_t);
/*  86 */           this.mc.displayGuiScreen(null);
/*     */           break;
/*     */         case 0:
/*  89 */           packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  90 */           packetbuffer.writeByte(this.localCommandBlock.func_145751_f());
/*  91 */           this.localCommandBlock.func_145757_a((ByteBuf)packetbuffer);
/*  92 */           packetbuffer.writeString(this.commandTextField.getText());
/*  93 */           packetbuffer.writeBoolean(this.localCommandBlock.shouldTrackOutput());
/*  94 */           this.mc.getNetHandler().addToSendQueue((Packet)new C17PacketCustomPayload("MC|AdvCdm", packetbuffer));
/*     */           
/*  96 */           if (!this.localCommandBlock.shouldTrackOutput())
/*     */           {
/*  98 */             this.localCommandBlock.setLastOutput(null);
/*     */           }
/*     */           
/* 101 */           this.mc.displayGuiScreen(null);
/*     */           break;
/*     */         case 4:
/* 104 */           this.localCommandBlock.setTrackOutput(!this.localCommandBlock.shouldTrackOutput());
/* 105 */           func_175388_a();
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 117 */     this.commandTextField.textboxKeyTyped(typedChar, keyCode);
/* 118 */     this.previousOutputTextField.textboxKeyTyped(typedChar, keyCode);
/* 119 */     this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
/*     */     
/* 121 */     if (keyCode != 28 && keyCode != 156) {
/*     */       
/* 123 */       if (keyCode == 1)
/*     */       {
/* 125 */         actionPerformed(this.cancelBtn);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 130 */       actionPerformed(this.doneBtn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 139 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 140 */     this.commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
/* 141 */     this.previousOutputTextField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 149 */     drawDefaultBackground();
/* 150 */     drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), this.width / 2, 20, 16777215);
/* 151 */     drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), this.width / 2 - 150, 37, 10526880);
/* 152 */     this.commandTextField.drawTextBox();
/* 153 */     int i = 75;
/* 154 */     int j = 0;
/* 155 */     drawString(this.fontRendererObj, I18n.format("advMode.nearestPlayer", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 156 */     drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 157 */     drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 158 */     drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/* 159 */     drawString(this.fontRendererObj, "", this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
/*     */     
/* 161 */     if (this.previousOutputTextField.getText().length() > 0) {
/*     */       
/* 163 */       i = i + j * this.fontRendererObj.FONT_HEIGHT + 16;
/* 164 */       drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), this.width / 2 - 150, i, 10526880);
/* 165 */       this.previousOutputTextField.drawTextBox();
/*     */     } 
/*     */     
/* 168 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175388_a() {
/* 173 */     if (this.localCommandBlock.shouldTrackOutput()) {
/*     */       
/* 175 */       this.field_175390_s.displayString = "O";
/*     */       
/* 177 */       if (this.localCommandBlock.getLastOutput() != null)
/*     */       {
/* 179 */         this.previousOutputTextField.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 184 */       this.field_175390_s.displayString = "X";
/* 185 */       this.previousOutputTextField.setText("-");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */