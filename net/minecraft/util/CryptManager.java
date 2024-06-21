/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.EncodedKeySpec;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class CryptManager
/*     */ {
/*  31 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey createNewSharedKey() {
/*     */     try {
/*  40 */       KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
/*  41 */       keygenerator.init(128);
/*  42 */       return keygenerator.generateKey();
/*     */     }
/*  44 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  46 */       throw new Error(nosuchalgorithmexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyPair generateKeyPair() {
/*     */     try {
/*  57 */       KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
/*  58 */       keypairgenerator.initialize(1024);
/*  59 */       return keypairgenerator.generateKeyPair();
/*     */     }
/*  61 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  63 */       nosuchalgorithmexception.printStackTrace();
/*  64 */       LOGGER.error("Key pair generation failed!");
/*  65 */       return null;
/*     */     } 
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
/*     */   public static byte[] getServerIdHash(String serverId, PublicKey publicKey, SecretKey secretKey) {
/*     */     try {
/*  80 */       return digestOperation("SHA-1", new byte[][] { serverId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
/*     */     }
/*  82 */     catch (UnsupportedEncodingException unsupportedencodingexception) {
/*     */       
/*  84 */       unsupportedencodingexception.printStackTrace();
/*  85 */       return null;
/*     */     } 
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
/*     */   private static byte[] digestOperation(String algorithm, byte[]... data) {
/*     */     try {
/*  99 */       MessageDigest messagedigest = MessageDigest.getInstance(algorithm);
/*     */       
/* 101 */       for (byte[] abyte : data)
/*     */       {
/* 103 */         messagedigest.update(abyte);
/*     */       }
/*     */       
/* 106 */       return messagedigest.digest();
/*     */     }
/* 108 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/* 110 */       nosuchalgorithmexception.printStackTrace();
/* 111 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PublicKey decodePublicKey(byte[] encodedKey) {
/*     */     try {
/* 124 */       EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(encodedKey);
/* 125 */       KeyFactory keyfactory = KeyFactory.getInstance("RSA");
/* 126 */       return keyfactory.generatePublic(encodedkeyspec);
/*     */     }
/* 128 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */ 
/*     */     
/*     */     }
/* 132 */     catch (InvalidKeySpecException invalidKeySpecException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     LOGGER.error("Public key reconstitute failed!");
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey decryptSharedKey(PrivateKey key, byte[] secretKeyEncrypted) {
/* 149 */     return new SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encryptData(Key key, byte[] data) {
/* 160 */     return cipherOperation(1, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decryptData(Key key, byte[] data) {
/* 171 */     return cipherOperation(2, key, data);
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
/*     */   private static byte[] cipherOperation(int opMode, Key key, byte[] data) {
/*     */     try {
/* 186 */       return createTheCipherInstance(opMode, key.getAlgorithm(), key).doFinal(data);
/*     */     }
/* 188 */     catch (IllegalBlockSizeException illegalblocksizeexception) {
/*     */       
/* 190 */       illegalblocksizeexception.printStackTrace();
/*     */     }
/* 192 */     catch (BadPaddingException badpaddingexception) {
/*     */       
/* 194 */       badpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 197 */     LOGGER.error("Cipher data failed!");
/* 198 */     return null;
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
/*     */   private static Cipher createTheCipherInstance(int opMode, String transformation, Key key) {
/*     */     try {
/* 213 */       Cipher cipher = Cipher.getInstance(transformation);
/* 214 */       cipher.init(opMode, key);
/* 215 */       return cipher;
/*     */     }
/* 217 */     catch (InvalidKeyException invalidkeyexception) {
/*     */       
/* 219 */       invalidkeyexception.printStackTrace();
/*     */     }
/* 221 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/* 223 */       nosuchalgorithmexception.printStackTrace();
/*     */     }
/* 225 */     catch (NoSuchPaddingException nosuchpaddingexception) {
/*     */       
/* 227 */       nosuchpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 230 */     LOGGER.error("Cipher creation failed!");
/* 231 */     return null;
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
/*     */   public static Cipher createNetCipherInstance(int opMode, Key key) {
/*     */     try {
/* 245 */       Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
/* 246 */       cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
/* 247 */       return cipher;
/*     */     }
/* 249 */     catch (GeneralSecurityException generalsecurityexception) {
/*     */       
/* 251 */       throw new RuntimeException(generalsecurityexception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\CryptManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */