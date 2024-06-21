/*     */ package fr.litarvan.openauth;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import fr.litarvan.openauth.model.AuthAgent;
/*     */ import fr.litarvan.openauth.model.AuthError;
/*     */ import fr.litarvan.openauth.model.request.AuthRequest;
/*     */ import fr.litarvan.openauth.model.request.InvalidateRequest;
/*     */ import fr.litarvan.openauth.model.request.RefreshRequest;
/*     */ import fr.litarvan.openauth.model.request.SignoutRequest;
/*     */ import fr.litarvan.openauth.model.request.ValidateRequest;
/*     */ import fr.litarvan.openauth.model.response.AuthResponse;
/*     */ import fr.litarvan.openauth.model.response.RefreshResponse;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
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
/*     */ public class Authenticator
/*     */ {
/*     */   @Deprecated
/*     */   public static final String MOJANG_AUTH_URL = "https://authserver.mojang.com/";
/*     */   private final String authURL;
/*     */   private final AuthPoints authPoints;
/*     */   
/*     */   public Authenticator(String authURL, AuthPoints authPoints) {
/*  73 */     this.authURL = authURL;
/*  74 */     this.authPoints = authPoints;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthResponse authenticate(AuthAgent agent, String username, String password, String clientToken) throws AuthenticationException {
/*  94 */     return authenticate(agent, username, password, clientToken, Proxy.NO_PROXY);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthResponse authenticate(AuthAgent agent, String username, String password, String clientToken, Proxy proxy) throws AuthenticationException {
/* 116 */     AuthRequest request = new AuthRequest(agent, username, password, clientToken);
/* 117 */     return (AuthResponse)sendRequest(request, AuthResponse.class, this.authPoints.getAuthenticatePoint(), proxy);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public RefreshResponse refresh(String accessToken, String clientToken) throws AuthenticationException {
/* 134 */     return refresh(accessToken, clientToken, Proxy.NO_PROXY);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RefreshResponse refresh(String accessToken, String clientToken, Proxy proxy) throws AuthenticationException {
/* 153 */     RefreshRequest request = new RefreshRequest(accessToken, clientToken);
/* 154 */     return (RefreshResponse)sendRequest(request, RefreshResponse.class, this.authPoints.getRefreshPoint(), proxy);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(String accessToken) throws AuthenticationException {
/* 171 */     validate(accessToken, Proxy.NO_PROXY);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(String accessToken, Proxy proxy) throws AuthenticationException {
/* 190 */     ValidateRequest request = new ValidateRequest(accessToken);
/* 191 */     sendRequest(request, null, this.authPoints.getValidatePoint(), proxy);
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
/*     */   public void signout(String username, String password) throws AuthenticationException {
/* 205 */     signout(username, password, Proxy.NO_PROXY);
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
/*     */ 
/*     */   
/*     */   public void signout(String username, String password, Proxy proxy) throws AuthenticationException {
/* 221 */     SignoutRequest request = new SignoutRequest(username, password);
/* 222 */     sendRequest(request, null, this.authPoints.getSignoutPoint(), proxy);
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
/*     */   public void invalidate(String accessToken, String clientToken) throws AuthenticationException {
/* 236 */     invalidate(accessToken, clientToken, Proxy.NO_PROXY);
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
/*     */ 
/*     */   
/*     */   public void invalidate(String accessToken, String clientToken, Proxy proxy) throws AuthenticationException {
/* 252 */     InvalidateRequest request = new InvalidateRequest(accessToken, clientToken);
/* 253 */     sendRequest(request, null, this.authPoints.getInvalidatePoint(), proxy);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object sendRequest(Object request, Class<?> model, String authPoint) throws AuthenticationException {
/* 273 */     return sendRequest(request, model, authPoint, Proxy.NO_PROXY);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object sendRequest(Object request, Class<?> model, String authPoint, Proxy proxy) throws AuthenticationException {
/*     */     String response;
/* 295 */     Gson gson = new Gson();
/*     */ 
/*     */     
/*     */     try {
/* 299 */       response = sendPostRequest(this.authURL + authPoint, gson.toJson(request), proxy);
/* 300 */     } catch (IOException e) {
/* 301 */       throw new AuthenticationException(new AuthError("Can't send the request : " + e.getClass().getName(), e.getMessage(), "Unknown"));
/*     */     } 
/*     */     
/* 304 */     if (model != null) {
/* 305 */       return gson.fromJson(response, model);
/*     */     }
/* 307 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String sendPostRequest(String url, String json) throws AuthenticationException, IOException {
/* 325 */     return sendPostRequest(url, json, Proxy.NO_PROXY);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String sendPostRequest(String url, String json, Proxy proxy) throws AuthenticationException, IOException {
/*     */     InputStream is;
/* 345 */     byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
/* 346 */     URL serverURL = new URL(url);
/* 347 */     HttpURLConnection connection = (HttpURLConnection)serverURL.openConnection((proxy != null) ? proxy : Proxy.NO_PROXY);
/* 348 */     connection.setRequestMethod("POST");
/*     */ 
/*     */     
/* 351 */     connection.setDoOutput(true);
/* 352 */     connection.setRequestProperty("Accept-Charset", "UTF-8");
/* 353 */     connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
/* 354 */     connection.setRequestProperty("Content-Length", String.valueOf(jsonBytes.length));
/* 355 */     DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
/* 356 */     wr.write(jsonBytes, 0, jsonBytes.length);
/* 357 */     wr.flush();
/* 358 */     wr.close();
/*     */     
/* 360 */     connection.connect();
/*     */     
/* 362 */     int responseCode = connection.getResponseCode();
/*     */     
/* 364 */     if (responseCode == 204) {
/* 365 */       connection.disconnect();
/* 366 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 370 */     if (responseCode == 200) {
/* 371 */       is = connection.getInputStream();
/*     */     } else {
/* 373 */       is = connection.getErrorStream();
/*     */     } 
/*     */ 
/*     */     
/* 377 */     BufferedReader br = new BufferedReader(new InputStreamReader(is));
/* 378 */     String response = br.readLine();
/*     */     try {
/* 380 */       br.close();
/* 381 */     } catch (IOException e) {
/* 382 */       e.printStackTrace();
/*     */     } 
/* 384 */     connection.disconnect();
/*     */     
/* 386 */     while (response != null && response.startsWith("﻿")) {
/* 387 */       response = response.substring(1);
/*     */     }
/* 389 */     if (responseCode != 200) {
/* 390 */       Gson gson = new Gson();
/*     */       
/* 392 */       if (response != null && !response.startsWith("{")) {
/* 393 */         throw new AuthenticationException(new AuthError("Internal server error", response, "Remote"));
/*     */       }
/* 395 */       throw new AuthenticationException((AuthError)gson.fromJson(response, AuthError.class));
/*     */     } 
/*     */     
/* 398 */     return response;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\fr\litarvan\openauth\Authenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */