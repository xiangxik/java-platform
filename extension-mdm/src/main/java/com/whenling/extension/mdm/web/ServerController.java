package com.whenling.extension.mdm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.extension.mdm.model.Device;
import com.whenling.extension.mdm.service.DeviceService;
import com.whenling.extension.mdm.support.apns.CommandCallback;
import com.whenling.extension.mdm.support.apns.CommandContext;
import com.whenling.extension.mdm.support.apns.CommandService;
import com.whenling.extension.mdm.support.payload.BasePayload;
import com.whenling.extension.mdm.support.payload.Payload;
import com.whenling.extension.mdm.support.payload.PayloadContext;

@Controller
@RequestMapping("/server")
public class ServerController {

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private CommandService commandService;

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Payload put(@RequestBody PayloadContext map) {
		String status = map.getString("Status");
		String udid = map.getString("UDID");
		Device device = deviceService.findByUdid(udid);

		if (device != null) {
			switch (status) {
			case "Idle":

				break;

			case "Acknowledged":
				String commandUUID = map.getString("CommandUUID");
				CommandContext cmdCtx = commandService.get(commandUUID);
				CommandCallback callback = cmdCtx.getCallback();
				if (callback != null) {
					try {
						callback.exe(device, status, map);
					} finally {
						commandService.remove(commandUUID);
					}
				}
				break;

			case "NotNow":

				break;

			case "Error":

				break;

			case "CommandFormatError":

				break;

			default:
				break;
			}
		}

		CommandContext commandContext = commandService.getAndRemoveCommand(device);
		if (commandContext == null) {
			return null;
		}

		return new CommandWrap(commandContext.getPayload(), commandContext.getUuid());
	}

	public static class CommandWrap extends BasePayload {
		private Payload command;
		private String commandUUID;

		public CommandWrap(Payload command, String commandUUID) {
			this.command = command;
			this.commandUUID = commandUUID;
		}

		public Payload getCommand() {
			return command;
		}

		public void setCommand(Payload command) {
			this.command = command;
		}

		public String getCommandUUID() {
			return commandUUID;
		}

		public void setCommandUUID(String commandUUID) {
			this.commandUUID = commandUUID;
		}

	}
}
