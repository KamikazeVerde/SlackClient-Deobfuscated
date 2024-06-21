/*     */ package cc.slack.features.modules.impl.player;
/*     */ 
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.player.BlinkUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "Antivoid", category = Category.PLAYER)
/*     */ public class AntiVoid
/*     */   extends Module
/*     */ {
/*  28 */   private final ModeValue<String> antivoidMode = new ModeValue((Object[])new String[] { "Universal", "Self TP", "Polar" });
/*     */   
/*  30 */   private double groundX = 0.0D;
/*  31 */   private double groundY = 0.0D;
/*  32 */   private double groundZ = 0.0D;
/*     */   
/*     */   private boolean universalStarted = false;
/*     */   
/*     */   private boolean universalFlag = false;
/*     */   
/*     */   private boolean triedTP = false;
/*     */ 
/*     */   
/*     */   public AntiVoid() {
/*  42 */     addSettings(new Value[] { (Value)this.antivoidMode });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  47 */     this.universalStarted = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  52 */     if (this.antivoidMode.getValue() == "Universal") BlinkUtil.disable();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent event) {
/*  59 */     switch (((String)this.antivoidMode.getValue()).toLowerCase()) {
/*     */       case "universal":
/*  61 */         if (this.universalStarted) {
/*  62 */           if ((mc.getPlayer()).onGround) {
/*  63 */             BlinkUtil.disable();
/*  64 */             this.universalStarted = false;
/*  65 */             this.universalFlag = false; break;
/*  66 */           }  if ((mc.getPlayer()).fallDistance > 8.0F && !this.universalFlag) {
/*  67 */             this.universalFlag = true;
/*  68 */             mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.groundX, this.groundY + 1.0D, this.groundZ, false));
/*     */           }  break;
/*  70 */         }  if ((mc.getPlayer()).fallDistance > 0.0F && !(mc.getPlayer()).onGround && (mc.getPlayer()).motionY < 0.0D && 
/*  71 */           isOverVoid()) {
/*  72 */           this.universalStarted = true;
/*  73 */           this.universalFlag = false;
/*  74 */           BlinkUtil.enable(false, true);
/*  75 */           this.groundX = (mc.getPlayer()).posX;
/*  76 */           this.groundY = (mc.getPlayer()).posY;
/*  77 */           this.groundZ = (mc.getPlayer()).posZ;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case "self tp":
/*  82 */         if ((mc.getPlayer()).onGround) {
/*  83 */           this.groundX = (mc.getPlayer()).posX;
/*  84 */           this.groundY = (mc.getPlayer()).posY;
/*  85 */           this.groundZ = (mc.getPlayer()).posZ;
/*  86 */           this.triedTP = false; break;
/*  87 */         }  if ((mc.getPlayer()).fallDistance > 5.0F && !this.triedTP) {
/*  88 */           mc.getPlayer().setPosition(this.groundX, this.groundY, this.groundZ);
/*  89 */           (mc.getPlayer()).fallDistance = 0.0F;
/*  90 */           (mc.getPlayer()).motionX = 0.0D;
/*  91 */           (mc.getPlayer()).motionY = 0.0D;
/*  92 */           (mc.getPlayer()).motionZ = 0.0D;
/*  93 */           this.triedTP = true;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onPacket(PacketEvent event) {
/* 101 */     switch (((String)this.antivoidMode.getValue()).toLowerCase()) {
/*     */       case "universal":
/* 103 */         if (event.getPacket() instanceof S08PacketPlayerPosLook && (
/* 104 */           (S08PacketPlayerPosLook)event.getPacket()).getX() == this.groundX && ((S08PacketPlayerPosLook)event.getPacket()).getY() == this.groundY && ((S08PacketPlayerPosLook)event.getPacket()).getZ() == this.groundZ) {
/* 105 */           BlinkUtil.disable(false);
/* 106 */           mc.getPlayer().setPosition(this.groundX, this.groundY, this.groundZ);
/* 107 */           this.universalFlag = false;
/* 108 */           this.universalStarted = false;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isOverVoid() {
/* 116 */     return (mc.getWorld().rayTraceBlocks(new Vec3(
/* 117 */           (mc.getPlayer()).posX, (mc.getPlayer()).posY, (mc.getPlayer()).posZ), new Vec3(
/* 118 */           (mc.getPlayer()).posX, (mc.getPlayer()).posY - 40.0D, (mc.getPlayer()).posZ), true, true, false) == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\AntiVoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */