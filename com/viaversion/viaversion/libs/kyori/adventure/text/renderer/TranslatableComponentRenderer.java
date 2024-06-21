/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.translation.Translator;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ public abstract class TranslatableComponentRenderer<C>
/*     */   extends AbstractComponentRenderer<C>
/*     */ {
/*  60 */   private static final Set<Style.Merge> MERGES = Style.Merge.merges(new Style.Merge[] { Style.Merge.COLOR, Style.Merge.DECORATIONS, Style.Merge.INSERTION, Style.Merge.FONT });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static TranslatableComponentRenderer<Locale> usingTranslationSource(@NotNull final Translator source) {
/*  70 */     Objects.requireNonNull(source, "source");
/*  71 */     return new TranslatableComponentRenderer<Locale>() {
/*     */         @Nullable
/*     */         protected MessageFormat translate(@NotNull String key, @NotNull Locale context) {
/*  74 */           return source.translate(key, context);
/*     */         }
/*     */       };
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
/*     */   @NotNull
/*     */   protected Component renderBlockNbt(@NotNull BlockNBTComponent component, @NotNull C context) {
/*  91 */     BlockNBTComponent.Builder builder = ((BlockNBTComponent.Builder)nbt(context, Component.blockNBT(), component)).pos(component.pos());
/*  92 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Component renderEntityNbt(@NotNull EntityNBTComponent component, @NotNull C context) {
/*  98 */     EntityNBTComponent.Builder builder = ((EntityNBTComponent.Builder)nbt(context, Component.entityNBT(), component)).selector(component.selector());
/*  99 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Component renderStorageNbt(@NotNull StorageNBTComponent component, @NotNull C context) {
/* 105 */     StorageNBTComponent.Builder builder = ((StorageNBTComponent.Builder)nbt(context, Component.storageNBT(), component)).storage(component.storage());
/* 106 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */   
/*     */   protected <O extends com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent<O, B>, B extends com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder<O, B>> B nbt(@NotNull C context, B builder, O oldComponent) {
/* 110 */     builder
/* 111 */       .nbtPath(oldComponent.nbtPath())
/* 112 */       .interpret(oldComponent.interpret());
/* 113 */     Component separator = oldComponent.separator();
/* 114 */     if (separator != null) {
/* 115 */       builder.separator((ComponentLike)render(separator, context));
/*     */     }
/* 117 */     return builder;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Component renderKeybind(@NotNull KeybindComponent component, @NotNull C context) {
/* 122 */     KeybindComponent.Builder builder = Component.keybind().keybind(component.keybind());
/* 123 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Component renderScore(@NotNull ScoreComponent component, @NotNull C context) {
/* 132 */     ScoreComponent.Builder builder = Component.score().name(component.name()).objective(component.objective()).value(component.value());
/* 133 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Component renderSelector(@NotNull SelectorComponent component, @NotNull C context) {
/* 138 */     SelectorComponent.Builder builder = Component.selector().pattern(component.pattern());
/* 139 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected Component renderText(@NotNull TextComponent component, @NotNull C context) {
/* 144 */     TextComponent.Builder builder = Component.text().content(component.content());
/* 145 */     return mergeStyleAndOptionallyDeepRender((Component)component, builder, context);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Component renderTranslatable(@NotNull TranslatableComponent component, @NotNull C context) {
/* 151 */     MessageFormat format = translate(component.key(), context);
/* 152 */     if (format == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       TranslatableComponent.Builder builder1 = Component.translatable().key(component.key());
/* 158 */       if (!component.args().isEmpty()) {
/* 159 */         List<Component> list = new ArrayList<>(component.args());
/* 160 */         for (int i = 0, size = list.size(); i < size; i++) {
/* 161 */           list.set(i, render(list.get(i), context));
/*     */         }
/* 163 */         builder1.args(list);
/*     */       } 
/* 165 */       return mergeStyleAndOptionallyDeepRender((Component)component, builder1, context);
/*     */     } 
/*     */     
/* 168 */     List<Component> args = component.args();
/*     */     
/* 170 */     TextComponent.Builder builder = Component.text();
/* 171 */     mergeStyle((Component)component, builder, context);
/*     */ 
/*     */     
/* 174 */     if (args.isEmpty()) {
/* 175 */       builder.content(format.format((Object[])null, new StringBuffer(), (FieldPosition)null).toString());
/* 176 */       return optionallyRenderChildrenAppendAndBuild(component.children(), builder, context);
/*     */     } 
/*     */     
/* 179 */     Object[] nulls = new Object[args.size()];
/* 180 */     StringBuffer sb = format.format(nulls, new StringBuffer(), (FieldPosition)null);
/* 181 */     AttributedCharacterIterator it = format.formatToCharacterIterator(nulls);
/*     */     
/* 183 */     while (it.getIndex() < it.getEndIndex()) {
/* 184 */       int end = it.getRunLimit();
/* 185 */       Integer index = (Integer)it.getAttribute(MessageFormat.Field.ARGUMENT);
/* 186 */       if (index != null) {
/* 187 */         builder.append(render(args.get(index.intValue()), context));
/*     */       } else {
/* 189 */         builder.append((Component)Component.text(sb.substring(it.getIndex(), end)));
/*     */       } 
/* 191 */       it.setIndex(end);
/*     */     } 
/*     */     
/* 194 */     return optionallyRenderChildrenAppendAndBuild(component.children(), builder, context);
/*     */   }
/*     */   
/*     */   protected <O extends com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent<O, B>, B extends ComponentBuilder<O, B>> O mergeStyleAndOptionallyDeepRender(Component component, B builder, C context) {
/* 198 */     mergeStyle(component, (ComponentBuilder<?, ?>)builder, context);
/* 199 */     return optionallyRenderChildrenAppendAndBuild(component.children(), builder, context);
/*     */   }
/*     */   
/*     */   protected <O extends com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent<O, B>, B extends ComponentBuilder<O, B>> O optionallyRenderChildrenAppendAndBuild(List<Component> children, B builder, C context) {
/* 203 */     if (!children.isEmpty()) {
/* 204 */       children.forEach(child -> builder.append(render(child, (C)context)));
/*     */     }
/* 206 */     return (O)builder.build();
/*     */   }
/*     */   
/*     */   protected <B extends ComponentBuilder<?, ?>> void mergeStyle(Component component, B builder, C context) {
/* 210 */     builder.mergeStyle(component, MERGES);
/* 211 */     builder.clickEvent(component.clickEvent());
/* 212 */     HoverEvent<?> hoverEvent = component.hoverEvent();
/* 213 */     if (hoverEvent != null)
/* 214 */       builder.hoverEvent((HoverEventSource)hoverEvent.withRenderedValue(this, context)); 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected abstract MessageFormat translate(@NotNull String paramString, @NotNull C paramC);
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\renderer\TranslatableComponentRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */