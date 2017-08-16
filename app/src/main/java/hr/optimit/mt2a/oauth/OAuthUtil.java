package hr.optimit.mt2a.oauth;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import hr.optimit.mt2a.Mt2AApplication;
import hr.optimit.mt2a.exception.InvalidTokenException;
import hr.optimit.mt2a.exception.UtException;
import hr.optimit.mt2a.util.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by tomek on 21.08.16..
 */
public class OAuthUtil {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    @Named("restRetrofit")
    Retrofit restRetrofit;

    @Inject
    @Named("oauthRetrofit")
    Retrofit oauthRetrofit;

    @Inject
    public OAuthUtil() {
        Mt2AApplication.getComponent().inject(this);
    }

    public void authenticate(String username, String password) throws UtException, InvalidTokenException, IOException {

        OAuthService service = oauthRetrofit.create(OAuthService.class);

        Call<OAuthTokenResponse> call = service.getAccessToken("password",
                username, password);

        processOAuthResponse(call, new Date().getTime(), username);

    }

    public void refreshTokenIfNeeded() throws UtException, InvalidTokenException, IOException {

        long tokenExpiresIn = (sharedPreferences.getLong(Constants.EXPIRE_DATE, 0) - System.currentTimeMillis()) / 1000;
        if (tokenExpiresIn < 60) {

            OAuthService service = oauthRetrofit.create(OAuthService.class);

            Call<OAuthTokenResponse> call = service.getAccessToken("refresh_token", sharedPreferences.getString(Constants.REFRESH_TOKEN, ""));

            processOAuthResponse(call, new Date().getTime(), sharedPreferences.getString(Constants.USERNAME, ""));
        }
    }

    private void processOAuthResponse(Call<OAuthTokenResponse> call, long callTime, String username) throws UtException, InvalidTokenException, IOException {

        Response<OAuthTokenResponse> response = call.execute();

        if (response.code() != 200) {
            ResponseBody errorBody = response.errorBody();
            BufferedReader reader = new BufferedReader(errorBody.charStream());

            Gson gson = new GsonBuilder().create();
            OAuthErrorResponse error = gson.fromJson(reader.readLine(), OAuthErrorResponse.class);

            if (error.getError().equals("invalid_grant")) {
                throw new InvalidTokenException();
            }

            throw new UtException(error.getErrorDesc());
        }

        OAuthTokenResponse tokenResponse = response.body();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.ACCESS_TOKEN, tokenResponse.getAccessToken());
        editor.putString(Constants.REFRESH_TOKEN, tokenResponse.getRefreshToken());
        editor.putString(Constants.TOKEN_TYPE, tokenResponse.getTokenType());
        editor.putLong(Constants.EXPIRE_DATE, callTime + tokenResponse.getExpiresIn());
        editor.putString(Constants.USERNAME, username);
        editor.commit();

    }

    public boolean isUserLoggedIn() throws IOException, UtException {

        boolean hasAccessToken = sharedPreferences.getString(Constants.ACCESS_TOKEN, null) == null ? false : true;
        if (hasAccessToken) {
            try {
                refreshTokenIfNeeded();
                return true;
            } catch (InvalidTokenException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }


//    public static void getProtectedResource(Properties config) {
//        String resourceURL = config
//                .getProperty(PropertiesHelper.getServerUrl() + PropertiesHelper);
//        OAuth2Details oauthDetails = createOAuthDetails(config);
//        HttpGet get = new HttpGet(resourceURL);
//        get.addHeader(OAuthConstants.AUTHORIZATION,
//                getAuthorizationHeaderForAccessToken(oauthDetails
//                        .getAccessToken()));
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpResponse response = null;
//        int code = -1;
//        try {
//            response = client.execute(get);
//            code = response.getStatusLine().getStatusCode();
//            if (code >= 400) {
//                // Access token is invalid or expired.Regenerate the access
//                // token
//                System.out
//                        .println("Access token is invalid or expired. Regenerating access token....");
//                String accessToken = getAccessToken(oauthDetails);
//                if (isValid(accessToken)) {
//                    // update the access token
//                    // System.out.println("New access token: " + accessToken);
//                    oauthDetails.setAccessToken(accessToken);
//                    get.removeHeaders(OAuthConstants.AUTHORIZATION);
//                    get.addHeader(OAuthConstants.AUTHORIZATION,
//                            getAuthorizationHeaderForAccessToken(oauthDetails
//                                    .getAccessToken()));
//                    get.releaseConnection();
//                    response = client.execute(get);
//                    code = response.getStatusLine().getStatusCode();
//                    if (code >= 400) {
//                        throw new RuntimeException(
//                                "Could not access protected resource. Server returned http code: "
//                                        + code);
//
//                    }
//
//                } else {
//                    throw new RuntimeException(
//                            "Could not regenerate access token");
//                }
//
//            }
//
//            handleResponse(response);
//
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            get.releaseConnection();
//        }
//
//    }
//
//    public static String getAccessToken(OAuth2Details oauthDetails) {
//        HttpPost post = new HttpPost(oauthDetails.getAuthenticationServerUrl());
//        String clientId = oauthDetails.getClientId();
//        String clientSecret = oauthDetails.getClientSecret();
//        String scope = oauthDetails.getScope();
//
//        List<BasicNameValuePair> parametersBody = new ArrayList<BasicNameValuePair>();
//        parametersBody.add(new BasicNameValuePair(OAuthConstants.GRANT_TYPE,
//                oauthDetails.getGrantType()));
//        parametersBody.add(new BasicNameValuePair(OAuthConstants.USERNAME,
//                oauthDetails.getUsername()));
//        parametersBody.add(new BasicNameValuePair(OAuthConstants.PASSWORD,
//                oauthDetails.getPassword()));
//
//        if (isValid(clientId)) {
//            parametersBody.add(new BasicNameValuePair(OAuthConstants.CLIENT_ID,
//                    clientId));
//        }
//        if (isValid(clientSecret)) {
//            parametersBody.add(new BasicNameValuePair(
//                    OAuthConstants.CLIENT_SECRET, clientSecret));
//        }
//        if (isValid(scope)) {
//            parametersBody.add(new BasicNameValuePair(OAuthConstants.SCOPE,
//                    scope));
//        }
//
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpResponse response = null;
//        String accessToken = null;
//        try {
//            post.setEntity(new UrlEncodedFormEntity(parametersBody, HTTP.UTF_8));
//
//            response = client.execute(post);
//            int code = response.getStatusLine().getStatusCode();
//            if (code >= 400) {
//                System.out
//                        .println("Authorization server expects Basic authentication");
//                // Add Basic Authorization header
//                post.addHeader(
//                        OAuthConstants.AUTHORIZATION,
//                        getBasicAuthorizationHeader(oauthDetails.getUsername(),
//                                oauthDetails.getPassword()));
//                System.out.println("Retry with login credentials");
//                post.releaseConnection();
//                response = client.execute(post);
//                code = response.getStatusLine().getStatusCode();
//                if (code >= 400) {
//                    System.out.println("Retry with client credentials");
//                    post.removeHeaders(OAuthConstants.AUTHORIZATION);
//                    post.addHeader(
//                            OAuthConstants.AUTHORIZATION,
//                            getBasicAuthorizationHeader(
//                                    oauthDetails.getClientId(),
//                                    oauthDetails.getClientSecret()));
//                    post.releaseConnection();
//                    response = client.execute(post);
//                    code = response.getStatusLine().getStatusCode();
//                    if (code >= 400) {
//                        throw new RuntimeException(
//                                "Could not retrieve access token for user: "
//                                        + oauthDetails.getUsername());
//                    }
//                }
//
//            }
//            Map<String, String> map = handleResponse(response);
//            accessToken = map.get(OAuthConstants.ACCESS_TOKEN);
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return accessToken;
//    }
//
//    public static Map handleResponse(HttpResponse response) {
//        String contentType = OAuthConstants.JSON_CONTENT;
//        if (response.getEntity().getContentType() != null) {
//            contentType = response.getEntity().getContentType().getValue();
//        }
//        if (contentType.contains(OAuthConstants.JSON_CONTENT)) {
//            return handleJsonResponse(response);
//        } else if (contentType.contains(OAuthConstants.URL_ENCODED_CONTENT)) {
//            return handleURLEncodedResponse(response);
//        } else if (contentType.contains(OAuthConstants.XML_CONTENT)) {
//            return handleXMLResponse(response);
//        } else {
//            // Unsupported Content type
//            throw new RuntimeException(
//                    "Cannot handle "
//                            + contentType
//                            + " content type. Supported content types include JSON, XML and URLEncoded");
//        }
//
//    }
//
//    public static Map handleJsonResponse(HttpResponse response) {
//        Map<String, String> oauthLoginResponse = null;
//        String contentType = response.getEntity().getContentType().getValue();
//        try {
//            oauthLoginResponse = (Map<String, String>) new JSONParser()
//                    .parse(EntityUtils.toString(response.getEntity()));
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            throw new RuntimeException();
//        } catch (org.json.simple.parser.ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            throw new RuntimeException();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            throw new RuntimeException();
//        } catch (RuntimeException e) {
//            System.out.println("Could not parse JSON response");
//            throw e;
//        }
//        System.out.println();
//        System.out.println("********** Response Received **********");
//        for (Map.Entry<String, String> entry : oauthLoginResponse.entrySet()) {
//            System.out.println(String.format("  %s = %s", entry.getKey(),
//                    entry.getValue()));
//        }
//        return oauthLoginResponse;
//    }
//
//    public static Map handleURLEncodedResponse(HttpResponse response) {
//        Map<String, Charset> map = Charset.availableCharsets();
//        Map<String, String> oauthResponse = new HashMap<String, String>();
//        Set<Map.Entry<String, Charset>> set = map.entrySet();
//        Charset charset = null;
//        HttpEntity entity = response.getEntity();
//
//        System.out.println();
//        System.out.println("********** Response Received **********");
//
//        for (Map.Entry<String, Charset> entry : set) {
//            System.out.println(String.format("  %s = %s", entry.getKey(),
//                    entry.getValue()));
//            if (entry.getKey().equalsIgnoreCase(HTTP.UTF_8)) {
//                charset = entry.getValue();
//            }
//        }
//
//        try {
//            List<NameValuePair> list = URLEncodedUtils.parse(
//                    EntityUtils.toString(entity), Charset.forName(HTTP.UTF_8));
//            for (NameValuePair pair : list) {
//                System.out.println(String.format("  %s = %s", pair.getName(),
//                        pair.getValue()));
//                oauthResponse.put(pair.getName(), pair.getValue());
//            }
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            throw new RuntimeException("Could not parse URLEncoded Response");
//        }
//
//        return oauthResponse;
//    }
//
//    public static Map handleXMLResponse(HttpResponse response) {
//        Map<String, String> oauthResponse = new HashMap<String, String>();
//        try {
//
//            String xmlString = EntityUtils.toString(response.getEntity());
//            DocumentBuilderFactory factory = DocumentBuilderFactory
//                    .newInstance();
//            DocumentBuilder db = factory.newDocumentBuilder();
//            InputSource inStream = new InputSource();
//            inStream.setCharacterStream(new StringReader(xmlString));
//            Document doc = db.parse(inStream);
//
//            System.out.println("********** Response Receieved **********");
//            parseXMLDoc(null, doc, oauthResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(
//                    "Exception occurred while parsing XML response");
//        }
//        return oauthResponse;
//    }
//
//    public static void parseXMLDoc(Element element, Document doc,
//                                   Map<String, String> oauthResponse) {
//        NodeList child = null;
//        if (element == null) {
//            child = doc.getChildNodes();
//
//        } else {
//            child = element.getChildNodes();
//        }
//        for (int j = 0; j < child.getLength(); j++) {
//            if (child.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
//                org.w3c.dom.Element childElement = (org.w3c.dom.Element) child
//                        .item(j);
//                if (childElement.hasChildNodes()) {
//                    System.out.println(childElement.getTagName() + " : "
//                            + childElement.getTextContent());
//                    oauthResponse.put(childElement.getTagName(),
//                            childElement.getTextContent());
//                    parseXMLDoc(childElement, null, oauthResponse);
//                }
//
//            }
//        }
//    }
//
//    public static String getAuthorizationHeaderForAccessToken(String accessToken) {
//        return OAuthConstants.BEARER + " " + accessToken;
//    }
//
//    public static String getBasicAuthorizationHeader(String username,
//                                                     String password) {
//        return OAuthConstants.BASIC + " "
//                + encodeCredentials(username, password);
//    }
//
//    public static String encodeCredentials(String username, String password) {
//        String cred = username + ":" + password;
//        String encodedValue = null;
//        byte[] encodedBytes = Base64.encodeBase64(cred.getBytes());
//        encodedValue = new String(encodedBytes);
//        System.out.println("encodedBytes " + new String(encodedBytes));
//
//        byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
//        System.out.println("decodedBytes " + new String(decodedBytes));
//
//        return encodedValue;
//
//    }
//
//    public static boolean isValid(String str) {
//        return (str != null && str.trim().length() > 0);
//    }
//
//    public void getNewAuthToken() throws OAuthProblemException, OAuthSystemException {
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, 0);
//
//        String refreshToken = sharedPreferences.getString(Constants.REFRESH_TOKEN, "");
//
//        OAuthClientRequest request = OAuthClientRequest.tokenLocation(PropertiesHelper.getServerUrl() + PropertiesHelper.getOauthTokenPath())
//                .setClientId(PropertiesHelper.getOauthClientId())
//                .setClientSecret(PropertiesHelper.getOauthClientSecret())
//                .setGrantType(GrantType.REFRESH_TOKEN)
//                .setRefreshToken(refreshToken)
//                .buildBodyMessage();
//        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
//        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);
//
//        storeAccessToken(oAuthResponse.getAccessToken());
//
//    }

}
