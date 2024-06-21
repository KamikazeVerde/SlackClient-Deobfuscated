/*     */ package fr.litarvan.openauth.microsoft;
/*     */ 
/*     */ import fr.litarvan.openauth.microsoft.model.request.MinecraftLoginRequest;
/*     */ import fr.litarvan.openauth.microsoft.model.request.XSTSAuthorizationProperties;
/*     */ import fr.litarvan.openauth.microsoft.model.request.XboxLiveLoginProperties;
/*     */ import fr.litarvan.openauth.microsoft.model.request.XboxLoginRequest;
/*     */ import fr.litarvan.openauth.microsoft.model.response.MicrosoftRefreshResponse;
/*     */ import fr.litarvan.openauth.microsoft.model.response.MinecraftLoginResponse;
/*     */ import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
/*     */ import fr.litarvan.openauth.microsoft.model.response.MinecraftStoreResponse;
/*     */ import fr.litarvan.openauth.microsoft.model.response.XboxLoginResponse;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.CookieHandler;
/*     */ import java.net.CookieManager;
/*     */ import java.net.CookiePolicy;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Base64;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionException;
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
/*     */ public class MicrosoftAuthenticator
/*     */ {
/*     */   public static final String MICROSOFT_AUTHORIZATION_ENDPOINT = "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize";
/*     */   public static final String MICROSOFT_TOKEN_ENDPOINT = "https://login.live.com/oauth20_token.srf";
/*     */   public static final String MICROSOFT_REDIRECTION_ENDPOINT = "https://login.live.com/oauth20_desktop.srf";
/*     */   public static final String XBOX_LIVE_AUTH_HOST = "user.auth.xboxlive.com";
/*     */   public static final String XBOX_LIVE_CLIENT_ID = "000000004C12AE6F";
/*     */   public static final String XBOX_LIVE_SERVICE_SCOPE = "service::user.auth.xboxlive.com::MBI_SSL";
/*     */   public static final String XBOX_LIVE_AUTHORIZATION_ENDPOINT = "https://user.auth.xboxlive.com/user/authenticate";
/*     */   public static final String XSTS_AUTHORIZATION_ENDPOINT = "https://xsts.auth.xboxlive.com/xsts/authorize";
/*     */   public static final String MINECRAFT_AUTH_ENDPOINT = "https://api.minecraftservices.com/authentication/login_with_xbox";
/*     */   public static final String XBOX_LIVE_AUTH_RELAY = "http://auth.xboxlive.com";
/*     */   public static final String MINECRAFT_AUTH_RELAY = "rp://api.minecraftservices.com/";
/*     */   public static final String MINECRAFT_STORE_ENDPOINT = "https://api.minecraftservices.com/entitlements/mcstore";
/*     */   public static final String MINECRAFT_PROFILE_ENDPOINT = "https://api.minecraftservices.com/minecraft/profile";
/*     */   public static final String MINECRAFT_STORE_IDENTIFIER = "game_minecraft";
/*  83 */   private final HttpClient http = new HttpClient();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MicrosoftAuthResult loginWithCredentials(String email, String password) throws MicrosoftAuthenticationException {
/*     */     HttpURLConnection result;
/*  95 */     CookieHandler currentHandler = CookieHandler.getDefault();
/*  96 */     CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
/*     */     
/*  98 */     Map<String, String> params = new HashMap<>();
/*  99 */     params.put("login", email);
/* 100 */     params.put("loginfmt", email);
/* 101 */     params.put("passwd", password);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 106 */       PreAuthData authData = preAuthRequest();
/* 107 */       params.put("PPFT", authData.getPPFT());
/*     */       
/* 109 */       result = this.http.followRedirects(this.http.postForm(authData.getUrlPost(), params));
/*     */     } finally {
/* 111 */       CookieHandler.setDefault(currentHandler);
/*     */     } 
/*     */     
/*     */     try {
/* 115 */       return loginWithTokens(extractTokens(result.getURL().toString()), true);
/* 116 */     } catch (MicrosoftAuthenticationException e) {
/* 117 */       if (match("(identity/confirm)", this.http.readResponse(result)) != null) {
/* 118 */         throw new MicrosoftAuthenticationException("User has enabled double-authentication or must allow sign-in on https://account.live.com/activity");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 123 */       throw e;
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
/*     */   public MicrosoftAuthResult loginWithWebview() throws MicrosoftAuthenticationException {
/*     */     try {
/* 138 */       return loginWithAsyncWebview().get();
/* 139 */     } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
/* 140 */       throw new MicrosoftAuthenticationException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<MicrosoftAuthResult> loginWithAsyncWebview() {
/* 150 */     if (!System.getProperty("java.version").startsWith("1.")) {
/* 151 */       CookieHandler.setDefault(new CookieManager());
/*     */     }
/* 153 */     String url = String.format("%s?%s", new Object[] { "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize", this.http.buildParams(getLoginParams()) });
/* 154 */     LoginFrame frame = new LoginFrame();
/*     */     
/* 156 */     return frame.start(url).thenApplyAsync(result -> {
/*     */ 
/*     */           
/*     */           try {
/*     */             return (result != null) ? loginWithTokens(extractTokens(result), true) : null;
/* 161 */           } catch (MicrosoftAuthenticationException e) {
/*     */             throw new CompletionException(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MicrosoftAuthResult loginWithRefreshToken(String refreshToken) throws MicrosoftAuthenticationException {
/* 175 */     Map<String, String> params = getLoginParams();
/* 176 */     params.put("refresh_token", refreshToken);
/* 177 */     params.put("grant_type", "refresh_token");
/*     */     
/* 179 */     MicrosoftRefreshResponse response = this.http.<MicrosoftRefreshResponse>postFormGetJson("https://login.live.com/oauth20_token.srf", params, MicrosoftRefreshResponse.class);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     return loginWithTokens(new AuthTokens(response.getAccessToken(), response.getRefreshToken()), true);
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
/*     */   public MicrosoftAuthResult loginWithTokens(AuthTokens tokens) throws MicrosoftAuthenticationException {
/* 196 */     return loginWithTokens(tokens, true);
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
/*     */   public MicrosoftAuthResult loginWithTokens(AuthTokens tokens, boolean retrieveProfile) throws MicrosoftAuthenticationException {
/* 209 */     XboxLoginResponse xboxLiveResponse = xboxLiveLogin(tokens.getAccessToken());
/* 210 */     XboxLoginResponse xstsResponse = xstsLogin(xboxLiveResponse.getToken());
/*     */     
/* 212 */     String userHash = xstsResponse.getDisplayClaims().getUsers()[0].getUserHash();
/* 213 */     MinecraftLoginResponse minecraftResponse = minecraftLogin(userHash, xstsResponse.getToken());
/* 214 */     MinecraftStoreResponse storeResponse = this.http.<MinecraftStoreResponse>getJson("https://api.minecraftservices.com/entitlements/mcstore", minecraftResponse
/*     */         
/* 216 */         .getAccessToken(), MinecraftStoreResponse.class);
/*     */ 
/*     */ 
/*     */     
/* 220 */     if (Arrays.<MinecraftStoreResponse.StoreProduct>stream(storeResponse.getItems()).noneMatch(item -> item.getName().equals("game_minecraft"))) {
/* 221 */       throw new MicrosoftAuthenticationException("Player didn't buy Minecraft Java Edition or did not migrate its account");
/*     */     }
/* 223 */     MinecraftProfile profile = null;
/* 224 */     if (retrieveProfile) {
/* 225 */       profile = this.http.<MinecraftProfile>getJson("https://api.minecraftservices.com/minecraft/profile", minecraftResponse
/*     */           
/* 227 */           .getAccessToken(), MinecraftProfile.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     return new MicrosoftAuthResult(profile, minecraftResponse
/*     */         
/* 234 */         .getAccessToken(), tokens
/* 235 */         .getRefreshToken(), xboxLiveResponse
/* 236 */         .getDisplayClaims().getUsers()[0].getUserHash(), 
/* 237 */         Base64.getEncoder().encodeToString(minecraftResponse.getUsername().getBytes()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PreAuthData preAuthRequest() throws MicrosoftAuthenticationException {
/* 243 */     Map<String, String> params = getLoginParams();
/* 244 */     params.put("display", "touch");
/* 245 */     params.put("locale", "en");
/*     */     
/* 247 */     String result = this.http.getText("https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize", params);
/*     */     
/* 249 */     String ppft = match("sFTTag:'.*value=\"([^\"]*)\"", result);
/* 250 */     String urlPost = match("urlPost: ?'(.+?(?='))", result);
/*     */     
/* 252 */     return new PreAuthData(ppft, urlPost);
/*     */   }
/*     */   
/*     */   protected XboxLoginResponse xboxLiveLogin(String accessToken) throws MicrosoftAuthenticationException {
/* 256 */     XboxLiveLoginProperties properties = new XboxLiveLoginProperties("RPS", "user.auth.xboxlive.com", accessToken);
/* 257 */     XboxLoginRequest<XboxLiveLoginProperties> request = new XboxLoginRequest(properties, "http://auth.xboxlive.com", "JWT");
/*     */ 
/*     */ 
/*     */     
/* 261 */     return this.http.<XboxLoginResponse>postJson("https://user.auth.xboxlive.com/user/authenticate", request, XboxLoginResponse.class);
/*     */   }
/*     */   
/*     */   protected XboxLoginResponse xstsLogin(String xboxLiveToken) throws MicrosoftAuthenticationException {
/* 265 */     XSTSAuthorizationProperties properties = new XSTSAuthorizationProperties("RETAIL", new String[] { xboxLiveToken });
/* 266 */     XboxLoginRequest<XSTSAuthorizationProperties> request = new XboxLoginRequest(properties, "rp://api.minecraftservices.com/", "JWT");
/*     */ 
/*     */ 
/*     */     
/* 270 */     return this.http.<XboxLoginResponse>postJson("https://xsts.auth.xboxlive.com/xsts/authorize", request, XboxLoginResponse.class);
/*     */   }
/*     */   
/*     */   protected MinecraftLoginResponse minecraftLogin(String userHash, String xstsToken) throws MicrosoftAuthenticationException {
/* 274 */     MinecraftLoginRequest request = new MinecraftLoginRequest(String.format("XBL3.0 x=%s;%s", new Object[] { userHash, xstsToken }));
/* 275 */     return this.http.<MinecraftLoginResponse>postJson("https://api.minecraftservices.com/authentication/login_with_xbox", request, MinecraftLoginResponse.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, String> getLoginParams() {
/* 280 */     Map<String, String> params = new HashMap<>();
/* 281 */     params.put("client_id", "000000004C12AE6F");
/* 282 */     params.put("redirect_uri", "https://login.live.com/oauth20_desktop.srf");
/* 283 */     params.put("scope", "service::user.auth.xboxlive.com::MBI_SSL");
/* 284 */     params.put("response_type", "token");
/*     */     
/* 286 */     return params;
/*     */   }
/*     */   
/*     */   protected AuthTokens extractTokens(String url) throws MicrosoftAuthenticationException {
/* 290 */     return new AuthTokens(extractValue(url, "access_token"), extractValue(url, "refresh_token"));
/*     */   }
/*     */   
/*     */   protected String extractValue(String url, String key) throws MicrosoftAuthenticationException {
/* 294 */     String matched = match(key + "=([^&]*)", url);
/* 295 */     if (matched == null) {
/* 296 */       throw new MicrosoftAuthenticationException("Invalid credentials or tokens");
/*     */     }
/*     */     
/*     */     try {
/* 300 */       return URLDecoder.decode(matched, "UTF-8");
/* 301 */     } catch (UnsupportedEncodingException e) {
/* 302 */       throw new MicrosoftAuthenticationException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String match(String regex, String content) {
/* 307 */     Matcher matcher = Pattern.compile(regex).matcher(content);
/* 308 */     if (!matcher.find()) {
/* 309 */       return null;
/*     */     }
/*     */     
/* 312 */     return matcher.group(1);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\fr\litarvan\openauth\microsoft\MicrosoftAuthenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */