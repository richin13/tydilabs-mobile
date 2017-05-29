package cr.ac.ucr.paraiso.tydilabs.rest;

import java.util.List;

import cr.ac.ucr.paraiso.tydilabs.models.Asset;
import cr.ac.ucr.paraiso.tydilabs.models.Revision;
import cr.ac.ucr.paraiso.tydilabs.models.User;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Project: Tydilabs
 * Date: 5/26/17
 *
 * @author ricardo
 */

public interface TydilabsService {

    /* Login and security */

    @POST("users/login.json")
    Call<User> login(@Body User user);

    /* Assets */
    @GET("assets/{id}.json")
    Call<Asset> asset(@Path("id") int id);

    @POST("assets.json")
    Call<Asset> assetCreate(@Body Asset asset);

    @PUT("assets/{id}.json")
    Call<Asset> assetUpdate(@Path("id") int id, @Body Asset asset);

    @GET("assets/search/{key}.json")
    Call<List<Asset>> assetSearch(@Path("key") String key);

    /* Revision mode */
    @GET("revisions.json")
    Call<List<Revision>> revisions(NetworkTools.APIRequestCallback<User> callback);

    @GET("revisions/{id}.json")
    Call<Revision> revision(@Path("id") int id);

    @POST("revisions.json")
    Call<Revision> revisionCreate(@Body Revision revision);

    @DELETE("revisions/{id}/close.json")
    Call<Revision> revisionClose(@Path("id") int id);
}
