/*     */ package net.optifine.config;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagDouble;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.StrUtils;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ 
/*     */ public class NbtTagValue
/*     */ {
/*  21 */   private String[] parents = null;
/*  22 */   private String name = null;
/*     */   private boolean negative = false;
/*  24 */   private int type = 0;
/*  25 */   private String value = null;
/*  26 */   private int valueFormat = 0;
/*     */   private static final int TYPE_TEXT = 0;
/*     */   private static final int TYPE_PATTERN = 1;
/*     */   private static final int TYPE_IPATTERN = 2;
/*     */   private static final int TYPE_REGEX = 3;
/*     */   private static final int TYPE_IREGEX = 4;
/*     */   private static final String PREFIX_PATTERN = "pattern:";
/*     */   private static final String PREFIX_IPATTERN = "ipattern:";
/*     */   private static final String PREFIX_REGEX = "regex:";
/*     */   private static final String PREFIX_IREGEX = "iregex:";
/*     */   private static final int FORMAT_DEFAULT = 0;
/*     */   private static final int FORMAT_HEX_COLOR = 1;
/*     */   private static final String PREFIX_HEX_COLOR = "#";
/*  39 */   private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");
/*     */ 
/*     */   
/*     */   public NbtTagValue(String tag, String value) {
/*  43 */     String[] astring = Config.tokenize(tag, ".");
/*  44 */     this.parents = Arrays.<String>copyOfRange(astring, 0, astring.length - 1);
/*  45 */     this.name = astring[astring.length - 1];
/*     */     
/*  47 */     if (value.startsWith("!")) {
/*     */       
/*  49 */       this.negative = true;
/*  50 */       value = value.substring(1);
/*     */     } 
/*     */     
/*  53 */     if (value.startsWith("pattern:")) {
/*     */       
/*  55 */       this.type = 1;
/*  56 */       value = value.substring("pattern:".length());
/*     */     }
/*  58 */     else if (value.startsWith("ipattern:")) {
/*     */       
/*  60 */       this.type = 2;
/*  61 */       value = value.substring("ipattern:".length()).toLowerCase();
/*     */     }
/*  63 */     else if (value.startsWith("regex:")) {
/*     */       
/*  65 */       this.type = 3;
/*  66 */       value = value.substring("regex:".length());
/*     */     }
/*  68 */     else if (value.startsWith("iregex:")) {
/*     */       
/*  70 */       this.type = 4;
/*  71 */       value = value.substring("iregex:".length()).toLowerCase();
/*     */     }
/*     */     else {
/*     */       
/*  75 */       this.type = 0;
/*     */     } 
/*     */     
/*  78 */     value = StringEscapeUtils.unescapeJava(value);
/*     */     
/*  80 */     if (this.type == 0 && PATTERN_HEX_COLOR.matcher(value).matches())
/*     */     {
/*  82 */       this.valueFormat = 1;
/*     */     }
/*     */     
/*  85 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(NBTTagCompound nbt) {
/*  90 */     return this.negative ? (!matchesCompound(nbt)) : matchesCompound(nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesCompound(NBTTagCompound nbt) {
/*  95 */     if (nbt == null)
/*     */     {
/*  97 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 101 */     NBTTagCompound nBTTagCompound = nbt;
/*     */     
/* 103 */     for (int i = 0; i < this.parents.length; i++) {
/*     */       
/* 105 */       String s = this.parents[i];
/* 106 */       nBTBase = getChildTag((NBTBase)nBTTagCompound, s);
/*     */       
/* 108 */       if (nBTBase == null)
/*     */       {
/* 110 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 114 */     if (this.name.equals("*"))
/*     */     {
/* 116 */       return matchesAnyChild(nBTBase);
/*     */     }
/*     */ 
/*     */     
/* 120 */     NBTBase nBTBase = getChildTag(nBTBase, this.name);
/*     */     
/* 122 */     if (nBTBase == null)
/*     */     {
/* 124 */       return false;
/*     */     }
/* 126 */     if (matchesBase(nBTBase))
/*     */     {
/* 128 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesAnyChild(NBTBase tagBase) {
/* 140 */     if (tagBase instanceof NBTTagCompound) {
/*     */       
/* 142 */       NBTTagCompound nbttagcompound = (NBTTagCompound)tagBase;
/*     */       
/* 144 */       for (String s : nbttagcompound.getKeySet()) {
/*     */         
/* 146 */         NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */         
/* 148 */         if (matchesBase(nbtbase))
/*     */         {
/* 150 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     if (tagBase instanceof NBTTagList) {
/*     */       
/* 157 */       NBTTagList nbttaglist = (NBTTagList)tagBase;
/* 158 */       int i = nbttaglist.tagCount();
/*     */       
/* 160 */       for (int j = 0; j < i; j++) {
/*     */         
/* 162 */         NBTBase nbtbase1 = nbttaglist.get(j);
/*     */         
/* 164 */         if (matchesBase(nbtbase1))
/*     */         {
/* 166 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static NBTBase getChildTag(NBTBase tagBase, String tag) {
/* 176 */     if (tagBase instanceof NBTTagCompound) {
/*     */       
/* 178 */       NBTTagCompound nbttagcompound = (NBTTagCompound)tagBase;
/* 179 */       return nbttagcompound.getTag(tag);
/*     */     } 
/* 181 */     if (tagBase instanceof NBTTagList) {
/*     */       
/* 183 */       NBTTagList nbttaglist = (NBTTagList)tagBase;
/*     */       
/* 185 */       if (tag.equals("count"))
/*     */       {
/* 187 */         return (NBTBase)new NBTTagInt(nbttaglist.tagCount());
/*     */       }
/*     */ 
/*     */       
/* 191 */       int i = Config.parseInt(tag, -1);
/* 192 */       return (i >= 0 && i < nbttaglist.tagCount()) ? nbttaglist.get(i) : null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesBase(NBTBase nbtBase) {
/* 203 */     if (nbtBase == null)
/*     */     {
/* 205 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 209 */     String s = getNbtString(nbtBase, this.valueFormat);
/* 210 */     return matchesValue(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesValue(String nbtValue) {
/* 216 */     if (nbtValue == null)
/*     */     {
/* 218 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 222 */     switch (this.type) {
/*     */       
/*     */       case 0:
/* 225 */         return nbtValue.equals(this.value);
/*     */       
/*     */       case 1:
/* 228 */         return matchesPattern(nbtValue, this.value);
/*     */       
/*     */       case 2:
/* 231 */         return matchesPattern(nbtValue.toLowerCase(), this.value);
/*     */       
/*     */       case 3:
/* 234 */         return matchesRegex(nbtValue, this.value);
/*     */       
/*     */       case 4:
/* 237 */         return matchesRegex(nbtValue.toLowerCase(), this.value);
/*     */     } 
/*     */     
/* 240 */     throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesPattern(String str, String pattern) {
/* 247 */     return StrUtils.equalsMask(str, pattern, '*', '?');
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesRegex(String str, String regex) {
/* 252 */     return str.matches(regex);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getNbtString(NBTBase nbtBase, int format) {
/* 257 */     if (nbtBase == null)
/*     */     {
/* 259 */       return null;
/*     */     }
/* 261 */     if (nbtBase instanceof NBTTagString) {
/*     */       
/* 263 */       NBTTagString nbttagstring = (NBTTagString)nbtBase;
/* 264 */       return nbttagstring.getString();
/*     */     } 
/* 266 */     if (nbtBase instanceof NBTTagInt) {
/*     */       
/* 268 */       NBTTagInt nbttagint = (NBTTagInt)nbtBase;
/* 269 */       return (format == 1) ? ("#" + StrUtils.fillLeft(Integer.toHexString(nbttagint.getInt()), 6, '0')) : Integer.toString(nbttagint.getInt());
/*     */     } 
/* 271 */     if (nbtBase instanceof NBTTagByte) {
/*     */       
/* 273 */       NBTTagByte nbttagbyte = (NBTTagByte)nbtBase;
/* 274 */       return Byte.toString(nbttagbyte.getByte());
/*     */     } 
/* 276 */     if (nbtBase instanceof NBTTagShort) {
/*     */       
/* 278 */       NBTTagShort nbttagshort = (NBTTagShort)nbtBase;
/* 279 */       return Short.toString(nbttagshort.getShort());
/*     */     } 
/* 281 */     if (nbtBase instanceof NBTTagLong) {
/*     */       
/* 283 */       NBTTagLong nbttaglong = (NBTTagLong)nbtBase;
/* 284 */       return Long.toString(nbttaglong.getLong());
/*     */     } 
/* 286 */     if (nbtBase instanceof NBTTagFloat) {
/*     */       
/* 288 */       NBTTagFloat nbttagfloat = (NBTTagFloat)nbtBase;
/* 289 */       return Float.toString(nbttagfloat.getFloat());
/*     */     } 
/* 291 */     if (nbtBase instanceof NBTTagDouble) {
/*     */       
/* 293 */       NBTTagDouble nbttagdouble = (NBTTagDouble)nbtBase;
/* 294 */       return Double.toString(nbttagdouble.getDouble());
/*     */     } 
/*     */ 
/*     */     
/* 298 */     return nbtBase.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 304 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 306 */     for (int i = 0; i < this.parents.length; i++) {
/*     */       
/* 308 */       String s = this.parents[i];
/*     */       
/* 310 */       if (i > 0)
/*     */       {
/* 312 */         stringbuffer.append(".");
/*     */       }
/*     */       
/* 315 */       stringbuffer.append(s);
/*     */     } 
/*     */     
/* 318 */     if (stringbuffer.length() > 0)
/*     */     {
/* 320 */       stringbuffer.append(".");
/*     */     }
/*     */     
/* 323 */     stringbuffer.append(this.name);
/* 324 */     stringbuffer.append(" = ");
/* 325 */     stringbuffer.append(this.value);
/* 326 */     return stringbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\config\NbtTagValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */