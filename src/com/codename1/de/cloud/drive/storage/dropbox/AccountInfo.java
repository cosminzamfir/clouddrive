package com.codename1.de.cloud.drive.storage.dropbox;

import java.util.Hashtable;

/**
 * {
    "referral_link": "https://www.dropbox.com/referrals/r1a2n3d4m5s6t7",
    "display_name": "John P. User",
    "uid": 12345678,
    "country": "US",
    "quota_info": {
        "shared": 253738410565,
        "quota": 107374182400000,
        "normal": 680031877871
    }
}
 * @author Cosmin Zamfir
 *
 */
public class AccountInfo {

    private String referralLink;
    private String displayName;
    private String uid;
    private String country;
    private long sharedQuota;
    private long quota;
    private long normalQuota;

    /**
     * Creates a new instance from the accountInfo json response
     * @see Dropbox API reference
     * @param jsonResponse
     */
    public AccountInfo(Hashtable json) {
        this.referralLink = (String) json.get("referral_link");
        this.displayName = (String) json.get("display_name");
        this.uid = (String) json.get("uid");
        this.country = (String) json.get("country");
        Hashtable quotaInfo = (Hashtable) json.get("quota_info");
        this.sharedQuota = Long.parseLong(((String) quotaInfo.get("shared")));
        this.quota = Long.parseLong(((String) quotaInfo.get("quota")));
        this.normalQuota = Long.parseLong(((String) quotaInfo.get("normal")));
    }

    public String getReferralLink() {
        return referralLink;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUid() {
        return uid;
    }

    public String getCountry() {
        return country;
    }

    public long getSharedQuota() {
        return sharedQuota;
    }

    public long getQuota() {
        return quota;
    }

    public long getNormalQuota() {
        return normalQuota;
    }

}
