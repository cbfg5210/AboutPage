package com.ue.aboutpage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hawk on 2017/11/23.
 */

public class AboutPageView extends android.support.v4.widget.NestedScrollView implements View.OnClickListener {
    private TextView tvAppDesc;
    private TextView tvAppDescDetail;
    private TextView tvVersion;
    private TextView tvVersionNote;
    private TextView tvVersionNoteDetail;
    private TextView tvFaq;
    private TextView tvFaqDetail;
    private TextView tvFeedback;
    private TextView tvShare;
    private TextView tvSupport;

    private String appDescDetail;
    private String versionNoteDetail;
    private String emailAddress;
    private String emailSubject;
    private String shareContent;
    private String faqDetail;

    private OnAboutItemClickListener mAboutItemClickListener;

    public void setAboutItemClickListener(OnAboutItemClickListener aboutItemClickListener) {
        mAboutItemClickListener = aboutItemClickListener;
    }

    public AboutPageView(@NonNull Context context) {
        this(context, null, 0);
    }

    public AboutPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AboutPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AboutPageView);

        appDescDetail = ta.getString(R.styleable.AboutPageView_appDescDetail);
        versionNoteDetail = ta.getString(R.styleable.AboutPageView_versionNoteDetail);
        emailAddress = ta.getString(R.styleable.AboutPageView_emailAddress);
        emailSubject = ta.getString(R.styleable.AboutPageView_emailSubject);
        shareContent = ta.getString(R.styleable.AboutPageView_shareContent);
        faqDetail = ta.getString(R.styleable.AboutPageView_faqDetail);

        ta.recycle();

        View.inflate(context, R.layout.view_about_page, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvAppDesc = findViewById(R.id.tvAppDesc);
        tvAppDescDetail = findViewById(R.id.tvAppDescDetail);
        tvVersion = findViewById(R.id.tvVersion);
        tvVersionNote = findViewById(R.id.tvVersionNote);
        tvVersionNoteDetail = findViewById(R.id.tvVersionNoteDetail);
        tvFaq = findViewById(R.id.tvFaq);
        tvFaqDetail = findViewById(R.id.tvFaqDetail);
        tvFeedback = findViewById(R.id.tvFeedback);
        tvShare = findViewById(R.id.tvShare);
        tvSupport = findViewById(R.id.tvSupport);

        try {
            PackageManager packageManager = getContext().getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            tvVersion.setText(getContext().getString(R.string.app_version, info.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tvAppDescDetail.setText(appDescDetail);
        tvVersionNoteDetail.setText(versionNoteDetail);
        tvFaqDetail.setText(faqDetail);

        String feedbackTxt = getContext().getString(R.string.feedback) + "(" + emailAddress + ")";
        tvFeedback.setText(feedbackTxt);

        tvAppDesc.setOnClickListener(this);
        tvVersion.setOnClickListener(this);
        tvVersionNote.setOnClickListener(this);
        tvFaq.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        tvSupport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tvAppDesc) {
            toggleVisibility(tvAppDescDetail);
            return;
        }
        if (viewId == R.id.tvVersion) {
            if (mAboutItemClickListener != null) {
                mAboutItemClickListener.onVersionClicked();
            }
            return;
        }
        if (viewId == R.id.tvVersionNote) {
            toggleVisibility(tvVersionNoteDetail);
            return;
        }
        if (viewId == R.id.tvFaq) {
            toggleVisibility(tvFaqDetail);
            return;
        }
        if (viewId == R.id.tvFeedback) {
            sendEMail(getContext(), emailAddress, emailSubject);
            return;
        }
        if (viewId == R.id.tvShare) {
            share(getContext(), shareContent);
            return;
        }
        if (viewId == R.id.tvSupport) {
            if (mAboutItemClickListener != null) {
                mAboutItemClickListener.onSupportClicked();
            }
            return;
        }
    }

    public void toggleAppDescDetail() {
        toggleVisibility(tvAppDescDetail);
    }

    public void toggleFaqDetail() {
        toggleVisibility(tvFaqDetail);
    }

    private void toggleVisibility(View view) {
        view.setVisibility((view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));
    }

    private void share(Context context, String shareContent) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);//设置分享行为
        intent.setType("text/plain");//设置分享内容的类型
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share));//添加分享内容标题
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);//添加分享内容
        intent = Intent.createChooser(intent, context.getString(R.string.share));
        try {
            context.startActivity(intent);
        } catch (Exception exp) {
            Toast.makeText(getContext(), context.getString(R.string.error_share), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEMail(Context context, String toEmail, String subject) {
        Uri uri = Uri.fromParts("mailto", toEmail, null);
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent = Intent.createChooser(intent, subject);
        try {
            context.startActivity(intent);
        } catch (Exception exp) {
            Toast.makeText(getContext(), context.getString(R.string.error_send_mail), Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnAboutItemClickListener {
        void onVersionClicked();

        void onSupportClicked();
    }
}
