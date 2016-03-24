package com.whenling.extension.mdm.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.extension.mdm.model.Device;
import com.whenling.extension.mdm.service.DeviceService;
import com.whenling.extension.mdm.support.apns.CommandService;
import com.whenling.extension.mdm.support.callback.DeviceInformationCallback;
import com.whenling.extension.mdm.support.payload.Payload;
import com.whenling.extension.mdm.support.payload.PayloadContext;
import com.whenling.extension.mdm.support.payload.command.DeviceInformation;

@Controller
@RequestMapping("/checkin")
public class CheckinController {

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private CommandService commandService;

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Payload put(@RequestBody PayloadContext map) throws IOException {

		String messageType = map.getString("MessageType");
		String udid = map.getString("UDID");
		Device device = deviceService.findByUdid(udid);

		switch (messageType) {
		case "Authenticate":
			if (device == null) {
				device = deviceService.newEntity();
				device.setUdid(udid);
			}

			device.setBuildVersion(map.getString("BuildVersion"));
			device.setImei(map.getString("IMEI"));
			device.setOSVersion(map.getString("OSVersion"));
			device.setProductName(map.getString("ProductName"));
			device.setSerialNumber(map.getString("SerialNumber"));

			deviceService.save(device);

			break;

		case "TokenUpdate":
			device.setPushMagic(map.getString("PushMagic"));
			device.setToken(map.getText("Token").getData().trim());
			device.setUnlockToken(map.getText("UnlockToken").getData());

			deviceService.save(device);

			final Device fDevice = device;
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(10000l);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					commandService.send(new DeviceInformation(), new DeviceInformationCallback(deviceService), fDevice);
				}
			}).start();

			break;

		case "CheckOut":
			deviceService.delete(device);
			break;

		default:
			break;
		}

		return null;
	}
}
