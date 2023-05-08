package com.poupa.vinylmusicplayer.discog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.poupa.vinylmusicplayer.App;
import com.poupa.vinylmusicplayer.R;

/**
 * @author SC (soncaokim)
 */

public class SnackbarUtil {
    @DrawableRes
    public static final int ICON = R.drawable.ic_bookmark_music_white_24dp;

    private Snackbar progressBar = null;
    private final View viewContainer;

    public SnackbarUtil(View view) {
        viewContainer = view;
    }

    @NonNull
    private static Drawable tintedIcon(@NonNull Snackbar snackbar) {
        final Context context = App.getInstance().getApplicationContext();

        // Pick the color from the text view...
        TextView tv = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        final int color = tv.getCurrentTextColor();

        // ... and apply the color on the icon
        final Drawable icon = context.getDrawable(ICON);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        icon.setTint(color);

        return icon;
    }

    @NonNull
    private static CharSequence buildMessageWithIcon(@NonNull final CharSequence message, @NonNull Snackbar snackbar) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {return message;}

        SpannableStringBuilder messageWithIcon = new SpannableStringBuilder();
        messageWithIcon.append(
                " ",
                new ImageSpan(tintedIcon(snackbar)),
                0);
        messageWithIcon.append(" "); // some extra space before the text message
        messageWithIcon.append(message);

        return messageWithIcon;
    }

    public void showProgress(@NonNull final CharSequence text) {
        if (progressBar == null) {
            progressBar = Snackbar.make(
                    viewContainer,
                    "",
                    Snackbar.LENGTH_INDEFINITE);
        }

        progressBar.setText(buildMessageWithIcon(text, progressBar));
        if (!progressBar.isShownOrQueued()) {
            progressBar.show();
        }
    }

    void showResult(@NonNull final CharSequence message) {
        dismiss();

        progressBar = Snackbar.make(
                viewContainer,
                "",
                Snackbar.LENGTH_LONG);
        progressBar.setText(buildMessageWithIcon(message, progressBar));
        progressBar.show();
    }

    void dismiss() {
        if ((progressBar != null) && progressBar.isShownOrQueued()) {
            progressBar.dismiss();
        }
        progressBar = null;
    }
}
