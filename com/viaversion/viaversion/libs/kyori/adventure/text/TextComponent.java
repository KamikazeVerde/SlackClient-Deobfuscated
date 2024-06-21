/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*    */ import java.util.stream.Stream;
/*    */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
/*    */ import org.jetbrains.annotations.Contract;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface TextComponent
/*    */   extends BuildableComponent<TextComponent, TextComponent.Builder>, ScopedComponent<TextComponent>
/*    */ {
/*    */   @Deprecated
/*    */   @ScheduledForRemoval(inVersion = "5.0.0")
/*    */   @NotNull
/*    */   static TextComponent ofChildren(@NotNull ComponentLike... components) {
/* 55 */     return Component.textOfChildren(components);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   String content();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Contract(pure = true)
/*    */   @NotNull
/*    */   TextComponent content(@NotNull String paramString);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 78 */     return Stream.concat(
/* 79 */         Stream.of(
/* 80 */           ExaminableProperty.of("content", content())), super
/*    */         
/* 82 */         .examinableProperties());
/*    */   }
/*    */   
/*    */   public static interface Builder extends ComponentBuilder<TextComponent, Builder> {
/*    */     @NotNull
/*    */     String content();
/*    */     
/*    */     @Contract("_ -> this")
/*    */     @NotNull
/*    */     Builder content(@NotNull String param1String);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\TextComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */