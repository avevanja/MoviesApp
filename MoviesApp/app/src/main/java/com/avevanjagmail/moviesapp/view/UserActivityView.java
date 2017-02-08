package com.avevanjagmail.moviesapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

public interface UserActivityView {

    Context getContext();

    void setUrl(Uri uri);
    void setBm(Bitmap bitmap);
    void setName(String name);

}
