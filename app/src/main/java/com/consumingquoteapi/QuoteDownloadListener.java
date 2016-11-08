package com.consumingquoteapi;

/**
 * Created by aalishan on 7/11/16.
 */

public interface QuoteDownloadListener {
    void onQuoteDownloadSucess(QuoteModel model);

    void onQuoteDownloadFailure();

}
