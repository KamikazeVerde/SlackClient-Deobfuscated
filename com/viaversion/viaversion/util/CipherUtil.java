/*    */ package com.viaversion.viaversion.util;
/*    */ 
/*    */ import java.security.KeyFactory;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.PublicKey;
/*    */ import java.security.spec.EncodedKeySpec;
/*    */ import java.security.spec.X509EncodedKeySpec;
/*    */ import javax.crypto.Cipher;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CipherUtil
/*    */ {
/*    */   private static final KeyFactory RSA_FACTORY;
/*    */   
/*    */   static {
/*    */     try {
/* 33 */       RSA_FACTORY = KeyFactory.getInstance("RSA");
/* 34 */     } catch (NoSuchAlgorithmException e) {
/* 35 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static byte[] encryptNonce(byte[] publicKeyBytes, byte[] nonceBytes) throws Exception {
/* 40 */     EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
/* 41 */     PublicKey key = RSA_FACTORY.generatePublic(keySpec);
/* 42 */     Cipher cipher = Cipher.getInstance(key.getAlgorithm());
/* 43 */     cipher.init(1, key);
/* 44 */     return cipher.doFinal(nonceBytes);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\CipherUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */