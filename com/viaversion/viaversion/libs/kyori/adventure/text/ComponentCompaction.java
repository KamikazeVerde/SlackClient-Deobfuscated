/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ final class ComponentCompaction
/*     */ {
/*     */   static Component compact(@NotNull Component self, @Nullable Style parentStyle) {
/*  40 */     List<Component> children = self.children();
/*  41 */     Component optimized = self.children(Collections.emptyList());
/*  42 */     if (parentStyle != null) {
/*  43 */       optimized = optimized.style(self.style().unmerge(parentStyle));
/*     */     }
/*     */     
/*  46 */     int childrenSize = children.size();
/*     */     
/*  48 */     if (childrenSize == 0) {
/*     */       
/*  50 */       if (isBlank(optimized)) {
/*  51 */         optimized = optimized.style(simplifyStyleForBlank(optimized.style(), parentStyle));
/*     */       }
/*     */ 
/*     */       
/*  55 */       return optimized;
/*     */     } 
/*     */ 
/*     */     
/*  59 */     if (childrenSize == 1 && optimized instanceof TextComponent) {
/*  60 */       TextComponent textComponent = (TextComponent)optimized;
/*     */       
/*  62 */       if (textComponent.content().isEmpty()) {
/*  63 */         Component child = children.get(0);
/*     */ 
/*     */         
/*  66 */         return child.style(child.style().merge(optimized.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)).compact();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  72 */     Style childParentStyle = optimized.style();
/*  73 */     if (parentStyle != null) {
/*  74 */       childParentStyle = childParentStyle.merge(parentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
/*     */     }
/*     */ 
/*     */     
/*  78 */     List<Component> childrenToAppend = new ArrayList<>(children.size()); int i;
/*  79 */     for (i = 0; i < children.size(); i++) {
/*  80 */       Component child = children.get(i);
/*     */ 
/*     */       
/*  83 */       child = compact(child, childParentStyle);
/*     */ 
/*     */       
/*  86 */       if (child.children().isEmpty() && child instanceof TextComponent) {
/*  87 */         TextComponent textComponent = (TextComponent)child;
/*     */         
/*  89 */         if (textComponent.content().isEmpty()) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/*  94 */       childrenToAppend.add(child);
/*     */       
/*     */       continue;
/*     */     } 
/*  98 */     if (optimized instanceof TextComponent) {
/*  99 */       while (!childrenToAppend.isEmpty()) {
/* 100 */         Component child = childrenToAppend.get(0);
/* 101 */         Style childStyle = child.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
/*     */         
/* 103 */         if (child instanceof TextComponent && Objects.equals(childStyle, childParentStyle)) {
/*     */ 
/*     */           
/* 106 */           optimized = joinText((TextComponent)optimized, (TextComponent)child);
/* 107 */           childrenToAppend.remove(0);
/*     */ 
/*     */           
/* 110 */           childrenToAppend.addAll(0, child.children());
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     for (i = 0; i + 1 < childrenToAppend.size(); ) {
/* 121 */       Component child = childrenToAppend.get(i);
/* 122 */       Component neighbor = childrenToAppend.get(i + 1);
/*     */       
/* 124 */       if (child.children().isEmpty() && child instanceof TextComponent && neighbor instanceof TextComponent) {
/*     */         
/* 126 */         Style childStyle = child.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
/* 127 */         Style neighborStyle = neighbor.style().merge(childParentStyle, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
/*     */ 
/*     */         
/* 130 */         if (childStyle.equals(neighborStyle)) {
/* 131 */           Component combined = joinText((TextComponent)child, (TextComponent)neighbor);
/*     */ 
/*     */           
/* 134 */           childrenToAppend.set(i, combined);
/* 135 */           childrenToAppend.remove(i + 1);
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 143 */       i++;
/*     */     } 
/*     */ 
/*     */     
/* 147 */     if (childrenToAppend.isEmpty() && isBlank(optimized)) {
/* 148 */       optimized = optimized.style(simplifyStyleForBlank(optimized.style(), parentStyle));
/*     */     }
/*     */     
/* 151 */     return optimized.children((List)childrenToAppend);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isBlank(Component component) {
/* 161 */     if (component instanceof TextComponent) {
/* 162 */       TextComponent textComponent = (TextComponent)component;
/*     */       
/* 164 */       String content = textComponent.content();
/*     */       
/* 166 */       for (int i = 0; i < content.length(); i++) {
/* 167 */         char c = content.charAt(i);
/* 168 */         if (c != ' ') return false;
/*     */       
/*     */       } 
/* 171 */       return true;
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static Style simplifyStyleForBlank(@NotNull Style style, @Nullable Style parentStyle) {
/* 185 */     Style.Builder builder = style.toBuilder();
/*     */ 
/*     */     
/* 188 */     if (!style.hasDecoration(TextDecoration.UNDERLINED) && !style.hasDecoration(TextDecoration.STRIKETHROUGH) && (parentStyle == null || (
/* 189 */       !parentStyle.hasDecoration(TextDecoration.UNDERLINED) && !parentStyle.hasDecoration(TextDecoration.STRIKETHROUGH)))) {
/* 190 */       builder.color(null);
/*     */     }
/*     */ 
/*     */     
/* 194 */     builder.decoration(TextDecoration.ITALIC, TextDecoration.State.NOT_SET);
/* 195 */     builder.decoration(TextDecoration.OBFUSCATED, TextDecoration.State.NOT_SET);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     return builder.build();
/*     */   }
/*     */   
/*     */   private static TextComponent joinText(TextComponent one, TextComponent two) {
/* 206 */     return TextComponentImpl.create((List)two.children(), one.style(), one.content() + two.content());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\ComponentCompaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */