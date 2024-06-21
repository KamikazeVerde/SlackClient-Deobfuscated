/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.translation.Translatable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public interface TranslatableComponent
/*     */   extends BuildableComponent<TranslatableComponent, TranslatableComponent.Builder>, ScopedComponent<TranslatableComponent>
/*     */ {
/*     */   @NotNull
/*     */   String key();
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   default TranslatableComponent key(@NotNull Translatable translatable) {
/*  81 */     return key(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   TranslatableComponent key(@NotNull String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   List<Component> args();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   TranslatableComponent args(@NotNull ComponentLike... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   TranslatableComponent args(@NotNull List<? extends ComponentLike> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 124 */     return Stream.concat(
/* 125 */         Stream.of(new ExaminableProperty[] {
/* 126 */             ExaminableProperty.of("key", key()), 
/* 127 */             ExaminableProperty.of("args", args())
/*     */           
/* 129 */           }), super.examinableProperties());
/*     */   }
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
/*     */   public static interface Builder
/*     */     extends ComponentBuilder<TranslatableComponent, Builder>
/*     */   {
/*     */     @Contract(pure = true)
/*     */     @NotNull
/*     */     default Builder key(@NotNull Translatable translatable) {
/* 148 */       return key(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey());
/*     */     }
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder key(@NotNull String param1String);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull ComponentBuilder<?, ?> param1ComponentBuilder);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull ComponentBuilder<?, ?>... param1VarArgs);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull Component param1Component);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull ComponentLike... param1VarArgs);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder args(@NotNull List<? extends ComponentLike> param1List);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\TranslatableComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */