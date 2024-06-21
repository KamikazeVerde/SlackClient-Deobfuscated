/*     */ package net.optifine.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.config.ShaderOption;
/*     */ import net.optifine.shaders.gui.GuiButtonShaderOption;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class TooltipProviderShaderOptions
/*     */   extends TooltipProviderOptions
/*     */ {
/*     */   public String[] getTooltipLines(GuiButton btn, int width) {
/*  19 */     if (!(btn instanceof GuiButtonShaderOption))
/*     */     {
/*  21 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  25 */     GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)btn;
/*  26 */     ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*  27 */     String[] astring = makeTooltipLines(shaderoption, width);
/*  28 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(ShaderOption so, int width) {
/*  34 */     String s = so.getNameText();
/*  35 */     String s1 = Config.normalize(so.getDescriptionText()).trim();
/*  36 */     String[] astring = splitDescription(s1);
/*  37 */     GameSettings gamesettings = Config.getGameSettings();
/*  38 */     String s2 = null;
/*     */     
/*  40 */     if (!s.equals(so.getName()) && gamesettings.advancedItemTooltips)
/*     */     {
/*  42 */       s2 = "§8" + Lang.get("of.general.id") + ": " + so.getName();
/*     */     }
/*     */     
/*  45 */     String s3 = null;
/*     */     
/*  47 */     if (so.getPaths() != null && gamesettings.advancedItemTooltips)
/*     */     {
/*  49 */       s3 = "§8" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths());
/*     */     }
/*     */     
/*  52 */     String s4 = null;
/*     */     
/*  54 */     if (so.getValueDefault() != null && gamesettings.advancedItemTooltips) {
/*     */       
/*  56 */       String s5 = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
/*  57 */       s4 = "§8" + Lang.getDefault() + ": " + s5;
/*     */     } 
/*     */     
/*  60 */     List<String> list = new ArrayList<>();
/*  61 */     list.add(s);
/*  62 */     list.addAll(Arrays.asList(astring));
/*     */     
/*  64 */     if (s2 != null)
/*     */     {
/*  66 */       list.add(s2);
/*     */     }
/*     */     
/*  69 */     if (s3 != null)
/*     */     {
/*  71 */       list.add(s3);
/*     */     }
/*     */     
/*  74 */     if (s4 != null)
/*     */     {
/*  76 */       list.add(s4);
/*     */     }
/*     */     
/*  79 */     String[] astring1 = makeTooltipLines(width, list);
/*  80 */     return astring1;
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] splitDescription(String desc) {
/*  85 */     if (desc.length() <= 0)
/*     */     {
/*  87 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/*  91 */     desc = StrUtils.removePrefix(desc, "//");
/*  92 */     String[] astring = desc.split("\\. ");
/*     */     
/*  94 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  96 */       astring[i] = "- " + astring[i].trim();
/*  97 */       astring[i] = StrUtils.removeSuffix(astring[i], ".");
/*     */     } 
/*     */     
/* 100 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(int width, List<String> args) {
/* 106 */     FontRenderer fontrenderer = (Config.getMinecraft()).MCfontRenderer;
/* 107 */     List<String> list = new ArrayList<>();
/*     */     
/* 109 */     for (int i = 0; i < args.size(); i++) {
/*     */       
/* 111 */       String s = args.get(i);
/*     */       
/* 113 */       if (s != null && s.length() > 0)
/*     */       {
/* 115 */         for (String s1 : fontrenderer.listFormattedStringToWidth(s, width))
/*     */         {
/* 117 */           list.add(s1);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 122 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 123 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\gui\TooltipProviderShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */