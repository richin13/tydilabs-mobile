package cr.ac.ucr.paraiso.tydilabs.rest;

import java.io.IOException;
import java.util.List;

import cr.ac.ucr.paraiso.tydilabs.exceptions.BadRequestException;
import cr.ac.ucr.paraiso.tydilabs.exceptions.InternalErrorException;
import cr.ac.ucr.paraiso.tydilabs.exceptions.NotFoundException;
import cr.ac.ucr.paraiso.tydilabs.models.Asset;
import cr.ac.ucr.paraiso.tydilabs.models.Revision;
import cr.ac.ucr.paraiso.tydilabs.models.User;
import cr.ac.ucr.paraiso.tydilabs.tools.NetworkTools;

/**
 * Project: Tydilabs
 * Date: 5/26/17
 *
 * @author ricardo
 */

public interface API {

    /**
     * Takes an incomplete user object containing a PIN and performs
     * a login attempt agains Tydilabs API.
     *
     * @param user Object with PIN attribute set to a valid number.
     */
    void login(User user, NetworkTools.APIRequestCallback<User> callback);

    /**
     * Retrieves the information of an asset given his ID.
     *
     * @param id The numeric ID of the asset to retrieve.
     */
    void asset(int id, NetworkTools.APIRequestCallback<User> callback);


    /**
     * Creates a new asset through the API.
     *
     * @param asset The newly-created asset instance to save.
     */
    void assetCreate(Asset asset, NetworkTools.APIRequestCallback<User> callback);

    /**
     * Retrieves all the assets that contains a given key in either its name, description, plate number or category
     *
     * @param key The key to be used as a search parameter.
     */
    void assetSearch(String key, NetworkTools.APIRequestCallback<User> callback);

    /**
     * Retrieves all the active (opened) revisions that exists.
     */
    void revisions(NetworkTools.APIRequestCallback<User> callback);

    /**
     * Retrieves a revision given his ID.
     *
     * @param id The ID of the revision to retrieve.
     */
    void revision(int id, NetworkTools.APIRequestCallback<User> callback);

    /**
     * Creates a new revision through the API.
     *
     * @param revision The revision object to be used as srequest body.
     */
    void revisionCreate(Revision revision, NetworkTools.APIRequestCallback<User> callback);


    /**
     * Closes a given revision.
     *
     * @param id The ID of the revision to be closed.
     */
    void revisionClose(int id, NetworkTools.APIRequestCallback<User> callback);

}
