package cr.ac.ucr.paraiso.tydilabs.rest.impl;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import cr.ac.ucr.paraiso.tydilabs.exceptions.BadRequestException;
import cr.ac.ucr.paraiso.tydilabs.exceptions.InternalErrorException;
import cr.ac.ucr.paraiso.tydilabs.exceptions.NotFoundException;
import cr.ac.ucr.paraiso.tydilabs.exceptions.UnauthorizedException;
import cr.ac.ucr.paraiso.tydilabs.models.Asset;
import cr.ac.ucr.paraiso.tydilabs.models.Revision;
import cr.ac.ucr.paraiso.tydilabs.models.User;
import cr.ac.ucr.paraiso.tydilabs.rest.API;
import cr.ac.ucr.paraiso.tydilabs.rest.TydilabsService;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Project: Tydilabs
 * Date: 5/26/17
 *
 * @author ricardo
 */

public class TydilabsAPI implements API {

    private TydilabsService service;

    private static TydilabsAPI instance;

    private TydilabsAPI(final User user) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("X-User-Email", user.getEmail(true))
                                .addHeader("X-User-Token", user.getAuthenticationToken(true)).build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkTools.URL_DEV) // change this accordingly!
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.service = retrofit.create(TydilabsService.class);
    }

    @Override
    public void login(User user, final NetworkTools.APIRequestCallback<User> callback) {
        Call<User> call = service.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int code = response.code();

                if (code == HTTPStatusCode.OK) {
                    // invalid credentials are being handled by
                    // the response status code.
                    callback.onResponse(response.body());
                } else {
                    Throwable exception;

                    if (code == HTTPStatusCode.BAD_REQUEST) {
                        // invalid credentials probably.
                        // is this compliant with REST?
                        // who shall know
                        exception = new BadRequestException();
                    } else {
                        // something bad happened :(
                        exception = new InternalErrorException();
                    }

                    callback.onFailure(exception);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void asset(int id, final NetworkTools.APIRequestCallback<Asset> callback) {
        Call<Asset> call = service.asset(id);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                int code = response.code();
                if (code == HTTPStatusCode.OK) {
                    callback.onResponse(response.body());
                } else {
                    Throwable exception;

                    if (code == HTTPStatusCode.UNAUTHORIZED) {
                        exception = new UnauthorizedException();
                    } else if (code == HTTPStatusCode.NOT_FOUND) {
                        exception = new NotFoundException();
                    } else {
                        Log.d("DEBUG", "The response code is " + code);
                        exception = new InternalErrorException();
                    }

                    callback.onFailure(exception);
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void assetCreate(Asset asset, NetworkTools.APIRequestCallback<User> callback) {
    }

    @Override
    public void assetUpdate(Asset asset, final NetworkTools.APIRequestCallback<Asset> callback) {
        Call<Asset> call = service.assetUpdate(asset.getId(), asset);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                int code = response.code();
                if (code == HTTPStatusCode.OK) {
                    callback.onResponse(response.body());
                } else {
                    Throwable exception;

                    if (code == HTTPStatusCode.BAD_REQUEST) {
                        exception = new BadRequestException();
                    } else if (code == HTTPStatusCode.UNAUTHORIZED) {
                        exception = new UnauthorizedException();
                    } else if (code == HTTPStatusCode.NOT_FOUND) {
                        exception = new NotFoundException();
                    } else {
                        Log.d("DEBUG", "The response code is " + code);
                        exception = new InternalErrorException();
                    }

                    callback.onFailure(exception);
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void assetSearch(String key, NetworkTools.APIRequestCallback<User> callback) {
    }

    @Override
    public void revisions(final NetworkTools.APIRequestCallback<List<Revision>> callback) {
        final Call<List<Revision>> call = service.revisions(callback);
        call.enqueue(new Callback<List<Revision>>() {
            @Override
            public void onResponse(Call<List<Revision>> call, Response<List<Revision>> response) {
                int code = response.code();
                if (code == HTTPStatusCode.OK) {
                    callback.onResponse(response.body());
                } else {
                    Throwable exception;

                    if (code == HTTPStatusCode.UNAUTHORIZED) {
                        exception = new UnauthorizedException();
                    } else if (code == HTTPStatusCode.NOT_FOUND) {
                        exception = new NotFoundException();
                    } else {
                        Log.d("DEBUG", "The response code is " + code);
                        exception = new InternalErrorException();
                    }

                    callback.onFailure(exception);
                }
            }

            @Override
            public void onFailure(Call<List<Revision>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void revision(int id, NetworkTools.APIRequestCallback<Revision> callback) {
    }

    @Override
    public void revisionCreate(Revision revision, NetworkTools.APIRequestCallback<Revision> callback) {
    }

    @Override
    public void revisionClose(int id, NetworkTools.APIRequestCallback<Revision> callback) {
    }

    public static TydilabsAPI getInstance(User user) {
        // spoiler alert! it's a singleton
        if (instance == null) {
            instance = new TydilabsAPI(user);
        }

        return instance;
    }


    private static class HTTPStatusCode {
        static int OK = 200;
        static int CREATED = 201;

        static int BAD_REQUEST = 400;
        static int UNAUTHORIZED = 401;
        static int FORBIDDEN = 403;
        static int NOT_FOUND = 404;
        static int METHOD_NOT_ALLOWED = 405;

        static int INTERNAL_ERROR = 500;
        static int NOT_IMPLEMENTED = 501;
    }
}
