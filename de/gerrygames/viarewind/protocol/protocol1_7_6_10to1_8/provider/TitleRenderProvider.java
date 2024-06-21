/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider;
/*    */ 
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ public abstract class TitleRenderProvider
/*    */   implements Provider {
/* 11 */   protected Map<UUID, Integer> fadeIn = new HashMap<>();
/* 12 */   protected Map<UUID, Integer> stay = new HashMap<>();
/* 13 */   protected Map<UUID, Integer> fadeOut = new HashMap<>();
/* 14 */   protected Map<UUID, String> titles = new HashMap<>();
/* 15 */   protected Map<UUID, String> subTitles = new HashMap<>();
/* 16 */   protected Map<UUID, AtomicInteger> times = new HashMap<>();
/*    */   
/*    */   public void setTimings(UUID uuid, int fadeIn, int stay, int fadeOut) {
/* 19 */     setFadeIn(uuid, fadeIn);
/* 20 */     setStay(uuid, stay);
/* 21 */     setFadeOut(uuid, fadeOut);
/*    */     
/* 23 */     AtomicInteger time = getTime(uuid);
/* 24 */     if (time.get() > 0) time.set(getFadeIn(uuid) + getStay(uuid) + getFadeOut(uuid)); 
/*    */   }
/*    */   
/*    */   public void reset(UUID uuid) {
/* 28 */     this.titles.remove(uuid);
/* 29 */     this.subTitles.remove(uuid);
/* 30 */     getTime(uuid).set(0);
/* 31 */     this.fadeIn.remove(uuid);
/* 32 */     this.stay.remove(uuid);
/* 33 */     this.fadeOut.remove(uuid);
/*    */   }
/*    */   
/*    */   public void setTitle(UUID uuid, String title) {
/* 37 */     this.titles.put(uuid, title);
/* 38 */     getTime(uuid).set(getFadeIn(uuid) + getStay(uuid) + getFadeOut(uuid));
/*    */   }
/*    */   
/*    */   public void setSubTitle(UUID uuid, String subTitle) {
/* 42 */     this.subTitles.put(uuid, subTitle);
/*    */   }
/*    */   
/*    */   public void clear(UUID uuid) {
/* 46 */     this.titles.remove(uuid);
/* 47 */     this.subTitles.remove(uuid);
/* 48 */     getTime(uuid).set(0);
/*    */   }
/*    */   
/*    */   public AtomicInteger getTime(UUID uuid) {
/* 52 */     return this.times.computeIfAbsent(uuid, key -> new AtomicInteger(0));
/*    */   }
/*    */   
/*    */   public int getFadeIn(UUID uuid) {
/* 56 */     return ((Integer)this.fadeIn.getOrDefault(uuid, Integer.valueOf(10))).intValue();
/*    */   }
/*    */   
/*    */   public int getStay(UUID uuid) {
/* 60 */     return ((Integer)this.stay.getOrDefault(uuid, Integer.valueOf(70))).intValue();
/*    */   }
/*    */   
/*    */   public int getFadeOut(UUID uuid) {
/* 64 */     return ((Integer)this.fadeOut.getOrDefault(uuid, Integer.valueOf(20))).intValue();
/*    */   }
/*    */   
/*    */   public void setFadeIn(UUID uuid, int fadeIn) {
/* 68 */     if (fadeIn >= 0) { this.fadeIn.put(uuid, Integer.valueOf(fadeIn)); }
/* 69 */     else { this.fadeIn.remove(uuid); }
/*    */   
/*    */   }
/*    */   public void setStay(UUID uuid, int stay) {
/* 73 */     if (stay >= 0) { this.stay.put(uuid, Integer.valueOf(stay)); }
/* 74 */     else { this.stay.remove(uuid); }
/*    */   
/*    */   }
/*    */   public void setFadeOut(UUID uuid, int fadeOut) {
/* 78 */     if (fadeOut >= 0) { this.fadeOut.put(uuid, Integer.valueOf(fadeOut)); }
/* 79 */     else { this.fadeOut.remove(uuid); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\provider\TitleRenderProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */