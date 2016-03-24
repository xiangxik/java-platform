package com.whenling.extension.mdm.support.callback;

import com.whenling.extension.mdm.model.Device;
import com.whenling.extension.mdm.service.DeviceService;
import com.whenling.extension.mdm.support.apns.CommandCallback;
import com.whenling.extension.mdm.support.payload.PayloadContext;
import com.whenling.extension.mdm.support.payload.command.DeviceInformation;

public class DeviceInformationCallback implements CommandCallback {

	private DeviceService deviceService;

	public DeviceInformationCallback(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@Override
	public void exe(Device device, String status, PayloadContext map) {

		PayloadContext queryResponses = map.getPayload("QueryResponses");

		Double availableDeviceCapacity = queryResponses.getDouble(DeviceInformation.Q_AvailableDeviceCapacity);
		Double deviceCapacity = queryResponses.getDouble(DeviceInformation.Q_DeviceCapacity);
		String deviceName = queryResponses.getString(DeviceInformation.Q_DeviceName);
		String phoneNumber = queryResponses.getString(DeviceInformation.Q_PhoneNumber);
		String SIMCarrierNetwork = queryResponses.getString(DeviceInformation.Q_SIMCarrierNetwork);

		device.setAvailableDeviceCapacity(availableDeviceCapacity);
		device.setDeviceCapacity(deviceCapacity);
		device.setName(deviceName);
		device.setPhoneNumber(phoneNumber);
		device.setSIMCarrierNetwork(SIMCarrierNetwork);

		deviceService.save(device);
	}

}
