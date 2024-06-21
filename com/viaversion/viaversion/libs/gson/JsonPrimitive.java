/*     */ package com.viaversion.viaversion.libs.gson;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Objects;
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
/*     */ public final class JsonPrimitive
/*     */   extends JsonElement
/*     */ {
/*     */   private final Object value;
/*     */   
/*     */   public JsonPrimitive(Boolean bool) {
/*  43 */     this.value = Objects.requireNonNull(bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPrimitive(Number number) {
/*  53 */     this.value = Objects.requireNonNull(number);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPrimitive(String string) {
/*  63 */     this.value = Objects.requireNonNull(string);
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
/*     */   public JsonPrimitive(Character c) {
/*  76 */     this.value = ((Character)Objects.<Character>requireNonNull(c)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonPrimitive deepCopy() {
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoolean() {
/*  95 */     return this.value instanceof Boolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAsBoolean() {
/* 106 */     if (isBoolean()) {
/* 107 */       return ((Boolean)this.value).booleanValue();
/*     */     }
/*     */     
/* 110 */     return Boolean.parseBoolean(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNumber() {
/* 119 */     return this.value instanceof Number;
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
/*     */   public Number getAsNumber() {
/* 132 */     if (this.value instanceof Number)
/* 133 */       return (Number)this.value; 
/* 134 */     if (this.value instanceof String) {
/* 135 */       return (Number)new LazilyParsedNumber((String)this.value);
/*     */     }
/* 137 */     throw new UnsupportedOperationException("Primitive is neither a number nor a string");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isString() {
/* 146 */     return this.value instanceof String;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAsString() {
/* 152 */     if (this.value instanceof String)
/* 153 */       return (String)this.value; 
/* 154 */     if (isNumber())
/* 155 */       return getAsNumber().toString(); 
/* 156 */     if (isBoolean()) {
/* 157 */       return ((Boolean)this.value).toString();
/*     */     }
/* 159 */     throw new AssertionError("Unexpected value type: " + this.value.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAsDouble() {
/* 167 */     return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigDecimal getAsBigDecimal() {
/* 175 */     return (this.value instanceof BigDecimal) ? (BigDecimal)this.value : new BigDecimal(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getAsBigInteger() {
/* 183 */     return (this.value instanceof BigInteger) ? 
/* 184 */       (BigInteger)this.value : new BigInteger(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAsFloat() {
/* 192 */     return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAsLong() {
/* 203 */     return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getAsShort() {
/* 211 */     return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAsInt() {
/* 219 */     return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getAsByte() {
/* 227 */     return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
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
/*     */   public char getAsCharacter() {
/* 239 */     String s = getAsString();
/* 240 */     if (s.isEmpty()) {
/* 241 */       throw new UnsupportedOperationException("String value is empty");
/*     */     }
/* 243 */     return s.charAt(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 252 */     if (this.value == null) {
/* 253 */       return 31;
/*     */     }
/*     */     
/* 256 */     if (isIntegral(this)) {
/* 257 */       long value = getAsNumber().longValue();
/* 258 */       return (int)(value ^ value >>> 32L);
/*     */     } 
/* 260 */     if (this.value instanceof Number) {
/* 261 */       long value = Double.doubleToLongBits(getAsNumber().doubleValue());
/* 262 */       return (int)(value ^ value >>> 32L);
/*     */     } 
/* 264 */     return this.value.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 274 */     if (this == obj) {
/* 275 */       return true;
/*     */     }
/* 277 */     if (obj == null || getClass() != obj.getClass()) {
/* 278 */       return false;
/*     */     }
/* 280 */     JsonPrimitive other = (JsonPrimitive)obj;
/* 281 */     if (this.value == null) {
/* 282 */       return (other.value == null);
/*     */     }
/* 284 */     if (isIntegral(this) && isIntegral(other)) {
/* 285 */       return (getAsNumber().longValue() == other.getAsNumber().longValue());
/*     */     }
/* 287 */     if (this.value instanceof Number && other.value instanceof Number) {
/* 288 */       double a = getAsNumber().doubleValue();
/*     */ 
/*     */       
/* 291 */       double b = other.getAsNumber().doubleValue();
/* 292 */       return (a == b || (Double.isNaN(a) && Double.isNaN(b)));
/*     */     } 
/* 294 */     return this.value.equals(other.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isIntegral(JsonPrimitive primitive) {
/* 302 */     if (primitive.value instanceof Number) {
/* 303 */       Number number = (Number)primitive.value;
/* 304 */       return (number instanceof BigInteger || number instanceof Long || number instanceof Integer || number instanceof Short || number instanceof Byte);
/*     */     } 
/*     */     
/* 307 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\JsonPrimitive.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */