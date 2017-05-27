package cr.ac.ucr.paraiso.tydilabs.rest.impl;

import cr.ac.ucr.paraiso.tydilabs.exceptions.BadRequestException;
import cr.ac.ucr.paraiso.tydilabs.exceptions.InternalErrorException;
import cr.ac.ucr.paraiso.tydilabs.models.Asset;
import cr.ac.ucr.paraiso.tydilabs.models.Revision;
import cr.ac.ucr.paraiso.tydilabs.models.User;
import cr.ac.ucr.paraiso.tydilabs.rest.API;
import cr.ac.ucr.paraiso.tydilabs.rest.TydilabsService;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;
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

    private static final String URL_DEV = "http://192.168.43.149:3000";
    private static final String URL_PROD = "http://163.X.X.X";

    private TydilabsService service;

    private static TydilabsAPI instance;

    private TydilabsAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_DEV) // change this accordingly!
                .addConverterFactory(GsonConverterFactory.create())
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
    public void asset(int id, NetworkTools.APIRequestCallback<User> callback) {
    }

    @Override
    public void assetCreate(Asset asset, NetworkTools.APIRequestCallback<User> callback) {
    }

    @Override
    public void assetSearch(String key, NetworkTools.APIRequestCallback<User> callback) {
    }

    @Override
    public void revisions(NetworkTools.APIRequestCallback<User> callback) {
    }

    @Override
    public void revision(int id, NetworkTools.APIRequestCallback<User> callback) {
    }

    @Override
    public void revisionCreate(Revision revision, NetworkTools.APIRequestCallback<User> callback) {
    }

    @Override
    public void revisionClose(int id, NetworkTools.APIRequestCallback<User> callback) {
    }

    public static TydilabsAPI getInstance() {
        // spoiler alert! it's a singleton
        if (instance == null) {
            instance = new TydilabsAPI();
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
