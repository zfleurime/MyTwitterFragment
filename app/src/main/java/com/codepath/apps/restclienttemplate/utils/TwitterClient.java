package com.codepath.apps.restclienttemplate.utils;

import android.content.Context;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/scribejava/scribejava/tree/master/scribejava-apis/src/main/java/com/github/scribejava/apis
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "F41BWhUO2CJSeDo28uiUTH9fu";       // Change this
	public static final String REST_CONSUMER_SECRET = "M5h3TK117wN2s55uwubvb9gpAH3yzDgcsp18wusXm4yQyiqd4e"; // Change this

	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE,
				REST_URL,
				REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET,
				String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
						context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
	}
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getTimelines(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		client.get(apiUrl, params, handler);
	}


	public void getMoreTimelines(AsyncHttpResponseHandler handler, long max_id) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("max_id", max_id);
		client.get(apiUrl, params, handler);
	}


	public void getMentionsTimelines(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		client.get(apiUrl, params, handler);
	}


	public void getMoreMentionsTimelines(AsyncHttpResponseHandler handler,long max_id) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("max_id", max_id);
		client.get(apiUrl, params, handler);
	}

	public void getUserInfo(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("account/verify_credentials.json");
		// Can specify query string params directly or through RequestParams.
		client.get(apiUrl, handler);
	}


	public void getOtherUserInfo(AsyncHttpResponseHandler handler, String screen_name){
		String apiUrl = getApiUrl("users/show.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("screen_name", screen_name);
		client.get(apiUrl, params, handler);
	}

    public void getSearchResult(AsyncHttpResponseHandler handler, String query){
        String apiUrl = getApiUrl("search/tweets.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("q", query);
        client.get(apiUrl, params, handler);
    }


	public void postTweet(AsyncHttpResponseHandler handler,String status){
			String apiUrl = getApiUrl("statuses/update.json");
			RequestParams params = new RequestParams();
			params.put("status", status);
			client.post(apiUrl,params,handler);
	}


	public void replyTweet(AsyncHttpResponseHandler handler,String status, long status_id){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", status);
		params.put("in_reply_to_status_id", status_id);
		client.post(apiUrl,params,handler);
	}


	public void postReTweet(AsyncHttpResponseHandler handler,long status_id){
		String apiUrl = getApiUrl("statuses/retweet/"+status_id+".json");
		client.post(apiUrl,handler);
	}


	// Create the favorite
    public void postCreateFavorite(AsyncHttpResponseHandler handler,long status_id){
        String apiUrl = getApiUrl("favorites/create.json");
        RequestParams params = new RequestParams();
        params.put("id", status_id);
        client.post(apiUrl,params,handler);
    }

    // Destroy the favorite
    public void postDestroyFavorite(AsyncHttpResponseHandler handler,long status_id){
        String apiUrl = getApiUrl("favorites/destroy.json");
        RequestParams params = new RequestParams();
        params.put("id", status_id);
        client.post(apiUrl,params,handler);
    }



    public void getDirectMessages(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("direct_messages/sent.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        client.get(apiUrl, params, handler);
    }

    public void getMoreDirectMessages(AsyncHttpResponseHandler handler, long max_id){
        String apiUrl = getApiUrl("direct_messages/sent.json");
        RequestParams params = new RequestParams();
        params.put("max_id", max_id);
        client.get(apiUrl, params, handler);
    }


    public void postDirectMessage(AsyncHttpResponseHandler handler, String screen_name, String text){
        String apiUrl = getApiUrl("direct_messages/new.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screen_name);
        params.put("text", text);
        client.post(apiUrl, params, handler);
    }



    public void getUserTimelines(AsyncHttpResponseHandler handler, String screen_name) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
        params.put("since_id",1);
		params.put("screen_name", screen_name);
		client.get(apiUrl, params, handler);
	}

	// Followers List
    public void getfollowersList(AsyncHttpResponseHandler handler, long userID) {
        String apiUrl = getApiUrl("followers/list.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("count", 25);
        client.get(apiUrl, params, handler);
    }


    public void getMorefollowersList(AsyncHttpResponseHandler handler, long userID,long cursor) {
        String apiUrl = getApiUrl("followers/list.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("cursor", cursor);
        client.get(apiUrl, params, handler);
    }


    //Following List
    public void getfriendsList(AsyncHttpResponseHandler handler, long userID) {
        String apiUrl = getApiUrl("friends/list.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("count", 25);
        client.get(apiUrl, params, handler);
    }


    public void getMorefriendsList(AsyncHttpResponseHandler handler, long userID,long cursor) {
        String apiUrl = getApiUrl("friends/list.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("cursor", cursor);
        client.get(apiUrl, params, handler);
    }



	public void getMoreUserTimelines(AsyncHttpResponseHandler handler, String screen_name, long max_id) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("screen_name", screen_name);
		params.put("max_id", max_id);
		client.get(apiUrl, params, handler);
	}


    public void getfavoritesList(AsyncHttpResponseHandler handler, String screen_name) {
        String apiUrl = getApiUrl("favorites/list.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("screen_name", screen_name);
        params.put("count", 25);
        params.put("since_id",1);
        client.get(apiUrl, params, handler);
    }


    public void getMorefavoritesList(AsyncHttpResponseHandler handler, String screen_name, long max_id) {
        String apiUrl = getApiUrl("favorites/list.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("screen_name", screen_name);
        params.put("max_id",max_id);
        client.get(apiUrl, params, handler);
    }








	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
