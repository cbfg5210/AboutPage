package com.ue.aboutpage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by hawk on 2017/11/27.
 */

public class PraiseDialog extends DialogFragment {
    private static final String ARG_URL_MI = "arg_url_mi";
    private static final String ARG_URL_BD = "arg_url_bd";

    private String miStoreUrl;
    private String bdStoreUrl;

    public static PraiseDialog newInstance(String miStoreUrl, String bdStoreUrl) {
        PraiseDialog dialog = new PraiseDialog();
        Bundle arguments = new Bundle();
        if (!TextUtils.isEmpty(miStoreUrl)) {
            arguments.putString(ARG_URL_MI, miStoreUrl);
        }
        if (!TextUtils.isEmpty(bdStoreUrl)) {
            arguments.putString(ARG_URL_BD, bdStoreUrl);
        }
        dialog.setArguments(arguments);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            miStoreUrl = arguments.getString(ARG_URL_MI, null);
            bdStoreUrl = arguments.getString(ARG_URL_BD, null);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_praise, null);
        View tvPraiseMi = contentView.findViewById(R.id.tvPraiseMi);
        View tvPraiseBd = contentView.findViewById(R.id.tvPraiseBd);

        View.OnClickListener clickListener = v -> {
            int viewId = v.getId();
            dismissAllowingStateLoss();
            openBrowser(getContext(), (viewId == R.id.tvPraiseMi ? miStoreUrl : bdStoreUrl));
        };

        if (TextUtils.isEmpty(miStoreUrl)) {
            tvPraiseMi.setVisibility(View.GONE);
            contentView.findViewById(R.id.viewSeparator).setVisibility(View.GONE);
        } else {
            tvPraiseMi.setOnClickListener(clickListener);
        }

        if (TextUtils.isEmpty(bdStoreUrl)) {
            tvPraiseBd.setVisibility(View.GONE);
            contentView.findViewById(R.id.viewSeparator).setVisibility(View.GONE);
        } else {
            tvPraiseBd.setOnClickListener(clickListener);
        }

        return new AlertDialog.Builder(getContext())
                .setView(contentView)
                .create();
    }

    private void openBrowser(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(context, context.getString(R.string.error_open_browser), Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception exp) {
            Toast.makeText(context, context.getString(R.string.error_open_browser), Toast.LENGTH_SHORT).show();
        }
    }
}
