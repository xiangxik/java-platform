package com.whenling.extension.mdm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.extension.mdm.model.App;
import com.whenling.extension.mdm.model.Device;
import com.whenling.extension.mdm.service.DeviceService;
import com.whenling.extension.mdm.support.apns.CommandService;
import com.whenling.extension.mdm.support.apns.WakeupService;
import com.whenling.extension.mdm.support.callback.DeviceInformationCallback;
import com.whenling.extension.mdm.support.payload.TextData;
import com.whenling.extension.mdm.support.payload.command.ClearPasscode;
import com.whenling.extension.mdm.support.payload.command.DeviceInformation;
import com.whenling.extension.mdm.support.payload.command.DeviceLocation;
import com.whenling.extension.mdm.support.payload.command.DeviceLock;
import com.whenling.extension.mdm.support.payload.command.DisableLostMode;
import com.whenling.extension.mdm.support.payload.command.EnableLostMode;
import com.whenling.extension.mdm.support.payload.command.EraseDevice;
import com.whenling.extension.mdm.support.payload.command.InstallApplication;
import com.whenling.module.domain.model.Result;
import com.whenling.module.web.controller.BaseController;

@Controller
@RequestMapping("/admin/command")
public class CommandController extends BaseController {

	@Autowired
	private WakeupService wakeupService;

	@Autowired
	private CommandService commandService;

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = "/revive", method = RequestMethod.GET)
	@ResponseBody
	public Result revive(@RequestParam("id") Device device) {
		wakeupService.revive(device);
		return Result.success();
	}

	@RequestMapping(value = "/information", method = RequestMethod.POST)
	@ResponseBody
	public Result information(@RequestParam("id") Device device) {
		commandService.send(new DeviceInformation(), new DeviceInformationCallback(deviceService), device);
		return Result.success();
	}

	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	@ResponseBody
	public Result lock(@RequestParam("id") Device device, String pin, String message, String phoneNumber) {
		DeviceLock deviceLock = new DeviceLock();
		deviceLock.setPIN(pin);
		deviceLock.setMessage(message);
		deviceLock.setPhoneNumber(phoneNumber);
		commandService.send(deviceLock, device);
		return Result.success();
	}

	@RequestMapping(value = "/clearPasscode", method = RequestMethod.POST)
	@ResponseBody
	public Result clearPasscode(@RequestParam("id") Device device) {
		commandService.send(new ClearPasscode(new TextData(device.getUnlockToken())), device);
		return Result.success();
	}

	@RequestMapping(value = "/erase", method = RequestMethod.POST)
	@ResponseBody
	public Result erase(@RequestParam("id") Device device) {
		commandService.send(new EraseDevice(), device);
		return Result.success();
	}

	@RequestMapping(value = "/installAppForDevices", method = RequestMethod.POST)
	@ResponseBody
	public Result installAppForDevices(@RequestParam("id") App app, @RequestParam("devices") Device[] devices) {
		InstallApplication installApplication = new InstallApplication();
		installApplication.setiTunesStoreID(app.getTrackId());
		commandService.send(installApplication, devices);
		return Result.success();
	}

	@RequestMapping(value = "/enableLostMode", method = RequestMethod.POST)
	@ResponseBody
	public Result enableLostMode(@RequestParam("id") Device device) {
		commandService.send(new EnableLostMode(), device);
		return Result.success();
	}

	@RequestMapping(value = "/disableLostMode", method = RequestMethod.POST)
	@ResponseBody
	public Result disableLostMode(@RequestParam("id") Device device) {
		commandService.send(new DisableLostMode(), device);
		return Result.success();
	}

	@RequestMapping(value = "/location", method = RequestMethod.POST)
	@ResponseBody
	public Result deviceLocation(@RequestParam("id") Device device) {
		commandService.send(new DeviceLocation(), (dev, status, map) -> {

		} , device);
		return Result.success();
	}
}
