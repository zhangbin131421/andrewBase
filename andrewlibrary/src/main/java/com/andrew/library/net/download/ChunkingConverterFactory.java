package com.andrew.library.net.download;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Body;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class ChunkingConverterFactory extends Converter.Factory {

    @Target(PARAMETER)
    @Retention(RUNTIME)
    @interface Chunked {

    }

    private BufferedSink bufferedSink;
    private final RequestBody requestBody;

    private final ProgressResponseListener listener;

    public ChunkingConverterFactory(RequestBody requestBody,ProgressResponseListener listener){
        this.requestBody = requestBody;
        this.listener = listener ;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

        boolean isBody = false;
        boolean isChunked = false;

        for (Annotation annotation : parameterAnnotations){
            isBody |= annotation instanceof Body;
            isChunked |= annotation instanceof Chunked;
        }

        final Converter<Object,RequestBody> delegate = retrofit
                .nextRequestBodyConverter(this,type,parameterAnnotations,methodAnnotations);

        return new Converter<Object, RequestBody>() {
            @Override
            public RequestBody convert(Object value) throws IOException {


                return new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return requestBody.contentType();
                    }


                    @Override
                    public long contentLength() throws IOException {
                        return requestBody.contentLength();
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
//                        realBody.writeTo(sink);
                        if (bufferedSink == null) {
                            //??????
                            bufferedSink = Okio.buffer(sink(sink));
                        }
                        //??????
                        requestBody.writeTo(bufferedSink);
                        //????????????flush???????????????????????????????????????????????????
                        bufferedSink.flush();

                    }

                    private Sink sink(Sink sink) {
                        return new ForwardingSink(sink) {
                            //?????????????????????
                            long bytesWritten = 0L;
                            //????????????????????????????????????contentLength()??????
                            long contentLength = 0L;

                            @Override
                            public void write(Buffer source, long byteCount) throws IOException {
                                super.write(source, byteCount);
                                if (contentLength == 0) {
                                    //??????contentLength???????????????????????????
                                    contentLength = contentLength();
                                }
                                //??????????????????????????????
                                bytesWritten += byteCount;
                                //??????
                                listener.onResponseProgress(bytesWritten, contentLength, bytesWritten == contentLength);
                            }
                        };
                    }
                };
            }
        };
    }


}
