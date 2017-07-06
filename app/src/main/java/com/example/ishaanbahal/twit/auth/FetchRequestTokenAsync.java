package com.example.ishaanbahal.twit.auth;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.ishaanbahal.twit.LoginActivity;
import com.example.ishaanbahal.twit.R;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by IshaanBahal on 27/06/17.
 */

public class FetchRequestTokenAsync extends AsyncTask<String, Void, String> {

    private static Twitter twitter;
    private static RequestToken requestToken;
    private static AccessToken accessToken;
    private String oauthURL, verifier;
    private Dialog dialog;
    private LoginActivity activity;
    private WebView webView;
    private ProgressDialog progressBar;

    public FetchRequestTokenAsync(LoginActivity activity) {
        this.activity = activity;
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("j6dSXo7AuUEVi0zWtiqO0YqSQ", "7uN8yMGHfbQ0Ys3xLZflQCoFJEgcfKVGfKfSHziwszely9ScoS");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //showing a progress dialog
        progressBar = new ProgressDialog(activity);
        progressBar.setMessage("Connecting...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            requestToken = twitter.getOAuthRequestToken();
            oauthURL = requestToken.getAuthorizationURL();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return oauthURL;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (oauthURL != null) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.dialog_login_webview);
            webView = (WebView) dialog.findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(oauthURL);

            webView.setWebViewClient(new WebViewClient() {
                boolean authComplete = false;

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (url.contains("oauth_verifier") && !authComplete) {
                        authComplete = true;
                        Log.e("AsyncTask", url);
                        Uri uri = Uri.parse(url);
                        verifier = uri.getQueryParameter("oauth_verifier");

                        dialog.dismiss();

                        //revoke access token asynctask
                        new AccessTokenGetTask().execute();
                    } else if (url.contains("denied")) {
                        dialog.dismiss();
                        Toast.makeText(activity, "Permission is Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            dialog.show();
            dialog.setCancelable(false);
            progressBar.dismiss();
        }
    }

    private class AccessTokenGetTask extends AsyncTask<String, String, User> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(activity);
            progressBar.setMessage("Fetching Data ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setCancelable(false);
            progressBar.show();
        }

        @Override
        protected User doInBackground(String... args) {
            User user = null;
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                user = twitter.showUser(accessToken.getUserId());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User response) {
            if (response == null) {
                Log.e("AsyncTask", "null user");
            } else {
                activity.callBackDataFromAsyncTask(accessToken, response);
            }
            progressBar.dismiss();
        }
    }
}
