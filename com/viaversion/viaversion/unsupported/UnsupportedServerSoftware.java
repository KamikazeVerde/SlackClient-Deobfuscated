/*     */ package com.viaversion.viaversion.unsupported;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ public final class UnsupportedServerSoftware
/*     */   implements UnsupportedSoftware
/*     */ {
/*     */   private final String name;
/*     */   private final List<String> classNames;
/*     */   private final List<UnsupportedMethods> methods;
/*     */   private final String reason;
/*     */   
/*     */   public UnsupportedServerSoftware(String name, List<String> classNames, List<UnsupportedMethods> methods, String reason) {
/*  37 */     Preconditions.checkNotNull(name);
/*  38 */     Preconditions.checkNotNull(reason);
/*  39 */     Preconditions.checkArgument((!classNames.isEmpty() || !methods.isEmpty()));
/*  40 */     this.name = name;
/*  41 */     this.classNames = Collections.unmodifiableList(classNames);
/*  42 */     this.methods = Collections.unmodifiableList(methods);
/*  43 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  48 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getReason() {
/*  53 */     return this.reason;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String match() {
/*  58 */     for (String className : this.classNames) {
/*     */       try {
/*  60 */         Class.forName(className);
/*  61 */         return this.name;
/*  62 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */     } 
/*     */     
/*  65 */     for (UnsupportedMethods method : this.methods) {
/*  66 */       if (method.findMatch()) {
/*  67 */         return this.name;
/*     */       }
/*     */     } 
/*  70 */     return null;
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/*  75 */     private final List<String> classNames = new ArrayList<>();
/*  76 */     private final List<UnsupportedMethods> methods = new ArrayList<>();
/*     */     private String name;
/*     */     private String reason;
/*     */     
/*     */     public Builder name(String name) {
/*  81 */       this.name = name;
/*  82 */       return this;
/*     */     }
/*     */     
/*     */     public Builder reason(String reason) {
/*  86 */       this.reason = reason;
/*  87 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addMethod(String className, String methodName) {
/*  91 */       this.methods.add(new UnsupportedMethods(className, Collections.singleton(methodName)));
/*  92 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addMethods(String className, String... methodNames) {
/*  96 */       this.methods.add(new UnsupportedMethods(className, new HashSet<>(Arrays.asList(methodNames))));
/*  97 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addClassName(String className) {
/* 101 */       this.classNames.add(className);
/* 102 */       return this;
/*     */     }
/*     */     
/*     */     public UnsupportedSoftware build() {
/* 106 */       return new UnsupportedServerSoftware(this.name, this.classNames, this.methods, this.reason);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Reason {
/*     */     public static final String DANGEROUS_SERVER_SOFTWARE = "You are using server software that - outside of possibly breaking ViaVersion - can also cause severe damage to your server's integrity as a whole.";
/*     */     public static final String BREAKING_PROXY_SOFTWARE = "You are using proxy software that intentionally breaks ViaVersion. Please use another proxy software or move ViaVersion to each backend server instead of the proxy.";
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\unsupported\UnsupportedServerSoftware.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */