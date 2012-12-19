package de.digitalstep.match.android.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

/**
 * This class defines an authenticator with no authentication.
 * 
 * @author Gunnar Bastkowski (gunnar@digitalstep.de)
 */
class AccountAuthenticator extends AbstractAccountAuthenticator {

	private static final Logger log = LoggerFactory.getLogger(AccountAuthenticator.class);

	public AccountAuthenticator(Context context) {
		super(context);
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
		log.debug("editProperties");
		return null;
	}

	@Override
	public Bundle addAccount(
			AccountAuthenticatorResponse response,
			String accountType,
			String authTokenType,
			String[] requiredFeatures,
			Bundle options) throws NetworkErrorException {
		log.debug("addAccount");

		return null;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options)
			throws NetworkErrorException {
		log.debug("confirmCredentials");
		return null;
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options)
			throws NetworkErrorException {
		log.debug("getAuthToken");
		return null;
	}

	@Override
	public String getAuthTokenLabel(String authTokenType) {
		log.debug("getAuthTokenLabel");
		return null;
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options)
			throws NetworkErrorException {
		log.debug("updateCredentials");
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
		log.debug("hasFeatures");
		return null;
	}

}