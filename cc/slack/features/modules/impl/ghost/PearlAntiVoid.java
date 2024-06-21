/*     */ package cc.slack.features.modules.impl.ghost;
/*     */ 
/*     */ import cc.slack.events.State;
/*     */ import cc.slack.events.impl.network.DisconnectEvent;
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.events.impl.player.MotionEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.other.PrintUtil;
/*     */ import cc.slack.utils.player.PlayerUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketDirection;
/*     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "PearlAntiVoid", category = Category.GHOST)
/*     */ public class PearlAntiVoid
/*     */   extends Module
/*     */ {
/*     */   private int overVoidTicks;
/*     */   private Vec3 position;
/*     */   private Vec3 motion;
/*     */   private boolean wasVoid;
/*     */   private boolean setBack;
/*     */   boolean shouldStuck;
/*     */   double x;
/*     */   double y;
/*     */   double z;
/*     */   boolean wait;
/*  39 */   private final NumberValue<Integer> fall = new NumberValue("Min fall distance", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(10), Integer.valueOf(1));
/*     */   
/*     */   public PearlAntiVoid() {
/*  42 */     addSettings(new Value[] { (Value)this.fall });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  47 */     (mc.getTimer()).timerSpeed = 1.0F;
/*  48 */     (mc.getPlayer()).isDead = false;
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onDisconnect(DisconnectEvent event) {
/*  53 */     PrintUtil.print("XD");
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onPacket(PacketEvent e) {
/*  58 */     Packet<?> p = e.getPacket();
/*  59 */     if (e.getDirection() == PacketDirection.OUTGOING) {
/*  60 */       if (!(mc.getPlayer()).onGround && this.shouldStuck && p instanceof net.minecraft.network.play.client.C03PacketPlayer && !(p instanceof net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook) && !(p instanceof net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook))
/*     */       {
/*     */         
/*  63 */         e.cancel();
/*     */       }
/*  65 */       if (p instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement && this.wait) {
/*  66 */         this.shouldStuck = false;
/*  67 */         (mc.getTimer()).timerSpeed = 0.2F;
/*  68 */         this.wait = false;
/*     */       } 
/*     */     } 
/*  71 */     if (e.getDirection() == PacketDirection.INCOMING && 
/*  72 */       p instanceof S08PacketPlayerPosLook) {
/*  73 */       S08PacketPlayerPosLook wrapper = (S08PacketPlayerPosLook)p;
/*  74 */       this.x = wrapper.getX();
/*  75 */       this.y = wrapper.getY();
/*  76 */       this.z = wrapper.getZ();
/*  77 */       (mc.getTimer()).timerSpeed = 0.2F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onMotion(MotionEvent e) {
/*     */     try {
/*  85 */       if (e.getState() != State.POST) {
/*  86 */         if (mc.getPlayer().getHeldItem() == null) {
/*  87 */           (mc.getTimer()).timerSpeed = 1.0F;
/*     */         }
/*     */         
/*  90 */         if (mc.getPlayer().getHeldItem().getItem() instanceof net.minecraft.item.ItemEnderPearl) {
/*  91 */           this.wait = true;
/*     */         }
/*     */         
/*  94 */         if (this.shouldStuck && !(mc.getPlayer()).onGround) {
/*  95 */           (mc.getPlayer()).motionX = 0.0D;
/*  96 */           (mc.getPlayer()).motionY = 0.0D;
/*  97 */           (mc.getPlayer()).motionZ = 0.0D;
/*  98 */           mc.getPlayer().setPositionAndRotation(this.x, this.y, this.z, (mc.getPlayer()).rotationYaw, 
/*  99 */               (mc.getPlayer()).rotationPitch);
/*     */         } 
/* 101 */         boolean overVoid = (!(mc.getPlayer()).onGround && !PlayerUtil.isBlockUnderP(30));
/* 102 */         if (!overVoid) {
/* 103 */           this.shouldStuck = false;
/* 104 */           this.x = (mc.getPlayer()).posX;
/* 105 */           this.y = (mc.getPlayer()).posY;
/* 106 */           this.z = (mc.getPlayer()).posZ;
/* 107 */           (mc.getTimer()).timerSpeed = 1.0F;
/*     */         } 
/* 109 */         if (overVoid) {
/* 110 */           this.overVoidTicks++;
/* 111 */         } else if ((mc.getPlayer()).onGround) {
/* 112 */           this.overVoidTicks = 0;
/*     */         } 
/* 114 */         if (overVoid && this.position != null && this.motion != null && this.overVoidTicks < 30.0D + ((Integer)this.fall
/* 115 */           .getValue()).intValue() * 20.0D) {
/* 116 */           if (!this.setBack) {
/* 117 */             this.wasVoid = true;
/* 118 */             if ((mc.getPlayer()).fallDistance > ((Integer)this.fall.getValue()).intValue()) {
/* 119 */               (mc.getPlayer()).fallDistance = 0.0F;
/* 120 */               this.setBack = true;
/* 121 */               this.shouldStuck = true;
/* 122 */               this.x = (mc.getPlayer()).posX;
/* 123 */               this.y = (mc.getPlayer()).posY;
/* 124 */               this.z = (mc.getPlayer()).posZ;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 128 */           if (this.shouldStuck) {
/* 129 */             toggle();
/*     */           }
/* 131 */           this.shouldStuck = false;
/* 132 */           (mc.getTimer()).timerSpeed = 1.0F;
/* 133 */           this.setBack = false;
/* 134 */           if (this.wasVoid) {
/* 135 */             this.wasVoid = false;
/*     */           }
/* 137 */           this.motion = new Vec3((mc.getPlayer()).motionX, (mc.getPlayer()).motionY, (mc.getPlayer()).motionZ);
/* 138 */           this.position = new Vec3((mc.getPlayer()).posX, (mc.getPlayer()).posY, (mc.getPlayer()).posZ);
/*     */         } 
/*     */       } 
/* 141 */     } catch (NullPointerException nullPointerException) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\PearlAntiVoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */