package com.javi.chinesevocabulary.helpers;

import com.javi.chinesevocabulary.pojos.Vocabulary;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by javi on 22/10/2016.
 */

public interface HttpHelper {
    @GET("vocabulary.json")
    Call<Vocabulary> getVocabulary();
}
