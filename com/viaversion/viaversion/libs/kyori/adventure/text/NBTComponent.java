/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.stream.Stream;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public interface NBTComponent<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>>
/*     */   extends BuildableComponent<C, B>
/*     */ {
/*     */   @NotNull
/*     */   String nbtPath();
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   C nbtPath(@NotNull String paramString);
/*     */   
/*     */   boolean interpret();
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   C interpret(boolean paramBoolean);
/*     */   
/*     */   @Nullable
/*     */   Component separator();
/*     */   
/*     */   @NotNull
/*     */   C separator(@Nullable ComponentLike paramComponentLike);
/*     */   
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 110 */     return Stream.concat(
/* 111 */         Stream.of(new ExaminableProperty[] {
/* 112 */             ExaminableProperty.of("nbtPath", nbtPath()), 
/* 113 */             ExaminableProperty.of("interpret", interpret()), 
/* 114 */             ExaminableProperty.of("separator", separator())
/*     */           
/* 116 */           }), super.examinableProperties());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\NBTComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */