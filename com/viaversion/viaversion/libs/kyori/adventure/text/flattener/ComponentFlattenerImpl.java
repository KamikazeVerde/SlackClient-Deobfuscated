/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
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
/*     */ final class ComponentFlattenerImpl
/*     */   implements ComponentFlattener
/*     */ {
/*     */   static final ComponentFlattener BASIC;
/*     */   
/*     */   static {
/*  55 */     BASIC = (ComponentFlattener)(new BuilderImpl()).<KeybindComponent>mapper(KeybindComponent.class, component -> component.keybind()).<ScoreComponent>mapper(ScoreComponent.class, ScoreComponent::value).<SelectorComponent>mapper(SelectorComponent.class, SelectorComponent::pattern).<TextComponent>mapper(TextComponent.class, TextComponent::content).<TranslatableComponent>mapper(TranslatableComponent.class, TranslatableComponent::key).build();
/*  56 */   } static final ComponentFlattener TEXT_ONLY = (ComponentFlattener)(new BuilderImpl())
/*  57 */     .<TextComponent>mapper(TextComponent.class, TextComponent::content)
/*  58 */     .build();
/*     */   
/*     */   private static final int MAX_DEPTH = 512;
/*     */   
/*     */   private final Map<Class<?>, Function<?, String>> flatteners;
/*     */   private final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners;
/*  64 */   private final ConcurrentMap<Class<?>, Handler> propagatedFlatteners = new ConcurrentHashMap<>();
/*     */   private final Function<Component, String> unknownHandler;
/*     */   
/*     */   ComponentFlattenerImpl(Map<Class<?>, Function<?, String>> flatteners, Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners, @Nullable Function<Component, String> unknownHandler) {
/*  68 */     this.flatteners = Collections.unmodifiableMap(new HashMap<>(flatteners));
/*  69 */     this.complexFlatteners = Collections.unmodifiableMap(new HashMap<>(complexFlatteners));
/*  70 */     this.unknownHandler = unknownHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flatten(@NotNull Component input, @NotNull FlattenerListener listener) {
/*  75 */     flatten0(input, listener, 0);
/*     */   }
/*     */   
/*     */   private void flatten0(@NotNull Component input, @NotNull FlattenerListener listener, int depth) {
/*  79 */     Objects.requireNonNull(input, "input");
/*  80 */     Objects.requireNonNull(listener, "listener");
/*  81 */     if (input == Component.empty())
/*  82 */       return;  if (depth > 512) {
/*  83 */       throw new IllegalStateException("Exceeded maximum depth of 512 while attempting to flatten components!");
/*     */     }
/*     */     
/*  86 */     Handler flattener = flattener(input);
/*  87 */     Style inputStyle = input.style();
/*     */     
/*  89 */     listener.pushStyle(inputStyle);
/*     */     try {
/*  91 */       if (flattener != null) {
/*  92 */         flattener.handle(input, listener, depth + 1);
/*     */       }
/*     */       
/*  95 */       if (!input.children().isEmpty()) {
/*  96 */         for (Component child : input.children()) {
/*  97 */           flatten0(child, listener, depth + 1);
/*     */         }
/*     */       }
/*     */     } finally {
/* 101 */       listener.popStyle(inputStyle);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private <T extends Component> Handler flattener(T test) {
/* 107 */     Handler flattener = this.propagatedFlatteners.computeIfAbsent(test.getClass(), key -> {
/*     */           Function<Component, String> value = (Function<Component, String>)this.flatteners.get(key);
/*     */           
/*     */           if (value != null) {
/*     */             return ();
/*     */           }
/*     */           
/*     */           for (Map.Entry<Class<?>, Function<?, String>> entry : this.flatteners.entrySet()) {
/*     */             if (((Class)entry.getKey()).isAssignableFrom(key)) {
/*     */               return ();
/*     */             }
/*     */           } 
/*     */           
/*     */           BiConsumer<Component, Consumer<Component>> complexValue = (BiConsumer<Component, Consumer<Component>>)this.complexFlatteners.get(key);
/*     */           if (complexValue != null) {
/*     */             return ();
/*     */           }
/*     */           for (Map.Entry<Class<?>, BiConsumer<?, Consumer<Component>>> entry : this.complexFlatteners.entrySet()) {
/*     */             if (((Class)entry.getKey()).isAssignableFrom(key)) {
/*     */               return ();
/*     */             }
/*     */           } 
/*     */           return Handler.NONE;
/*     */         });
/* 131 */     if (flattener == Handler.NONE) {
/* 132 */       return (this.unknownHandler == null) ? null : ((component, listener, depth) -> listener.component(this.unknownHandler.apply(component)));
/*     */     }
/* 134 */     return flattener;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentFlattener.Builder toBuilder() {
/* 140 */     return new BuilderImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   static interface Handler {
/*     */     public static final Handler NONE = (input, listener, depth) -> {
/*     */       
/*     */       };
/*     */     
/*     */     void handle(Component param1Component, FlattenerListener param1FlattenerListener, int param1Int); }
/*     */   
/*     */   static final class BuilderImpl implements ComponentFlattener.Builder { private final Map<Class<?>, Function<?, String>> flatteners;
/*     */     private final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners;
/*     */     @Nullable
/*     */     private Function<Component, String> unknownHandler;
/*     */     
/*     */     BuilderImpl() {
/* 157 */       this.flatteners = new HashMap<>();
/* 158 */       this.complexFlatteners = new HashMap<>();
/*     */     }
/*     */     
/*     */     BuilderImpl(Map<Class<?>, Function<?, String>> flatteners, Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners, @Nullable Function<Component, String> unknownHandler) {
/* 162 */       this.flatteners = new HashMap<>(flatteners);
/* 163 */       this.complexFlatteners = new HashMap<>(complexFlatteners);
/* 164 */       this.unknownHandler = unknownHandler;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public ComponentFlattener build() {
/* 169 */       return new ComponentFlattenerImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T extends Component> ComponentFlattener.Builder mapper(@NotNull Class<T> type, @NotNull Function<T, String> converter) {
/* 174 */       validateNoneInHierarchy((Class<? extends Component>)Objects.requireNonNull(type, "type"));
/* 175 */       this.flatteners.put(type, 
/*     */           
/* 177 */           Objects.<Function<?, String>>requireNonNull(converter, "converter"));
/*     */       
/* 179 */       this.complexFlatteners.remove(type);
/* 180 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T extends Component> ComponentFlattener.Builder complexMapper(@NotNull Class<T> type, @NotNull BiConsumer<T, Consumer<Component>> converter) {
/* 185 */       validateNoneInHierarchy((Class<? extends Component>)Objects.requireNonNull(type, "type"));
/* 186 */       this.complexFlatteners.put(type, 
/*     */           
/* 188 */           Objects.<BiConsumer<?, Consumer<Component>>>requireNonNull(converter, "converter"));
/*     */       
/* 190 */       this.flatteners.remove(type);
/* 191 */       return this;
/*     */     }
/*     */     
/*     */     private void validateNoneInHierarchy(Class<? extends Component> beingRegistered) {
/* 195 */       for (Class<?> clazz : this.flatteners.keySet()) {
/* 196 */         testHierarchy(clazz, beingRegistered);
/*     */       }
/*     */       
/* 199 */       for (Class<?> clazz : this.complexFlatteners.keySet()) {
/* 200 */         testHierarchy(clazz, beingRegistered);
/*     */       }
/*     */     }
/*     */     
/*     */     private static void testHierarchy(Class<?> existing, Class<?> beingRegistered) {
/* 205 */       if (!existing.equals(beingRegistered) && (existing.isAssignableFrom(beingRegistered) || beingRegistered.isAssignableFrom(existing))) {
/* 206 */         throw new IllegalArgumentException("Conflict detected between already registered type " + existing + " and newly registered type " + beingRegistered + "! Types in a component flattener must not share a common hierarchy!");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ComponentFlattener.Builder unknownMapper(@Nullable Function<Component, String> converter) {
/* 213 */       this.unknownHandler = converter;
/* 214 */       return this;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\flattener\ComponentFlattenerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */