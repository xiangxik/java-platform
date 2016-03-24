package com.whenling.extension.mdm.support.payload.command;

public class DeviceInformation extends CommandPayload {

	public static final String Q_UDID = "UDID";
	public static final String Q_Languages = "Languages";
	public static final String Q_Locales = "Locales";
	public static final String Q_DeviceID = "DeviceID";
	public static final String Q_OrganizationInfo = "OrganizationInfo";
	public static final String Q_LastCloudBackupDate = "LastCloudBackupDate";
	public static final String Q_AwaitingConfiguration = "AwaitingConfiguration";

	public static final String Q_DeviceName = "DeviceName";
	public static final String Q_OSVersion = "OSVersion";
	public static final String Q_BuildVersion = "BuildVersion";
	public static final String Q_ModelName = "ModelName";
	public static final String Q_Model = "Model";
	public static final String Q_ProductName = "ProductName";
	public static final String Q_SerialNumber = "SerialNumber";
	public static final String Q_DeviceCapacity = "DeviceCapacity";
	public static final String Q_AvailableDeviceCapacity = "AvailableDeviceCapacity";
	public static final String Q_BatteryLevel = "BatteryLevel";
	public static final String Q_CellularTechnology = "CellularTechnology";
	public static final String Q_IMEI = "IMEI";
	public static final String Q_MEID = "MEID";
	public static final String Q_ModemFirmwareVersion = "ModemFirmwareVersion";
	public static final String Q_IsSupervised = "IsSupervised";
	public static final String Q_IsDeviceLocatorServiceEnabled = "IsDeviceLocatorServiceEnabled";
	public static final String Q_IsActivationLockEnabled = "IsActivationLockEnabled";
	public static final String Q_IsDoNotDisturbInEffect = "IsDoNotDisturbInEffect";
	public static final String Q_EASDeviceIdentifier = "EASDeviceIdentifier";
	public static final String Q_IsCloudBackupEnabled = "IsCloudBackupEnabled";
	public static final String Q_OSUpdateSettings = "OSUpdateSettings";
	public static final String Q_LocalHostName = "LocalHostName";
	public static final String Q_HostName = "HostName";
	public static final String Q_ActiveManagedUsers = "ActiveManagedUsers";
	public static final String Q_IsMDMLostModeEnabled = "IsMDMLostModeEnabled";
	public static final String Q_MaximumResidentUser = "MaximumResidentUser";

	public static final String Q_ICCID = "ICCID";
	public static final String Q_BluetoothMAC = "BluetoothMAC";
	public static final String Q_WiFiMAC = "WiFiMAC";
	public static final String Q_EthernetMACs = "EthernetMACs";
	public static final String Q_CurrentCarrierNetwork = "CurrentCarrierNetwork";
	public static final String Q_SIMCarrierNetwork = "SIMCarrierNetwork";
	public static final String Q_SubscriberCarrierNetwork = "SubscriberCarrierNetwork";
	public static final String Q_CarrierSettingsVersion = "CarrierSettingsVersion";
	public static final String Q_PhoneNumber = "PhoneNumber";
	public static final String Q_VoiceRoamingEnabled = "VoiceRoamingEnabled";
	public static final String Q_DataRoamingEnabled = "DataRoamingEnabled";
	public static final String Q_IsRoaming = "IsRoaming";
	public static final String Q_PersonalHotspotEnabled = "PersonalHotspotEnabled";
	public static final String Q_SubscriberMCC = "SubscriberMCC";
	public static final String Q_SubscriberMNC = "SubscriberMNC";
	public static final String Q_CurrentMCC = "CurrentMCC";
	public static final String Q_CurrentMNC = "CurrentMNC";

	private String[] queries;

	public DeviceInformation(String... queries) {
		super("DeviceInformation");

		this.queries = queries;
	}

	public DeviceInformation() {
		this(new String[] { Q_UDID, Q_Languages, Q_Locales, Q_DeviceID, Q_OrganizationInfo, Q_LastCloudBackupDate,
				Q_AwaitingConfiguration,

				Q_DeviceName, Q_OSVersion, Q_BuildVersion, Q_ModelName, Q_Model, Q_ProductName, Q_SerialNumber,
				Q_DeviceCapacity, Q_AvailableDeviceCapacity, Q_BatteryLevel, Q_CellularTechnology, Q_IMEI, Q_MEID,
				Q_ModemFirmwareVersion, Q_IsSupervised, Q_IsDeviceLocatorServiceEnabled, Q_IsActivationLockEnabled,
				Q_IsDoNotDisturbInEffect, Q_EASDeviceIdentifier, Q_IsCloudBackupEnabled, Q_OSUpdateSettings,
				Q_LocalHostName, Q_HostName, Q_ActiveManagedUsers, Q_IsMDMLostModeEnabled, Q_MaximumResidentUser,

				Q_ICCID, Q_BluetoothMAC, Q_WiFiMAC, Q_EthernetMACs, Q_CurrentCarrierNetwork, Q_SIMCarrierNetwork,
				Q_SubscriberCarrierNetwork, Q_CarrierSettingsVersion, Q_PhoneNumber, Q_VoiceRoamingEnabled,
				Q_DataRoamingEnabled, Q_IsRoaming, Q_PersonalHotspotEnabled, Q_SubscriberMCC, Q_SubscriberMNC,
				Q_CurrentMCC, Q_CurrentMNC });
	}

	public String[] getQueries() {
		return queries;
	}

	public void setQueries(String[] queries) {
		this.queries = queries;
	}

}
