/*     */ package cc.slack.features.modules.impl.render;
/*     */ 
/*     */ import cc.slack.Slack;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.events.impl.render.RenderEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*     */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*     */ import cc.slack.features.modules.impl.render.hud.arraylist.IArraylist;
/*     */ import cc.slack.features.modules.impl.render.hud.arraylist.impl.Basic2ArrayList;
/*     */ import cc.slack.features.modules.impl.render.hud.arraylist.impl.BasicArrayList;
/*     */ import cc.slack.features.modules.impl.world.Scaffold;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.font.Fonts;
/*     */ import cc.slack.utils.player.MovementUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "HUD", category = Category.RENDER)
/*     */ public class HUD
/*     */   extends Module
/*     */ {
/*  38 */   private final ModeValue<IArraylist> arraylistModes = new ModeValue("Arraylist", (Object[])new IArraylist[] { (IArraylist)new BasicArrayList(), (IArraylist)new Basic2ArrayList() });
/*     */   
/*  40 */   private final ModeValue<String> watermarksmodes = new ModeValue("WaterMark", (Object[])new String[] { "Classic", "Backgrounded" });
/*     */   
/*  42 */   public final BooleanValue notification = new BooleanValue("Notificatons", true);
/*     */   
/*  44 */   private final BooleanValue fpsdraw = new BooleanValue("FPS Counter", true);
/*  45 */   private final BooleanValue bpsdraw = new BooleanValue("BPS Counter", true);
/*     */   
/*  47 */   private final BooleanValue scaffoldDraw = new BooleanValue("Scaffold Counter", true);
/*     */   
/*  49 */   private int scaffoldTicks = 0;
/*     */   
/*  51 */   private ArrayList<String> notText = new ArrayList<>();
/*  52 */   private ArrayList<Long> notEnd = new ArrayList<>();
/*  53 */   private ArrayList<Long> notStart = new ArrayList<>();
/*  54 */   private ArrayList<String> notDetailed = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public HUD() {
/*  58 */     addSettings(new Value[] { (Value)this.arraylistModes, (Value)this.watermarksmodes, (Value)this.notification, (Value)this.fpsdraw, (Value)this.bpsdraw, (Value)this.scaffoldDraw });
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent e) {
/*  63 */     ((IArraylist)this.arraylistModes.getValue()).onUpdate(e);
/*     */   }
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onRender(RenderEvent e) {
/*  69 */     if (e.state != RenderEvent.State.RENDER_2D)
/*     */       return; 
/*  71 */     ((IArraylist)this.arraylistModes.getValue()).onRender(e);
/*     */     
/*  73 */     switch ((String)this.watermarksmodes.getValue()) {
/*     */       case "Classic":
/*  75 */         Fonts.apple18.drawStringWithShadow("S", 4.0F, 4.0F, 5544391);
/*  76 */         Fonts.apple18.drawStringWithShadow("lack", 10.0F, 4.0F, -1);
/*     */         break;
/*     */       case "Backgrounded":
/*  79 */         Gui.drawRect(2, 2, 55 + Fonts.apple18.getStringWidth(" - " + Minecraft.getDebugFPS()), 15, -2147483648);
/*  80 */         Fonts.apple18.drawStringWithShadow("Slack " + Slack.getInstance().getInfo().getVersion(), 4.0F, 5.0F, 5544391);
/*  81 */         Fonts.apple18.drawStringWithShadow(" - " + Minecraft.getDebugFPS(), 53.0F, 5.0F, -1);
/*     */         break;
/*     */     } 
/*  84 */     if (((Boolean)this.fpsdraw.getValue()).booleanValue()) {
/*  85 */       Fonts.apple18.drawStringWithShadow("FPS:  ", 4.0F, (mc.getScaledResolution().getScaledHeight() - 10), 5544391);
/*  86 */       Fonts.apple18.drawStringWithShadow("" + Minecraft.getDebugFPS(), 25.0F, (mc.getScaledResolution().getScaledHeight() - 10), -1);
/*     */     } 
/*     */     
/*  89 */     if (((Boolean)this.bpsdraw.getValue()).booleanValue()) {
/*  90 */       Fonts.apple18.drawStringWithShadow("BPS:  ", 50.0F, (mc.getScaledResolution().getScaledHeight() - 10), 5544391);
/*  91 */       Fonts.apple18.drawStringWithShadow(getBPS(), 71.0F, (mc.getScaledResolution().getScaledHeight() - 10), -1);
/*     */     } 
/*     */ 
/*     */     
/*  95 */     if (((Boolean)this.scaffoldDraw.getValue()).booleanValue()) {
/*  96 */       if (((Scaffold)Slack.getInstance().getModuleManager().getInstance(Scaffold.class)).isToggle())
/*  97 */       { if (this.scaffoldTicks < 5) this.scaffoldTicks++;
/*     */          }
/*  99 */       else if (this.scaffoldTicks > 0) { this.scaffoldTicks--; }
/*     */ 
/*     */       
/* 102 */       if (this.scaffoldTicks == 0)
/* 103 */         return;  ScaledResolution sr = mc.getScaledResolution();
/* 104 */       if ((mc.getPlayer()).inventoryContainer.getSlot((mc.getPlayer()).inventory.currentItem + 36).getStack() != null) {
/* 105 */         String displayString = ((mc.getPlayer()).inventoryContainer.getSlot((mc.getPlayer()).inventory.currentItem + 36).getStack()).stackSize + " blocks";
/* 106 */         Gui.drawRect((int)((sr.getScaledWidth() - mc.getFontRenderer().getStringWidth(displayString)) / 2.0F) - 2, 
/* 107 */             (int)(sr.getScaledHeight() * 3.0F / 4.0F - 2.0F), 
/* 108 */             (int)((sr.getScaledWidth() + mc.getFontRenderer().getStringWidth(displayString)) / 2.0F) + 2, 
/* 109 */             (int)(sr.getScaledHeight() * 3.0F / 4.0F + (mc.getFontRenderer()).FONT_HEIGHT + 2.0F), -2147483648);
/*     */         
/* 111 */         mc.getFontRenderer().drawString(displayString, (sr
/* 112 */             .getScaledWidth() - mc.getFontRenderer().getStringWidth(displayString)) / 2.0F, sr
/* 113 */             .getScaledHeight() * 3.0F / 4.0F, (new Color(255, 255, 255))
/* 114 */             .getRGB(), false);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 119 */     if (((Boolean)this.notification.getValue()).booleanValue()) {
/* 120 */       int y = mc.getScaledResolution().getScaledHeight() - 10;
/* 121 */       for (int i = 0; i < this.notText.size(); i++) {
/* 122 */         double x = getXpos(this.notStart.get(i), this.notEnd.get(i));
/* 123 */         renderNotification(
/* 124 */             (int)((mc.getScaledResolution().getScaledWidth() - 10) + 100.0D * x), y, this.notText
/*     */             
/* 126 */             .get(i), this.notDetailed.get(i));
/* 127 */         y -= (int)(Math.pow(1.0D - x, 2.0D) * 19.0D);
/*     */       } 
/*     */       
/* 130 */       ArrayList<Integer> removeList = new ArrayList<>();
/*     */       
/* 132 */       for (int j = 0; j < this.notText.size(); j++) {
/* 133 */         if (System.currentTimeMillis() > ((Long)this.notEnd.get(j)).longValue()) {
/* 134 */           removeList.add(Integer.valueOf(j));
/*     */         }
/*     */       } 
/*     */       
/* 138 */       Collections.reverse(removeList);
/*     */       
/* 140 */       for (Iterator<Integer> iterator = removeList.iterator(); iterator.hasNext(); ) { int k = ((Integer)iterator.next()).intValue();
/* 141 */         this.notText.remove(k);
/* 142 */         this.notEnd.remove(k);
/* 143 */         this.notStart.remove(k);
/* 144 */         this.notDetailed.remove(k); }
/*     */     
/*     */     } else {
/* 147 */       this.notText.clear();
/* 148 */       this.notEnd.clear();
/* 149 */       this.notStart.clear();
/* 150 */       this.notDetailed.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getBPS() {
/* 157 */     double currentBPS = Math.round(MovementUtil.getSpeed() * 20.0D * 100.0D) / 100.0D;
/* 158 */     return String.format("%.2f", new Object[] { Double.valueOf(currentBPS) });
/*     */   }
/*     */   
/*     */   private void renderNotification(int x, int y, String bigText, String smallText) {
/* 162 */     Gui.drawRect(x - 6 - mc.getFontRenderer().getStringWidth(bigText), y - 6 - 
/* 163 */         (mc.getFontRenderer()).FONT_HEIGHT, x, y, (new Color(50, 50, 50))
/*     */ 
/*     */         
/* 166 */         .getRGB());
/* 167 */     mc.getFontRenderer().drawString(bigText, x - 3 - 
/*     */         
/* 169 */         mc.getFontRenderer().getStringWidth(bigText), y - 3 - 
/* 170 */         (mc.getFontRenderer()).FONT_HEIGHT, (new Color(255, 255, 255))
/* 171 */         .getRGB());
/*     */   }
/*     */   
/*     */   private double getXpos(Long startTime, Long endTime) {
/* 175 */     if (endTime.longValue() - System.currentTimeMillis() < 300L)
/* 176 */       return Math.pow((1.0F - (float)(endTime.longValue() - System.currentTimeMillis()) / 300.0F), 3.0D); 
/* 177 */     if (System.currentTimeMillis() - startTime.longValue() < 300L) {
/* 178 */       return 1.0D - Math.pow(((float)(System.currentTimeMillis() - startTime.longValue()) / 300.0F), 3.0D);
/*     */     }
/* 180 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNotification(String bigText, String smallText, Long duration) {
/* 185 */     if (!((Boolean)this.notification.getValue()).booleanValue())
/* 186 */       return;  this.notText.add(bigText);
/* 187 */     this.notEnd.add(Long.valueOf(System.currentTimeMillis() + duration.longValue()));
/* 188 */     this.notStart.add(Long.valueOf(System.currentTimeMillis()));
/* 189 */     this.notDetailed.add(smallText);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\HUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */