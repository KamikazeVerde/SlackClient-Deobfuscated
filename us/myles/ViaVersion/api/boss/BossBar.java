/*     */ package us.myles.ViaVersion.api.boss;
/*     */ 
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
/*     */ import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
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
/*     */ @Deprecated
/*     */ public class BossBar<T>
/*     */ {
/*     */   private final com.viaversion.viaversion.api.legacy.bossbar.BossBar bossBar;
/*     */   
/*     */   public BossBar(com.viaversion.viaversion.api.legacy.bossbar.BossBar bossBar) {
/*  34 */     this.bossBar = bossBar;
/*     */   }
/*     */   
/*     */   public String getTitle() {
/*  38 */     return this.bossBar.getTitle();
/*     */   }
/*     */   
/*     */   public BossBar setTitle(String title) {
/*  42 */     this.bossBar.setTitle(title);
/*  43 */     return this;
/*     */   }
/*     */   
/*     */   public float getHealth() {
/*  47 */     return this.bossBar.getHealth();
/*     */   }
/*     */   
/*     */   public BossBar setHealth(float health) {
/*  51 */     this.bossBar.setHealth(health);
/*  52 */     return this;
/*     */   }
/*     */   
/*     */   public BossColor getColor() {
/*  56 */     return BossColor.values()[this.bossBar.getColor().ordinal()];
/*     */   }
/*     */   
/*     */   public BossBar setColor(BossColor color) {
/*  60 */     this.bossBar.setColor(BossColor.values()[color.ordinal()]);
/*  61 */     return this;
/*     */   }
/*     */   
/*     */   public BossStyle getStyle() {
/*  65 */     return BossStyle.values()[this.bossBar.getStyle().ordinal()];
/*     */   }
/*     */   
/*     */   public BossBar setStyle(BossStyle style) {
/*  69 */     this.bossBar.setStyle(BossStyle.values()[style.ordinal()]);
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BossBar addPlayer(T player) {
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public BossBar addPlayer(UUID player) {
/*  79 */     this.bossBar.addPlayer(player);
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BossBar addPlayers(T... players) {
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BossBar removePlayer(T player) {
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public BossBar removePlayer(UUID uuid) {
/*  94 */     this.bossBar.removePlayer(uuid);
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   public BossBar addFlag(BossFlag flag) {
/*  99 */     this.bossBar.addFlag(BossFlag.values()[flag.ordinal()]);
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public BossBar removeFlag(BossFlag flag) {
/* 104 */     this.bossBar.removeFlag(BossFlag.values()[flag.ordinal()]);
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public boolean hasFlag(BossFlag flag) {
/* 109 */     return this.bossBar.hasFlag(BossFlag.values()[flag.ordinal()]);
/*     */   }
/*     */   
/*     */   public Set<UUID> getPlayers() {
/* 113 */     return this.bossBar.getPlayers();
/*     */   }
/*     */   
/*     */   public BossBar show() {
/* 117 */     this.bossBar.show();
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public BossBar hide() {
/* 122 */     this.bossBar.hide();
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 127 */     return this.bossBar.isVisible();
/*     */   }
/*     */   
/*     */   public UUID getId() {
/* 131 */     return this.bossBar.getId();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar\\us\myles\ViaVersion\api\boss\BossBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */