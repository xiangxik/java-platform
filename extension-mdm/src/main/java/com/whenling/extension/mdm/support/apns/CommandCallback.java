package com.whenling.extension.mdm.support.apns;

import com.whenling.extension.mdm.model.Device;
import com.whenling.extension.mdm.support.payload.PayloadContext;

public interface CommandCallback {

	void exe(Device device, String status, PayloadContext map);

}
