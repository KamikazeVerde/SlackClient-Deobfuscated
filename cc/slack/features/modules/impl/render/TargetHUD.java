/*     */ package cc.slack.features.modules.impl.render;
/*     */ 
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.events.impl.render.RenderEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "TargetHUD", category = Category.RENDER)
/*     */ public class TargetHUD
/*     */   extends Module
/*     */ {
/*  31 */   private final NumberValue<Integer> posX = new NumberValue("Pos-X", Integer.valueOf(100), Integer.valueOf(-100), Integer.valueOf(500), Integer.valueOf(10));
/*  32 */   private final NumberValue<Integer> posY = new NumberValue("Pos-Y", Integer.valueOf(10), Integer.valueOf(-100), Integer.valueOf(500), Integer.valueOf(10));
/*     */   private EntityPlayer player;
/*     */   
/*     */   public TargetHUD() {
/*  36 */     addSettings(new Value[] { (Value)this.posX, (Value)this.posY });
/*     */   }
/*     */ 
/*     */   
/*     */   private int ticksSinceAttack;
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  44 */     this.player = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent event) {
/*  50 */     this.ticksSinceAttack++;
/*     */     
/*  52 */     if (this.ticksSinceAttack > 20) {
/*  53 */       this.player = null;
/*     */     }
/*     */     
/*  56 */     if (mc.getCurrentScreen() instanceof net.minecraft.client.gui.GuiChat) {
/*  57 */       this.player = (EntityPlayer)mc.getPlayer();
/*  58 */       this.ticksSinceAttack = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onPacket(PacketEvent event) {
/*  64 */     if (event.getPacket() instanceof C02PacketUseEntity) {
/*  65 */       C02PacketUseEntity wrapper = (C02PacketUseEntity)event.getPacket();
/*  66 */       if (wrapper.getEntityFromWorld((World)mc.getWorld()) instanceof EntityPlayer && wrapper.getAction() == C02PacketUseEntity.Action.ATTACK) {
/*  67 */         this.ticksSinceAttack = 0;
/*  68 */         this.player = (EntityPlayer)wrapper.getEntityFromWorld((World)mc.getWorld());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onRender(RenderEvent event) {
/*  75 */     if (event.getState() != RenderEvent.State.RENDER_2D)
/*     */       return; 
/*  77 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*  78 */     int x = sr.getScaledWidth() / 2 + ((Integer)this.posX.getValue()).intValue(), y = sr.getScaledHeight() / 2 + ((Integer)this.posY.getValue()).intValue();
/*  79 */     if (this.player == null)
/*     */       return; 
/*  81 */     drawRect(x, y, 120, 40, (new Color(0, 0, 0, 120)).getRGB());
/*  82 */     mc.getFontRenderer().drawString(this.player.getCommandSenderName(), x + 45, y + 8, 5544391);
/*  83 */     double offset = -(this.player.hurtTime * 20);
/*  84 */     Color color = new Color(255, (int)(255.0D + offset), (int)(255.0D + offset));
/*  85 */     GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*  86 */     mc.getTextureManager().bindTexture(((AbstractClientPlayer)this.player).getLocationSkin());
/*  87 */     Gui.drawScaledCustomSizeModalRect(x + 5, y + 5, 3.0F, 3.0F, 3, 3, 30, 30, 24.0F, 24.0F);
/*  88 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  90 */     drawRect(x + 45, y + 20, 70, 15, (new Color(255, 255, 255, 120)).getRGB());
/*     */     
/*  92 */     drawRect(x + 45, y + 20, (int)(70.0F * this.player.getHealth() / this.player.getMaxHealth()), 15, (new Color(90, 150, 200, 200)).getRGB());
/*     */     
/*  94 */     String s = (int)(this.player.getHealth() / this.player.getMaxHealth() * 100.0F) + "%";
/*  95 */     mc.getFontRenderer().drawString(s, x + 45 + 35 - mc.getFontRenderer().getStringWidth(s) / 2, y + 20 + 7 - 
/*  96 */         (mc.getFontRenderer()).FONT_HEIGHT / 2 + 1, -1);
/*     */   }
/*     */   
/*     */   private void drawRect(int x, int y, int width, int height, int color) {
/* 100 */     Gui.drawRect(x, y, x + width, y + height, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\TargetHUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */