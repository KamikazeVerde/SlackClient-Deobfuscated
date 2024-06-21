/*    */ package cc.slack.utils.font;
/*    */ 
/*    */ import cc.slack.utils.other.PrintUtil;
/*    */ import java.awt.Font;
/*    */ import java.awt.FontFormatException;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class Fonts
/*    */ {
/* 11 */   public static final MCFontRenderer IconFont = new MCFontRenderer(fontFromTTF("guiicons.ttf", 24.0F), true, true);
/* 12 */   public static final MCFontRenderer IconFontBig = new MCFontRenderer(fontFromTTF("guiicons.ttf", 38.0F), true, true);
/*    */   
/* 14 */   public static final MCFontRenderer SFBold30 = new MCFontRenderer(fontFromTTF("sfsemibold.ttf", 30.0F), true, true);
/* 15 */   public static final MCFontRenderer SFBold18 = new MCFontRenderer(fontFromTTF("sfsemibold.ttf", 18.0F), true, true);
/* 16 */   public static final MCFontRenderer SFReg18 = new MCFontRenderer(fontFromTTF("sfregular.ttf", 18.0F), true, true);
/* 17 */   public static final MCFontRenderer SFReg24 = new MCFontRenderer(fontFromTTF("sfregular.ttf", 24.0F), true, true);
/* 18 */   public static final MCFontRenderer SFReg45 = new MCFontRenderer(fontFromTTF("sfregular.ttf", 45.0F), true, true);
/* 19 */   public static final MCFontRenderer axi24 = new MCFontRenderer(fontFromTTF("axi.ttf", 24.0F), true, false);
/* 20 */   public static final MCFontRenderer axi12 = new MCFontRenderer(fontFromTTF("axi.ttf", 12.0F), true, false);
/* 21 */   public static final MCFontRenderer axi16 = new MCFontRenderer(fontFromTTF("axi.ttf", 16.0F), true, false);
/* 22 */   public static final MCFontRenderer axi18 = new MCFontRenderer(fontFromTTF("axi.ttf", 18.0F), true, false);
/* 23 */   public static final MCFontRenderer axi45 = new MCFontRenderer(fontFromTTF("axi.ttf", 45.0F), true, false);
/*    */   
/* 25 */   public static final MCFontRenderer apple18 = new MCFontRenderer(fontFromTTF("apple.ttf", 18.0F), true, false);
/* 26 */   public static final MCFontRenderer apple20 = new MCFontRenderer(fontFromTTF("apple.ttf", 20.0F), true, false);
/*    */   
/* 28 */   public static final MCFontRenderer apple24 = new MCFontRenderer(fontFromTTF("apple.ttf", 24.0F), true, false);
/*    */   
/* 30 */   public static final MCFontRenderer Arial18 = new MCFontRenderer(new Font("Arial", 0, 18), true, true);
/* 31 */   public static final MCFontRenderer Arial45 = new MCFontRenderer(new Font("Arial", 0, 45), true, false);
/* 32 */   public static final MCFontRenderer Arial65 = new MCFontRenderer(new Font("Arial", 0, 65), true, false);
/* 33 */   public static final MCFontRenderer Checkmark = new MCFontRenderer(fontFromTTF("checkmark.ttf", 24.0F), true, false);
/*    */   
/*    */   private static Font fontFromTTF(String fileName, float fontSize) {
/* 36 */     PrintUtil.print("Initializing Font: " + fileName + " | Size: " + fontSize);
/* 37 */     Font output = null;
/*    */     try {
/* 39 */       output = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("slack/fonts/" + fileName)).getInputStream());
/* 40 */       output = output.deriveFont(fontSize);
/* 41 */     } catch (FontFormatException|java.io.IOException fontFormatException) {}
/*    */ 
/*    */     
/* 44 */     return output;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\font\Fonts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */