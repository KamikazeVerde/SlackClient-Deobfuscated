/*    */ package com.viaversion.viaversion.libs.kyori.adventure.chat;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ final class ChatTypeImpl
/*    */   implements ChatType
/*    */ {
/*    */   private final Key key;
/*    */   
/*    */   ChatTypeImpl(@NotNull Key key) {
/* 36 */     this.key = key;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Key key() {
/* 41 */     return this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return Internals.toString(this);
/*    */   }
/*    */   
/*    */   static final class BoundImpl
/*    */     implements ChatType.Bound
/*    */   {
/*    */     private final ChatType chatType;
/*    */     
/*    */     BoundImpl(ChatType chatType, Component name, @Nullable Component target) {
/* 55 */       this.chatType = chatType;
/* 56 */       this.name = name;
/* 57 */       this.target = target;
/*    */     } private final Component name; @Nullable
/*    */     private final Component target;
/*    */     @NotNull
/*    */     public ChatType type() {
/* 62 */       return this.chatType;
/*    */     }
/*    */     
/*    */     @NotNull
/*    */     public Component name() {
/* 67 */       return this.name;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public Component target() {
/* 72 */       return this.target;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 77 */       return Internals.toString(this);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\chat\ChatTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */