package com.smart.compact.module;

import android.content.Context;

import com.smart.compact.qualifier.ApplicationContext;
import com.smart.compact.qualifier.Cached;
import com.smart.compact.qualifier.NonCached;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by lshabory on 3/8/18.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Cache provideOkHttpCache(@ApplicationContext Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @IntoSet
    Protocol providesProtocolHTTP1() {
        return Protocol.HTTP_1_1;
    }

    @Provides
    @IntoSet
    Protocol providesProtocolHTTP2() {
        return Protocol.HTTP_2;
    }

    @Provides
    @IntoSet
    Interceptor providesBodyInterceptors() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    @NonCached
    OkHttpClient provideOkHttpClientNonCached(Set<Protocol> protocols, Set<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .protocols(new ArrayList<>(protocols))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);
        builder.interceptors().addAll(interceptors);
        return builder.build();
    }

    @Provides
    @Singleton
    @Cached
    OkHttpClient provideOkHttpClientCached(Set<Protocol> protocols, Set<Interceptor> interceptors, Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .protocols(new ArrayList<>(protocols))
                .cache(cache)
                .connectTimeout(3 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        builder.interceptors().addAll(interceptors);
        return builder.build();
    }
}