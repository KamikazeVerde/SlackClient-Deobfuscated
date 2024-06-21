/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class GuiPlayerTabOverlay extends Gui {
/*  25 */   private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new PlayerComparator());
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final GuiIngame guiIngame;
/*     */   
/*     */   private IChatComponent footer;
/*     */   
/*     */   private IChatComponent header;
/*     */   
/*     */   private long lastTimeOpened;
/*     */   
/*     */   private boolean isBeingRendered;
/*     */ 
/*     */   
/*     */   public GuiPlayerTabOverlay(Minecraft mcIn, GuiIngame guiIngameIn) {
/*  41 */     this.mc = mcIn;
/*  42 */     this.guiIngame = guiIngameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
/*  50 */     return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePlayerList(boolean willBeRendered) {
/*  61 */     if (willBeRendered && !this.isBeingRendered)
/*     */     {
/*  63 */       this.lastTimeOpened = Minecraft.getSystemTime();
/*     */     }
/*     */     
/*  66 */     this.isBeingRendered = willBeRendered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn) {
/*     */     int l;
/*  74 */     NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
/*  75 */     List<NetworkPlayerInfo> list = field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
/*  76 */     int i = 0;
/*  77 */     int j = 0;
/*     */     
/*  79 */     for (NetworkPlayerInfo networkplayerinfo : list) {
/*     */       
/*  81 */       int k = this.mc.MCfontRenderer.getStringWidth(getPlayerName(networkplayerinfo));
/*  82 */       i = Math.max(i, k);
/*     */       
/*  84 */       if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
/*     */         
/*  86 */         k = this.mc.MCfontRenderer.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
/*  87 */         j = Math.max(j, k);
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     list = list.subList(0, Math.min(list.size(), 80));
/*  92 */     int l3 = list.size();
/*  93 */     int i4 = l3;
/*     */     
/*     */     int j4;
/*  96 */     for (j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4)
/*     */     {
/*  98 */       j4++;
/*     */     }
/*     */     
/* 101 */     boolean flag = (this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted());
/*     */ 
/*     */     
/* 104 */     if (scoreObjectiveIn != null) {
/*     */       
/* 106 */       if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS)
/*     */       {
/* 108 */         l = 90;
/*     */       }
/*     */       else
/*     */       {
/* 112 */         l = j;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 117 */       l = 0;
/*     */     } 
/*     */     
/* 120 */     int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
/* 121 */     int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
/* 122 */     int k1 = 10;
/* 123 */     int l1 = i1 * j4 + (j4 - 1) * 5;
/* 124 */     List<String> list1 = null;
/* 125 */     List<String> list2 = null;
/*     */     
/* 127 */     if (this.header != null) {
/*     */       
/* 129 */       list1 = this.mc.MCfontRenderer.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);
/*     */       
/* 131 */       for (String s : list1)
/*     */       {
/* 133 */         l1 = Math.max(l1, this.mc.MCfontRenderer.getStringWidth(s));
/*     */       }
/*     */     } 
/*     */     
/* 137 */     if (this.footer != null) {
/*     */       
/* 139 */       list2 = this.mc.MCfontRenderer.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);
/*     */       
/* 141 */       for (String s2 : list2)
/*     */       {
/* 143 */         l1 = Math.max(l1, this.mc.MCfontRenderer.getStringWidth(s2));
/*     */       }
/*     */     } 
/*     */     
/* 147 */     if (list1 != null) {
/*     */       
/* 149 */       drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list1.size() * this.mc.MCfontRenderer.FONT_HEIGHT, -2147483648);
/*     */       
/* 151 */       for (String s3 : list1) {
/*     */         
/* 153 */         int i2 = this.mc.MCfontRenderer.getStringWidth(s3);
/* 154 */         this.mc.MCfontRenderer.drawStringWithShadow(s3, (width / 2 - i2 / 2), k1, -1);
/* 155 */         k1 += this.mc.MCfontRenderer.FONT_HEIGHT;
/*     */       } 
/*     */       
/* 158 */       k1++;
/*     */     } 
/*     */     
/* 161 */     drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + i4 * 9, -2147483648);
/*     */     
/* 163 */     for (int k4 = 0; k4 < l3; k4++) {
/*     */       
/* 165 */       int l4 = k4 / i4;
/* 166 */       int i5 = k4 % i4;
/* 167 */       int j2 = j1 + l4 * i1 + l4 * 5;
/* 168 */       int k2 = k1 + i5 * 9;
/* 169 */       drawRect(j2, k2, j2 + i1, k2 + 8, 553648127);
/* 170 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 171 */       GlStateManager.enableAlpha();
/* 172 */       GlStateManager.enableBlend();
/* 173 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*     */       
/* 175 */       if (k4 < list.size()) {
/*     */         
/* 177 */         NetworkPlayerInfo networkplayerinfo1 = list.get(k4);
/* 178 */         String s1 = getPlayerName(networkplayerinfo1);
/* 179 */         GameProfile gameprofile = networkplayerinfo1.getGameProfile();
/*     */         
/* 181 */         if (flag) {
/*     */           
/* 183 */           EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
/* 184 */           boolean flag1 = (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm")));
/* 185 */           this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
/* 186 */           int l2 = 8 + (flag1 ? 8 : 0);
/* 187 */           int i3 = 8 * (flag1 ? -1 : 1);
/* 188 */           Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0F, l2, 8, i3, 8, 8, 64.0F, 64.0F);
/*     */           
/* 190 */           if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
/*     */             
/* 192 */             int j3 = 8 + (flag1 ? 8 : 0);
/* 193 */             int k3 = 8 * (flag1 ? -1 : 1);
/* 194 */             Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0F, j3, 8, k3, 8, 8, 64.0F, 64.0F);
/*     */           } 
/*     */           
/* 197 */           j2 += 9;
/*     */         } 
/*     */         
/* 200 */         if (networkplayerinfo1.getGameType() == WorldSettings.GameType.SPECTATOR) {
/*     */           
/* 202 */           s1 = ChatFormatting.ITALIC + s1;
/* 203 */           this.mc.MCfontRenderer.drawStringWithShadow(s1, j2, k2, -1862270977);
/*     */         }
/*     */         else {
/*     */           
/* 207 */           this.mc.MCfontRenderer.drawStringWithShadow(s1, j2, k2, -1);
/*     */         } 
/*     */         
/* 210 */         if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != WorldSettings.GameType.SPECTATOR) {
/*     */           
/* 212 */           int k5 = j2 + i + 1;
/* 213 */           int l5 = k5 + l;
/*     */           
/* 215 */           if (l5 - k5 > 5)
/*     */           {
/* 217 */             drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
/*     */           }
/*     */         } 
/*     */         
/* 221 */         drawPing(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     if (list2 != null) {
/*     */       
/* 227 */       k1 = k1 + i4 * 9 + 1;
/* 228 */       drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list2.size() * this.mc.MCfontRenderer.FONT_HEIGHT, -2147483648);
/*     */       
/* 230 */       for (String s4 : list2) {
/*     */         
/* 232 */         int j5 = this.mc.MCfontRenderer.getStringWidth(s4);
/* 233 */         this.mc.MCfontRenderer.drawStringWithShadow(s4, (width / 2 - j5 / 2), k1, -1);
/* 234 */         k1 += this.mc.MCfontRenderer.FONT_HEIGHT;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn) {
/* 241 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 242 */     this.mc.getTextureManager().bindTexture(icons);
/* 243 */     int i = 0;
/* 244 */     int j = 0;
/*     */     
/* 246 */     if (networkPlayerInfoIn.getResponseTime() < 0) {
/*     */       
/* 248 */       j = 5;
/*     */     }
/* 250 */     else if (networkPlayerInfoIn.getResponseTime() < 150) {
/*     */       
/* 252 */       j = 0;
/*     */     }
/* 254 */     else if (networkPlayerInfoIn.getResponseTime() < 300) {
/*     */       
/* 256 */       j = 1;
/*     */     }
/* 258 */     else if (networkPlayerInfoIn.getResponseTime() < 600) {
/*     */       
/* 260 */       j = 2;
/*     */     }
/* 262 */     else if (networkPlayerInfoIn.getResponseTime() < 1000) {
/*     */       
/* 264 */       j = 3;
/*     */     }
/*     */     else {
/*     */       
/* 268 */       j = 4;
/*     */     } 
/*     */     
/* 271 */     this.zLevel += 100.0F;
/* 272 */     drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + i * 10, 176 + j * 8, 10, 8);
/* 273 */     this.zLevel -= 100.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawScoreboardValues(ScoreObjective p_175247_1_, int p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_) {
/* 278 */     int i = p_175247_1_.getScoreboard().getValueFromObjective(p_175247_3_, p_175247_1_).getScorePoints();
/*     */     
/* 280 */     if (p_175247_1_.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
/*     */       
/* 282 */       this.mc.getTextureManager().bindTexture(icons);
/*     */       
/* 284 */       if (this.lastTimeOpened == p_175247_6_.func_178855_p())
/*     */       {
/* 286 */         if (i < p_175247_6_.func_178835_l()) {
/*     */           
/* 288 */           p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/* 289 */           p_175247_6_.func_178844_b((this.guiIngame.getUpdateCounter() + 20));
/*     */         }
/* 291 */         else if (i > p_175247_6_.func_178835_l()) {
/*     */           
/* 293 */           p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/* 294 */           p_175247_6_.func_178844_b((this.guiIngame.getUpdateCounter() + 10));
/*     */         } 
/*     */       }
/*     */       
/* 298 */       if (Minecraft.getSystemTime() - p_175247_6_.func_178847_n() > 1000L || this.lastTimeOpened != p_175247_6_.func_178855_p()) {
/*     */         
/* 300 */         p_175247_6_.func_178836_b(i);
/* 301 */         p_175247_6_.func_178857_c(i);
/* 302 */         p_175247_6_.func_178846_a(Minecraft.getSystemTime());
/*     */       } 
/*     */       
/* 305 */       p_175247_6_.func_178843_c(this.lastTimeOpened);
/* 306 */       p_175247_6_.func_178836_b(i);
/* 307 */       int j = MathHelper.ceiling_float_int(Math.max(i, p_175247_6_.func_178860_m()) / 2.0F);
/* 308 */       int k = Math.max(MathHelper.ceiling_float_int((i / 2)), Math.max(MathHelper.ceiling_float_int((p_175247_6_.func_178860_m() / 2)), 10));
/* 309 */       boolean flag = (p_175247_6_.func_178858_o() > this.guiIngame.getUpdateCounter() && (p_175247_6_.func_178858_o() - this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L);
/*     */       
/* 311 */       if (j > 0) {
/*     */         
/* 313 */         float f = Math.min((p_175247_5_ - p_175247_4_ - 4) / k, 9.0F);
/*     */         
/* 315 */         if (f > 3.0F) {
/*     */           
/* 317 */           for (int l = j; l < k; l++)
/*     */           {
/* 319 */             drawTexturedModalRect(p_175247_4_ + l * f, p_175247_2_, flag ? 25 : 16, 0, 9, 9);
/*     */           }
/*     */           
/* 322 */           for (int j1 = 0; j1 < j; j1++)
/*     */           {
/* 324 */             drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, flag ? 25 : 16, 0, 9, 9);
/*     */             
/* 326 */             if (flag) {
/*     */               
/* 328 */               if (j1 * 2 + 1 < p_175247_6_.func_178860_m())
/*     */               {
/* 330 */                 drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, 70, 0, 9, 9);
/*     */               }
/*     */               
/* 333 */               if (j1 * 2 + 1 == p_175247_6_.func_178860_m())
/*     */               {
/* 335 */                 drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, 79, 0, 9, 9);
/*     */               }
/*     */             } 
/*     */             
/* 339 */             if (j1 * 2 + 1 < i)
/*     */             {
/* 341 */               drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, (j1 >= 10) ? 160 : 52, 0, 9, 9);
/*     */             }
/*     */             
/* 344 */             if (j1 * 2 + 1 == i)
/*     */             {
/* 346 */               drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, (j1 >= 10) ? 169 : 61, 0, 9, 9);
/*     */             }
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 352 */           float f1 = MathHelper.clamp_float(i / 20.0F, 0.0F, 1.0F);
/* 353 */           int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
/* 354 */           String s = "" + (i / 2.0F);
/*     */           
/* 356 */           if (p_175247_5_ - this.mc.MCfontRenderer.getStringWidth(s + "hp") >= p_175247_4_)
/*     */           {
/* 358 */             s = s + "hp";
/*     */           }
/*     */           
/* 361 */           this.mc.MCfontRenderer.drawStringWithShadow(s, ((p_175247_5_ + p_175247_4_) / 2 - this.mc.MCfontRenderer.getStringWidth(s) / 2), p_175247_2_, i1);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 367 */       String s1 = ChatFormatting.YELLOW + "" + i;
/* 368 */       this.mc.MCfontRenderer.drawStringWithShadow(s1, (p_175247_5_ - this.mc.MCfontRenderer.getStringWidth(s1)), p_175247_2_, 16777215);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFooter(IChatComponent footerIn) {
/* 374 */     this.footer = footerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeader(IChatComponent headerIn) {
/* 379 */     this.header = headerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181030_a() {
/* 384 */     this.header = null;
/* 385 */     this.footer = null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class PlayerComparator
/*     */     implements Comparator<NetworkPlayerInfo>
/*     */   {
/*     */     private PlayerComparator() {}
/*     */ 
/*     */     
/*     */     public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
/* 396 */       ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
/* 397 */       ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
/* 398 */       return ComparisonChain.start().compareTrueFirst((p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR), (p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR)).compare((scoreplayerteam != null) ? scoreplayerteam.getRegisteredName() : "", (scoreplayerteam1 != null) ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiPlayerTabOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */