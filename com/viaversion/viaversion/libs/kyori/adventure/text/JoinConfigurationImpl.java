/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
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
/*     */ final class JoinConfigurationImpl
/*     */   implements JoinConfiguration
/*     */ {
/*  39 */   static final Function<ComponentLike, Component> DEFAULT_CONVERTOR = ComponentLike::asComponent;
/*     */   static final Predicate<ComponentLike> DEFAULT_PREDICATE = componentLike -> true;
/*  41 */   static final JoinConfigurationImpl NULL = new JoinConfigurationImpl();
/*     */   
/*  43 */   static final JoinConfiguration STANDARD_NEW_LINES = JoinConfiguration.separator(Component.newline());
/*  44 */   static final JoinConfiguration STANDARD_COMMA_SEPARATED = JoinConfiguration.separator(Component.text(","));
/*  45 */   static final JoinConfiguration STANDARD_COMMA_SPACE_SEPARATED = JoinConfiguration.separator(Component.text(", "));
/*  46 */   static final JoinConfiguration STANDARD_ARRAY_LIKE = (JoinConfiguration)JoinConfiguration.builder()
/*  47 */     .separator(Component.text(", "))
/*  48 */     .prefix(Component.text("["))
/*  49 */     .suffix(Component.text("]"))
/*  50 */     .build();
/*     */   
/*     */   private final Component prefix;
/*     */   private final Component suffix;
/*     */   private final Component separator;
/*     */   private final Component lastSeparator;
/*     */   private final Component lastSeparatorIfSerial;
/*     */   private final Function<ComponentLike, Component> convertor;
/*     */   private final Predicate<ComponentLike> predicate;
/*     */   private final Style rootStyle;
/*     */   
/*     */   private JoinConfigurationImpl() {
/*  62 */     this.prefix = null;
/*  63 */     this.suffix = null;
/*  64 */     this.separator = null;
/*  65 */     this.lastSeparator = null;
/*  66 */     this.lastSeparatorIfSerial = null;
/*  67 */     this.convertor = DEFAULT_CONVERTOR;
/*  68 */     this.predicate = DEFAULT_PREDICATE;
/*  69 */     this.rootStyle = Style.empty();
/*     */   }
/*     */   
/*     */   private JoinConfigurationImpl(@NotNull BuilderImpl builder) {
/*  73 */     this.prefix = ComponentLike.unbox(builder.prefix);
/*  74 */     this.suffix = ComponentLike.unbox(builder.suffix);
/*  75 */     this.separator = ComponentLike.unbox(builder.separator);
/*  76 */     this.lastSeparator = ComponentLike.unbox(builder.lastSeparator);
/*  77 */     this.lastSeparatorIfSerial = ComponentLike.unbox(builder.lastSeparatorIfSerial);
/*  78 */     this.convertor = builder.convertor;
/*  79 */     this.predicate = builder.predicate;
/*  80 */     this.rootStyle = builder.rootStyle;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component prefix() {
/*  85 */     return this.prefix;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component suffix() {
/*  90 */     return this.suffix;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component separator() {
/*  95 */     return this.separator;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component lastSeparator() {
/* 100 */     return this.lastSeparator;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component lastSeparatorIfSerial() {
/* 105 */     return this.lastSeparatorIfSerial;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Function<ComponentLike, Component> convertor() {
/* 110 */     return this.convertor;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Predicate<ComponentLike> predicate() {
/* 115 */     return this.predicate;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style parentStyle() {
/* 120 */     return this.rootStyle;
/*     */   }
/*     */ 
/*     */   
/*     */   public JoinConfiguration.Builder toBuilder() {
/* 125 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 130 */     return Stream.of(new ExaminableProperty[] {
/* 131 */           ExaminableProperty.of("prefix", this.prefix), 
/* 132 */           ExaminableProperty.of("suffix", this.suffix), 
/* 133 */           ExaminableProperty.of("separator", this.separator), 
/* 134 */           ExaminableProperty.of("lastSeparator", this.lastSeparator), 
/* 135 */           ExaminableProperty.of("lastSeparatorIfSerial", this.lastSeparatorIfSerial), 
/* 136 */           ExaminableProperty.of("convertor", this.convertor), 
/* 137 */           ExaminableProperty.of("predicate", this.predicate), 
/* 138 */           ExaminableProperty.of("rootStyle", this.rootStyle)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return Internals.toString(this);
/*     */   }
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   static Component join(@NotNull JoinConfiguration config, @NotNull Iterable<? extends ComponentLike> components) {
/* 149 */     Objects.requireNonNull(config, "config");
/* 150 */     Objects.requireNonNull(components, "components");
/*     */     
/* 152 */     Iterator<? extends ComponentLike> it = components.iterator();
/*     */     
/* 154 */     if (!it.hasNext()) {
/* 155 */       return singleElementJoin(config, null);
/*     */     }
/*     */     
/* 158 */     ComponentLike component = Objects.<ComponentLike>requireNonNull(it.next(), "Null elements in \"components\" are not allowed");
/* 159 */     int componentsSeen = 0;
/*     */     
/* 161 */     if (!it.hasNext()) {
/* 162 */       return singleElementJoin(config, component);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     Component prefix = config.prefix();
/* 169 */     Component suffix = config.suffix();
/* 170 */     Function<ComponentLike, Component> convertor = config.convertor();
/* 171 */     Predicate<ComponentLike> predicate = config.predicate();
/* 172 */     Style rootStyle = config.parentStyle();
/* 173 */     boolean hasRootStyle = (rootStyle != Style.empty());
/*     */     
/* 175 */     Component separator = config.separator();
/* 176 */     boolean hasSeparator = (separator != null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     TextComponent.Builder builder = hasRootStyle ? Component.text().style(rootStyle) : Component.text();
/* 182 */     if (prefix != null) builder.append(prefix);
/*     */     
/* 184 */     while (component != null) {
/* 185 */       if (!predicate.test(component)) {
/* 186 */         if (it.hasNext()) {
/* 187 */           component = it.next();
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/* 194 */       builder.append(Objects.<Component>requireNonNull(convertor.apply(component), "Null output from \"convertor\" is not allowed"));
/* 195 */       componentsSeen++;
/*     */       
/* 197 */       if (!it.hasNext()) {
/* 198 */         component = null; continue;
/*     */       } 
/* 200 */       component = Objects.<ComponentLike>requireNonNull(it.next(), "Null elements in \"components\" are not allowed");
/*     */       
/* 202 */       if (it.hasNext()) {
/* 203 */         if (hasSeparator) builder.append(separator);  continue;
/*     */       } 
/* 205 */       Component lastSeparator = null;
/*     */       
/* 207 */       if (componentsSeen > 1) lastSeparator = config.lastSeparatorIfSerial(); 
/* 208 */       if (lastSeparator == null) lastSeparator = config.lastSeparator(); 
/* 209 */       if (lastSeparator == null) lastSeparator = config.separator();
/*     */       
/* 211 */       if (lastSeparator != null) builder.append(lastSeparator);
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 216 */     if (suffix != null) builder.append(suffix); 
/* 217 */     return builder.build();
/*     */   }
/*     */   @NotNull
/*     */   static Component singleElementJoin(@NotNull JoinConfiguration config, @Nullable ComponentLike component) {
/* 221 */     Component prefix = config.prefix();
/* 222 */     Component suffix = config.suffix();
/* 223 */     Function<ComponentLike, Component> convertor = config.convertor();
/* 224 */     Predicate<ComponentLike> predicate = config.predicate();
/* 225 */     Style rootStyle = config.parentStyle();
/* 226 */     boolean hasRootStyle = (rootStyle != Style.empty());
/*     */     
/* 228 */     if (prefix == null && suffix == null) {
/*     */       Component result;
/* 230 */       if (component == null || !predicate.test(component)) {
/* 231 */         result = Component.empty();
/*     */       } else {
/* 233 */         result = convertor.apply(component);
/*     */       } 
/* 235 */       return hasRootStyle ? Component.text().style(rootStyle).append(result).build() : result;
/*     */     } 
/*     */     
/* 238 */     TextComponent.Builder builder = Component.text();
/* 239 */     if (prefix != null) builder.append(prefix); 
/* 240 */     if (component != null && predicate.test(component)) builder.append(convertor.apply(component)); 
/* 241 */     if (suffix != null) builder.append(suffix); 
/* 242 */     return hasRootStyle ? Component.text().style(rootStyle).append(builder).build() : builder.build();
/*     */   }
/*     */   
/*     */   static final class BuilderImpl implements JoinConfiguration.Builder {
/*     */     private ComponentLike prefix;
/*     */     private ComponentLike suffix;
/*     */     private ComponentLike separator;
/*     */     private ComponentLike lastSeparator;
/*     */     private ComponentLike lastSeparatorIfSerial;
/*     */     private Function<ComponentLike, Component> convertor;
/*     */     private Predicate<ComponentLike> predicate;
/*     */     private Style rootStyle;
/*     */     
/*     */     BuilderImpl() {
/* 256 */       this(JoinConfigurationImpl.NULL);
/*     */     }
/*     */     
/*     */     private BuilderImpl(@NotNull JoinConfigurationImpl joinConfig) {
/* 260 */       this.separator = joinConfig.separator;
/* 261 */       this.lastSeparator = joinConfig.lastSeparator;
/* 262 */       this.prefix = joinConfig.prefix;
/* 263 */       this.suffix = joinConfig.suffix;
/* 264 */       this.convertor = joinConfig.convertor;
/* 265 */       this.lastSeparatorIfSerial = joinConfig.lastSeparatorIfSerial;
/* 266 */       this.predicate = joinConfig.predicate;
/* 267 */       this.rootStyle = joinConfig.rootStyle;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder prefix(@Nullable ComponentLike prefix) {
/* 272 */       this.prefix = prefix;
/* 273 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder suffix(@Nullable ComponentLike suffix) {
/* 278 */       this.suffix = suffix;
/* 279 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder separator(@Nullable ComponentLike separator) {
/* 284 */       this.separator = separator;
/* 285 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder lastSeparator(@Nullable ComponentLike lastSeparator) {
/* 290 */       this.lastSeparator = lastSeparator;
/* 291 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder lastSeparatorIfSerial(@Nullable ComponentLike lastSeparatorIfSerial) {
/* 296 */       this.lastSeparatorIfSerial = lastSeparatorIfSerial;
/* 297 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder convertor(@NotNull Function<ComponentLike, Component> convertor) {
/* 302 */       this.convertor = Objects.<Function<ComponentLike, Component>>requireNonNull(convertor, "convertor");
/* 303 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder predicate(@NotNull Predicate<ComponentLike> predicate) {
/* 308 */       this.predicate = Objects.<Predicate<ComponentLike>>requireNonNull(predicate, "predicate");
/* 309 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration.Builder parentStyle(@NotNull Style parentStyle) {
/* 314 */       this.rootStyle = Objects.<Style>requireNonNull(parentStyle, "rootStyle");
/* 315 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public JoinConfiguration build() {
/* 320 */       return new JoinConfigurationImpl(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\JoinConfigurationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */