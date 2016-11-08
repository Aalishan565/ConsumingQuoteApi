package com.consumingquoteapi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements QuoteDownloadListener {
    private TextView mTvQuote;
    private TextView mTvAuthor;
    private ImageView mIvBg;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        mTvQuote = (TextView) findViewById(R.id.tv_quote);
        mTvAuthor = (TextView) findViewById(R.id.tv_author_name);
        mIvBg = (ImageView) findViewById(R.id.imageView);
        pd.show();
        new QuoteDownloadAsyncTask(this).execute();
    }

    @Override
    public void onQuoteDownloadSucess(QuoteModel model) {
        pd.dismiss();
        Picasso.with(this).load(model.getBackground()).into(mIvBg);
        mTvQuote.setText(model.getQuote());
        mTvAuthor.setText(model.getAuthor());
    }

    @Override
    public void onQuoteDownloadFailure() {
        pd.dismiss();

    }
}
