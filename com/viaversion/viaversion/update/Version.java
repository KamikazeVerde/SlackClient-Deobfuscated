/*     */ package com.viaversion.viaversion.update;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class Version
/*     */   implements Comparable<Version>
/*     */ {
/*  27 */   private static final Pattern semVer = Pattern.compile("(?<a>0|[1-9]\\d*)\\.(?<b>0|[1-9]\\d*)(?:\\.(?<c>0|[1-9]\\d*))?(?:-(?<tag>[A-z0-9.-]*))?");
/*  28 */   private final int[] parts = new int[3];
/*     */   private final String tag;
/*     */   
/*     */   public Version(String value) {
/*  32 */     if (value == null) {
/*  33 */       throw new IllegalArgumentException("Version can not be null");
/*     */     }
/*  35 */     Matcher matcher = semVer.matcher(value);
/*  36 */     if (!matcher.matches())
/*  37 */       throw new IllegalArgumentException("Invalid version format"); 
/*  38 */     this.parts[0] = Integer.parseInt(matcher.group("a"));
/*  39 */     this.parts[1] = Integer.parseInt(matcher.group("b"));
/*  40 */     this.parts[2] = (matcher.group("c") == null) ? 0 : Integer.parseInt(matcher.group("c"));
/*     */     
/*  42 */     this.tag = (matcher.group("tag") == null) ? "" : matcher.group("tag");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int compare(Version verA, Version verB) {
/*  53 */     if (verA == verB) return 0; 
/*  54 */     if (verA == null) return -1; 
/*  55 */     if (verB == null) return 1;
/*     */     
/*  57 */     int max = Math.max(verA.parts.length, verB.parts.length);
/*     */     
/*  59 */     for (int i = 0; i < max; i++) {
/*  60 */       int partA = (i < verA.parts.length) ? verA.parts[i] : 0;
/*  61 */       int partB = (i < verB.parts.length) ? verB.parts[i] : 0;
/*  62 */       if (partA < partB) return -1; 
/*  63 */       if (partA > partB) return 1;
/*     */     
/*     */     } 
/*     */     
/*  67 */     if (verA.tag.isEmpty() && !verB.tag.isEmpty())
/*  68 */       return 1; 
/*  69 */     if (!verA.tag.isEmpty() && verB.tag.isEmpty()) {
/*  70 */       return -1;
/*     */     }
/*  72 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Version verA, Version verB) {
/*  83 */     return (verA == verB || (verA != null && verB != null && compare(verA, verB) == 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  88 */     String[] split = new String[this.parts.length];
/*     */     
/*  90 */     for (int i = 0; i < this.parts.length; i++) {
/*  91 */       split[i] = String.valueOf(this.parts[i]);
/*     */     }
/*  93 */     return Joiner.on(".").join((Object[])split) + (!this.tag.isEmpty() ? ("-" + this.tag) : "");
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Version that) {
/*  98 */     return compare(this, that);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 103 */     return (that instanceof Version && equals(this, (Version)that));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 108 */     int result = Objects.hash(new Object[] { this.tag });
/* 109 */     result = 31 * result + Arrays.hashCode(this.parts);
/* 110 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 119 */     return this.tag;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\update\Version.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */