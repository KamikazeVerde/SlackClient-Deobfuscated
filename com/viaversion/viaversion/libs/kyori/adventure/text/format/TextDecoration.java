/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
/*     */ import java.util.Objects;
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
/*     */ public enum TextDecoration
/*     */   implements StyleBuilderApplicable, TextFormat
/*     */ {
/*  45 */   OBFUSCATED("obfuscated"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   BOLD("bold"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   STRIKETHROUGH("strikethrough"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   UNDERLINED("underlined"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   ITALIC("italic");
/*     */   
/*     */   public static final Index<String, TextDecoration> NAMES;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   static {
/*  76 */     NAMES = Index.create(TextDecoration.class, constant -> constant.name);
/*     */   }
/*     */   
/*     */   TextDecoration(String name) {
/*  80 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public final TextDecorationAndState as(boolean state) {
/*  93 */     return withState(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public final TextDecorationAndState as(@NotNull State state) {
/* 106 */     return withState(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final TextDecorationAndState withState(boolean state) {
/* 117 */     return new TextDecorationAndStateImpl(this, State.byBoolean(state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final TextDecorationAndState withState(@NotNull State state) {
/* 128 */     return new TextDecorationAndStateImpl(this, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final TextDecorationAndState withState(@NotNull TriState state) {
/* 139 */     return new TextDecorationAndStateImpl(this, State.byTriState(state));
/*     */   }
/*     */ 
/*     */   
/*     */   public void styleApply(Style.Builder style) {
/* 144 */     style.decorate(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 149 */     return this.name;
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
/*     */   public enum State
/*     */   {
/* 163 */     NOT_SET("not_set"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     FALSE("false"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     TRUE("true");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     State(String name) {
/* 180 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 185 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static State byBoolean(boolean flag) {
/* 196 */       return flag ? TRUE : FALSE;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static State byBoolean(@Nullable Boolean flag) {
/* 207 */       return (flag == null) ? NOT_SET : byBoolean(flag.booleanValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static State byTriState(@NotNull TriState flag) {
/* 218 */       Objects.requireNonNull(flag);
/* 219 */       switch (flag) { case TRUE:
/* 220 */           return TRUE;
/* 221 */         case FALSE: return FALSE;
/* 222 */         case NOT_SET: return NOT_SET; }
/*     */       
/* 224 */       throw new IllegalArgumentException("Unable to turn TriState: " + flag + " into a TextDecoration.State");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\TextDecoration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */