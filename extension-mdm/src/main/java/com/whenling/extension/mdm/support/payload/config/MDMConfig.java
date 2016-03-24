package com.whenling.extension.mdm.support.payload.config;

public class MDMConfig extends ConfigPayload {

	/**
	 * Mandatory. UUIDofthecertificatepayloadforthedevice'sidentity.
	 * ItmayalsopointtoaSCEPpayload.
	 */
	private String identityCertificateUUID;

	/**
	 * Mandatory. ThetopicthatMDMlistenstoforpushnotifications.The
	 * certificatethattheserverusestosendpushnotificationsmusthave
	 * thesametopicinitssubject.Thetopicmustbeginwiththe com.apple.mgmt.prefix.
	 */
	private String topic;

	/**
	 * Mandatory. TheURLthatthedevicecontactstoretrievedevice
	 * managementinstructions.Mustbeginwiththehttps://URLscheme,
	 * andmaycontainaportnumber(:1234,forexample).
	 * 
	 */
	private String serverURL;

	/**
	 * Optional. Anarrayofstringsindicatingservercapabilities.Iftheserver
	 * managesOSXdevices,thisfieldismandatoryandmustcontainthe value
	 * <string>com.apple.mdm.per-user-connections</string>.
	 * Thisindicatesthattheserversupportsbothdeviceanduser
	 * connections.SeeMDMProtocolExtensions (page 32).
	 * 
	 */
	private String[] serverCapabilities;

	/**
	 * Optional. Iftrue,eachmessagecomingfromthedevicecarriesthe
	 * additionalMdm-SignatureHTTPheader.Defaultstofalse.
	 */
	private Boolean signMessage;

	/**
	 * Optional. TheURLthatthedeviceshouldusetocheckinduring
	 * installation.Mustbeginwiththehttps://URLschemeandmay
	 * containaportnumber(:1234,forexample).IfthisURLisnotgiven,
	 * theServerURLisusedforbothpurposes.
	 * 
	 */
	private String checkInURL;

	/**
	 * Optional. Iftrue,thedeviceattemptstosendaCheckOutmessage
	 * tothecheck-inserverwhentheprofileisremoved.Defaultstofalse.
	 * Note:OSXv10.8actsasthoughthissettingisalwaystrue.
	 */
	private Boolean checkOutWhenRemoved;

	/**
	 * Required. LogicalORofthefollowingbit-flags: ●
	 * 1—Allowinspectionofinstalledconfigurationprofiles. ●
	 * 2—Allowinstallationandremovalofconfigurationprofiles ●
	 * 4—Allowdevicelockandpasscoderemoval ● 8—Allowdeviceerase ●
	 * 16—AllowqueryofDeviceInformation(devicecapacity,serial number) ●
	 * 32—AllowqueryofNetworkInformation(phone/SIMnumbers, MACaddresses) ●
	 * 64—Allowinspectionofinstalledprovisioningprofiles ●
	 * 128—Allowinstallationandremovalofprovisioningprofiles ●
	 * 256—Allowinspectionofinstalledapplications ●
	 * 512—Allowrestriction-relatedqueries ● 1024—Allowsecurity-relatedqueries ●
	 * 2048—Allowmanipulationofsettings.Availability:Availablein
	 * iOS5.0andlater.AvailableinOSX10.9forcertaincommands. ●
	 * 4096—Allowappmanagement.Availability:AvailableiniOS5.0
	 * andlater.AvailableinOSX10.9forcertaincommands.
	 * Maynotbezero.If2isspecified,then1mustalsobespecified.If128
	 * isspecified,then64mustalsobespecified.
	 * 
	 */
	private int accessRights = 8191;
	
	private Boolean useDevelopmentAPNS;

	public MDMConfig(String identityCertificateUUID, String serverURL, String topic) {
		super("com.apple.mdm");

		setPayloadDescription("Mobile Device Management");
		this.identityCertificateUUID = identityCertificateUUID;
		this.serverURL = serverURL;
		this.topic = topic;

	}

	public String getIdentityCertificateUUID() {
		return identityCertificateUUID;
	}

	public void setIdentityCertificateUUID(String identityCertificateUUID) {
		this.identityCertificateUUID = identityCertificateUUID;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String[] getServerCapabilities() {
		return serverCapabilities;
	}

	public void setServerCapabilities(String[] serverCapabilities) {
		this.serverCapabilities = serverCapabilities;
	}

	public Boolean getSignMessage() {
		return signMessage;
	}

	public void setSignMessage(Boolean signMessage) {
		this.signMessage = signMessage;
	}

	public String getCheckInURL() {
		return checkInURL;
	}

	public void setCheckInURL(String checkInURL) {
		this.checkInURL = checkInURL;
	}

	public Boolean getCheckOutWhenRemoved() {
		return checkOutWhenRemoved;
	}

	public void setCheckOutWhenRemoved(Boolean checkOutWhenRemoved) {
		this.checkOutWhenRemoved = checkOutWhenRemoved;
	}

	public int getAccessRights() {
		return accessRights;
	}

	public void setAccessRights(int accessRights) {
		this.accessRights = accessRights;
	}

	public Boolean getUseDevelopmentAPNS() {
		return useDevelopmentAPNS;
	}

	public void setUseDevelopmentAPNS(Boolean useDevelopmentAPNS) {
		this.useDevelopmentAPNS = useDevelopmentAPNS;
	}

}
