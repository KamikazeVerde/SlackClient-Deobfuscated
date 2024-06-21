/*     */ package cc.slack.features.modules.impl.ghost;
/*     */ 
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketDirection;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "Backtrack", category = Category.GHOST)
/*     */ public class Backtrack
/*     */   extends Module
/*     */ {
/*  33 */   private final NumberValue<Integer> maxDelay = new NumberValue("Max Delay", Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(40), Integer.valueOf(1));
/*  34 */   private final NumberValue<Integer> backtrackTime = new NumberValue("Backtrack ticks", Integer.valueOf(20), Integer.valueOf(10), Integer.valueOf(60), Integer.valueOf(1));
/*     */   
/*  36 */   private int ticksSinceAttack = 0;
/*  37 */   private int backtrackTicks = 0;
/*     */   
/*     */   private boolean enabled = false;
/*     */   public EntityPlayer player;
/*  41 */   List<List<Packet>> packetCache = new ArrayList<>();
/*  42 */   List<Packet> currentTick = new ArrayList<>();
/*     */   
/*     */   public Backtrack() {
/*  45 */     addSettings(new Value[] { (Value)this.maxDelay, (Value)this.backtrackTime });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  50 */     this.ticksSinceAttack = 0;
/*  51 */     this.backtrackTicks = 0;
/*  52 */     this.enabled = false;
/*  53 */     this.packetCache.clear();
/*  54 */     this.currentTick.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent event) {
/*  60 */     if (!this.currentTick.isEmpty()) {
/*  61 */       this.packetCache.add(this.currentTick);
/*  62 */       this.currentTick.clear();
/*     */     } 
/*  64 */     if (this.enabled) {
/*  65 */       if (this.ticksSinceAttack < ((Integer)this.maxDelay.getValue()).intValue()) {
/*  66 */         this.ticksSinceAttack++;
/*     */       } else {
/*  68 */         releaseFirst();
/*     */       } 
/*     */     }
/*  71 */     if (this.backtrackTicks > 0) {
/*  72 */       this.backtrackTicks--;
/*     */     }
/*  74 */     else if (this.enabled) {
/*  75 */       int cacheSize = this.packetCache.size();
/*  76 */       for (int i = 0; i < cacheSize; i++) {
/*  77 */         releaseFirst();
/*     */       }
/*  79 */       this.enabled = false;
/*  80 */       this.currentTick.clear();
/*  81 */       this.packetCache.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onPacket(PacketEvent event) {
/*  88 */     Packet packet = event.getPacket();
/*     */     
/*  90 */     if (event.getDirection() == PacketDirection.OUTGOING) {
/*  91 */       if (event.getPacket() instanceof C02PacketUseEntity) {
/*  92 */         C02PacketUseEntity wrapper = (C02PacketUseEntity)packet;
/*  93 */         if (wrapper.getEntityFromWorld((World)mc.getWorld()) instanceof EntityPlayer && wrapper.getAction() == C02PacketUseEntity.Action.ATTACK) {
/*  94 */           if (this.backtrackTicks == 0) this.ticksSinceAttack = 0; 
/*  95 */           this.backtrackTicks = ((Integer)this.backtrackTime.getValue()).intValue();
/*  96 */           this.enabled = true;
/*  97 */           this.player = (EntityPlayer)wrapper.getEntityFromWorld((World)mc.getWorld());
/*     */         }
/*     */       
/*     */       } 
/* 101 */     } else if (!(packet instanceof net.minecraft.network.login.server.S00PacketDisconnect) && !(packet instanceof net.minecraft.network.status.server.S00PacketServerInfo) && !(packet instanceof net.minecraft.network.play.server.S3EPacketTeams) && !(packet instanceof net.minecraft.network.play.server.S19PacketEntityStatus) && !(packet instanceof net.minecraft.network.play.server.S02PacketChat) && !(packet instanceof net.minecraft.network.play.server.S3BPacketScoreboardObjective)) {
/*     */ 
/*     */       
/* 104 */       if ((mc.getPlayer()).ticksExisted > 4 && 
/* 105 */         this.enabled) {
/* 106 */         this.currentTick.add(packet);
/* 107 */         event.cancel();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void releaseFirst() {
/* 114 */     if (this.packetCache.isEmpty())
/* 115 */       return;  for (Packet packet : this.packetCache.get(0)) {
/*     */       try {
/* 117 */         packet.processPacket(mc.getMinecraft().getNetHandler().getNetworkManager().getNetHandler());
/* 118 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 122 */     this.packetCache.remove(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\Backtrack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */