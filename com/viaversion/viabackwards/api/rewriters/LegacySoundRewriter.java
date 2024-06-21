/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.rewriter.RewriterBase;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
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
/*     */ public abstract class LegacySoundRewriter<T extends BackwardsProtocol<?, ?, ?, ?>>
/*     */   extends RewriterBase<T>
/*     */ {
/*  28 */   protected final Int2ObjectMap<SoundData> soundRewrites = (Int2ObjectMap<SoundData>)new Int2ObjectOpenHashMap(64);
/*     */   
/*     */   protected LegacySoundRewriter(T protocol) {
/*  31 */     super((Protocol)protocol);
/*     */   }
/*     */   
/*     */   public SoundData added(int id, int replacement) {
/*  35 */     return added(id, replacement, -1.0F);
/*     */   }
/*     */   
/*     */   public SoundData added(int id, int replacement, float newPitch) {
/*  39 */     SoundData data = new SoundData(replacement, true, newPitch, true);
/*  40 */     this.soundRewrites.put(id, data);
/*  41 */     return data;
/*     */   }
/*     */   
/*     */   public SoundData removed(int id) {
/*  45 */     SoundData data = new SoundData(-1, false, -1.0F, false);
/*  46 */     this.soundRewrites.put(id, data);
/*  47 */     return data;
/*     */   }
/*     */   
/*     */   public int handleSounds(int soundId) {
/*  51 */     int newSoundId = soundId;
/*  52 */     SoundData data = (SoundData)this.soundRewrites.get(soundId);
/*  53 */     if (data != null) return data.getReplacementSound();
/*     */     
/*  55 */     for (ObjectIterator<Int2ObjectMap.Entry<SoundData>> objectIterator = this.soundRewrites.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<SoundData> entry = objectIterator.next();
/*  56 */       if (soundId > entry.getIntKey()) {
/*  57 */         if (((SoundData)entry.getValue()).isAdded()) {
/*  58 */           newSoundId--; continue;
/*     */         } 
/*  60 */         newSoundId++;
/*     */       }  }
/*     */ 
/*     */     
/*  64 */     return newSoundId;
/*     */   }
/*     */   
/*     */   public boolean hasPitch(int soundId) {
/*  68 */     SoundData data = (SoundData)this.soundRewrites.get(soundId);
/*  69 */     return (data != null && data.isChangePitch());
/*     */   }
/*     */   
/*     */   public float handlePitch(int soundId) {
/*  73 */     SoundData data = (SoundData)this.soundRewrites.get(soundId);
/*  74 */     return (data != null) ? data.getNewPitch() : 1.0F;
/*     */   }
/*     */   
/*     */   public static final class SoundData {
/*     */     private final int replacementSound;
/*     */     private final boolean changePitch;
/*     */     private final float newPitch;
/*     */     private final boolean added;
/*     */     
/*     */     public SoundData(int replacementSound, boolean changePitch, float newPitch, boolean added) {
/*  84 */       this.replacementSound = replacementSound;
/*  85 */       this.changePitch = changePitch;
/*  86 */       this.newPitch = newPitch;
/*  87 */       this.added = added;
/*     */     }
/*     */     
/*     */     public int getReplacementSound() {
/*  91 */       return this.replacementSound;
/*     */     }
/*     */     
/*     */     public boolean isChangePitch() {
/*  95 */       return this.changePitch;
/*     */     }
/*     */     
/*     */     public float getNewPitch() {
/*  99 */       return this.newPitch;
/*     */     }
/*     */     
/*     */     public boolean isAdded() {
/* 103 */       return this.added;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\LegacySoundRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */